package org.analyzer;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Runnable server = new TCPServer();
        Thread serverThread = new Thread(server);
        serverThread.start();
        Timer timer = new Timer();
        timer.schedule(new NewsFeedHandler.ShowTask(), 0, 10000);
    }
}