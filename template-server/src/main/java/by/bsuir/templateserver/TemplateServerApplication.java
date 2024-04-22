package by.bsuir.templateserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class TemplateServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateServerApplication.class, args);
    }

}
