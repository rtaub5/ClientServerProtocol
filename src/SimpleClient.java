import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleClient {
    public static void main(String[] args) throws IOException {
        
		// Hardcode in IP and Port here if required
    	//args = new String[] {"127.0.0.1", "30121"};
    	
        if (args.length != 2) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (
            Socket clientSocket = new Socket(hostName, portNumber);
            PrintWriter requestWriter = // stream to write text requests to server
                new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader responseReader= // stream to read text response from server
                new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())); 
            BufferedReader stdIn = // standard input stream to get user's requests
                new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
			String serverResponse = "<>";
            List<Integer> numPackets = new ArrayList<Integer>();
            List<String> message = new ArrayList<>();
            while (!serverResponse.equals("Done"))
            {

               requestWriter.println(stdIn.readLine());

                  serverResponse = responseReader.readLine();
                  boolean isNum = isNumeric(serverResponse);
                  if (isNum)
                  {
                      numPackets.add(Integer.parseInt(serverResponse));
                  }
                  else
                  {
                      message.add(serverResponse);
                  }
                System.out.println("SERVER RESPONDS: \"" + serverResponse + "\"");

            }
            UnscrambleMessage unscrambleMessage = new UnscrambleMessage(message, numPackets);
            List<Integer>missingPackets = unscrambleMessage.checkPackets();
            if (!missingPackets.isEmpty())
            {
                for (int ix = 0; ix < missingPackets.size(); ++ix)
                {
                    System.out.println("missingpacket " + missingPackets.get(ix));
                }
            }
            List<String>missingLetters = new ArrayList<>();
            while (!missingPackets.isEmpty())
            {
                for (int ix = 0; ix < missingPackets.size(); ++ix)
                {
                    requestWriter.println(missingPackets.get(ix));
                }
                while (!serverResponse.equals("Done"))
                {

                    requestWriter.println(stdIn.readLine());

                    serverResponse = responseReader.readLine();
                    boolean isNum = isNumeric(serverResponse);
                   if (!isNum)
                   {
                       missingLetters.add(serverResponse);
                   }
                   unscrambleMessage.addMissingPackets(missingLetters, missingPackets);
                  missingPackets =  unscrambleMessage.checkPackets();
                }


            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
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
