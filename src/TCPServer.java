import java.util.*;
import java.io.*;
import java.net.*;

public class TCPServer
{
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
            ShuffleMessage message = new ShuffleMessage("My name is Rachel Taub. I am a computer science major in Touro LCW.");
            List<String>origList = message.setOriginalMessage();
            List<String>shuffList = message.shuffle();
            List<Integer>serverPackets = message.setPacketNumbers(shuffList, origList);
            message.setNumPackets(serverPackets);
            message.randomizedSend(responseWriter1, shuffList, serverPackets);

            String usersRequest = "<>";

            while (((usersRequest = requestReader1.readLine()) != null) && (!usersRequest.equals("Done")))
            {



                boolean isNum = isNumeric(usersRequest);

                int missingPackNum = 0;
                if (isNum)
                {
                     missingPackNum = (Integer.parseInt(usersRequest));
                }
                System.out.println("SERVER RESPONDS: \"" + usersRequest + "\"");

                message.resendSinglePacket(responseWriter1, missingPackNum, serverPackets, shuffList);
            }
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
            Integer.parseInt(response);
        }
        catch (NumberFormatException exc)
        {
            return false;
        }
        return true;
    }
}
