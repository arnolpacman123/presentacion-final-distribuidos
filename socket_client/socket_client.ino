#include <DHT.h>
#include <ESP8266WiFi.h>
#include <NTPClient.h>
#include <WiFiUdp.h>
#include <WiFiClient.h>
#include "functions.h"

#define DHTTYPE DHT11
#define DHTPIN D3
DHT dht(DHTPIN, DHTTYPE);

const char* ssid = "Flia. Guevara";
const char* password = "erika2506";

const uint32_t chipId = ESP.getChipId();

const uint16_t port = 5000;
const char *host = "192.168.0.6";

// GMT-4 - Bolivia | For UTC-4.00 : -4 * 60 * 60 : -14400
const long utcOffsetInSeconds = -4 * 60 * 60;

// Define NTP Client to get time
WiFiUDP ntpUDP;
NTPClient timeClient(ntpUDP, "pool.ntp.org", utcOffsetInSeconds);

WiFiClient client;
void setup()
{
  Serial.begin(9600);

  dht.begin();
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(5000);
    Serial.print(".");
  }
  Serial.println();
  Serial.println("WiFi conectado");
  Serial.print("Ip address: ");
  Serial.println(WiFi.localIP());
  Serial.println("----------------------Temperatura y Humedad----------------------");
  Serial.println();
  timeClient.begin();
  if (!client.connect(host, port))
  {
    Serial.println("Conexion al servidor fallida");
    delay(1000);
    return;
  }
}

String getMessage()
{
  time_t epochTime = timeClient.getEpochTime();
  float temperature = dht.readTemperature();
  float humidity = dht.readHumidity();
  String message = "ID=" + String(chipId) + "|Temp=" + String(temperature) + "|Hum=" + String(humidity) + "|Tiempo=" + String(epochTime);
  return message;
}

void loop()
{
  timeClient.update();
  String message = getMessage();
  Serial.println(message);
  client.println(message);
  client.flush();
  delay(1000);
}
