package com.amigoscode.devicemanagement.external.mqtt;

import com.amigoscode.devicemanagement.domain.devicesetting.DeviceSettingPublisher;
import com.amigoscode.devicemanagement.domain.devicesetting.model.DeviceSetting;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class DeviceSettingMqttAdapter implements DeviceSettingPublisher {

    MqttGateway mqtGateway;
    DeviceSettingMqttMapper deviceSettingMqttMapper;

    private static final Logger log = LoggerFactory.getLogger(DeviceSettingMqttAdapter.class);

    DeviceSettingMqttAdapter(final MqttGateway mqtGateway, final DeviceSettingMqttMapper deviceSettingMqttMapper) {
        this.mqtGateway = mqtGateway;
        this.deviceSettingMqttMapper = deviceSettingMqttMapper;
    }

    @Override
    public void publish(final DeviceSetting deviceSetting) {

        String measurementTopic = "ds/" + deviceSetting.getDeviceId();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        DeviceSettingMqttDto deviceSettingMqttDto = deviceSettingMqttMapper.toMqtt(deviceSetting);

        try {
            String deviceSettingJsonInString = mapper.writeValueAsString(deviceSettingMqttDto);
            mqtGateway.sendToMqtt(deviceSettingJsonInString, measurementTopic);
        } catch (JsonProcessingException e) {
            throw new JsonCouldNotBeCreatedException();
        }



    }
}
