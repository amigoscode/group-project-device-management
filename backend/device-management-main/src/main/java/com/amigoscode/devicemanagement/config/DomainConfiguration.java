package com.amigoscode.devicemanagement.config;

import com.amigoscode.devicemanagement.domain.device.DeviceRepository;
import com.amigoscode.devicemanagement.domain.device.DeviceService;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingPublisher;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingRepository;
import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingService;
import com.amigoscode.devicemanagement.domain.measurement.MeasurementRepository;
import com.amigoscode.devicemanagement.domain.measurement.MeasurementService;
import com.amigoscode.devicemanagement.domain.rule.RuleRepository;
import com.amigoscode.devicemanagement.domain.rule.RuleService;
import com.amigoscode.devicemanagement.domain.user.EncodingService;
import com.amigoscode.devicemanagement.domain.user.UserRepository;
import com.amigoscode.devicemanagement.domain.user.UserService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;


@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public UserService userService(UserRepository userRepository, EncodingService encoder, Clock clock)  {
        return new UserService(userRepository, encoder, clock);
    }

    @Bean
    public DeviceService deviceService(DeviceRepository deviceRepository, DeviceSettingService deviceSettingService, MeasurementService measurementService, Clock clock) {
        return new DeviceService(deviceRepository, deviceSettingService,measurementService, clock);
    }

    @Bean
    public MeasurementService measurementService(MeasurementRepository measurementRepository) {
        return new MeasurementService(measurementRepository);
    }

    @Bean
    public DeviceSettingService deviceSettingService(DeviceSettingRepository deviceSettingRepository, DeviceSettingPublisher deviceSettingPublisher, Clock clock){
        return new DeviceSettingService(deviceSettingRepository, deviceSettingPublisher, clock);
    }

    @Bean
    public RuleService ruleService(RuleRepository ruleRepository){
        return new RuleService(ruleRepository);
    }


}
