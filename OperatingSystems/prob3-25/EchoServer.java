/**
Author: Lucas Kushner

Implements an EchoServer which sends back the message that 
it receives from the EchoClient
**/

import java.net.*;
import java.io.*;

public class EchoServer
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket sock = new ServerSocket(6013);

            while(true)
            {
                Socket client = sock.accept();

                InputStreamReader in = new InputStreamReader(
                        client.getInputStream());

                OutputStreamWriter out = new OutputStreamWriter(
                        client.getOutputStream());

                // Read in message from the client
                int data = in.read();
                while (in.ready())
                {
                    char c = (char)data;

                    // Echo back the message
                    out.write(c);
                    System.out.print(c);
                    data = in.read();
                }
                System.out.println("Finished echoing data");

                out.close();
                client.close();
            }
        } 
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
