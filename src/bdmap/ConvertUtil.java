package bdmap;

public class ConvertUtil
{
    private ConvertUtil()
    {
    }
    
    public static byte[] toByte(int[] bytes)
    {
        byte[] res = new byte[bytes.length];
        for (int i = 0; i < res.length; i++)
        {
            if (bytes[i] < 0 || bytes[i] > 255)
            {
                throw new IllegalArgumentException("out of range: " + bytes[i] + " at: " + i);
            }
            res[i] = (byte) bytes[i];
        }
        return res;
    }
    
    public static int indexOf(byte[] outerArray, byte[] smallerArray)
    {
        for (int i = 0; i < outerArray.length - smallerArray.length + 1; ++i)
        {
            boolean found = true;
            for (int j = 0; j < smallerArray.length; ++j)
            {
                if (outerArray[i + j] != smallerArray[j])
                {
                    found = false;
                    break;
                }
            }
            if (found)
                return i;
        }
        return -1;
    }
}
