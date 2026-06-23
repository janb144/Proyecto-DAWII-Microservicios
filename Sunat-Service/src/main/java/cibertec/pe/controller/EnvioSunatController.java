package cibertec.pe.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cibertec.pe.dto.FacturaDTO;
import cibertec.pe.entity.EnvioSunat;
import cibertec.pe.service.IEnvioSunatService;

@RestController
@RequestMapping("/api/sunat")
public class EnvioSunatController {
    
    @Autowired
    private IEnvioSunatService service;

    @GetMapping("/listar") public List<EnvioSunat> listar(){ return service.listar(); }
    @PostMapping("/registrar") public EnvioSunat registrar(@RequestBody EnvioSunat envio){ return service.guardar(envio); }
    @GetMapping("/buscar/{id}") public Optional<EnvioSunat> buscar(@PathVariable Integer id){ return service.buscar(id); }
    @PutMapping("/actualizar/{id}") public EnvioSunat actualizar(@PathVariable Integer id, @RequestBody EnvioSunat envio){ return service.actualizar(id, envio); }
    @DeleteMapping("/eliminar/{id}") public void eliminar(@PathVariable Integer id){ service.eliminar(id); }

    @PostMapping("/xml/{idFactura}")
    public ResponseEntity<String> generarXml(@PathVariable Integer idFactura) {
        try {
            // Llama a la implementación interna que orquesta todo el flujo hacia SUNAT
            service.generarXmlFactura(idFactura);
            return ResponseEntity.ok("Proceso de envío completado con éxito para el comprobante ID: " + idFactura);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el envío a SUNAT: " + e.getMessage());
        }
    }
}