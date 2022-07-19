package server.interfaces;

import server.events.EventConnection;
import server.events.EventDisconnection;
import server.events.EventReceive;
import server.events.EventSend;

import java.util.EventListener;

public interface SocketListenerI extends EventListener {
    public void onClientConnect(EventConnection e);
    public void onClientDisconnect(EventDisconnection e);
    public void onSendMessage(EventSend e);
    public void onReceiveMessage(EventReceive e);
}

