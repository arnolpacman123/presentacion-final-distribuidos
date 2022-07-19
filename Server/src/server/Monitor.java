package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import controllers.DataController;
import controllers.DeviceController;
import controllers.RuleController;

import entities.Datas;
import entities.Devices;
import entities.Rules;

import firebase.PushNotification;

import server.events.*;
import server.helpers.Serialization;
import server.interfaces.SocketListenerI;

public class Monitor implements SocketListenerI {

    public static void main(String[] args) {
        //int port = getPortProperty(args);
        Server server = new Server(5000);
        server.start();
    }

    public static int getPortProperty(String[] args) {
        try {
            Properties properties = new Properties();
            String fileName = args[0];
            String propertyPort = args[1];
            properties.load(Files.newInputStream(Paths.get(fileName)));
            String port = properties.getProperty(propertyPort);
            return Integer.parseInt(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onClientConnect(EventConnection e) {

    }

    @Override
    public void onClientDisconnect(EventDisconnection e) {

    }

    @Override
    public void onSendMessage(EventSend e) {

    }

    @Override
    public void onReceiveMessage(EventReceive e) {
        String message = e.getDataClient();
        Serialization.deserialize(message);
        System.out.println(message);
        String client = Serialization.client;
        float temperature = Float.parseFloat(Serialization.temperature);
        float humidity = Float.parseFloat(Serialization.humidity);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        Date date = new Date(Long.parseLong(Serialization.time) * 1000L);
        Datas datas = new Datas(null, Serialization.client, temperature, humidity, date);
        saveData(datas);
        Devices device = new Devices(null, client, date, temperature, humidity, true);
        saveDevice(device);
        PushNotification.sendNotification("Nueva data", "El dispositivo " + client + " envio nueva data", client);
    }

    private void saveData(Datas datas) {
        DataController dataController = new DataController();
        dataController.create(datas);
    }

    private void saveDevice(Devices device) {
        DeviceController deviceController = new DeviceController();
        deviceController.save(device);
    }

    private List<Rules> getAllRules() {
        RuleController ruleController = new RuleController();
        return ruleController.getAll();
    }

    private Rules getRuleByTemperature(float temperature) {
        List<Rules> rules = getAllRules();
        for (Rules rule : rules) {
            if (temperature >= rule.getRi() && temperature <= rule.getRf()) {
                return rule;
            }
        }
        return null;
    }
}
