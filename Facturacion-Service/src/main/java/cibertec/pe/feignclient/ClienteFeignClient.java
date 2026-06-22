package cibertec.pe.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cibertec.pe.entity.ClienteDto;

@FeignClient(name="Cliente-Service",url="http://localhost:9004")
public interface ClienteFeignClient {
	@GetMapping("/api/cliente/buscarCliente/{codigo}")
	public ClienteDto obtenerClienteDtoPorId(@PathVariable int codigo);

}
