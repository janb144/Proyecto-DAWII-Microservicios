package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class FacturacionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacturacionServiceApplication.class, args);
		System.out.println("Sistema Facturación Iniciado");
	}

}
