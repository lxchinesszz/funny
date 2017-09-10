package com.funny;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

/**
 * Created by mac on 2017/6/26.
 */
@SpringBootApplication
public class FunnyAnimalApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunnyAnimalApplication.class,args);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("./");
        return factory.createMultipartConfig();
    }

}
