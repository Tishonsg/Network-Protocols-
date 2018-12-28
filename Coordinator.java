/**
 *
 * Node Coordinator - DDOS simulator
 *
 * This is one of three components in creating a DDOS attack
 * This code dictates the action taken by the Coordinator when creating a connection to multiple clients(attackers)
 *
 * Exceptions
 * Notable exceptions where problems might arise from user input
 *
 * Improvements
 * For scalability a constructor could have been implemented to create connections to attackers,
 * and limit the repeating code utilized in developing this code
 *
 *
 * Notable Mentions in executing this code
 *
 * Please note that attcaker 1 corresponds to the first instance of  client executed,
 * attacker 2 corresponds to the second instance of client executed, and ...
 *
 * @author Tishon S. Gumbs
 * @version 2.0
 * @since 2018-11-06
 */





import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class Coordinator {

    //Global variables
    public static int PORT_1;
    public static int PORT_2;
    public static int PORT_3;

    public static String connection_1;
    public static String connection_2;
    public static String connection_3;


    public static void main(String args[]) throws Exception {

        /* Variable Declarations */

        Socket socket1 = null;
        Socket socket2 = null;
        Socket socket3 = null;
        String prompt = "attack";


        //Prompt user for  ip address and PORT numbers
        try (Scanner sc = new Scanner(System.in))
        {
            System.out.println("Enter host address of attacker 1");
            connection_1 = sc.next();
            System.out.println("Enter port to attacker 1");
            PORT_1 = sc.nextInt();

            System.out.println("Enter host address of attacker 2");
            connection_2 = sc.next();
            System.out.println("Enter port to attacker 2");
            PORT_2 = sc.nextInt();

            System.out.println("Enter host address of attacker 3");
            connection_3 = sc.next();
            System.out.println("Enter port to attacker 3");
            PORT_3 = sc.nextInt();
        }



        //***************** CONNECTION 1 *****************
        // Connection 1
        try {
            socket1 = new Socket(connection_1, PORT_1);
            System.out.println("Connection Established with attacker 1");
        } catch (IOException e) {
            System.out.println("Failed to connect to attacker 1 \n");

        }
        //Establish communication with Coordinator
        BufferedReader sin1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        PrintStream sout1 = new PrintStream(socket1.getOutputStream());
        //trigger DDOS with attacker 1
        sout1.println(prompt);

        //***************** CONNECTION 2 *****************
        //Connection 2
        try {
            socket2 = new Socket(connection_2, PORT_2);
            System.out.println("Connection Established with attacker 2");
        } catch (IOException e) {
            System.out.println("Failed to connect to attacker 2 \n");

        }
        //Establish communication with Coordinator
        BufferedReader sin2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
        PrintStream sout2 = new PrintStream(socket2.getOutputStream());

        //trigger DDOS with attacker 2
        sout2.println(prompt);

        //***************** CONNECTION 3 *****************
        //Connection 3
        try {
            socket3 = new Socket(connection_3, PORT_3);
            System.out.println("Connection Established with attacker 3");
        } catch (IOException e) {
            System.out.println("Failed to connect to attacker 3");

        }

        //Establish communication with Coordinator
        BufferedReader sin3 = new BufferedReader(new InputStreamReader(socket3.getInputStream()));
        PrintStream sout3 = new PrintStream(socket3.getOutputStream());

        //trigger DDOS with attacker 3
        sout3.println(prompt);

    }
}