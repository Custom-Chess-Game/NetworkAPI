package com.github.smuddgge.connections;

import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;
import com.github.smuddgge.server.Server;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents a communication between the server and client
 * This is a thread on the servers end
 */
public class ServerConnection extends Connection {

    /**
     * Status of the connection
     */
    private boolean running = true;

    /**
     * Instance of the server
     */
    private final Server server;

    /**
     * Used to create a server thread
     * @param socket The socket connected to the client
     * @param server The server the thread is running on
     * @throws IOException Socket error
     */
    public ServerConnection(Socket socket, Server server) throws IOException {
        super(socket);

        this.server = server;
    }

    /**
     * Threaded method
     */
    public void run() {
        while(this.running) {
            try {
                String data = read();
                System.out.println(data);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Used to stop the thread
     */
    public void stop() {
        this.running = false;
        Console.print(ConsoleColour.WHITE + "[Server Thread] Thread stopped on socket: " + this.socket);
    }
}
