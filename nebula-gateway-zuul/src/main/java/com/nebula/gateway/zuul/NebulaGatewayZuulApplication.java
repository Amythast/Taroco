package com.nebula.gateway.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * zuul gateway
 *
 * @author feifeixia
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class NebulaGatewayZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(NebulaGatewayZuulApplication.class, args);
	}
}
