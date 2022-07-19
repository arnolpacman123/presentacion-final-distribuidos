package server.events;

import server.info.ConnectionInfo;

import java.util.EventObject;

public class EventConnection extends EventObject {
    ConnectionInfo client;

    public ConnectionInfo getClient() {
        return client;
    }

    public void setClient(ConnectionInfo client) {
        this.client = client;
    }

    public EventConnection(Object source, ConnectionInfo client) {
        super(source);
        this.client = client;
    }
}