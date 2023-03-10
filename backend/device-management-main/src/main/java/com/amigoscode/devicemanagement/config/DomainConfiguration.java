package com.amigoscode.devicemanagement.config;

import com.amigoscode.devicemanagement.domain.device.DeviceRepository;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.measurement.MeasurementRepository;
import com.amigoscode.devicemanagement.domain.measurement.MeasurementService;
import com.amigoscode.devicemanagement.domain.user.EncodingService;
import com.amigoscode.devicemanagement.domain.user.UserRepository;
import com.amigoscode.devicemanagement.domain.user.UserService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public UserService userService(UserRepository userRepository, EncodingService encoder)  {
        return new UserService(userRepository, encoder);
    }

    @Bean
    public DeviceService deviceService(DeviceRepository deviceRepository) {
        return new DeviceService(deviceRepository);
    }

    @Bean
    public MeasurementService measurementService(MeasurementRepository measurementRepository) {
        return new MeasurementService(measurementRepository);
    }

}
