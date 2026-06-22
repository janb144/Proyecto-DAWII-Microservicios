package cibertec.pe.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cibertec.pe.dto.FacturaDTO;
import cibertec.pe.entity.EnvioSunat;
import cibertec.pe.feign.FacturaFeignClient;
import cibertec.pe.service.IEnvioSunatService;
import cibertec.pe.service.SunatProcesoService;
import cibertec.pe.signer.XmlSigner;
import cibertec.pe.soap.SunatSoapClient;

@RestController
@RequestMapping("/api/sunat")
public class EnvioSunatController {
    
    // Inyectamos únicamente la interfaz. Spring por debajo sabrá usar EnvioSunatImplement.
    @Autowired
    private IEnvioSunatService service;
    
    @Autowired
    private FacturaFeignClient facturaFeign;
    @Autowired
    private XmlSigner xmlSigner;

    @Autowired
    private SunatSoapClient sunatSoapClient;

    @GetMapping("/listar")
    public List<EnvioSunat> listar(){
        return service.listar();
    }

    @PostMapping("/registrar")
    public EnvioSunat registrar(@RequestBody EnvioSunat envio){
        return service.guardar(envio);
    }

    @GetMapping("/buscar/{id}")
    public Optional<EnvioSunat> buscar(@PathVariable Integer id){
        return service.buscar(id);
    }

    @PutMapping("/actualizar/{id}")
    public EnvioSunat actualizar(@PathVariable Integer id, @RequestBody EnvioSunat envio){
        return service.actualizar(id, envio);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }

    @GetMapping("/factura/{codigo}")
    public FacturaDTO obtenerFactura(@PathVariable int codigo) {
        return facturaFeign.buscarFactura(codigo);
    }
    @Autowired
    private SunatProcesoService sunatProcesoService;
    
    // Cambiado de @GetMapping a @PostMapping porque estás procesando y alterando estados
 // Cambiado para que procese, firme, comprima y envíe de verdad
    @PostMapping("/xml/{idFactura}")
    public ResponseEntity<String> generarXml(@PathVariable Integer idFactura) {
        try {
            // 1. Traemos los datos de la factura desde el otro microservicio usando Feign
            FacturaDTO factura = facturaFeign.buscarFactura(idFactura);
            
            if (factura == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontró la factura con ID: " + idFactura);
            }
            
            // 2. Llamamos a tu método procesar (Genera -> Firma -> Comprime -> Envía)
            String respuestaSunat = sunatProcesoService.procesar(factura);
            
            // 3. Retornamos la respuesta real que devolvió el servidor de SUNAT
            return ResponseEntity.ok("Proceso completado: " + respuestaSunat);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el envío: " + e.getMessage());
        }
    }
}