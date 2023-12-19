package com.Proyecto.TallerMecanico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.EnableMBeanExport;

@SpringBootApplication
@EnableMBeanExport
public class TallerMecanicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TallerMecanicoApplication.class, args);
	}

}
