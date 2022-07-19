package server.info;

import server.listeners.DataListener;

import java.net.Socket;

public class ThreadInfo {
    private Socket clientSocket;
    private DataListener dataListener;

    public ThreadInfo(Socket clientSocket, DataListener dataListener) {
        this.clientSocket = clientSocket;
        this.dataListener = dataListener;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public DataListener getDataListener() {
        return dataListener;
    }

    public void setDataListener(DataListener dataListener) {
        this.dataListener = dataListener;
    }
}