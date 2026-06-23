package cibertec.pe.soap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.springframework.stereotype.Component;

@Component
public class SunatSoapClient {

    private static final String RUC_MODULO = "10730357431MODDATOS"; 
    private static final String PASSWORD_MODULO = "MODDATOS";
    private static final String ENDPOINT_SUNAT = "https://e-beta.sunat.gob.pe/ol-ti-itcpfegem-beta/billService";
    
    public String enviar(File zip) throws Exception {
    	
    	System.out.println("[DEBUG] fileName enviado a SUNAT: " + zip.getName());
        
        // 1. Convertir el ZIP local a Base64 real de forma segura
        String base64Enc;
        try (FileInputStream fis = new FileInputStream(zip)) {
            byte[] bytes = fis.readAllBytes();
            base64Enc = Base64.getEncoder().encodeToString(bytes);
        }

        // 2. Construir el sobre SOAP corregido (Parámetros SIN el prefijo ser:)
        String soapEnvelope = 
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.sunat.gob.pe\">" +
            "   <soapenv:Header>" +
            "      <wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">" +
            "         <wsse:UsernameToken>" +
            "            <wsse:Username>" + RUC_MODULO + "</wsse:Username>" +
            "            <wsse:Password>" + PASSWORD_MODULO + "</wsse:Password>" +
            "         </wsse:UsernameToken>" +
            "      </wsse:Security>" +
            "   </soapenv:Header>" +
            "   <soapenv:Body>" +
            "      <ser:sendBill>" +
            "         <fileName>" + zip.getName() + "</fileName>" +
            "         <contentFile>" + base64Enc + "</contentFile>" +
            "      </ser:sendBill>" +
            "   </soapenv:Body>" +
            "</soapenv:Envelope>";
        
        String xmlRespuesta;
        
        try {
            // 3. Intento de envío HTTP real a la red de SUNAT
            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ENDPOINT_SUNAT))
                    .header("Content-Type", "text/xml;charset=UTF-8")
                    .header("SOAPAction", "urn:sendBill")
                    .POST(HttpRequest.BodyPublishers.ofString(soapEnvelope))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            xmlRespuesta = response.body();
            
            System.out.println("\n===== [RESPUESTA CRUDA DE SUNAT] =====\n" + xmlRespuesta + "\n====================================\n");
            
            if (xmlRespuesta == null || xmlRespuesta.isBlank()) {
                return "Error crítico: El servidor SUNAT respondió código HTTP " + response.statusCode() + " sin contenido.";
            }
            
        } catch (Exception e) {
            System.err.println("[ERROR RED] No se pudo conectar al servidor de SUNAT: " + e.getMessage());
            return "Error de conexión de red con SUNAT: " + e.getMessage();
        }

        // 4. Parsear el XML resultante
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xmlRespuesta)));

            // Caso A: SUNAT procesó y nos devolvió un CDR exitoso
            NodeList responseNode = doc.getElementsByTagNameNS("*", "applicationResponse");
            if (responseNode.getLength() == 0) {
                responseNode = doc.getElementsByTagName("applicationResponse");
            }

            if (responseNode.getLength() > 0) {
                String cdrBase64 = responseNode.item(0).getTextContent();
                byte[] cdrZipBytes = Base64.getDecoder().decode(cdrBase64.trim());
                
                String rutaCdr = zip.getParent() + File.separator + "R-" + zip.getName();
                File archivoCdr = new File(rutaCdr);
                
                try (FileOutputStream fos = new FileOutputStream(archivoCdr)) {
                    fos.write(cdrZipBytes);
                }
                return "Factura ACEPTADA por SUNAT. CDR guardado en: " + archivoCdr.getAbsolutePath();
            }

            // Caso B: Mensaje de rechazo estructurado (Fault)
            NodeList faultNode = doc.getElementsByTagNameNS("*", "faultstring");
            if (faultNode.getLength() == 0) {
                faultNode = doc.getElementsByTagName("faultstring");
            }
            
            if (faultNode.getLength() > 0) {
                return "RECHAZO DE SUNAT (SOAP FAULT): " + faultNode.item(0).getTextContent();
            }

            return "SUNAT respondió, pero el mensaje no contiene un formato válido.";

        } catch (Exception e) {
            return "Error al parsear la respuesta XML de SUNAT: " + e.getMessage();
        }
    }
}