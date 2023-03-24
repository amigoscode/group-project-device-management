How to prepare dedicated Mosquitto Docker image
=================

Mosquitto is an open source implementation of a server for version 5.0, 3.1.1,
and 3.1 of the MQTT protocol. It also includes a C and C++ client library, and
the `mosquitto_pub` and `mosquitto_sub` utilities for publishing and
subscribing.

## run Mosquitto MQTT broker in the Docker container

- create docker-compose.yml
````
version: "3.8"

services:
   mqtt:
    container_name: mqtt
    image: eclipse-mosquitto:2.0.15
    restart: always
    volumes:
      - ./mosquitto/config:/mosquitto/config
      - ./mosquitto/data:/mosquitto/data
      - ./mosquitto/log:/mosquitto/log
    ports:
      - 1883:1883
      - 9001:9001

volumes:
  data: {}
````

- in the host terminal invoke command
````
docker compose up -d
````

- go to the Terminal tab in the Docker Desktop app

- in container terminal invoke command
```
vi docker-entrypoint.sh
```

- modify the `docker-entrypoint.sh` file

- stop the `mqtt` container

- in container terminal invoke commands
```
sudo docker commit <containerId> rafalnowak444/dm-mosquitto
docker login
docker push rafalnowak444/dm-mosquitto
```

- in the host terminal invoke command
````
docker compose down
````



