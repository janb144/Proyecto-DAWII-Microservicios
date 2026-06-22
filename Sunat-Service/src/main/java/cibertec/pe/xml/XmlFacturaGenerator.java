package cibertec.pe.xml;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;
import cibertec.pe.dto.FacturaDTO;

@Component
public class XmlFacturaGenerator {

    public File generarXml(FacturaDTO factura) throws Exception {

        String ruc = "10730357431";
        String nombreArchivo = ruc + "-01-F001-" + factura.getCod_Factura() + ".xml";
        File archivo = new File(nombreArchivo);

        double total = factura.getTotal();
        double gravada = total / 1.18;
        double igv = total - gravada;

        try (FileWriter writer = new FileWriter(archivo, StandardCharsets.UTF_8)) {
            writer.write("""
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
    <cbc:ID>F001-%d</cbc:ID>
    <cbc:IssueDate>%s</cbc:IssueDate>
    <cbc:InvoiceTypeCode>01</cbc:InvoiceTypeCode>
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

    <cac:AccountingCustomerParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="6">%s</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName>%s</cbc:RegistrationName>
            </cac:PartyLegalEntity>
        </cac:Party>
    </cac:AccountingCustomerParty>

    <cac:PaymentTerms>
        <cbc:ID>FormaPago</cbc:ID>
        <cbc:PaymentMeansID>Contado</cbc:PaymentMeansID>
    </cac:PaymentTerms>

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

    <cac:LegalMonetaryTotal>
        <cbc:LineExtensionAmount currencyID="PEN">%.2f</cbc:LineExtensionAmount>
        <cbc:TaxInclusiveAmount currencyID="PEN">%.2f</cbc:TaxInclusiveAmount>
        <cbc:PayableAmount currencyID="PEN">%.2f</cbc:PayableAmount>
    </cac:LegalMonetaryTotal>

    <cac:InvoiceLine>
        <cbc:ID>1</cbc:ID>
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
                    <cac:TaxScheme>
                        <cbc:ID>1000</cbc:ID>
                        <cbc:Name>IGV</cbc:Name>
                        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
                    </cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
        </cac:TaxTotal>
        
        <cac:Item>
            <cbc:Description>POR CONSUMO DE SERVICIOS</cbc:Description>
        </cac:Item>
        <cac:Price>
            <cbc:PriceAmount currencyID="PEN">%.2f</cbc:PriceAmount>
        </cac:Price>
    </cac:InvoiceLine>

</Invoice>
"""
.formatted(
        factura.getCod_Factura(),
        factura.getFechaEmision(),
        ruc,
        factura.getClienteDocumento(),
        factura.getClienteNombre(),
        igv,
        gravada,
        igv,
        gravada,
        total,
        total,
        gravada,
        total,
        igv,
        gravada,
        igv,
        gravada
));
        }

        return archivo;
    }
}