package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RepuestoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepuestoServiceApplication.class, args);
		System.out.println("Proyecto Repuesto-Service Iniciado");
	}

}
