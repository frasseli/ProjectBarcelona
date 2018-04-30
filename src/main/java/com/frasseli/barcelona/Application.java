package com.frasseli.barcelona;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.frasseli.barcelona.services"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}