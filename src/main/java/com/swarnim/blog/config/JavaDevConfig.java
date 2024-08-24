package com.swarnim.blog.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("DEV")
@Configuration
public class JavaDevConfig {
    @PostConstruct
    public void test() {
        System.out.println("This is dev profile");
    }
}
