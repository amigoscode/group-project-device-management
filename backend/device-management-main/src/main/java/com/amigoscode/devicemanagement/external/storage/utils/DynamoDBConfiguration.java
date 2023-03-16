package com.amigoscode.devicemanagement.external.storage.utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import lombok.AllArgsConstructor;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Configuration
@EnableDynamoDBRepositories
        (
                basePackages = "com.amigoscode.devicemanagement.external.storage"
//                includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class)
        )
class DynamoDBConfiguration {
    private Environment environment;

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        AmazonDynamoDB amazonDynamoDB
                = new AmazonDynamoDBClient(amazonAWSCredentials());

        if (!StringUtils.isEmpty(environment.getProperty("aws.dynamoDBUrl"))) {
            amazonDynamoDB.setEndpoint(environment.getProperty("aws.dynamoDBUrl"));
        }

        return amazonDynamoDB;
    }

    @Bean
    public AWSCredentials amazonAWSCredentials() {
        return new BasicAWSCredentials(
                environment.getProperty("aws.accessKey"), environment.getProperty("aws.secretKey"));
    }

    @Bean
    @Primary
    public DynamoDBMapper mapper(){
        return new DynamoDBMapper(amazonDynamoDB());
    }

}
