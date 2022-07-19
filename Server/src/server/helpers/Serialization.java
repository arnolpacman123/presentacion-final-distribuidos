package server.helpers;

import models.DataClient;

public abstract class Serialization {
    public static String client;
    public static String temperature;
    public static String humidity;
    public static String time;

    public static void deserialize(String message) {
        String[] fields = message.split("\\|");
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].split("=")[1];
        }
        client = fields[0];
        temperature = fields[1];
        humidity = fields[2];
        time = fields[3];
    }
}
