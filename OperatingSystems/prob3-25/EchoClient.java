/**
Author: Lucas Kushner

Implements an echo client which sends a message to an EchoServer and then 
prints out the response
**/

import java.net.*;
import java.io.*;

public class EchoClient
{
    public static void main(String[] args)
    {
        try
        {
            Socket sock = new Socket("127.0.0.1", 6013);

            PrintWriter serverOut = new PrintWriter(
                        sock.getOutputStream(), true);

            InputStreamReader in = new InputStreamReader(
                    sock.getInputStream());

            // Send the message
            System.out.println("Sending message " + args[0]);
            serverOut.println(args[0]);

            // Read the response
            int data = in.read();
            while (data != -1)
            {
                char c = (char)data;
                System.out.print(c);
                data = in.read();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
