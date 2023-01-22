package com.amigoscode.devicemanagement.config;

import com.amigoscode.devicemanagement.domain.user.EncodingService;
import com.amigoscode.devicemanagement.domain.user.UserRepository;
import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.external.storage.user.MongoUserRepository;
import com.amigoscode.devicemanagement.external.storage.user.UserEntityMapper;
import com.amigoscode.devicemanagement.external.storage.user.UserStorageAdapter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("domain.properties")
public class DomainConfiguration {

    @Bean
    public UserRepository userRepository(MongoUserRepository mongoUserRepository, UserEntityMapper mapper) {
        return new UserStorageAdapter(mongoUserRepository, mapper);
    }

    @Bean
    public UserService userService(UserRepository userRepository, EncodingService encoder)  {
        return new UserService(userRepository, encoder);
    }


}
