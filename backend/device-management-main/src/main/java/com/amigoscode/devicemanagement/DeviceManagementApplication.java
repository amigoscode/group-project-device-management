package com.amigoscode.devicemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DeviceManagementApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DeviceManagementApplication.class, args);
    }

}
