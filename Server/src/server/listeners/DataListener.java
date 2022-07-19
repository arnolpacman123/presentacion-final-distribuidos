package server.listeners;

import controllers.DeviceController;
import entities.Devices;
import firebase.PushNotification;
import server.events.EventDisconnection;
import server.events.EventReceive;
import server.events.EventSend;
import server.info.ConnectionInfo;
import server.interfaces.SocketListenerI;

import javax.swing.event.EventListenerList;
import java.io.*;
import java.net.Socket;
import server.helpers.Serialization;

public class DataListener extends Thread {

    Socket clientSocket;
    boolean condition = true;
    EventListenerList listenerList;
    InputStream input;
    int TIMEOUT_REACHABLE = 60000;
    String clientId = "";

    public DataListener(Socket clientSocket) {
        this.clientSocket = clientSocket;
        this.listenerList = new EventListenerList();
    }

    @Override
    public void run() {
        super.run();
        this.listen();
    }

    public void changeCondition() {
        condition = false;
    }

    private void listen() {
        System.out.println("Cliente nuevo conectado");
        try {
            System.out.println("Escuchando el mensaje del cliente");
            while (condition) {
                input = clientSocket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
                String message = null;
                if (bufferedReader.ready()) {
                    message = bufferedReader.readLine();
                }
                if (!clientSocket.isClosed() && message != null && !"".equals(message)) {
                    Serialization.deserialize(message);
                    clientId = Serialization.client;
                    EventReceive eventReceive = new EventReceive(this, clientSocket, message);
                    dispatchOnReceive(eventReceive);
                } else {
                    if (!this.clientSocket.getInetAddress().isReachable(TIMEOUT_REACHABLE)) {
                        System.out.println("El cliente no respondio en 1 minuto");
                        DeviceController deviceController = new DeviceController();
                        Devices device = deviceController.getDeviceByIdentifier(clientId);
                        device.setConnected(false);
                        deviceController.save(device);
                        PushNotification.sendNotification("Dispositivo desconectado", "El dispositivo " + clientId + " se desconecto", clientId);
                        EventDisconnection eventDisconnection = new EventDisconnection(this, new ConnectionInfo(clientSocket.getRemoteSocketAddress().hashCode() + "", clientSocket.getInetAddress().toString(), clientSocket));
                        dispatchOnDisconnect(eventDisconnection);
                    }
                }
            }
        } catch (IOException ignored) {
            this.close();
        }
    }

    public void addDataListener(SocketListenerI listener) {
        listenerList.add(SocketListenerI.class, listener);
    }

    public void removeDataListener(SocketListenerI listener) {
        listenerList.remove(SocketListenerI.class, listener);
    }

    public void dispatchOnReceive(EventReceive e) {
        Object[] listeners = listenerList.getListeners(SocketListenerI.class);
        for (Object o : listeners) {
            SocketListenerI listener = (SocketListenerI) o;
            listener.onReceiveMessage(e);
        }
    }

    public void dispatchOnSend(EventSend e) {
        Object[] listeners = listenerList.getListeners(SocketListenerI.class);
        for (Object o : listeners) {
            SocketListenerI listener = (SocketListenerI) o;
            listener.onSendMessage(e);
        }
    }

    public void dispatchOnDisconnect(EventDisconnection e) {
        Object[] listeners = listenerList.getListeners(SocketListenerI.class);
        for (Object o : listeners) {
            SocketListenerI listener = (SocketListenerI) o;
            listener.onClientDisconnect(e);
        }
    }

    private void close() {
        try {
            input.close();
            EventDisconnection eventDisconnection = new EventDisconnection(this, new ConnectionInfo(clientSocket.getRemoteSocketAddress().hashCode() + "", clientSocket.getInetAddress().toString(), clientSocket));
            dispatchOnDisconnect(eventDisconnection);
            this.clientSocket.close();
        } catch (IOException ignored) {
        }
    }

    private String getMultiline(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                // Server closed connection
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                // No more lines in the server response
                break;
            }
            if ((line.length() > 0) && (line.charAt(0) == '.')) {
                // The line starts with a "." - strip it off.
                line = line.substring(1);
            }
            // Add read line to the list of lines
            lines = lines + "\n" + line;
        }
        return lines;
    }
}
