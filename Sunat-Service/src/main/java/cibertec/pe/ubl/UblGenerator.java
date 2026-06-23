package cibertec.pe.ubl;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.stereotype.Component;
import cibertec.pe.dto.FacturaDTO;
import cibertec.pe.dto.DetalleComprobanteDTO;

@Component
public class UblGenerator {

    public File generarXML(FacturaDTO factura) throws Exception {

        String ruc = "10730357431";
        String tipoDocSunat = "FACTURA".equalsIgnoreCase(factura.getTipoComprobante()) ? "01" : "03";
        String serieYCorrelativo = factura.getNroSerie() + "-" + String.format("%08d", factura.getCorrelativo());
        
        String nombreArchivo = ruc + "-" + tipoDocSunat + "-" + serieYCorrelativo + ".xml";
        File archivo = new File(nombreArchivo);

        String fechaEmision = factura.getFechaEmision().toString();
        String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String schemeIdCliente = factura.getClienteDocumento().length() == 8 ? "1" : "6";

        StringBuilder xml = new StringBuilder();
        xml.append(String.format(Locale.US, """
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
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
    <cbc:IssueTime>%s</cbc:IssueTime>
    <cbc:InvoiceTypeCode listID="0101">%s</cbc:InvoiceTypeCode>
    <cbc:Note languageLocaleID="1000"><![CDATA[SON SOLES]]></cbc:Note>
    <cbc:DocumentCurrencyCode>PEN</cbc:DocumentCurrencyCode>
""", serieYCorrelativo, fechaEmision, horaActual, tipoDocSunat));

        xml.append(String.format(Locale.US, """
    <cac:Signature>
        <cbc:ID>%s</cbc:ID>
        <cac:SignatoryParty>
            <cac:PartyIdentification>
                <cbc:ID>%s</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyName>
                <cbc:Name><![CDATA[LLAMA PE S.A.C.]]></cbc:Name>
            </cac:PartyName>
        </cac:SignatoryParty>
        <cac:DigitalSignatureAttachment>
            <cac:ExternalReference>
                <cbc:URI>#SignSUNAT</cbc:URI>
            </cac:ExternalReference>
        </cac:DigitalSignatureAttachment>
    </cac:Signature>
""", serieYCorrelativo, ruc));

        xml.append(String.format(Locale.US, """
    <cac:AccountingSupplierParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="6">%s</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName><![CDATA[LLAMA PE S.A.C.]]></cbc:RegistrationName>
                <cac:RegistrationAddress>
                    <cbc:AddressTypeCode>0000</cbc:AddressTypeCode>
                </cac:RegistrationAddress>
            </cac:PartyLegalEntity>
        </cac:Party>
    </cac:AccountingSupplierParty>
""", ruc));

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

        xml.append("""
    <cac:PaymentTerms>
        <cbc:ID>FormaPago</cbc:ID>
        <cbc:PaymentMeansID>Contado</cbc:PaymentMeansID>
    </cac:PaymentTerms>
""");

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
""", factura.getIgv(), factura.getSubTotal(), factura.getIgv()));

        xml.append(String.format(Locale.US, """
    <cac:LegalMonetaryTotal>
        <cbc:LineExtensionAmount currencyID="PEN">%.2f</cbc:LineExtensionAmount>
        <cbc:TaxInclusiveAmount currencyID="PEN">%.2f</cbc:TaxInclusiveAmount>
        <cbc:PayableAmount currencyID="PEN">%.2f</cbc:PayableAmount>
    </cac:LegalMonetaryTotal>
""", factura.getSubTotal(), factura.getTotal(), factura.getTotal()));

        int itemIndex = 1;

        // ÍTEM 1: Mano de Obra (Si tiene costo asignado)
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

        // ÍTEMS DINÁMICOS: Los repuestos de la lista
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

        try (FileWriter writer = new FileWriter(archivo, StandardCharsets.UTF_8)) {
            writer.write(xml.toString());
        }

        return archivo;
    }
}