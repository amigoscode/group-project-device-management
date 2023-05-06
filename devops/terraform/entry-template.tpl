#!/bin/bash

sudo yum update -y && sudo yum install -y docker
sudo systemctl start docker
sudo usermod -aG docker ec2-user
public_ip=$(curl -s https://api.ipify.org)
mqtt_endpoint="tcp://$public_ip:1883"
export MQTT_ENDPOINT="$mqtt_endpoint"
echo "MQTT_ENDPOINT=$mqtt_endpoint" | sudo tee -a /etc/environment
docker run -d --name mqtt \
    -p 1883:1883 \
    -p 9001:9001 \
    -e MQTT_USERNAME=${mqtt_username} \
    -e MQTT_PASSWORD=${mqtt_password} \
    -e MQTT_LISTENER=${mqtt_listener} \
    rafalnowak444/dm-mosquitto:latest
docker run -d --name react \
    -p 80:5173 \
    rafalnowak444/device-management-react:latest
docker run -d --name device-management \
    -p 8080:8080 \
    -e MQTT_USERNAME=${mqtt_username} \
    -e MQTT_PASSWORD=${mqtt_password} \
    -e MQTT_ENDPOINT="$mqtt_endpoint" \
    -e DYNAMO_ENDPOINT=${dynamo_endpoint} \
    -e AWS_REGION=${aws_region} \
    -e AWS_ACCESS_KEY_ID=${aws_access_key_id} \
    -e AWS_SECRET_ACCESS_KEY=${aws_secret_access_key} \
    rafalnowak444/device-management:latest
