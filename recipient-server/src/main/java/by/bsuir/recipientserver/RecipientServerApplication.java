package by.bsuir.recipientserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class RecipientServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipientServerApplication.class, args);
	}

}
