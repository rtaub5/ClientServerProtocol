import javax.print.attribute.standard.Severity;
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.stream.Collectors;

public class TCPServer
{
    static Random rnd = new Random();

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
            ShuffleMessage message = new ShuffleMessage("Rocki Taub");
            List<String>origList = message.setOriginalMessage();
            List<String>shuffList = message.shuffle();
            List<Integer>serverPackets = message.setPacketNumbers(shuffList, origList);
            int numPackets = message.setNumPackets(serverPackets);
            message.randomizedSend(responseWriter1, shuffList, serverPackets);

            String usersRequest;
            List<Integer>missingPackets = new ArrayList<>();
            while ((usersRequest = requestReader1.readLine()) != null)
            {
                boolean isNum = isNumeric(usersRequest);
                if (isNum)
                {
                    missingPackets.add(Integer.parseInt(usersRequest));
                }
            }
            message.resendPackets(responseWriter1, missingPackets, serverPackets, shuffList);
        }
        catch (IOException exc1)
        {
            System.out.println("Exception caught when trying to listen on port " + portNumber +
                    "or listening for a connection.");
            System.out.println(exc1.getMessage());
        }
    }

    public static boolean isNumeric(String response)
    {
        if (response == null)
        {
            return false;
        }
        try
        {
            int num = Integer.parseInt(response);
        }
        catch (NumberFormatException exc)
        {
            return false;
        }
        return true;
    }
}
