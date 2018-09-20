package com.sweet.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.sweet.api")
public class Application extends SpringBootServletInitializer {

    protected final static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        new SpringApplication(Application.class).run(args);
        logger.info("PortalApplication is success!");
    }
}
