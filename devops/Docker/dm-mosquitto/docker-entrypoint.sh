#!/bin/ash
set -e

# Set permissions
user="$(id -u)"
if [ "$user" = '0' ]; then
        [ -d "/mosquitto" ] && chown -R mosquitto:mosquitto /mosquitto || true
fi

# Add new commands

# make sure it is defined and not empty
if [[ $MQTT_LISTENER && ${MQTT_LISTENER-_} ]]
        then
                echo listener $MQTT_LISTENER >> /mosquitto/config/mosquitto.conf
fi

# make sure it is defined and not empty
if [[ $MQTT_USERNAME && ${MQTT_USERNAME-_} && $MQTT_PASSWORD && ${MQTT_PASSWORD-_} ]]
        then
                yes $MQTT_PASSWORD | yes $MQTT_PASSWORD | mosquitto_passwd -c /mosquitto/config/pwfile $MQTT_USERNAME
                echo $'password_file /mosquitto/config/pwfile\nallow_anonymous false' >> /mosquitto/config/mosquitto.conf
        else
                echo $'allow_anonymous true' >> /mosquitto/config/mosquitto.conf
fi

# Start Mosquitto MQTT broker
exec "$@"