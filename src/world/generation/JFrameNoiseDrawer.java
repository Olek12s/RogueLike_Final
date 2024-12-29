package world.generation;

import world.generation.perlinNoiseTest.NoiseMap;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class JFrameNoiseDrawer extends JPanel
{
    private BufferedImage image;
    private static JFrame window;
    private int width;
    private int height;
    private long seed;


    public JFrameNoiseDrawer(int width, int height, long seed)
    {
        this.width = width;
        this.height = height;
        this.seed = seed;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        drawNoise(getRandomNoise());
    }

    public void drawNoise(short[][] noiseValues)
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                short value = noiseValues[x][y];
                value = (short) Math.max(0, Math.min(255, value));
                int color = (value << 16) | (value << 8) | value; // grey scale
                image.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public short[][] getRandomNoise()
    {
        Random random = new Random(seed);
        short[][] noiseValues = new short[width][height];
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                noiseValues[x][y] = (short) random.nextInt(256);
            }
        }
        return noiseValues;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int x = (panelWidth - image.getWidth()) / 2;
        int y = (panelHeight - image.getHeight()) / 2;

        g2.drawImage(image, x, y, image.getWidth(), image.getHeight(), null);
    }

    public static void main(String[] args)
    {
        int width = 512;
        int height = 512;
        int stepSize = 512;
        int scale = 150;
        long seed = System.currentTimeMillis();     // random.nextLong(System.currentTimeMillis())


        Random random = new Random(seed);
        JFrameNoiseDrawer noisePanel = new JFrameNoiseDrawer(width, height, seed);
        DiamondSquare diamondSquare = new DiamondSquare(width, height, stepSize, scale, (long)(Math.random() * System.currentTimeMillis()));
        noisePanel.drawNoise(diamondSquare.getValues());

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Seed: " + seed);
        window.setSize(width, height);

        // "Next" button with lambda function
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e ->
        {
            long DSseed = random.nextLong();
            long seed1 = random.nextLong();
            long seed2 = random.nextLong();
            long seed3 = random.nextLong();


            DiamondSquare ds1 = new DiamondSquare(width, height, stepSize/2, scale/2, seed1);
            DiamondSquare ds2 = new DiamondSquare(width, height, stepSize/4, scale/4, seed2);
            DiamondSquare ds3 = new DiamondSquare(width, height, stepSize/4, scale/8, seed3);


            short[][] map1 = ds1.getValues();
            short[][] map2 = ds2.getValues();
            short[][] map3 = ds3.getValues();
            short[][] combined = new short[width][height];

            for (int y = 0; y < height; y++)
            {
                for (int x = 0; x < width; x++)
                {


                    float val1 = map1[x][y];
                    float val2 = map2[x][y];
                    float val3 = map3[x][y];


                    double val = Math.abs(val1 - val2+16 + val3) * 5.0 - 3.0;


                    if (val < 0)   val = 0;
                    if (val > 255) val = 255;

                    combined[x][y] = (short) val;
                }
            }
            noisePanel.drawNoise(combined);
            window.setTitle("Seed: " + DSseed);

        });

        // creating panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(nextButton, BorderLayout.SOUTH);

        // adding button and panel to the window
        window.setLayout(new BorderLayout());
        window.add(noisePanel, BorderLayout.CENTER);
        window.add(controlPanel, BorderLayout.SOUTH);

        window.setLocationRelativeTo(null); // centering window
        window.setVisible(true);
    }
}
