package server.listeners;

import server.events.EventConnection;
import server.events.EventDisconnection;
import server.info.ConnectionInfo;
import server.interfaces.SocketListenerI;

import javax.swing.event.EventListenerList;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener extends Thread {
    ServerSocket serverSocket;
    Socket clientSocket;
    boolean condition = true;
    private final EventListenerList listenerList;

    public ConnectionListener(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        this.listenerList = new EventListenerList();
    }

    public void addConnectionListener(SocketListenerI listener) {
        this.listenerList.add(SocketListenerI.class, listener);
    }

    public void removeConnectionListener(SocketListenerI listener) {
        this.listenerList.remove(SocketListenerI.class, listener);
    }

    @Override
    public void run() {
        super.run();
        this.listen();
    }

    private void listen() {
        System.out.println("Servidor Iniciado");
        try {
            while (condition) {
                clientSocket = this.serverSocket.accept();
                ConnectionInfo connectionInfo = new ConnectionInfo(clientSocket.getRemoteSocketAddress().hashCode() + "", clientSocket.getInetAddress().toString(), clientSocket);
                EventConnection eventConnection = new EventConnection(this, connectionInfo);
                dispatchOnConnect(eventConnection);
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.close();
        }
    }

    public void changeCondition() {
        condition = false;
    }

    public void dispatchOnConnect(EventConnection e) {
        Object[] listeners = this.listenerList.getListeners(SocketListenerI.class);
        for (Object o : listeners) {
            SocketListenerI listener = (SocketListenerI) o;
            listener.onClientConnect(e);
        }
    }

    public void dispatchOnDisconnect(EventDisconnection e) {
        Object[] listeners = this.listenerList.getListeners(SocketListenerI.class);
        for (Object o : listeners) {
            SocketListenerI listener = (SocketListenerI) o;
            listener.onClientDisconnect(e);
        }
    }

    private void close() {
        try {
            EventDisconnection eventDisconnection = new EventDisconnection(this, new ConnectionInfo(clientSocket.getRemoteSocketAddress().hashCode() + "", clientSocket.getInetAddress().toString(), clientSocket));
            dispatchOnDisconnect(eventDisconnection);
            this.clientSocket.close();
        } catch (IOException ignored) {
        }
    }
}
