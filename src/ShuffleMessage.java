import java.util.*;
import java.io.*;
public class ShuffleMessage
{
    private String message;

    public ShuffleMessage(String m)
    {
        message = m;
    }

    public List<String> shuffle()
    {
        List<String> shuffledMessageList = new ArrayList<String>(Arrays.asList(message.split("")));
        Collections.shuffle(shuffledMessageList);
        return shuffledMessageList;
    }

    public List<String> setOriginalMessage()
    {
        List<String> originalMessageList = new ArrayList<String>(Arrays.asList(message.split("")));
        return originalMessageList;
    }

    public List<Integer> setPacketNumbers(List<String> shuffledMessageList, List<String> originalMessageList)
    {
        List<Integer>packets = new ArrayList<Integer>();
        for (int ix = 0; ix < shuffledMessageList.size(); ++ix)
        {
            label:
           for (int jx = 0; jx < originalMessageList.size(); ++jx)
            {
                if ((shuffledMessageList.get(ix).equals(originalMessageList.get(jx))))
                {
                    if (!packets.contains(jx))
                    {
                        packets.add(jx);
                        break label;
                    }

                }
            }
        }
        return packets;
    }

    public int setNumPackets(List<Integer>packets)
    {
        int numPackets = packets.size();
        return numPackets;
    }

    public void randomizedSend(PrintWriter writer1, List<String> shuffledMessageList, List<Integer>packets)
    {
        Random rand = new Random();

        for (int ix = 0; ix < shuffledMessageList.size(); ++ix)
        {
            int prob = rand.nextInt(101);
            if ((prob > 20) || (packets.get(ix) == (packets.size() - 1)))
            {
                writer1.println(shuffledMessageList.get(ix));
                writer1.println(packets.get(ix));
            }
        }
        writer1.println("Done");
    }


    public void resendSinglePacket(PrintWriter writer1, int missingPacket, List<Integer>serverPackets, List<String> shuffledMessageList)
    {
        Random rand = new Random();

         System.out.println("missing packet " + missingPacket);
             int prob = rand.nextInt(101);
            for (int jx = 0; jx < serverPackets.size(); ++jx)
            {
                if (missingPacket == serverPackets.get(jx))
                {
                    if ((prob > 20) || (serverPackets.get(jx) == (serverPackets.size() - 1)))
                      {

                    writer1.println(shuffledMessageList.get(jx));
                    System.out.println("Resending letter: " + shuffledMessageList.get(jx));
                    writer1.println(missingPacket);
                    System.out.println("Resending packet " + missingPacket);

                     }

                }

            }
        writer1.println("Done");


    }

}
