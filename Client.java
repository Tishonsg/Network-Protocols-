
/**
 * This is one of two components in creating a client-server socket
 * This code dictates the action taken by the client when creating a connection between the server, and client
 * respective sockets
 *
 * @author Tishon S. Gumbs
 * @version 1.0
 * @Copyright (c) 2014 https://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html to Present
 * All rights reserved
 * @since 2018-20-18
 * <p>
 * This code framework was largely modeled on the documentation provided from Oracle as referenced below
 */

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String args[]) throws Exception {
        for (int i = 0; i < 3; i++) {

            try
            {
                Socket socket = new Socket("127.0.0.1", 7000);
                System.out.println("Connection Established");
            }
            catch (IOException e)
            {
                System.out.println("Failed to connect to server");

            }
        }
        System.exit(1);
    }
}




