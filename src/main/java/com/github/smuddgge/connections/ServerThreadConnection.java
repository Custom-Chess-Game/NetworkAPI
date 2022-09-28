package com.github.smuddgge.connections;

import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;
import com.github.smuddgge.managers.ServerThreadNetworkManager;
import com.github.smuddgge.server.Server;

import java.io.IOException;
import java.net.Socket;

/**
 * Represents a communication between the server and client
 * This is a thread on the servers end
 */
public class ServerThreadConnection extends Connection {

    /**
     * Status of the connection
     */
    private boolean running = true;

    /**
     * Instance of the server
     */
    private final Server server;

    /**
     * The network manager for the server thread
     * This will manage the data being sent to the server thread
     */
    private final ServerThreadNetworkManager networkManager;

    /**
     * Used to create a server thread
     *
     * @param socket The socket connected to the client
     * @param server The server the thread is running on
     * @throws IOException Socket error
     */
    public ServerThreadConnection(Socket socket, Server server) throws IOException {
        super(socket);

        this.server = server;
        this.networkManager = new ServerThreadNetworkManager(this, this.server);
    }

    /**
     * Threaded method
     */
    public void run() {
        while (this.running) {
            try {

                if (this.socket != null && this.socket.isClosed()) return;

                String data = read();

                if (data == null) this.stop();

                networkManager.interpret(data);

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

        // Remove game rooms the player was in
        this.server.removeGameRoom(this.networkManager.getPlayerProfile());

        Console.log(ConsoleColour.GRAY + "[Server Thread] Thread stopped on socket: " + this.socket);
    }

    /**
     * Used to get the threads network manager
     *
     * @return Network manager
     */
    public ServerThreadNetworkManager getNetworkManager() {
        return this.networkManager;
    }
}
