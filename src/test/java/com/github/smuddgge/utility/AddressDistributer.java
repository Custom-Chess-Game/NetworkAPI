package com.github.smuddgge.utility;

/**
 * Used to distribute ports to each test
 * This makes sure each test is run on a different port
 */
public class AddressDistributer {

    /**
     * The current port
     */
    private static int currentPort = 17000;

    /**
     * Used to get the next port available
     * @return The port
     */
    public static int next() {
        AddressDistributer.currentPort ++;

        return AddressDistributer.currentPort;
    }
}
