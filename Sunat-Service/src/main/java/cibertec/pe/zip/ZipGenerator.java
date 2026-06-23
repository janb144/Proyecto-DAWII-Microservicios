package cibertec.pe.zip;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.stereotype.Component;

@Component
public class ZipGenerator {

    public File generarZip(File archivoXml) throws Exception {
        String nombreZip = archivoXml.getAbsolutePath().replace(".xml", ".zip");
        File archivoZip = new File(nombreZip);

        // try-with-resources asegura el cierre hermético de flujos para no corromper el ZIP
        try (FileOutputStream fos = new FileOutputStream(archivoZip);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(archivoXml)) {

            ZipEntry zipEntry = new ZipEntry(archivoXml.getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int longitud;
            while ((longitud = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, longitud);
            }
            zos.closeEntry();
        }

        return archivoZip;
    }
}