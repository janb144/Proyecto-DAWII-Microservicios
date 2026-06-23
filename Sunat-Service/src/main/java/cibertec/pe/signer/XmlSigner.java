package cibertec.pe.signer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collections;

import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.springframework.stereotype.Component;

@Component
public class XmlSigner {

    private static final String PASSWORD_PFX = "Jolis_1205";

    public File firmar(File xml) {
        try {
            // 1. Inicializar fábrica de firmas XML
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");

            // 2. Cargar certificado .pfx desde resources
            KeyStore ks = KeyStore.getInstance("PKCS12");
            try (InputStream is = getClass().getClassLoader()
                    .getResourceAsStream("LLAMA-PE-CERTIFICADO-DEMO-10730357431.pfx")) {
                if (is == null) throw new RuntimeException("Certificado .pfx no encontrado en resources");
                ks.load(is, PASSWORD_PFX.toCharArray());
            }

            // 3. Extraer clave privada y certificado
            String alias = ks.aliases().nextElement();
            PrivateKey privateKey = (PrivateKey) ks.getKey(alias, PASSWORD_PFX.toCharArray());
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);

            // 4. Parsear el XML con soporte de namespaces
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            Document doc = dbf.newDocumentBuilder().parse(xml);

            // 5. ✅ Buscar <ext:ExtensionContent/> vacío donde irá la firma
            NodeList extList = doc.getElementsByTagNameNS(
                "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2",
                "ExtensionContent"
            );

            if (extList.getLength() == 0) {
                throw new RuntimeException(
                    "No se encontró <ext:ExtensionContent> — " +
                    "verifica que el XML tenga <ext:UBLExtensions> con <ext:ExtensionContent/> vacío"
                );
            }

            Node extensionContent = extList.item(0);

            // 6. ✅ La firma se inserta DENTRO de ExtensionContent
            DOMSignContext signContext = new DOMSignContext(privateKey, extensionContent);

            // 7. Configurar algoritmos SHA1/RSA-SHA1 (requeridos por SUNAT Beta)
            Reference ref = fac.newReference(
                "",
                fac.newDigestMethod(DigestMethod.SHA1, null),
                Collections.singletonList(
                    fac.newTransform(
                        "http://www.w3.org/2000/09/xmldsig#enveloped-signature",
                        (TransformParameterSpec) null
                    )
                ),
                null, null
            );

            SignedInfo si = fac.newSignedInfo(
                fac.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
                fac.newSignatureMethod(SignatureMethod.RSA_SHA1, null),
                Collections.singletonList(ref)
            );

            // 8. KeyInfo con el certificado real del .pfx
            KeyInfoFactory kif = fac.getKeyInfoFactory();
            X509Data x509Data = kif.newX509Data(Collections.singletonList(cert));
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(x509Data));

            // 9. Firmar
            fac.newXMLSignature(si, ki).sign(signContext);

            // 10. Guardar el XML firmado
            Transformer trans = TransformerFactory.newInstance().newTransformer();
            try (FileOutputStream fos = new FileOutputStream(xml)) {
                trans.transform(new DOMSource(doc), new StreamResult(fos));
            }

            System.out.println("[FIRMA OK] XML firmado correctamente.");
            return xml;

        } catch (Exception e) {
            System.err.println("[ERROR EN FIRMA] " + e.getMessage());
            e.printStackTrace();
            return xml;
        }
    }
}