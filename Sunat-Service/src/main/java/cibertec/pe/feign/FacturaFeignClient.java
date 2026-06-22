package cibertec.pe.feign;

import cibertec.pe.feign.FacturaFeignClient;
import cibertec.pe.dto.FacturaDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@FeignClient(name = "FACTURACION-SERVICE")
public interface FacturaFeignClient {

    @GetMapping("/api/factura/buscar/{codigo}")
    FacturaDTO buscarFactura(
            @PathVariable("codigo") int codigo);

}