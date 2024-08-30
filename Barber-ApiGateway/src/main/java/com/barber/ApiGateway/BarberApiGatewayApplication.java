package com.barber.ApiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class BarberApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarberApiGatewayApplication.class, args);
	}

}
