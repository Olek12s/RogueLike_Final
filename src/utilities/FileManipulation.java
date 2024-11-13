package utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import main.map.Map;

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
}
