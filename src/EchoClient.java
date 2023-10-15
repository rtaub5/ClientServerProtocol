import java.io.*;
import java.net.*;

public class EchoClient
{
    public static void main(String[] args)
    {
        if (args.length != 2)
        {
            System.err.println("Usage java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                )
        {
            String userInput;
             while ((userInput = stdIn.readLine()) != null)
             {
                 out.println(userInput);
                 System.out.println("echo: " + in.readLine());
             }
        }
        catch (IOException exc1)
        {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}
