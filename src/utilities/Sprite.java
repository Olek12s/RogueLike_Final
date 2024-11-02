package utilities;

import java.awt.image.BufferedImage;

public class Sprite
{
    public BufferedImage image;
    public int resolution;

    public Sprite(BufferedImage image, int resolution)
    {
        this.image = image;
        this.resolution = resolution;
    }
}
