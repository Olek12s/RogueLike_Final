package utilities.sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sprite
{
    public BufferedImage image;
    public int resolutionX;
    public int resolutionY;
    public float spriteScale = 1.f;

    public Sprite(BufferedImage image, int resolution)
    {
        this.image = image;
        this.resolutionX = resolution;
        this.resolutionY = resolution;
    }

    public Sprite(BufferedImage image, int resolution, float spriteScale)
    {
        this.image = image;
        this.resolutionX = resolution;
        this.resolutionY = resolution;
        this.spriteScale = spriteScale;
    }
}
