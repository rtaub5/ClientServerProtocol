import java.util.*;

public class TestServer
{
    public static void main(String[] args)
    {
        Random rand = new Random();
        String message = "Rocki Taub";
        List<String> messageList = new ArrayList<String>(Arrays.asList(message.split("")));
        Collections.shuffle(messageList);
        List<String>originalMessageList = new ArrayList<String>(Arrays.asList(message.split("")));
        List<Integer>packets = new ArrayList<Integer>();

      System.out.println("messagelist size: " + messageList.size());

      Collections.shuffle(messageList);
      System.out.println((originalMessageList.size()-1) + " is the last packet");

        for (int ix = 0; ix < messageList.size(); ++ix)
        {
            System.out.println(messageList.get(ix));
            for (int jx = 0; jx < originalMessageList.size(); ++jx)
            {
                if ((messageList.get(ix).equals(originalMessageList.get(jx))))
                {
                    packets.add(jx);
                }
            }
        }
    }
}

