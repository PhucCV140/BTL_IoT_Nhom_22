// #include <FS.h>
// #include <FSImpl.h>
// #include <vfs_api.h>

#include <NewPing.h>
#include <PubSubClient.h>
#include <WiFi.h>

// Đặt các chân kết nối cho cảm biến siêu âm HC-SR04
#define TRIGGER_PIN 4
#define ECHO_PIN 2
#define LED 5

// Đặt chiều cao của bể nước (đơn vị: cm)
#define MAX_WATER_HEIGHT 20
#define MIN_WATER_PERCENTAGE 10
#define MAX_WATER_PERCENTAGE 70

// Khởi tạo đối tượng NewPing
NewPing sonar(TRIGGER_PIN, ECHO_PIN);

#define RELAY_PIN 27  // Chân GPIO kết nối với chân IN của Relay Module

bool pumpStatus = false;  // Trạng thái ban đầu của máy bơm

const char* ssid = "Phúc";
const char* password = "0339958045";
const char* mqtt_server = "broker.hivemq.com";

const char* Topic = "/IOT/BTL/pump";

WiFiClient espClient;
PubSubClient client(espClient);

void setup_wifi() { 
  delay(10);
  Serial.println();
  Serial.print("Đang kết nối tới ");
  Serial.println(ssid);

  WiFi.mode(WIFI_STA); 
  WiFi.begin(ssid, password); 

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  randomSeed(micros());

  Serial.println("");
  Serial.println("Đã kết nối WiFi");
  Serial.println("IP: ");
  Serial.println(WiFi.localIP());
  
}

void setup() {
  Serial.begin(115200);
  pinMode(LED, OUTPUT);
  pinMode(RELAY_PIN,OUTPUT);
  setup_wifi(); 
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void reconnect() { 
  // Lặp lại nếu không có kết nối kết nối
  while (!client.connected()) {
    Serial.print("Thử kết nối...");
    String clientId = "ESP32Client-";
    clientId += String(random(0xffff), HEX);
    // Thử kết nối
    if (client.connect(clientId.c_str())) {
      Serial.println("Kết nối tới " + clientId);
      client.subscribe(Topic);
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      // Đợi 5 giây và thử lại
      delay(5000);
    }
  }
}

void callback(char* topic, byte* payload, unsigned int length) {
  Serial.print("Nhận dữ liệu từ topic: ");
  Serial.println(topic);
  Serial.print("Nội dung: ");
  for (int i = 0; i < length; i++) {
    Serial.print((char)payload[i]);
  }
  Serial.println();

  if (strcmp(topic, Topic) == 0) {
    if (payload[0] == '0') {
      pumpStatus = false;
    } else if (payload[0] == '1') {
      pumpStatus = true;
    }
    controlPump(pumpStatus);
    Serial.println("Đã nhận thông điệp: " + String((char)payload[0]));
  }
}

void loop() {
  if (!client.connected()) {
    reconnect();
  }

  // Lắng nghe các sự kiện MQTT
  client.loop();

  // Đo khoảng cách sử dụng cảm biến siêu âm
  int distance = sonar.ping_cm();
  // int distance = random(2,20);

  // Tính toán lượng nước còn lại và tỉ lệ nước
  float waterLevel = MAX_WATER_HEIGHT - distance;
  float waterPercentage = (waterLevel / MAX_WATER_HEIGHT) * 100;

  // Hiển thị thông tin trên Serial Monitor
  Serial.print("Khoảng cách: ");
  Serial.print(distance);
  Serial.print(" cm, Lượng nước còn lại: ");
  Serial.print(waterLevel);
  Serial.print(" cm, Tỉ lệ nước: ");
  Serial.print(waterPercentage);
  Serial.print("%, Trạng thái máy bơm: ");
  if (pumpStatus) {
    Serial.println("Bật");
    digitalWrite(LED, LOW);
    client.publish("/IOT/BTL/state", "On");
  } else {
    Serial.println("Tắt");
    digitalWrite(LED, HIGH);
    client.publish("/IOT/BTL/state", "Off");
  }
  // Điều khiển relay từ Serial Monitor
  if (Serial.available() > 0) {
    String command = Serial.readStringUntil('\n');
    command.toLowerCase();

    if (command == "on") {
      controlPump(true);
      Serial.println("Đã bật máy bơm.");
    } else if (command == "off") {
      controlPump(false);
      Serial.println("Đã tắt máy bơm.");
    }
  }

  // Kiểm tra nếu tỉ lệ nước trên 70%, tắt relay
  // if (waterPercentage == MAX_WATER_PERCENTAGE && pumpStatus) {
  //   controlPump(false);
  //   Serial.println("Tỉ lệ nước trên 70%. Đang tắt relay...");
  // }
  // // Kiểm tra nếu tỉ lệ nước dưới 10%, bật relay
  // else if (waterPercentage == MIN_WATER_PERCENTAGE && !pumpStatus) {
  //   controlPump(true);
  //   Serial.println("Tỉ lệ nước dưới 10%. Đang bật relay...");
  // }

  // Gửi dữ liệu lên MQTT
  String str_distance = String(distance);
  client.publish("/IOT/BTL/distance", str_distance.c_str());

  String str_waterPercentage = String(waterPercentage);
  client.publish("/IOT/BTL/percent", str_waterPercentage.c_str());

  delay(1000);  // Đợi 1 giây trước khi đo lại khoảng cách
}

void controlPump(bool on) {
  // Điều khiển relay và gửi trạng thái lên MQTT
  digitalWrite(RELAY_PIN, on ? HIGH : LOW);
  pumpStatus = on;
}
