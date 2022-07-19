package server.events;

import models.Data;

import java.net.Socket;
import java.util.EventObject;

public class EventSend extends EventObject {
    Socket clientSocket;
    Object pack;
    Data data;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Object getPack() {
        return pack;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void setPack(Object pack) {
        this.pack = pack;
    }

    public EventSend(Object source, Socket clientSocket, Object pack) {
        super(source);
        this.clientSocket = clientSocket;
        this.pack = pack;
        this.data = (Data) pack;
    }
}

