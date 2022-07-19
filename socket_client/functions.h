#include <ESP8266WiFi.h>

void connectToNetworking()
{
    Serial.println("Intentando conectar a internet...");
    WiFi.beginWPSConfig();
    delay(1000);
    if (WiFi.status() == WL_CONNECTED)
    {
        Serial.println("¡Conectado a internet!");
        Serial.println(WiFi.localIP());
        Serial.println(WiFi.SSID());
        Serial.println(WiFi.macAddress());
    }
    else
    {
        Serial.println("¡Conexión fallida!");
    }
}

void tryConnectToNetworking()
{
    while (WiFi.status() != WL_CONNECTED)
    {
        connectToNetworking();
    }
}