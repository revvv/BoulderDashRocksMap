package util;

import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImageUtil2
{
    private static final Logger log = Logger.getLogger(ImageUtil2.class.getName());
    
    private ImageUtil2()
    {
    }
    
    public static BufferedImage readImage(String filename)
    {
        try
        {
            return ImageIO.read(new File(filename));
        }
        catch (Exception e)
        {
            log.log(Level.SEVERE, "could not read image file: " + filename, e);
        }
        
        return null;
    }
    
    public static BufferedImage readImage(URL url)
    {
        try
        {
            return ImageIO.read(url);
        }
        catch (Exception e)
        {
            log.log(Level.SEVERE, "could not read image URL: " + url, e);
        }
        
        return null;
    }

    public static boolean writeImage(BufferedImage image, String filename)
    {
        String extension = FileUtil2.getExtension(filename);
    
        // prevent typical user error (image would get wrong colors)
        String ext = extension.toLowerCase();
        if (ext.equals("jpg") || ext.equals("jpeg"))
        {
            if (image.getTransparency() != Transparency.OPAQUE)
            {
                throw new IllegalArgumentException("JPG does not support transparency");
            }
        }
        
        try
        {
            ImageIO.write(image, extension, new File(filename));
        }
        catch (Exception e)
        {
            log.log(Level.SEVERE, "could not write image file: " + filename, e);
            return false;
        }
        
        return true;
    }

    /** Auto-crop image. */
    public static BufferedImage crop(BufferedImage image)
    {
        // Get our top-left pixel color as our "baseline" for cropping
        int baseColor = image.getRGB(0, 0);
    
        int width = image.getWidth();
        int height = image.getHeight();
    
        int topY = Integer.MAX_VALUE;
        int topX = Integer.MAX_VALUE;
        int bottomY = -1;
        int bottomX = -1;
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                if (baseColor != image.getRGB(x, y))
                {
                    if (x < topX)
                    {
                        topX = x;
                    }
                    if (y < topY)
                    {
                        topY = y;
                    }
                    if (x > bottomX)
                    {
                        bottomX = x;
                    }
                    if (y > bottomY)
                    {
                        bottomY = y;
                    }
                }
            }
        }
    
        // FIX: there was one pixel missing (UPDATE: this is not always required, might go wrong)
        BufferedImage res = new BufferedImage((bottomX - topX + 2), (bottomY - topY + 2),
                BufferedImage.TYPE_INT_ARGB);
        
        res.getGraphics().drawImage(image, 0, 0, res.getWidth(), res.getHeight(), topX, topY,
                bottomX + 1, bottomY + 1, null);
    
        return res;
    }

}
