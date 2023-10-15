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

    public List<Integer> checkPackets()
    {
        List<Integer> verifiedPackets = new ArrayList<>();
        List<Integer> missingPackets = new ArrayList<>();
        int element = 0;
        for (int ix = 0; ix < packets.size(); ++ix)
        {
            for (int jx = 0; jx < packets.size(); ++jx)
            {
                if (packets.get(ix) == element)
                {
                    verifiedPackets.add(element);
                }
                element++;
            }
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

    public void addMissingPackets(List<String> missingLetters, List<Integer> missingNums)
    {
        for (int ix = 0; ix < missingLetters.size(); ++ix)
        {
            message.add(missingLetters.get(ix));
            packets.add(missingNums.get(ix));
        }
    }

    public String unscramble()
    {
        int maximumNum = Collections.max(packets);
        char [] unscrambledMessage = new char [(maximumNum + 1)];
        int element = 0;
        char num = '0';
        for (int ix = 0; ix <= maximumNum; ++ix)
        {
            for (int jx = 0; jx < packets.size(); ++jx)
            {
                if (packets.get(jx) == ix)
                {
                    num = message.get(ix).charAt(0);
                }
            }
            unscrambledMessage[ix] = num;
        }
    return unscrambledMessage.toString();
    }

}
