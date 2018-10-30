

/**
 * This is one of two components in creating a client-server socket
 * This code dictates the action taken by the server when creating a connection between the server, and client
 * respective sockets.
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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    /*VARIABLES*/

    int port;
    ServerSocket server = null;
    Socket client = null;
    //ExecutorService automatically provides a pool of threads and API for assigning tasks to it.
    ExecutorService pool = null;
    int clientcount = 0;

    /* Create server object & start server socket establishment */
    public static void main(String[] args) throws IOException {
        Server serverObj = new Server(0);
        serverObj.startServer();
    }

    /* Constructor for port number */
    Server(int port) {
        this.port = port;
        // Limit clients to a maximum of 3
        pool = Executors.newFixedThreadPool(3);
    }

    public void startServer() throws IOException {


        server = new ServerSocket(7000);
        System.out.println("Server Established");
        while (true) {
            //keep the server on until the client is accepted
            client = server.accept();
            clientcount++;
            ServerThread runnable = new ServerThread(client, clientcount, this);
            pool.execute(runnable);
        }

    }

    private static class ServerThread implements Runnable {

        Server server = null;
        Socket client = null;
        int id;


        /* Time Variables for run method */


        //Initializing constructor
        ServerThread(Socket client, int count, Server server) throws IOException {

            this.client = client;
            this.server = server;
            this.id = count;
            System.out.println("Connection " + id + " established with client " + client + " time " + System.currentTimeMillis());

        }


        @Override
        public void run() {
            long start = System.currentTimeMillis();

            while (true) {
                long current = System.currentTimeMillis();
                if (current - start >= 30000)
                    break;
            }

            System.out.println("Connection ended by server");
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}



