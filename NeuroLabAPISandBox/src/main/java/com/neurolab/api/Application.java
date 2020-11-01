package com.neurolab.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {
		
    public static void main(String[] args) {
    	System.setProperty("server.contextPath", "/NeuroLabAPISandBox");
        SpringApplication.run(Application.class, args);
       
    }
}

	