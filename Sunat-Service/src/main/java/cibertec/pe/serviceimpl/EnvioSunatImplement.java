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
import cibertec.pe.soap.SunatSoapClient;
import cibertec.pe.xml.XmlFacturaGenerator;
import cibertec.pe.zip.ZipGenerator;

@Service

public class EnvioSunatImplement implements IEnvioSunatService{
        
	@Autowired
	private FacturaFeignClient facturaFeign;
	@Autowired
	private XmlFacturaGenerator xmlGenerator;
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

        if(envio.getEstado()==null)
            envio.setEstado("PENDIENTE");

        return repository.save(envio);
    }

    @Override
    public Optional<EnvioSunat> buscar(Integer id) {
        return repository.findById(id);
    }

    @Override
    public EnvioSunat actualizar(Integer id, EnvioSunat envio) {

        EnvioSunat registro = repository.findById(id).orElse(null);

        if(registro!=null){

            registro.setEstado(envio.getEstado());
            registro.setCodigoRespuesta(envio.getCodigoRespuesta());
            registro.setDescripcionRespuesta(envio.getDescripcionRespuesta());

            return repository.save(registro);
        }

        return null;
    }

    @Override
    public void eliminar(Integer id) {

        repository.deleteById(id);

    }
    @Override
    public void generarXmlFactura(Integer idFactura) throws Exception {
        
        // 1. Crear el registro inicial en tu base de datos como "PROCESANDO"
        EnvioSunat envio = new EnvioSunat();
        envio.setIdFactura(idFactura); // <-- DESCOMENTADO: Vincula el ID de factura obligatoriamente
        envio.setEstado("PROCESANDO");
        envio.setFechaEnvio(LocalDateTime.now());
        envio = repository.save(envio);

        try {
            // 2. Consumir el microservicio REST externo mediante Feign
            FacturaDTO factura = facturaFeign.buscarFactura(idFactura);
            if (factura == null) {
                throw new RuntimeException("No se encontró la factura en el microservicio externo con ID: " + idFactura);
            }

            // CORRECCIÓN: Seteamos los datos del comprobante en la entidad para guardarlos en tbl_envio_sunat
            envio.setTipoDocumento("01"); // 01 = Factura
            envio.setSerie("F001");
            envio.setNumero(String.valueOf(factura.getCod_Factura()));

            // 3. Generar el archivo XML plano (UBL 2.1)
            File xml = xmlGenerator.generarXml(factura);

            // 4. Comprimir el archivo XML a formato ZIP con el nuevo compresor seguro
            File zip = zipGenerator.generarZip(xml);

            // 5. Enviar el ZIP a SUNAT SOAP
            String respuestaSunat = soapClient.enviar(zip);

            // 6. Analizar la respuesta del cliente SOAP
            if (respuestaSunat.contains("Error de SUNAT SOAP") || respuestaSunat.contains("soapenv:Envelope")) {
                envio.setEstado("RECHAZADO");
                envio.setCodigoRespuesta("500"); 
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
            // Aseguramos guardar los resultados finales pase lo que pase con todos sus datos completos
            repository.save(envio);
        }
    
    }

}