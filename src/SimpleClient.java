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
            while ((stdIn.readLine() != null) && (!serverResponse.equals("Done")))
            {

                  serverResponse = responseReader.readLine();
                  boolean isNum = isNumeric(serverResponse);
                  if (isNum)
                  {
                      numPackets.add(Integer.parseInt(serverResponse));
                  }
                  else if (!serverResponse.equals("Done"))
                  {
                      message.add(serverResponse);
                  }
                System.out.println("SERVER RESPONDS: \"" + serverResponse + "\"");

            }

            UnscrambleMessage unscrambleMessage = new UnscrambleMessage(message, numPackets);
            List<Integer>missingPackets = unscrambleMessage.checkPackets();
            boolean isMissPacketsEmpty = missingPackets.isEmpty();
            while (!isMissPacketsEmpty)
            {
                for (int ix = 0; ix < missingPackets.size(); ++ix)
                {

                    String mPack = missingPackets.get(ix).toString();
                    requestWriter.println(mPack);
                    boolean done = false;

                    while ((!done))
                    {

                        serverResponse = responseReader.readLine();
                        boolean isNum = isNumeric(serverResponse);

                        if (isNum)
                        {
                            int resentNum = Integer.parseInt(serverResponse);
                            if (missingPackets.contains(resentNum))
                             numPackets.add(Integer.parseInt(serverResponse));
                            unscrambleMessage.setPackets(numPackets);
                            int missingPacketsOrigSize = missingPackets.size();
                            missingPackets = unscrambleMessage.checkPackets();
                            int missingPacketsAfterSize = missingPackets.size();

                            if (missingPacketsOrigSize - missingPacketsAfterSize != 1)
                            {
                                message.remove(message.size() - 1);
                                unscrambleMessage.setMessage(message);
                            }

                            done = true;
                        } else if (!serverResponse.equals("Done"))
                        {
                                message.add(serverResponse);
                                unscrambleMessage.setMessage(message);

                            done = true;
                        }
                        System.out.println("SERVER RESPONDS: \"" + serverResponse + "\"");
                        if (!missingPackets.isEmpty())
                        {
                            missingPackets = unscrambleMessage.checkPackets();
                            isMissPacketsEmpty = false;
                            done = true;
                        }
                        else
                        {
                            missingPackets = unscrambleMessage.checkPackets();
                            isMissPacketsEmpty = true;
                            done = true;
                        }
                    }

                }

            }
            String answer = unscrambleMessage.unscramble();
            System.out.println(answer);
            requestWriter.println("Done");
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
            Integer.parseInt(response);
        }
        catch (NumberFormatException exc)
        {
            return false;
        }
        return true;
    }
}
