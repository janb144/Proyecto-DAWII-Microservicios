package cibertec.pe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TallerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerServiceApplication.class, args);
	}

}
