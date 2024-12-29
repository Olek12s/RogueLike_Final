package world.generation;

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
        int width = 1024;
        int height = 1024;
        int stepSize = 256;
        int scale = 128;
        long seed = System.currentTimeMillis();     // random.nextLong(System.currentTimeMillis())


        Random random = new Random(seed);
        JFrameNoiseDrawer noisePanel = new JFrameNoiseDrawer(width, height, seed);
        SurfaceGenerator surfaceGenerator2 = new SurfaceGenerator(width, height);
        noisePanel.drawNoise(surfaceGenerator2.getMapValues());

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Seed: " + seed);
        window.setSize(width, height);

        // "Next" button with lambda function
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e ->
        {
            CaveNegativeOneGenerator caveNegativeOne = new CaveNegativeOneGenerator(width, height);
            CaveNegativeTwoGenerator caveNegativeTwo = new CaveNegativeTwoGenerator(width, height);
            SurfaceGenerator surfaceGenerator = new SurfaceGenerator(width, height);
            noisePanel.drawNoise(caveNegativeTwo.getMapValues());
            window.setTitle("Seed: " + caveNegativeOne.getSeed());
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
