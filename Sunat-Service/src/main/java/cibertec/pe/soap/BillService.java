package cibertec.pe.soap;


import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(targetNamespace = "http://service.sunat.gob.pe")
public interface BillService {

    @WebMethod
    byte[] sendBill(

            @WebParam(name = "fileName")
            String fileName,

            @WebParam(name = "contentFile")
            byte[] contentFile);

}