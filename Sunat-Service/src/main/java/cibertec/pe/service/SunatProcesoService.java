package cibertec.pe.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cibertec.pe.dto.FacturaDTO;
import cibertec.pe.signer.XmlSigner;
import cibertec.pe.soap.SunatSoapClient;
import cibertec.pe.ubl.UblGenerator;
import cibertec.pe.zip.ZipGenerator;

@Service
public class SunatProcesoService {

    @Autowired
    private UblGenerator xmlGenerator;

    @Autowired
    private XmlSigner signer;

    @Autowired
    private ZipGenerator zipGenerator;

    @Autowired
    private SunatSoapClient soap;

    public String procesar(FacturaDTO factura) throws Exception {

        // 1. Generar XML
        File xml = xmlGenerator.generarXML(factura);

        // ✅ DEBUG: guardar copia ANTES de firmar
        File debugAntes = new File("debug_ANTES_firma.xml");
        Files.copy(xml.toPath(), debugAntes.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("[DEBUG] XML antes de firmar guardado en: " + debugAntes.getAbsolutePath());

        // 2. Firmar
        File firmado = signer.firmar(xml);

        // ✅ DEBUG: guardar copia DESPUÉS de firmar
        File debugDespues = new File("debug_DESPUES_firma.xml");
        Files.copy(firmado.toPath(), debugDespues.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("[DEBUG] XML después de firmar guardado en: " + debugDespues.getAbsolutePath());

        // 3. Comprimir y enviar
        File zip = zipGenerator.generarZip(firmado);
        
	     // ✅ DEBUG temporal
	     System.out.println("[DEBUG] Nombre ZIP: " + zip.getName());
	     System.out.println("[DEBUG] Nombre XML dentro: " + firmado.getName());

        return soap.enviar(zip);
    }
}