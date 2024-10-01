package TheIncredibles.clack;

import TheIncredibles.clack.*;
import TheIncredibles.clack.endpoint.Client;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException
    {
        Client myClient = new Client("olaolu");
        myClient.start();
    }
}
