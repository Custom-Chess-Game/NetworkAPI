package com.github.smuddgge.server;

import com.github.smuddgge.connections.ServerThreadConnection;
import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;
import com.github.smuddgge.utility.GameRoom;
import com.github.smuddgge.utility.PlayerProfile;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

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
    private ArrayList<ServerThreadConnection> connections = new ArrayList<>();

    /**
     * The active game rooms
     */
    private ArrayList<GameRoom> gameRooms = new ArrayList<>();

    /**
     * Weather or not the threads should start with debug mode
     */
    private boolean debugMode = false;

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
                ServerThreadConnection serverThread = new ServerThreadConnection(client, this);
                serverThread.setDebugMode(debugMode);
                this.connections.add(serverThread);

                Thread thread = new Thread(serverThread::run);
                thread.start();
            }
            catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Used to stop the server
     */
    public void stop() {
        Console.print("[Server] " + ConsoleColour.GREEN + "Stopping server");

        // Stop listening for connections
        this.running = false;

        // Stop all threads
        for (ServerThreadConnection serverConnection : this.connections) {
            serverConnection.stop();
        }

        Console.print("[Server] " + ConsoleColour.GREEN + "Server stopped");
    }

    /**
     * Used to set debug mode to a certain value for all threads
     * @param debugMode True to turn on debug mode for all threads
     */
    public void setDebugMode(boolean debugMode) {
        for (ServerThreadConnection serverConnection : this.connections) {
            serverConnection.setDebugMode(debugMode);
        }

        this.debugMode = debugMode;
    }

    /**
     * Used to add a game room
     * @param gameRoom Game room to add
     */
    public void addGameRoom(GameRoom gameRoom) {
        this.gameRooms.add(gameRoom);

        if (this.debugMode) Console.print("[server] " + ConsoleColour.WHITE + "New game room created: " + gameRoom.uuid.toString());
    }

    /**
     * Get the connections to the server
     * @return List of connections to the server
     */
    public ArrayList<ServerThreadConnection> getConnections() {
        return this.connections;
    }

    /**
     * Used to get all active game rooms
     */
    public ArrayList<GameRoom> getGameRooms() {
        return this.gameRooms;
    }

    /**
     * Used to get the game room given a player in the room
     * @param playerProfile The players profile
     * @return The instance of the game room
     */
    public GameRoom getGameRoom(PlayerProfile playerProfile) {
        for (GameRoom gameRoom : this.getGameRooms()) {
            if (!gameRoom.containsPlayer(playerProfile.uuid)) continue;
            return gameRoom;
        }
        return null;
    }

    /**
     * Used to get the game room given the game room uuid
     * @param uuid UUID of the game room
     * @return The game room instance
     */
    public GameRoom getGameRoom(UUID uuid) {
        for (GameRoom gameRoom : this.getGameRooms()) {
            if (Objects.equals(gameRoom.uuid.toString(), uuid.toString())) return gameRoom;
        }
        return null;
    }

    /**
     * Used to remove a game room from active game rooms
     * @param gameRoom Game room to remove
     */
    public void removeGameRoom(GameRoom gameRoom) {
        this.gameRooms.remove(gameRoom);

        if (this.debugMode) Console.print("[server] " + ConsoleColour.WHITE + "Game room removed: " + gameRoom.uuid.toString());
    }

    /**
     * Used to get if the server is in debug mode
     * @return True if in debug mode
     */
    public boolean getDebugMode() {
        return this.debugMode;
    }
}
