package com.app.productservice.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.app")
@EnableMongoRepositories("com.app")
@EnableFeignClients("com.app")
@EnableMongoAuditing
public class Application {

  public static void main(String[] args) {

    SpringApplication.run(Application.class, args);
  }

}
