package cibertec.pe.service;


import cibertec.pe.dto.EnvioSunatDTO;

public interface ISunatService {

    String procesarComprobante(EnvioSunatDTO dto) throws Exception;

}
