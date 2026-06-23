package cibertec.pe.xml;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.springframework.stereotype.Component;
import cibertec.pe.dto.FacturaDTO;
import cibertec.pe.dto.DetalleComprobanteDTO;

@Component
public class XmlFacturaGenerator {

    public File generarXml(FacturaDTO factura) throws Exception {

        String ruc = "10730357431";
        // Detectamos dinámicamente si es Factura (01) o Boleta (03)
        String tipoDocSunat = "FACTURA".equalsIgnoreCase(factura.getTipoComprobante()) ? "01" : "03";
        String serieYCorrelativo = factura.getNroSerie() + "-" + String.format("%08d", factura.getCorrelativo());
        
        String nombreArchivo = ruc + "-" + tipoDocSunat + "-" + serieYCorrelativo + ".xml";
        File archivo = new File(nombreArchivo);

        double total = factura.getTotal();
        double gravada = factura.getSubTotal();
        double igv = factura.getIgv();

        StringBuilder xml = new StringBuilder();
        
        // 1. Cabecera del XML. El <ext:ExtensionContent/> va VACÍO: el XmlSigner
        //    inserta ahí la firma digital real. NO incrustar una firma placeholder,
        //    SUNAT la detecta como documento alterado ("Incorrect reference digest value").
        xml.append(String.format(Locale.US, """
<?xml version="1.0" encoding="UTF-8"?>
<Invoice xmlns="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"
         xmlns:cac="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"
         xmlns:cbc="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"
         xmlns:ds="http://www.w3.org/2000/09/xmldsig#"
         xmlns:ext="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2">

    <ext:UBLExtensions>
        <ext:UBLExtension>
            <ext:ExtensionContent/>
        </ext:UBLExtension>
    </ext:UBLExtensions>

    <cbc:UBLVersionID>2.1</cbc:UBLVersionID>
    <cbc:CustomizationID>2.0</cbc:CustomizationID>
    <cbc:ID>%s</cbc:ID>
    <cbc:IssueDate>%s</cbc:IssueDate>
    <cbc:InvoiceTypeCode listID="0101"
            listAgencyName="PE:SUNAT"
            listName="Tipo de Operacion"
            listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo51">%s</cbc:InvoiceTypeCode>
    <cbc:DocumentCurrencyCode>PEN</cbc:DocumentCurrencyCode>

    <cac:AccountingSupplierParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="6">%s</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName>LLAMA PE S.A.C.</cbc:RegistrationName>
                <cac:RegistrationAddress>
                    <cbc:AddressTypeCode>0000</cbc:AddressTypeCode>
                </cac:RegistrationAddress>
            </cac:PartyLegalEntity>
        </cac:Party>
    </cac:AccountingSupplierParty>
""", serieYCorrelativo, factura.getFechaEmision(), tipoDocSunat, ruc));

        // 2. Datos del Cliente (Receptor)
        String schemeIdCliente = factura.getClienteDocumento().length() == 8 ? "1" : "6";
        xml.append(String.format(Locale.US, """
    <cac:AccountingCustomerParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="%s">%s</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName><![CDATA[%s]]></cbc:RegistrationName>
            </cac:PartyLegalEntity>
        </cac:Party>
    </cac:AccountingCustomerParty>
""", schemeIdCliente, factura.getClienteDocumento(), factura.getClienteNombre()));

        // 3. Términos de Pago
        xml.append("""
    <cac:PaymentTerms>
        <cbc:ID>FormaPago</cbc:ID>
        <cbc:PaymentMeansID>Contado</cbc:PaymentMeansID>
    </cac:PaymentTerms>
""");

        // 4. Totales de Impuestos globales (IGV)
        xml.append(String.format(Locale.US, """
    <cac:TaxTotal>
        <cbc:TaxAmount currencyID="PEN">%.2f</cbc:TaxAmount>
        <cac:TaxSubtotal>
            <cbc:TaxableAmount currencyID="PEN">%.2f</cbc:TaxableAmount>
            <cbc:TaxAmount currencyID="PEN">%.2f</cbc:TaxAmount>
            <cac:TaxCategory>
                <cac:TaxScheme>
                    <cbc:ID>1000</cbc:ID>
                    <cbc:Name>IGV</cbc:Name>
                    <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
                </cac:TaxScheme>
            </cac:TaxCategory>
        </cac:TaxSubtotal>
    </cac:TaxTotal>
""", igv, gravada, igv));

        // 5. Totales a pagar comerciales
        xml.append(String.format(Locale.US, """
    <cac:LegalMonetaryTotal>
        <cbc:LineExtensionAmount currencyID="PEN">%.2f</cbc:LineExtensionAmount>
        <cbc:TaxInclusiveAmount currencyID="PEN">%.2f</cbc:TaxInclusiveAmount>
        <cbc:PayableAmount currencyID="PEN">%.2f</cbc:PayableAmount>
    </cac:LegalMonetaryTotal>
""", gravada, total, total));

        int itemIndex = 1;

        // 6. Línea opcional: Mano de Obra (Si el costo es mayor a cero)
        if (factura.getCostoManoObra() > 0) {
            double manoObraNeto = factura.getCostoManoObra() / 1.18;
            double manoObraIgv = factura.getCostoManoObra() - manoObraNeto;

            xml.append(String.format(Locale.US, """
    <cac:InvoiceLine>
        <cbc:ID>%d</cbc:ID>
        <cbc:InvoicedQuantity unitCode="NIU">1</cbc:InvoicedQuantity>
        <cbc:LineExtensionAmount currencyID="PEN">%.2f</cbc:LineExtensionAmount>
        <cac:PricingReference>
            <cac:AlternativeConditionPrice>
                <cbc:PriceAmount currencyID="PEN">%.2f</cbc:PriceAmount>
                <cbc:PriceTypeCode>01</cbc:PriceTypeCode>
            </cac:AlternativeConditionPrice>
        </cac:PricingReference>
        <cac:TaxTotal>
            <cbc:TaxAmount currencyID="PEN">%.2f</cbc:TaxAmount>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="PEN">%.2f</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="PEN">%.2f</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cbc:Percent>18.00</cbc:Percent>
                    <cbc:TaxExemptionReasonCode>10</cbc:TaxExemptionReasonCode>
                    <cac:TaxScheme><cbc:ID>1000</cbc:ID><cbc:Name>IGV</cbc:Name><cbc:TaxTypeCode>VAT</cbc:TaxTypeCode></cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
        </cac:TaxTotal>
        <cac:Item><cbc:Description><![CDATA[SERVICIO DE MANO DE OBRA MANTENIMIENTO]]></cbc:Description></cac:Item>
        <cac:Price><cbc:PriceAmount currencyID="PEN">%.2f</cbc:PriceAmount></cac:Price>
    </cac:InvoiceLine>
""", itemIndex++, manoObraNeto, factura.getCostoManoObra(), manoObraIgv, manoObraNeto, manoObraIgv, manoObraNeto));
        }

        // 7. Líneas dinámicas: Detalle de repuestos asociados
        if (factura.getDetalles() != null) {
            for (DetalleComprobanteDTO item : factura.getDetalles()) {
                double totalItem = item.getPrecioUnitario() * item.getCantidad();
                double valorItemNeto = totalItem / 1.18;
                double igvItem = totalItem - valorItemNeto;
                double precioUnitarioNeto = item.getPrecioUnitario() / 1.18;

                xml.append(String.format(Locale.US, """
    <cac:InvoiceLine>
        <cbc:ID>%d</cbc:ID>
        <cbc:InvoicedQuantity unitCode="NIU">%d</cbc:InvoicedQuantity>
        <cbc:LineExtensionAmount currencyID="PEN">%.2f</cbc:LineExtensionAmount>
        <cac:PricingReference>
            <cac:AlternativeConditionPrice>
                <cbc:PriceAmount currencyID="PEN">%.2f</cbc:PriceAmount>
                <cbc:PriceTypeCode>01</cbc:PriceTypeCode>
            </cac:AlternativeConditionPrice>
        </cac:PricingReference>
        <cac:TaxTotal>
            <cbc:TaxAmount currencyID="PEN">%.2f</cbc:TaxAmount>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="PEN">%.2f</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="PEN">%.2f</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cbc:Percent>18.00</cbc:Percent>
                    <cbc:TaxExemptionReasonCode>10</cbc:TaxExemptionReasonCode>
                    <cac:TaxScheme><cbc:ID>1000</cbc:ID><cbc:Name>IGV</cbc:Name><cbc:TaxTypeCode>VAT</cbc:TaxTypeCode></cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
        </cac:TaxTotal>
        <cac:Item><cbc:Description><![CDATA[%s]]></cbc:Description></cac:Item>
        <cac:Price><cbc:PriceAmount currencyID="PEN">%.2f</cbc:PriceAmount></cac:Price>
    </cac:InvoiceLine>
""", itemIndex++, item.getCantidad(), valorItemNeto, item.getPrecioUnitario(), igvItem, valorItemNeto, igvItem, item.getNom_Repuesto(), precioUnitarioNeto));
            }
        }

        xml.append("\n</Invoice>");

        // Escribimos el archivo final con codificación UTF-8 pura
        try (FileWriter writer = new FileWriter(archivo, StandardCharsets.UTF_8)) {
            writer.write(xml.toString());
        }

        return archivo;
    }
}