import java.util.*;
import java.io.*;
import java.net.*;

public class SimpleServer
{
    static Random rnd = new Random();
    static MagicEightBall magicEightBall = new MagicEightBall(rnd);

    public static void main(String[] args)
    // hard code in port number if necessary
    // args = new String [] {"30121"};
    {
        if (args.length != 1)
        {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
                Socket clientSocket1 = serverSocket.accept();
                PrintWriter responseWriter1 = new PrintWriter(clientSocket1.getOutputStream(), true);
                BufferedReader requestReader1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
        )
        {
            String usersRequest;
            while ((usersRequest = requestReader1.readLine()) != null)
            {
                System.out.println("\"" + usersRequest + "\" received");
                String response = magicEightBall.getNextAnswer();
                System.out.println("Responding: \"" + response + "\"");
                responseWriter1.println(response);
            }
        } catch (IOException exc1)
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber +
                    "or listening for a connection.");
            System.out.println(exc1.getMessage());
        }
    }
}




