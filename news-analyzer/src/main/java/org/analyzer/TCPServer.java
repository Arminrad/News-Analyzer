package org.analyzer;

import org.feed.NewsItem;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public class TCPServer implements Runnable {
    private static final int portNumber = 11111;

    @Override
    public void run() {
        startServer();
    }
    public static void startServer() {

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server listening on port " + portNumber);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                NewsItem receivedNewsItem = (NewsItem) inputStream.readObject();
                receivedNewsItem.setReceivedTime(LocalDateTime.now());
                NewsItemListener newsItemListener = new NewsFeedHandler();
                newsItemListener.onNewsItemReceived(receivedNewsItem);
                inputStream.close();
                clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}
