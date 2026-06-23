package cibertec.pe.serviceimpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cibertec.pe.dto.FacturaDTO;
import cibertec.pe.entity.EnvioSunat;
import cibertec.pe.feign.FacturaFeignClient;
import cibertec.pe.repository.IEnvioSunatRepository;
import cibertec.pe.service.IEnvioSunatService;
import cibertec.pe.signer.XmlSigner;
import cibertec.pe.soap.SunatSoapClient;
import cibertec.pe.xml.XmlFacturaGenerator;
import cibertec.pe.zip.ZipGenerator;

@Service
public class EnvioSunatImplement implements IEnvioSunatService {
        
    @Autowired
    private FacturaFeignClient facturaFeign;
    
    @Autowired
    private XmlFacturaGenerator xmlGenerator;

    @Autowired
    private XmlSigner xmlSigner;

    @Autowired
    private IEnvioSunatRepository repository;

    @Autowired
    private ZipGenerator zipGenerator;

    @Autowired
    private SunatSoapClient soapClient;

    @Override 
    public List<EnvioSunat> listar() { 
        return repository.findAll(); 
    }
    
    @Override 
    public EnvioSunat guardar(EnvioSunat envio) { 
        envio.setFechaEnvio(LocalDateTime.now()); 
        if(envio.getEstado() == null) envio.setEstado("PENDIENTE"); 
        return repository.save(envio); 
    }
    
    @Override 
    public Optional<EnvioSunat> buscar(Integer id) { 
        return repository.findById(id); 
    }
    
    @Override 
    public void eliminar(Integer id) { 
        repository.deleteById(id); 
    }
    
    @Override 
    public EnvioSunat actualizar(Integer id, EnvioSunat envio) { 
        EnvioSunat r = repository.findById(id).orElse(null); 
        if(r != null){ 
            r.setEstado(envio.getEstado()); 
            r.setCodigoRespuesta(envio.getCodigoRespuesta()); 
            r.setDescripcionRespuesta(envio.getDescripcionRespuesta()); 
            return repository.save(r); 
        } 
        return null; 
    }

    @Override
    public void generarXmlFactura(Integer idFactura) throws Exception {
        
        EnvioSunat envio = new EnvioSunat();
        envio.setIdFactura(idFactura);
        envio.setEstado("PROCESANDO");
        envio.setFechaEnvio(LocalDateTime.now());
        envio = repository.save(envio);

        try {
            // 1. Obtener la información completa desde Facturacion-Service
            FacturaDTO factura = facturaFeign.obtenerDatosParaSunat(idFactura);
            if (factura == null) {
                throw new RuntimeException("No se encontraron datos para el comprobante con ID: " + idFactura);
            }

            // Mapear dinámicamente según las reglas de SUNAT
            // 01 = Factura, 03 = Boleta (según el tipo de comprobante emitido)
            String tipoDocSunat = "FACTURA".equalsIgnoreCase(factura.getTipoComprobante()) ? "01" : "03";
            envio.setTipoDocumento(tipoDocSunat);
            envio.setSerie(factura.getNroSerie());
            envio.setNumero(String.valueOf(factura.getCorrelativo()));

            // 2. Generar el XML dinámico (con <ext:ExtensionContent/> vacío)
            File xml = xmlGenerator.generarXml(factura);
            java.nio.file.Files.copy(xml.toPath(),
                    new File("debug_ANTES_firma.xml").toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // 3. Firmar digitalmente con el certificado X.509 (PASO QUE FALTABA)
            File firmado = xmlSigner.firmar(xml);
            java.nio.file.Files.copy(firmado.toPath(),
                    new File("debug_DESPUES_firma.xml").toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);

            // 4. Comprimir a ZIP el XML ya firmado
            File zip = zipGenerator.generarZip(firmado);

            // 5. Enviar al WebService SOAP de SUNAT
            String respuestaSunat = soapClient.enviar(zip);

            // 5. Analizar de forma exhaustiva la respuesta de la SUNAT (Case-Insensitive)
            String respuestaUpper = respuestaSunat != null ? respuestaSunat.toUpperCase() : "";

            if (respuestaUpper.contains("FAULT") ||
                respuestaUpper.contains("RECHAZO") ||
                respuestaUpper.contains("SOAP-ENV:ENVELOPE") ||
                respuestaUpper.contains("ALTERADO")) {

                envio.setEstado("RECHAZADO");
                envio.setCodigoRespuesta("2335"); // Código tributario estándar para firmas/alteración
                envio.setDescripcionRespuesta(respuestaSunat);
            } else {
                envio.setEstado("ACEPTADO");
                envio.setCodigoRespuesta("0");
                envio.setDescripcionRespuesta(respuestaSunat);
            }

        } catch (Exception e) {
            envio.setEstado("ERROR_INTERNO");
            envio.setCodigoRespuesta("500");
            envio.setDescripcionRespuesta("Excepción en el flujo: " + e.getMessage());
            throw e;

        } finally {
            // Guarda o actualiza de forma segura el estado final en tu base de datos de Aiven Cloud
            repository.save(envio);
        }
    }
}