package com.hunt.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(proxyBeanMethods = false)
//@EnableJpaAuditing
public class MsgFlowableApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgFlowableApplication.class, args);
	}
}
