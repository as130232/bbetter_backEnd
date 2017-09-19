package com.future.bbetter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class BbetterApplication {
	public final static Logger logger = LoggerFactory.getLogger(BbetterApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BbetterApplication.class, args);
	}
}
