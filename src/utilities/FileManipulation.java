package utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FileManipulation
{
    public static BufferedImage loadImage(String path)
    {
        BufferedImage image = null;
        try
        {
            image = ImageIO.read(new File(path + ".png"));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return image;
    }

    public static String path(String path)
    {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win") || os.contains("microsoft"))
        {
            //  path.replace("\", "/");
        }
        if (os.contains("lin") || os.contains( "unix"))
        {
            //  path.replace("/", "\");
        }
        return "";
    }
}