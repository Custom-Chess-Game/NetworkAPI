package com.github.smuddgge.server;

import com.github.smuddgge.connections.Connection;
import com.github.smuddgge.connections.ServerConnection;
import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Represents a server
 */
public class Server {

    /**
     * Status of the server
     */
    private boolean running = true;

    /**
     * Server socket
     */
    private final ServerSocket serverSocket;

    /**
     * The current connections to clients
     */
    private static ArrayList<ServerConnection> connections = new ArrayList<>();

    /**
     * Used to initialise the server
     * @param port The socket port
     */
    public Server(int port) throws IOException {
        // Create a server socket
        serverSocket = new ServerSocket(port);

        Console.print("[Server] " + ConsoleColour.GREEN + "Listening for connections on " + ConsoleColour.YELLOW + port);
    }

    /**
     * Used to run the server
     */
    public void run() {
        while(this.running) {
            try {
                // Wait for new client connection
                Socket client = serverSocket.accept();

                Console.print("[Server] " + ConsoleColour.PINK + "Client connected " + ConsoleColour.YELLOW + client.getInetAddress());

                // Thread the client
                ServerConnection serverThread = new ServerConnection(client, this);
                Server.connections.add(serverThread);

                Thread thread = new Thread(serverThread::run);
                thread.start();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Used to stop the server
     */
    public void stop() {
        Console.print("[Server] " + ConsoleColour.GREEN + "Stopping server");

        for (ServerConnection serverConnection : this.connections) {
            serverConnection.stop();
        }

        this.running = false;

        Console.print("[Server] " + ConsoleColour.GREEN + "Server stopped");
    }

    /**
     * Get the connections to the server
     * @return List of connections to the server
     */
    public ArrayList<ServerConnection> getConnections() {
        return Server.connections;
    }
}
