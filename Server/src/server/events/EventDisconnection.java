package server.events;

import server.info.ConnectionInfo;

import java.util.EventObject;

public class EventDisconnection extends EventObject {
    ConnectionInfo client;

    public ConnectionInfo getClient() {
        return client;
    }

    public void setClient(ConnectionInfo client) {
        this.client = client;
    }

    public EventDisconnection(Object source, ConnectionInfo client) {
        super(source);
        this.client = client;
    }
}
