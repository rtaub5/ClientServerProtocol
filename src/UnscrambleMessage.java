import java.util.*;
public class UnscrambleMessage
{
    private List<String> message;
    private List<Integer> packets;

    public UnscrambleMessage(List<String> m, List<Integer> p)
    {
        message = m;
        packets = p;
    }
    public void setMessage(List<String> m)
    {
        message = m;
    }
    public List<String> getMessage()
    {
        return message;
    }

    public void setPackets(List<Integer>p)
    {
        packets = p;
    }
    public List<Integer> getPackets()
    {
        return packets;
    }

    public List<Integer> checkPackets()
    {
        List<Integer> verifiedPackets = new ArrayList<>();
        List<Integer> missingPackets = new ArrayList<>();
        for (int ix = 0; ix < packets.size(); ++ix)
        {
           verifiedPackets.add(packets.get(ix));
        }
        int maximum = Collections.max(verifiedPackets);
        for (int ix = 0; ix <= maximum; ++ix)
        {
            boolean hasElement = verifiedPackets.contains(ix);
            if (!hasElement)
            {
                missingPackets.add(ix);
            }
        }

        return missingPackets;
    }
    public String unscramble()
    {
        int maximumNum = Collections.max(packets);
        char [] unscrambledMessage = new char [(maximumNum + 1)];
        char num = '0';
        for (int ix = 0; ix <= maximumNum; ++ix)
        {
            for (int jx = 0; jx < packets.size(); ++jx)
            {
                if (packets.get(jx) == ix)
                {
                    num = message.get(jx).charAt(0);
                }
            }
            unscrambledMessage[ix] = num;
        }
    return String.valueOf(unscrambledMessage);
    }

    public String toString()
    {
        String unscrambString = "Message: " + message.toString() + "packets: " + packets.toString();
        return unscrambString;
    }


}
