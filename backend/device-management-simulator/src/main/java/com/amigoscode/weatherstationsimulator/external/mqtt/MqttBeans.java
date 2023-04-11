package com.amigoscode.weatherstationsimulator.external.mqtt;

import com.amigoscode.weatherstationsimulator.domain.device.DeviceService;
import com.amigoscode.weatherstationsimulator.domain.devicesetting.DeviceSettingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

@Configuration
class MqttBeans {

    private Environment environment;

    private DeviceService deviceService;

    private DeviceSettingService deviceSettingService;

    private DeviceSettingMqttMapper deviceSettingMqttMapper;


    MqttBeans(final Environment environment, final DeviceService deviceService, final DeviceSettingService deviceSettingService, final DeviceSettingMqttMapper deviceSettingMqttMapper) {
        this.environment = environment;
        this.deviceService = deviceService;
        this.deviceSettingService = deviceSettingService;
        this.deviceSettingMqttMapper = deviceSettingMqttMapper;
    }

    private static final Logger log = LoggerFactory.getLogger(MqttBeans.class);
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();

        options.setServerURIs(new String[] { environment.getProperty("mqtt.server.uri") });
        options.setUserName(environment.getProperty("mqtt.username"));
        String pass = environment.getProperty("mqtt.password");
        options.setPassword(pass.toCharArray());
        options.setCleanSession(true);

        factory.setConnectionOptions(options);

        return factory;
    }
    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter("serverIn_1",
                mqttClientFactory(), "#");

        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(2);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }


    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                String topic = message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC).toString();
                log.info("Message topic: {} | Message payload: {}", topic, message.getPayload());

                if (topic.startsWith("ds/")) {
                    String deviceId = topic.substring(topic.indexOf("/") + 1);
//                    System.out.println("####### " + deviceId);
                    if (deviceId.equals(deviceService.getDevice().getId())) {
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.findAndRegisterModules();

                        try {
                            DeviceSettingMqttDto deviceSettingMqttDto = mapper.readValue(message.getPayload().toString(), DeviceSettingMqttDto.class);

                            deviceSettingService.saveDeviceSetting(deviceSettingMqttMapper.toDomain(deviceSettingMqttDto));

                        }
                        catch (JsonProcessingException e) {
                            throw new JsonCouldNotBeCreatedException();
                        }
                    }
                }
            }

        };
    }


    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        //clientId is generated using a random number
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("serverOut", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("#");
        messageHandler.setDefaultRetained(false);
        return messageHandler;
    }
}
