package server.events;

import models.Data;
import models.DataClient;

import java.net.Socket;
import java.util.EventObject;

public class EventReceive extends EventObject {
    Socket clientSocket;
    String dataClient;

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public String getDataClient() {
        return dataClient;
    }

    public void setDataClient(String dataClient) {
        this.dataClient = dataClient;
    }

    public EventReceive(Object source, Socket clientSocket, String dataClient) {
        super(source);
        this.clientSocket = clientSocket;
        this.dataClient = dataClient;
    }
}

