package util;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtil2
{
    private static Logger log = Logger.getLogger(FileUtil2.class.getName());
    
    private static final int BUFFER_SIZE_4K = 4 * 1024;

    private FileUtil2()
    {
    }    

    public static byte[] readFile(String filename)
    {
        return FileUtil2.readFile(new File(filename));
    }

    // java.nio.file.Files.readAllBytes()
    public static byte[] readFile(File file)
    {
        InputStream in = null;
        try
        {
            byte[] buffer = new byte[BUFFER_SIZE_4K];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            in = new FileInputStream(file);
            int read = 0;
            while ( (read = in.read(buffer)) != -1 )
            {
                out.write(buffer, 0, read);
            }
            return out.toByteArray();
        }
        catch (IOException e)
        {
            log.log(Level.SEVERE, "Unable to load file: " + file, e);
            return null;
        }
        finally
        {
            FileUtil2.close(in);
        }
    }
    
    public static byte[] readFile(URL url)
    {
        InputStream in = null;
        try
        {
            byte[] buffer = new byte[BUFFER_SIZE_4K];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            in = url.openStream();
            int read = 0;
            while ( (read = in.read(buffer)) != -1 )
            {
                out.write(buffer, 0, read);
            }
            return out.toByteArray();
        }
        catch (Exception e)
        {
            log.log(Level.SEVERE, "Unable to load file from url: " + url, e);
            return null;
        }
        finally
        {
            FileUtil2.close(in);
        }
    }
    
    public static String readFileUTF8(String filename)
    {
        byte[] bytes = FileUtil2.readFile(new File(filename));
        if (bytes != null)
        {
            try
            {
                return new String(bytes, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                // cannot happen
            }
        }
        
        return null;
    }

    public static boolean writeFileUTF8(String filename, String text)
    {
        Writer out = null;
        try
        {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "UTF-8"));
            out.write(text);
        }
        catch (UnsupportedEncodingException e)
        {
            // cannot happen
        }
        catch (IOException e)
        {
            log.log(Level.SEVERE, "Unable to write file: " + filename, e);
            return false;
        }
        finally
        {
            close(out);
        }
        
        return true;
    }
    
    public static boolean writeFile(File file, byte[] bytes)
    {
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(file);
            out.write(bytes);
        }
        catch (IOException e)
        {
            log.log(Level.SEVERE, "Unable to write file: " + file, e);
            return false;
        }
        finally
        {
            close(out);
        }
        
        return true;
    }
    
    public static boolean writeFile(String filename, byte[] bytes)
    {
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream(filename);
            out.write(bytes);
        }
        catch (IOException e)
        {
            log.log(Level.SEVERE, "Unable to write file: " + filename, e);
            return false;
        }
        finally
        {
            close(out);
        }
        
        return true;
    }
    
    public static String getExtension(String filename)
    {
        if (filename == null || filename.length() == 0)
        {
            return filename;
        }
        
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0)
        {
            extension = filename.substring(i + 1);
        }
        
        return extension;
    }
    
    public static String getBasename(String filename)
    {
        if (filename == null || filename.length() == 0)
        {
            return filename;
        }
        
        File f = new File(filename);
        filename = f.getName();
        filename = StringUtil2.removeSuffix(filename, ".");
        
        String extension = getExtension(filename);
        if (extension.length() == 0)
        {
            return filename;
        }
        
        return filename.substring(0, filename.length() - extension.length() - 1);
    }

    public static void close(InputStream in)
    {
        if (in != null)
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    public static void close(OutputStream out)
    {
        if (out != null)
        {
            try
            {
                out.close();
            }
            catch (IOException e)
            {
            }
        }
    }

    public static void close(Writer w)
    {
        if (w != null)
        {
            try
            {
                w.close();
            }
            catch (IOException e)
            {
            }
        }
    }

}
