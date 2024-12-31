package world.generation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class CaveNegativeOneGenerator
{
    private final int stepSize = 256;
    private final int scale = 128;
    private final int width;
    private final int height;
    private final long seed;
    private short[][] mapValues;
    short[][] map1;
    short[][] map2;
    private TerrainGenerator ds1;
    private TerrainGenerator ds2;

    public short[][] getMapValues() {return mapValues;}
    public long getSeed() {return seed;}

    public CaveNegativeOneGenerator(int width, int height)
    {
        Random random = new Random(System.currentTimeMillis());

        this.width = width;
        this.height = height;
        this.seed = random.nextLong();
        this.mapValues = new short[width][height];

        ds1 = new TerrainGenerator(width, height, stepSize/2,  scale/2, 0.82f, seed);
        ds2 = new TerrainGenerator(width, height, stepSize/2, scale/2, 0.89f, seed);
        map1 = ds1.getValues();
        map2 = ds2.getValues();

        combineValues();
    }

    private void combineValues()
    {
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                float val1 = map1[x][y];
                float val2 = map2[x][y];

                double val = (((val1+150) * val1-170) % 190) * 5;
                val = Math.min(val, val2);
                val = (val * 2 + 190);
                val = Math.clamp(val, 0, 255);
                mapValues[x][y] = (short)val;
            }
        }
    }

    public void saveGeneratedMapToFile()
    {
        String filePath = "resources/maps/CaveNegativeOne.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
        {
            for (int y = 0; y < height; y++)
            {
                StringBuilder line = new StringBuilder();
                for (int x = 0; x < width; x++)
                {
                    line.append(mapValues[x][y]);
                    if (x < width - 1)
                    {
                        line.append(" "); // Separate values with a space
                    }
                }
                writer.write(line.toString());
                writer.newLine(); // New line after each row
            }
        }
        catch (IOException e)
        {
            System.err.println("Error saving map to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
