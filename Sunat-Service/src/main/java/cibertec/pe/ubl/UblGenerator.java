package cibertec.pe.ubl;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.stereotype.Component;
import cibertec.pe.dto.FacturaDTO;

@Component
public class UblGenerator {

    public File generarXML(FacturaDTO factura) throws Exception {

        String ruc = "10730357431";
        String nombreArchivo = ruc + "-01-F001-" + String.format("%08d", factura.getCod_Factura()) + ".xml";
        File archivo = new File(nombreArchivo);

        String fechaEmision = factura.getFechaEmision().toString();
        String horaActual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String schemeIdCliente = factura.getClienteDocumento().length() == 8 ? "1" : "6";

        try (FileWriter writer = new FileWriter(archivo, StandardCharsets.UTF_8)) {
            writer.write(String.format(Locale.US, """
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
    <cbc:ID>F001-%08d</cbc:ID>
    <cbc:IssueDate>%s</cbc:IssueDate>
    <cbc:IssueTime>%s</cbc:IssueTime>
    <cbc:InvoiceTypeCode
            listID="0101"
            listAgencyName="PE:SUNAT"
            listName="Tipo de Documento"
            listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01">01</cbc:InvoiceTypeCode>
    <cbc:Note languageLocaleID="1000"><![CDATA[SON SOLES]]></cbc:Note>
    <cbc:DocumentCurrencyCode
            listID="ISO 4217 Alpha"
            listName="Currency"
            listAgencyName="United Nations Economic Commission for Europe">PEN</cbc:DocumentCurrencyCode>

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

    <cac:AccountingSupplierParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="6"
                        schemeName="Documento de Identidad"
                        schemeAgencyName="PE:SUNAT"
                        schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">%s</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyName>
                <cbc:Name><![CDATA[LLAMA PE S.A.C.]]></cbc:Name>
            </cac:PartyName>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName><![CDATA[LLAMA PE S.A.C.]]></cbc:RegistrationName>
                <cac:RegistrationAddress>
                    <cbc:ID schemeName="Ubigeos" schemeAgencyName="PE:INEI">150101</cbc:ID>
                    <cbc:AddressTypeCode
                            listName="Establecimientos anexos"
                            listAgencyName="PE:SUNAT">0000</cbc:AddressTypeCode>
                    <cbc:CitySubdivisionName>NINGUNO</cbc:CitySubdivisionName>
                    <cbc:CityName>LIMA</cbc:CityName>
                    <cbc:CountrySubentity>LIMA</cbc:CountrySubentity>
                    <cbc:District>LIMA</cbc:District>
                    <cac:AddressLine>
                        <cbc:Line><![CDATA[AV. PRINCIPAL 123]]></cbc:Line>
                    </cac:AddressLine>
                    <cac:Country>
                        <cbc:IdentificationCode
                                listID="ISO 3166-1"
                                listName="Country"
                                listAgencyName="United Nations Economic Commission for Europe">PE</cbc:IdentificationCode>
                    </cac:Country>
                </cac:RegistrationAddress>
            </cac:PartyLegalEntity>
        </cac:Party>
    </cac:AccountingSupplierParty>

    <cac:AccountingCustomerParty>
        <cac:Party>
            <cac:PartyIdentification>
                <cbc:ID schemeID="%s"
                        schemeName="Documento de Identidad"
                        schemeAgencyName="PE:SUNAT"
                        schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06">%s</cbc:ID>
            </cac:PartyIdentification>
            <cac:PartyLegalEntity>
                <cbc:RegistrationName><![CDATA[%s]]></cbc:RegistrationName>
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
                    <cbc:ID schemeName="Codigo de tributos"
                            schemeAgencyName="PE:SUNAT"
                            schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05">1000</cbc:ID>
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
        <cbc:InvoicedQuantity unitCode="NIU"
                unitCodeListID="UN/ECE rec 20"
                unitCodeListAgencyName="United Nations Economic Commission for Europe">1</cbc:InvoicedQuantity>
        <cbc:LineExtensionAmount currencyID="PEN">%.2f</cbc:LineExtensionAmount>
        <cac:PricingReference>
            <cac:AlternativeConditionPrice>
                <cbc:PriceAmount currencyID="PEN">%.2f</cbc:PriceAmount>
                <cbc:PriceTypeCode
                        listName="Tipo de Precio"
                        listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16"
                        listAgencyName="PE:SUNAT">01</cbc:PriceTypeCode>
            </cac:AlternativeConditionPrice>
        </cac:PricingReference>
        <cac:TaxTotal>
            <cbc:TaxAmount currencyID="PEN">%.2f</cbc:TaxAmount>
            <cac:TaxSubtotal>
                <cbc:TaxableAmount currencyID="PEN">%.2f</cbc:TaxableAmount>
                <cbc:TaxAmount currencyID="PEN">%.2f</cbc:TaxAmount>
                <cac:TaxCategory>
                    <cbc:Percent>18</cbc:Percent>
                    <cbc:TaxExemptionReasonCode
                            listName="Afectacion del IGV"
                            listURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07"
                            listAgencyName="PE:SUNAT">10</cbc:TaxExemptionReasonCode>
                    <cac:TaxScheme>
                        <cbc:ID schemeName="Codigo de tributos"
                                schemeAgencyName="PE:SUNAT"
                                schemeURI="urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo05">1000</cbc:ID>
                        <cbc:Name>IGV</cbc:Name>
                        <cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>
                    </cac:TaxScheme>
                </cac:TaxCategory>
            </cac:TaxSubtotal>
        </cac:TaxTotal>
        <cac:Item>
            <cbc:Description><![CDATA[POR CONSUMO]]></cbc:Description>
        </cac:Item>
        <cac:Price>
            <cbc:PriceAmount currencyID="PEN">%.2f</cbc:PriceAmount>
        </cac:Price>
    </cac:InvoiceLine>

</Invoice>
""",
                factura.getCod_Factura(),
                fechaEmision,
                horaActual,
                ruc, ruc,
                ruc,
                schemeIdCliente,
                factura.getClienteDocumento(),
                factura.getClienteNombre(),
                factura.getIgv(),
                factura.getSubTotal(),
                factura.getIgv(),
                factura.getSubTotal(),
                factura.getTotal(),
                factura.getTotal(),
                factura.getSubTotal(),
                factura.getTotal(),
                factura.getIgv(),
                factura.getSubTotal(),
                factura.getIgv(),
                factura.getSubTotal()
            ));
        }

        return archivo;
    }
}