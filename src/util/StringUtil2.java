package util;

public class StringUtil2
{
    private StringUtil2()
    {
    }
    
    public static String removePrefix(String s, String prefix)
    {
        if (s == null)
        {
            return null;
        }
        if (s.startsWith(prefix))
        {
            return s.substring(prefix.length());
        }
        return s;
    }
    
    public static String removeSuffix(String s, String suffix)
    {
        if (s == null)
        {
            return null;
        }
        if (s.endsWith(suffix))
        {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }

}
