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
            for (int jx = 0; jx < originalMessageList.size(); ++jx)
            {
                if ((shuffledMessageList.get(ix).equals(originalMessageList.get(jx))))
                {
                    packets.add(jx);
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

    public void resendPackets(PrintWriter writer1, List<Integer> missingPackets, List<Integer>packets, List<String>shuffledMessageList)
    {
        Random rand = new Random();
        int prob = rand.nextInt(101);
        for (int ix = 0; ix < missingPackets.size(); ++ix)
        {
            for (int jx = 0; jx < packets.size(); ++jx)
            {
                if (missingPackets.get(ix) == packets.get(jx))
                {
                    if ((prob > 20) || (packets.get(ix) == (packets.size() - 1)))
                    {
                        writer1.println(shuffledMessageList.get(ix));
                        writer1.println(packets.get(ix));
                    }
                }
            }

        }
    }
}
