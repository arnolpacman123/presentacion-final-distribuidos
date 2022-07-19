package server.info;

import java.net.Socket;

public class ConnectionInfo {
    private String id;
    private String host;
    private Socket clientSocket;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public ConnectionInfo(String id, String host, Socket clientSocket) {
        this.id = id;
        this.host = host;
        this.clientSocket = clientSocket;
    }

}
