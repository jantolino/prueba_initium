package com.prueba.atencion_al_cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AtencionAlClienteApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtencionAlClienteApplication.class, args);
    }
}
