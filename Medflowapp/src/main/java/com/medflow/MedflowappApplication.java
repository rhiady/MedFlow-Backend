package com.medflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;


@ComponentScan(basePackages = "com")
@EntityScan(basePackages = {"com.domains","com.domains.enums"})
@SpringBootApplication
public class MedflowappApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedflowappApplication.class, args);
    }

}
