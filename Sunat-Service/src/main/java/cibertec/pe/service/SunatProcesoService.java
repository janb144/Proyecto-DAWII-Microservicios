package cibertec.pe.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cibertec.pe.dto.FacturaDTO;
import cibertec.pe.signer.XmlSigner;
import cibertec.pe.soap.SunatSoapClient;
import cibertec.pe.xml.XmlFacturaGenerator; // Cambiado al nuevo paquete y generador
import cibertec.pe.zip.ZipGenerator;

@Service
public class SunatProcesoService {

    @Autowired
    private XmlFacturaGenerator xmlGenerator; // Inyectamos el generador dinámico corregido

    @Autowired
    private XmlSigner signer;

    @Autowired
    private ZipGenerator zipGenerator;

    @Autowired
    private SunatSoapClient soap;

    public String procesar(FacturaDTO factura) throws Exception {

        // 1. Generar XML dinámico (Boleta o Factura con su lista de repuestos)
        File xml = xmlGenerator.generarXml(factura);

        // Bloque opcional de Debug para revisar la estructura limpia antes de SUNAT
        File debugAntes = new File("debug_ANTES_firma.xml");
        Files.copy(xml.toPath(), debugAntes.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // 2. Firmar digitalmente con el certificado X.509
        File firmado = signer.firmar(xml);

        File debugDespues = new File("debug_DESPUES_firma.xml");
        Files.copy(firmado.toPath(), debugDespues.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // 3. Comprimir a formato .ZIP (Usa el ZipGenerator agnóstico que mantuvimos igual)
        File zip = zipGenerator.generarZip(firmado);
        
        // 4. Enviar el ZIP empaquetado al webservice SOAP de la SUNAT
        return soap.enviar(zip);
    }
}