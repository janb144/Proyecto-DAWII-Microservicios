package cibertec.pe.service;


import java.util.List;
import java.util.Optional;

import cibertec.pe.entity.EnvioSunat;

public interface IEnvioSunatService {

    List<EnvioSunat> listar();

    EnvioSunat guardar(EnvioSunat envio);

    Optional<EnvioSunat> buscar(Integer id);

    EnvioSunat actualizar(Integer id, EnvioSunat envio);

    void eliminar(Integer id);
    
    void generarXmlFactura(Integer idFactura) throws Exception;

}