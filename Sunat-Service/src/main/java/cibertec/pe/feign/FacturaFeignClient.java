package cibertec.pe.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import cibertec.pe.dto.FacturaDTO;

@FeignClient(name = "Facturacion-Service")
public interface FacturaFeignClient {

    // 🎯 Cambiamos la ruta para que use la que ya existe en tu controlador real
    @GetMapping("/api/comprobante/buscarComprobante/{idComprobante}")
    FacturaDTO obtenerDatosParaSunat(@PathVariable("idComprobante") int idComprobante);
}