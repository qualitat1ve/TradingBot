package com.binancebot;

import com.binancebot.services.impl.ServicesRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        var context = SpringApplication.run(Application.class, args);

        // just for testing purposes
        ServicesRunner servicesRunner = context.getBean(ServicesRunner.class);
        servicesRunner.run();
    }

}
