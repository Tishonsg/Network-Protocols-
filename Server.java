/**
 *
 * Victim Server - DDOS simulator
 *
 * This is one of three components in creating a DDOS attack
 * This code dictates the action taken by the server when creating a connection between the server (victim), and multiple clients
 *
 * Exceptions
 *
 * Notable exceptions where this application may not work, is when the server port selected is unavailable
 * An exception will be thrown
 *
 * Improvements
 * Implement timer for server to force time out and disconnection, presently server remains on
 *
 * @author Tishon S. Gumbs
 * @version 2.0
 * @since 2018-11-06
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    /*VARIABLES*/

    static int termination = -1;
    int port;
    ServerSocket server = null;
    Socket client = null;

    //ExecutorService automatically provides a pool of threads and API for assigning tasks to it.
    ExecutorService pool = null;
    int clientcount = 0;

    //Default port
    public static int default_port;

    /* Create server object & start server socket establishment */
    public static void main(String[] args) throws IOException {

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter port number to enter server mode ");
            default_port = sc.nextInt();
            sc.close();
        }
        Server serverObj = new Server(default_port);
        serverObj.startServer();
    }

    /* Constructor for port number */
    Server(int port) {
        this.port = port;
        // Limit clients to a maximum of 3
        pool = Executors.newFixedThreadPool(3);
    }

    public void startServer() throws IOException {


        //Create a new instance of a server socket
        server = new ServerSocket(default_port);
        System.out.println("Server Established");


        //Redirect output to log file
        try {
            PrintStream log = new PrintStream(new File("Server.log"));
            System.setOut(log);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


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

        PrintStream clientOut;
        BufferedReader clientIn;

        int id;


        //Initializing constructor
        ServerThread(Socket client, int count, Server server) throws IOException {

            this.client = client;
            this.server = server;
            this.id = count;


            clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
            clientOut = new PrintStream(client.getOutputStream());


        }


        @Override
        public void run() {

            // Check for timer
            boolean time_up = false;
            //Initialize start time of connection
            long start = System.currentTimeMillis();
            long duration = 30000;
            String message;

            // Determine if this is the first client to attempt to connect to the server
            System.out.println("Connection " + id + " established with client " + client + " time " + System.currentTimeMillis());

            while (true) {
                long current = System.currentTimeMillis();
                //Time out connection to client
                if ((current - start) > duration)
                {
                    time_up = true;
                    break;
                }

            }

            message = " and connection  has successfully  been ended";
            clientOut.println(start + message);


            // Attempt to close all connections to client
            try {
                clientIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientOut.close();

            if (time_up == true) {
                System.out.println("Connection " + id + " Server timed out");
            }
        }
    }
}
