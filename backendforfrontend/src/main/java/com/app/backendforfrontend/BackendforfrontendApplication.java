package com.app.backendforfrontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BackendforfrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendforfrontendApplication.class, args);
    }

}
