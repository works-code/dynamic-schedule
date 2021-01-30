package com.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/*@ComponentScan(basePackages = "com.code")*/
@SpringBootApplication
public class Module02Application {

    public static void main(String[] args) {
        SpringApplication.run(Module02Application.class, args);
    }

}