import sys
import paho.mqtt.client as mqtt
import threading
import datetime
import pytz
import json
import time

from PyQt6.QtCore import Qt
from PyQt6.QtWidgets import QApplication, QPushButton, QMainWindow, QSlider, QLabel, QHBoxLayout, QVBoxLayout, QWidget, \
    QLineEdit


class MainWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.counter = 0
        self.measurement_period = 5
        self.is_measurement_enabled = False
        self.connected_to_mqtt_broker = False
        self.initial_mqtt_username = "admin"
        self.initial_mqtt_password = "qwerty"
        self.initial_mqtt_host_name = "13.50.198.121"
        self.initial_mqtt_port = "1883"
        self.mqtt_measurement_topic = "dm/measurements"
        self.initial_device_id = "3"
        self.initial_temperature = 24.85
        self.initial_pressure = 1013.0
        self.initial_humidity = 30.4
        self.initial_wind_speed = 2.57
        self.initial_wind_direction = 125.1
        self.initial_longitude = 19.457216
        self.initial_latitude = 51.759445
        self.initial_elevation = 278
        self.temperature = self.initial_temperature
        self.pressure = self.initial_pressure
        self.humidity = self.initial_humidity
        self.wind_speed = self.initial_wind_speed
        self.wind_direction = self.initial_wind_direction
        self.longitude = self.initial_longitude
        self.latitude = self.initial_latitude
        self.elevation = self.initial_elevation
        self.setupUi()

    def setupUi(self):
        self.setWindowTitle("Weather Station Simulator")
        self.resize(600, 600)

        self.layout = QVBoxLayout()

        self.mqttUsernameLabel = QLabel()
        self.mqttUsernameLabel.setText("MQTT USERNAME")
        self.mqttUsernameText = QLineEdit()
        self.mqttUsernameText.setText(self.initial_mqtt_username)

        self.mqttUsernamelayout = QVBoxLayout()
        self.mqttUsernamelayout.addWidget(self.mqttUsernameLabel)
        self.mqttUsernamelayout.addWidget(self.mqttUsernameText)

        self.mqttPasswordLabel = QLabel()
        self.mqttPasswordLabel.setText("MQTT PASSWORD")
        self.mqttPasswordText = QLineEdit()
        self.mqttPasswordText.setEchoMode(QLineEdit.EchoMode.Password)
        self.mqttPasswordText.setText(self.initial_mqtt_password)

        self.mqttPasswordLayout = QVBoxLayout()
        self.mqttPasswordLayout.addWidget(self.mqttPasswordLabel)
        self.mqttPasswordLayout.addWidget(self.mqttPasswordText)

        self.mqttUsernameAndPasswordLayout = QHBoxLayout()
        self.mqttUsernameAndPasswordLayout.addLayout(self.mqttUsernamelayout)
        self.mqttUsernameAndPasswordLayout.addLayout(self.mqttPasswordLayout)
        self.layout.addLayout(self.mqttUsernameAndPasswordLayout)

        self.mqttHostNameLabel = QLabel()
        self.mqttHostNameLabel.setText("MQTT HOST NAME")
        self.mqttHostNameText = QLineEdit()
        self.mqttHostNameText.setText(self.initial_mqtt_host_name)

        self.mqttHostNameLayout = QVBoxLayout()
        self.mqttHostNameLayout.addWidget(self.mqttHostNameLabel)
        self.mqttHostNameLayout.addWidget(self.mqttHostNameText)

        self.mqttPortLabel = QLabel()
        self.mqttPortLabel.setText("MQTT PORT")
        self.mqttPortText = QLineEdit()
        self.mqttPortText.setText(self.initial_mqtt_port)

        self.mqttPortLayout = QVBoxLayout()
        self.mqttPortLayout.addWidget(self.mqttPortLabel)
        self.mqttPortLayout.addWidget(self.mqttPortText)

        self.mqttHostNameAndPortLayout = QHBoxLayout()
        self.mqttHostNameAndPortLayout.addLayout(self.mqttHostNameLayout)
        self.mqttHostNameAndPortLayout.addLayout(self.mqttPortLayout)
        self.layout.addLayout(self.mqttHostNameAndPortLayout)

        self.connectButton = QPushButton("Connect")
        self.connectButton.setStyleSheet(
            "background-color: green; border-style: outset; border-radius: 10px; font: 14px; min-width: 10em; padding: 6px;")
        self.connectButton.clicked.connect(self.connect_to_mqtt_broker)

        self.layout.addWidget(self.connectButton)

        self.disconnectButton = QPushButton("Disconnect")
        self.disconnectButton.setStyleSheet(
            "background-color: gray; border-style: outset; border-radius: 10px; font: 14px; min-width: 10em; padding: 6px;")
        self.disconnectButton.clicked.connect(self.disconnect_with_mqtt_broker)

        self.layout.addWidget(self.disconnectButton)

        self.deviceIdLabel = QLabel()
        self.deviceIdLabel.setText("DEVICE ID")
        self.deviceIdText = QLineEdit()
        self.deviceIdText.setText(self.initial_device_id)

        self.layout.addWidget(self.deviceIdLabel)
        self.layout.addWidget(self.deviceIdText)

        self.temperatureLabel = QLabel()
        self.temperatureLabel.setText("TEMPERATURE")
        self.temperatureValueLabel = QLabel()
        self.temperatureSlider = QSlider(Qt.Orientation.Horizontal)
        self.temperatureSlider.setRange(-500, 1000)
        self.temperatureSlider.valueChanged.connect(self.value_changed)

        self.layout.addWidget(self.temperatureLabel)
        self.layout.addWidget(self.temperatureValueLabel)
        self.layout.addWidget(self.temperatureSlider)

        self.pressureLabel = QLabel()
        self.pressureLabel.setText("PRESSURE")
        self.pressureValueLabel = QLabel()
        self.pressureSlider = QSlider(Qt.Orientation.Horizontal)
        self.pressureSlider.setRange(9800, 10300)
        self.pressureSlider.valueChanged.connect(self.value_changed)

        self.layout.addWidget(self.pressureLabel)
        self.layout.addWidget(self.pressureValueLabel)
        self.layout.addWidget(self.pressureSlider)

        self.humidityLabel = QLabel()
        self.humidityLabel.setText("HUMIDITY")
        self.humidityValueLabel = QLabel()
        self.humiditySlider = QSlider(Qt.Orientation.Horizontal)
        self.humiditySlider.setRange(0, 1000)
        self.humiditySlider.valueChanged.connect(self.value_changed)

        self.layout.addWidget(self.humidityLabel)
        self.layout.addWidget(self.humidityValueLabel)
        self.layout.addWidget(self.humiditySlider)

        self.windSpeedLabel = QLabel()
        self.windSpeedLabel.setText("WIND SPEED")
        self.windSpeedValueLabel = QLabel()
        self.windSpeedSlider = QSlider(Qt.Orientation.Horizontal)
        self.windSpeedSlider.setRange(0, 1000)
        self.windSpeedSlider.valueChanged.connect(self.value_changed)

        self.layout.addWidget(self.windSpeedLabel)
        self.layout.addWidget(self.windSpeedValueLabel)
        self.layout.addWidget(self.windSpeedSlider)

        self.windDirectionLabel = QLabel()
        self.windDirectionLabel.setText("WIND DIRECTION")
        self.windDirectionValueLabel = QLabel()
        self.windDirectionSlider = QSlider(Qt.Orientation.Horizontal)
        self.windDirectionSlider.setRange(0, 3600)
        self.windDirectionSlider.valueChanged.connect(self.value_changed)

        self.layout.addWidget(self.windDirectionLabel)
        self.layout.addWidget(self.windDirectionValueLabel)
        self.layout.addWidget(self.windDirectionSlider)

        self.longitudeLabel = QLabel()
        self.longitudeLabel.setText("LONGITUDE")
        self.longitudeValueLabel = QLabel()
        self.longitudeSlider = QSlider(Qt.Orientation.Horizontal)
        self.longitudeSlider.setRange(18000000, 22000000)
        self.longitudeSlider.valueChanged.connect(self.value_changed)

        self.layout.addWidget(self.longitudeLabel)
        self.layout.addWidget(self.longitudeValueLabel)
        self.layout.addWidget(self.longitudeSlider)

        self.latitudeLabel = QLabel()
        self.latitudeLabel.setText("LATITUDE")
        self.latitudeValueLabel = QLabel()
        self.latitudeSlider = QSlider(Qt.Orientation.Horizontal)
        self.latitudeSlider.setRange(50000000, 53000000)
        self.latitudeSlider.valueChanged.connect(self.value_changed)

        self.layout.addWidget(self.latitudeLabel)
        self.layout.addWidget(self.latitudeValueLabel)
        self.layout.addWidget(self.latitudeSlider)

        self.elevationLabel = QLabel()
        self.elevationLabel.setText("ELEVATION")
        self.elevationValueLabel = QLabel()
        self.elevationSlider = QSlider(Qt.Orientation.Horizontal)
        self.elevationSlider.setRange(0, 500)
        self.elevationSlider.valueChanged.connect(self.value_changed)

        self.layout.addWidget(self.elevationLabel)
        self.layout.addWidget(self.elevationValueLabel)
        self.layout.addWidget(self.elevationSlider)

        self.measurementPeriodLabel = QLabel()
        self.measurementPeriodLabel.setText(f"MEASUREMENT PERIOD: {self.measurement_period}")
        self.isMeasurementEnabledLabel = QLabel()
        self.isMeasurementEnabledLabel.setText(f"MEASUREMENT ENABLED: {self.is_measurement_enabled}")

        self.layout.addWidget(self.measurementPeriodLabel)
        self.layout.addWidget(self.isMeasurementEnabledLabel)

        self.sendMeasurementButton = QPushButton(" Send Measurement")
        self.sendMeasurementButton.clicked.connect(self.send_measurement)

        self.layout.addWidget(self.sendMeasurementButton)

        self.temperatureSlider.setValue(int(self.initial_temperature * 10))
        self.pressureSlider.setValue(int(self.initial_pressure * 10))
        self.humiditySlider.setValue(int(self.initial_humidity * 10))
        self.windSpeedSlider.setValue(int(self.initial_wind_speed * 10))
        self.windDirectionSlider.setValue(int(self.initial_wind_direction * 10))
        self.longitudeSlider.setValue(int(self.initial_longitude * 1000000))
        self.latitudeSlider.setValue(int(self.initial_latitude * 1000000))
        self.elevationSlider.setValue(int(self.initial_elevation))

        self.widget = QWidget()
        self.widget.setLayout(self.layout)

        self.setCentralWidget(self.widget)

    def send_measurement(self):
        if self.connected_to_mqtt_broker == True:
            # Publish message
            self.client.publish(self.mqtt_measurement_topic, self.take_measurement())
            print(self.take_measurement())
        else:
            print("You need to connect to MQTT broker first")

    def disconnect_with_mqtt_broker(self):
        if self.connected_to_mqtt_broker == True:
            self.stop_flag.set()
            self.timer_stop_flag.set()
            self.connected_to_mqtt_broker = False
            self.connectButton.setStyleSheet(
                "background-color: green; border-style: outset; border-radius: 10px; font: 14px; min-width: 10em; padding: 6px;")
            self.disconnectButton.setStyleSheet(
                "background-color: gray; border-style: outset; border-radius: 10px; font: 14px; min-width: 10em; padding: 6px;")
            print("Now you are disconnected from the MQTT broker")

    def connect_to_mqtt_broker(self):
        if self.connected_to_mqtt_broker == False:
            # Define MQTT broker settings
            broker_address = self.mqttHostNameText.text()
            broker_port = int(self.mqttPortText.text())
            client_id = "my_client"
            username = self.mqttUsernameText.text()
            password = self.mqttPasswordText.text()

            # Create MQTT client instance
            self.client = mqtt.Client(client_id=client_id)

            # Set username and password for broker authentication
            self.client.username_pw_set(username, password)

            # Connect to broker
            self.client.connect(broker_address, broker_port)

            # Subscribe to receive topic
            self.client.subscribe("#")
            # Set callback function for incoming messages
            self.client.on_message = self.on_message

            # Create flag to signal receiving thread to stop
            self.stop_flag = threading.Event()

            # Define function to run in receiving thread
            def run_receive_thread():
                # Start the MQTT client loop to listen for incoming messages
                self.client.loop_start()

                # Wait for stop signal
                while not self.stop_flag.is_set():
                    pass

                # Stop the MQTT client loop and disconnect from the broker
                self.client.loop_stop()
                self.client.disconnect()

            # Start receiving thread
            receive_thread = threading.Thread(target=run_receive_thread)
            receive_thread.start()

            # Create flag to signal receiving thread to stop
            self.timer_stop_flag = threading.Event()

            # Define function to run in receiving thread
            def run_timer_thread():
                # Wait for stop signal
                while not self.timer_stop_flag.is_set():
                    self.counter += 1
                    if self.counter >= self.measurement_period:
                        self.counter = 0
                        if self.is_measurement_enabled:
                            self.send_measurement()
                    print(self.get_timestamp())
                    time.sleep(1)

            # Start receiving thread
            timer_thread = threading.Thread(target=run_timer_thread)
            timer_thread.start()

            self.connected_to_mqtt_broker = True

            self.connectButton.setStyleSheet(
                "background-color: gray; border-style: outset; border-radius: 10px; font: 14px; min-width: 10em; padding: 6px;")
            self.disconnectButton.setStyleSheet(
                "background-color: darkRed; border-style: outset; border-radius: 10px; font: 14px; min-width: 10em; padding: 6px;")
            print("Now you are connected to the MQTT broker")

    def on_message(self, client, userdata, message):
        print("message received")
        print(f"Received topic: {message.topic}")
        print(f"Received message: {message.payload.decode()}")

        if (str(message.topic).startswith("ds/")):
            deviceId = str(message.topic)[3:]
            if (deviceId == self.deviceIdText.text()):
                device_settings = json.loads(message.payload.decode())
                print(f"Received device settings: {device_settings}")
                self.measurement_period = device_settings['measurementPeriod']
                self.is_measurement_enabled = device_settings['isMeasurementEnabled']
                self.measurementPeriodLabel.setText(f"MEASUREMENT PERIOD: {self.measurement_period}")
                self.isMeasurementEnabledLabel.setText(f"MEASUREMENT ENABLED: {self.is_measurement_enabled}")

    def value_changed(self):
        self.temperature = self.temperatureSlider.value() / 10
        self.temperatureValueLabel.setText(str(self.temperature))
        self.pressure = self.pressureSlider.value() / 10
        self.pressureValueLabel.setText(str(self.pressure))
        self.humidity = self.humiditySlider.value() / 10
        self.humidityValueLabel.setText(str(self.humidity))
        self.wind_speed = self.windSpeedSlider.value() / 10
        self.windSpeedValueLabel.setText(str(self.wind_speed))
        self.wind_direction = self.windDirectionSlider.value() / 10
        self.windDirectionValueLabel.setText(str(self.wind_direction))
        self.longitude = self.longitudeSlider.value() / 1000000
        self.longitudeValueLabel.setText(str(self.longitude))
        self.latitude = self.latitudeSlider.value() / 1000000
        self.latitudeValueLabel.setText(str(self.latitude))
        self.elevation = self.elevationSlider.value()
        self.elevationValueLabel.setText(str(self.elevation))

    def take_measurement(self):
        timestamp = time.time()

        measurement = {
            "deviceId": self.deviceIdText.text(),
            "temperature": self.temperature,
            "pressure": self.pressure,
            "humidity": self.humidity,
            "wind": {
                "speed": self.wind_speed,
                "direction": self.wind_direction
            },
            "location": {
                "longitude": self.longitude,
                "latitude": self.latitude,
                "elevation": self.elevation
            },
            "timestamp": timestamp
        }

        # convert the dictionary to JSON
        json_string = json.dumps(measurement)
        return json_string

    def get_timestamp(self):
        # Get current date and time in local timezone
        local_dt = datetime.datetime.now()

        # Convert to UTC timezone
        utc_dt = pytz.utc.localize(local_dt)

        # Convert to desired timezone (e.g. America/New_York)
        ny_tz = pytz.timezone('Europe/Warsaw')
        ny_dt = utc_dt.astimezone(ny_tz)

        # Format the date and time as a string compatible with java.time.ZonedDateTime
        formatted_date_time = ny_dt.strftime('%Y-%m-%dT%H:%M:%S.%f%z[%Z]')

        return formatted_date_time


app = QApplication(sys.argv)

window = MainWindow()
window.show()

app.exec()
