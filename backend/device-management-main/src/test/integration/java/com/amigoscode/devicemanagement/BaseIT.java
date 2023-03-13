package com.amigoscode.devicemanagement;

import com.amigoscode.devicemanagement.domain.user.UserService;
import com.amigoscode.devicemanagement.domain.user.model.User;
import com.amigoscode.devicemanagement.domain.user.model.UserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Set;

@ActiveProfiles("it")
@AutoConfigureDataMongo
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = DeviceManagementApplication.class
)
@ExtendWith(SpringExtension.class)
public class BaseIT {

    @Autowired
    protected Environment environment;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected UserService userService;

    protected BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    protected MqttPahoClientFactory mqttPahoClientFactory;

    protected IMqttClient client;

    @Autowired
    private ServerPortService serverPortService;

    @BeforeEach
    void init() {
        setUpMqttConnection();
        dropDb();
        addTestUsers();
    }

    @AfterEach
    void deinit() {
        tearDownMqttConnection();
    }

    @SneakyThrows
    public void setUpMqttConnection() {
        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[]{environment.getProperty("mqtt.server.uri")});
        options.setUserName(environment.getProperty("mqtt.username"));
        String pass = environment.getProperty("mqtt.password");
        options.setPassword(pass.toCharArray());
        options.setCleanSession(true);

        client = new MqttClient(environment.getProperty("mqtt.server.uri"), "fakeMqttClientId");
        client.connect(options);
    }

    @SneakyThrows
    public void tearDownMqttConnection() {
        client.disconnect();
        client.close();
    }

    protected void dropDb() {
        mongoTemplate.getDb().drop();
    }

    private User adminUser = new User(
            "ID3",
            "admin@example.pl",
            "Stefan Burczymucha",
            passwordEncoder.encode("password"),
            Set.of(UserRole.ADMIN)
    );

    protected String localUrl(String endpoint) {
        int port = serverPortService.getPort();
        return "http://localhost:" + port + endpoint;
    }

    protected void addTestUsers() {
        userService.save(adminUser);
    }

    protected String getAccessTokenForUser(String email, String password) {
        String token = "Basic ";
        try {
            token = token + Base64.getEncoder()
                    .encodeToString(
                            (email + ":" + password).getBytes("UTF-8")
                    );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }

    protected String getTokenForAdmin() {
        String adminToken = "Basic ";
        try {
            adminToken = adminToken + Base64.getEncoder()
                    .encodeToString(
                            (adminUser.getEmail() + ":" + adminUser.getPassword()).getBytes("UTF-8")
                    );
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return adminToken;
    }

    @SneakyThrows
    protected <T> void publishMqttMessage(
            String mqttTopic,
            T payload) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        String measurementJsonInString = mapper.writeValueAsString(payload);
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(measurementJsonInString.getBytes());
        client.publish(mqttTopic, mqttMessage);
    }

    protected <T, U> ResponseEntity<U> callHttpMethod(
            HttpMethod httpMethod,
            String url,
            String accessToken,
            T body,
            Class<U> mapToObject
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        headers.add(HttpHeaders.AUTHORIZATION, accessToken);
        headers.add(HttpHeaders.ACCEPT, "application/json");
        HttpEntity<T> requestEntity;
        if (body == null) {
            requestEntity = new HttpEntity<>(headers);
        } else {
            requestEntity = new HttpEntity<>(body, headers);
        }
        return restTemplate.exchange(
                localUrl(url),
                httpMethod,
                requestEntity,
                mapToObject
        );
    }

}
