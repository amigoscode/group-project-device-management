package com.amigoscode.weatherstationsimulator;

import com.amigoscode.weatherstationsimulator.domain.measurement.MeasurementRepository;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("it")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    classes = WeatherStationSimulatorApplication.class
)
@ExtendWith(SpringExtension.class)
public class BaseIT {

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected MeasurementRepository measurementRepository;

    @Autowired
    protected Environment environment;

    @Autowired
    protected MqttPahoClientFactory mqttPahoClientFactory;

    protected IMqttClient client;

    @BeforeEach
    public void setUp() throws Exception {

        measurementRepository.removeAll();

        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[] { environment.getProperty("mqtt.server.uri") });
        options.setUserName(environment.getProperty("mqtt.username"));
        String pass = environment.getProperty("mqtt.password");
        options.setPassword(pass.toCharArray());
        options.setCleanSession(true);

        client = new MqttClient(environment.getProperty("mqtt.server.uri"), "fakeMqttClientId");
        client.connect(options);
    }

    @AfterEach
    public void tearDown() throws Exception {
        client.disconnect();
        client.close();
    }


    protected String localUrl(String endpoint) {
        return "http://localhost:8081" + endpoint;
    }

    protected <T, U> ResponseEntity<U> callHttpMethod(
            HttpMethod httpMethod,
            String url,
            T body,
            Class<U> mapToObject
    ) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
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
