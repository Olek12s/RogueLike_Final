package utilities;

import java.awt.image.BufferedImage;

public class Sprite
{
    public BufferedImage image;
    public int resolutionX;
    public int resolutionY;

    public Sprite(BufferedImage image, int resolutionX, int resolutionY)
    {
        this.image = image;
        this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;
    }

    public Sprite(BufferedImage image, int resolution)
    {
        this.image = image;
        this.resolutionX = resolution;
        this.resolutionY = resolution;
    }
}
