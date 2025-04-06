#include <Arduino.h>
#include <WiFi.h>
#include "secrets.h"

#include <WiFiClientSecure.h>
#include <MQTTClient.h>
#include <ctime>

// Function declarations
int myFunction(int, int);

// The MQTT topics that this device should publish/subscribe
#define AWS_IOT_PUBLISH_TOPIC "esp32/pub"
#define AWS_IOT_SUBSCRIBE_TOPIC "esp32/sub"
#define THINGNAME "IoTController1" // Replace with your actual thing name

WiFiClientSecure net = WiFiClientSecure();
MQTTClient client = MQTTClient(256);

// Declare the messageHandler function before it's used
void messageHandler(String &topic, String &payload);

// Time zone and NTP settings
const long gmtOffset_sec = 3600;        // GMT offset in seconds (adjust for your timezone)
const int daylightOffset_sec = 3600;    // Daylight saving offset in seconds
const char *ntpServer = "pool.ntp.org"; // NTP server

String station_number = "99";

unsigned long previousMillis = 0; // Variable to store the last time the interval was updated
const long interval = 2000;       // Interval in milliseconds

unsigned long previousResetMillis = 0; // Variable to store the last time the reset was done
const long resetInterval = 1800000;    // Interval to reset (in milliseconds)

void wifiSetup()
{
  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  Serial.print("Connecting to WiFi...");
  unsigned long startAttemptTime = millis();

  while (WiFi.status() != WL_CONNECTED)
  {
    delay(1000);
    Serial.print(".");
    Serial.print(" WiFi Status: ");
    Serial.println(WiFi.status()); // Debugging

    if (millis() - startAttemptTime > 10000)
    {
      Serial.println("\nFailed to connect to WiFi!");
      return;
    }
  }

  Serial.println("\nConnected to WiFi!");
  Serial.print("IP Address: ");
  Serial.println(WiFi.localIP());
}

void connectAWS()
{
  WiFi.mode(WIFI_STA);
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);

  Serial.println("Connecting to Wi-Fi");

  while (WiFi.status() != WL_CONNECTED)
  {
    delay(500);
    Serial.print(".");
  }

  // Configure WiFiClientSecure to use the AWS IoT device credentials
  net.setCACert(AWS_CERT_CA);
  net.setCertificate(AWS_CERT_CRT);
  net.setPrivateKey(AWS_CERT_PRIVATE);

  // Connect to the MQTT broker on the AWS endpoint we defined earlier
  client.begin(AWS_IOT_ENDPOINT, 8883, net);

  // Create a message handler
  client.onMessage(messageHandler);

  Serial.print("Connecting to AWS IOT");

  while (!client.connect(THINGNAME))
  {
    Serial.print(".");
    delay(100);
  }

  if (!client.connected())
  {
    Serial.println("AWS IoT Timeout!");
    return;
  }

  // Subscribe to a topic
  client.subscribe(AWS_IOT_SUBSCRIBE_TOPIC);

  Serial.println("AWS IoT Connected!");

  // init and get the time
  configTime(gmtOffset_sec, daylightOffset_sec, ntpServer);
  // printLocalTime();
}

void messageHandler(String &topic, String &payload) {
  Serial.println("Incoming: " + topic + " - " + payload);
  
  // Handle incoming messages here if needed
  // For example, you could parse commands or configuration changes
}

void generateSimulatedData(float &x, float &y, float &z)
{
  // Simulate accelerometer or similar sensor data
  x = random(-100, 100) / 10.0; // Random value between -10.0 and 10.0
  y = random(-100, 100) / 10.0;
  z = random(-100, 100) / 10.0;
}

void publishMessage()
{
  // Generate simulated sensor data
  float x, y, z;

  // Simulate accelerometer or similar sensor data
  x = random(-100, 100) / 10.0; // Random value between -10.0 and 10.0
  y = random(-100, 100) / 10.0;
  z = random(-100, 100) / 10.0;

  // Get current time if needed
  struct tm timeinfo;
  char timestamp[30];

  if (getLocalTime(&timeinfo))
  {
    strftime(timestamp, sizeof(timestamp), "%Y-%m-%d %H:%M:%S", &timeinfo);
  }
  else
  {
    strcpy(timestamp, "Time not available");
  }

  // Create JSON document
  char jsonBuffer[256];
  snprintf(jsonBuffer, sizeof(jsonBuffer),
           "{\"station\":\"%s\",\"timestamp\":\"%s\",\"data\":{\"x\":%.2f,\"y\":%.2f,\"z\":%.2f}}",
           station_number.c_str(), timestamp, x, y, z);

  // Publish to AWS IoT
  if (client.connected())
  {
    if (client.publish(AWS_IOT_PUBLISH_TOPIC, jsonBuffer))
    {
      Serial.print("Published: ");
      Serial.println(jsonBuffer);
    }
    else
    {
      Serial.println("Failed to publish");
    }
  }
  else
  {
    Serial.println("Client not connected, reconnecting...");
    connectAWS();
  }
}

void setup()
{
  Serial.begin(115200);
  Serial.println("System Start ...");

  // Initialize random seed for simulation
  randomSeed(analogRead(0));

  wifiSetup();
  connectAWS();
}

void loop()
{
  // Check WiFi and AWS connection
  if (!client.connected())
  {
    connectAWS();
  }

  unsigned long currentMillis = millis();

  if (currentMillis - previousMillis >= interval)
  {
    previousMillis = currentMillis;
    publishMessage();
    client.loop();
  }

  // Reset every 30 minutes (1800000 ms)
  if (currentMillis - previousResetMillis >= resetInterval)
  {
    previousResetMillis = currentMillis;
    Serial.println("Resetting ESP32...");
    ESP.restart();
  }
}
// Function definitions
int myFunction(int x, int y)
{
  return x + y;
}

void printLocalTime() {
  struct tm timeInfo;
  if (getLocalTime(&timeInfo)) {
    Serial.print("Current time: ");
    Serial.print(timeInfo.tm_hour);
    Serial.print(":");
    Serial.print(timeInfo.tm_min);
    Serial.print(":");
    Serial.println(timeInfo.tm_sec);
  } else {
    Serial.println("Failed to obtain time");
  }
}
