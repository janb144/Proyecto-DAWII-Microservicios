package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cibertec.pe.feign") // Apunta al paquete de tus interfaces Feign
@ComponentScan(basePackages = "cibertec.pe") // Asegura que escanee .soap, .service, .controller, etc.
public class SunatServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunatServiceApplication.class, args);
    }

}
