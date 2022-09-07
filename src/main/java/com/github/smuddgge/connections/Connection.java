package com.github.smuddgge.connections;

import com.github.smuddgge.console.Console;
import com.github.smuddgge.console.ConsoleColour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Represents a connection to a socket
 */
public abstract class Connection {

    /**
     * The link between the client and server
     */
    protected Socket socket;

    /**
     * The printer used to send information though the socket
     */
    protected PrintWriter printWriter;

    /**
     * The buffered reader used to read lines from the socket
     */
    protected BufferedReader bufferedReader;

    /**
     * Used to initialise the connection to the socket
     * @param socket Socket to connect to
     */
    public Connection(Socket socket) throws IOException {
        Console.print(ConsoleColour.WHITE + "Connecting to socket " + socket.getLocalAddress() + " " + socket.getPort());

        // Used to send information to the server
        this.printWriter = new PrintWriter(socket.getOutputStream(), true);

        // Used to receive information from the server
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Console.print(ConsoleColour.YELLOW + "Connected to " + socket.getInetAddress());
    }

    /**
     * Used to initialise the connection to the socket
     * @param host The host
     *             localhost if running on the same machine
     * @param port The port the server is running on
     */
    public Connection(String host, int port) throws IOException {
        Console.print(ConsoleColour.WHITE + "Connecting to socket " + host + " " + port);

        // Connecting to the server with a socket
        this.socket = new Socket(host, port);

        // Used to send information to the server
        this.printWriter = new PrintWriter(socket.getOutputStream(), true);

        // Used to receive information from the server
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Console.print(ConsoleColour.YELLOW + "Connected to " + host + " " + port);
    }

    /**
     * Used to send data though the socket
     * @param data Data to send
     */
    public void send(String data) {
        this.printWriter.println(data);
    }

    /**
     * Used to read a line from the socket
     * @throws IOException Read error
     * @return Data read from the socket
     */
    public String read() throws IOException {
        return this.bufferedReader.readLine();
    }
}
