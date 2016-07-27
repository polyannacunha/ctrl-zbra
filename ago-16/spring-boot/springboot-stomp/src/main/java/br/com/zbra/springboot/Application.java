package br.com.zbra.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan({"br.com.zbra.springboot.configuration",
        "br.com.zbra.springboot.repository",
        "br.com.zbra.springboot.service",
        "br.com.zbra.springboot.controller"})
@EnableAutoConfiguration
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
