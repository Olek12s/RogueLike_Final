package world.generation.perlinNoiseTest;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NoiseMap
{
    public double[] values;
    private int width, height;

    public NoiseMap(int width, int height, int featureSize) {
        this.width = width;
        this.height = height;

        values = new double[width * height];
        Random random = new Random();

        for (int y = 0; y < width; y += featureSize)
        {
            for (int x = 0; x < height; x += featureSize)
            {
                setSample(x, y, random.nextFloat() * 2 - 1);
            }
        }

        int stepSize = featureSize;
        double scale = 1.0/width;
        double scaleMod = 1;
        do
        {
            int halfStep = stepSize/2;


            for (int y = 0; y < width; y += stepSize)
            {
                for (int x = 0; x < height; x += stepSize)
                {
                    double a = sample(x, y);
                    double b = sample(x+stepSize, y);
                    double c = sample(x, y+stepSize);
                    double d = sample(x+stepSize, y+stepSize);

                    double e = (a+b+c+d)/4.0 + (random.nextFloat()*2-1) * stepSize*scale;

                    setSample(x+halfStep, y+halfStep, e);
                }
            }
            for (int y = 0; y < width; y += stepSize)
            {
                for (int x = 0; x < height; x += stepSize)
                {
                    double a = sample(x, y);
                    double b = sample(x+stepSize, y);
                    double c = sample(x, y+stepSize);
                    double d = sample(x+halfStep, y+halfStep);
                    double e = sample(x+halfStep, y-halfStep);
                    double f = sample(x-halfStep, y+halfStep);

                    double H = (a+b+c+e)/4.0 + (random.nextFloat()*2-1) * stepSize*scale * 0.5;
                    double g = (a+c+d+f)/4.0 + (random.nextFloat()*2-1) * stepSize*scale * 0.5;

                    setSample(x+halfStep, y, H);
                    setSample(x, y + halfStep, g);
                }
            }
            stepSize /= 2;
            scale *= (scaleMod+1);
            scaleMod*=0.6;
        } while (stepSize>1);


    }

    private double sample(int x, int y)
    {
        return values[(x & (width - 1)) + (y & (height - 1)) * width];
    }

    private void setSample(int x, int y, double value)
    {
        values[(x & (width - 1)) + (y & (height - 1)) * width] = value;
    }


    public static void main(String[] args)
    {
        for (int j = 0; j < 10; j++)
        {
            int width = 512;
            int height = 512;
            NoiseMap noise1 = new NoiseMap(width, height, 64);
            NoiseMap noise2 = new NoiseMap(width, height, 128);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {
                    int i = x+y*width;
                    double val = Math.abs(noise1.values[i] - noise2.values[i]) * 5 - 2;
                    double xd = x/(width-1.0)*2-1;
                    double yd = y/(height-1.0)*2-1;
                    if (xd < 0) xd = -xd;
                    if (yd < 0) yd = -yd;
                    double dist = xd >= yd ? xd : yd;
                    dist = dist * dist * dist * dist;
                    dist = dist * dist * dist * dist;
                    val = val + 1 - dist * 20;

                    int br = (int)(noise1.values[i] * 64 + 128);
                   // br = val < 0 ? 0 : 255;
                    pixels[i] = br << 16 | br << 8 | br;
                    /*
                    if (val < 0)
                    {
                    map[i] = grass;
                    }
                    else if (val < 0.2)
                    {
                        map[i] = rock;
                    }
                    else if (...)
                    {
                    ...
                    }
                    */
                    if (val > 0.5)
                    {
                       // pixels[i] = 0xff00ff;
                    }
                    //System.out.println(noise1.values[i] + " ");
                }
            }
            image.setRGB(0,0,width,height,pixels,0,width);
            JOptionPane.showMessageDialog(null, null, "perlin", JOptionPane.YES_NO_OPTION, new ImageIcon(image));
        }
    }
}