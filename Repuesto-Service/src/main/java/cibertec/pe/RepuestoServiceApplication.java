package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RepuestoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepuestoServiceApplication.class, args);
		System.out.println("Proyecto Repuesto-Service Iniciado");
	}

}
