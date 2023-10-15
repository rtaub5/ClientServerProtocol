import java.util.*;

public class TestClient
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<String> scrambledMessage = new ArrayList<String>();
        List<Integer> packetNums = new ArrayList<Integer>();
        for (int ix = 0; ix < n ; ++ix)
        {
            scrambledMessage.add(in.next());
            packetNums.add(in.nextInt());
        }

        for (int ix = 0 ; ix < scrambledMessage.size(); ++ix)
        {
            System.out.println(scrambledMessage.get(ix));
        }
        for (int ix = 0; ix < packetNums.size(); ++ix)
        {
            System.out.println(packetNums.get(ix));
        }

    }
}
