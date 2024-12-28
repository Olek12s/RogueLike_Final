package world.generation;

import main.Drawable;
import main.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Noise extends JPanel
{
    private BufferedImage image;
    private static JFrame window;
    private static int WIDTH;
    private static int HEIGHT;


    public Noise(int width, int height)
    {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        generateNoise();
    }

    private void generateNoise()
    {
        for (int x = 0; x < WIDTH; x++)
        {
            for (int y = 0; y < HEIGHT; y++)
            {
                int color = (int) (Math.random() * 0xFFFFFF); // random color
                image.setRGB(x, y, color);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Rysowanie obrazu na środku panelu, dopasowanego do rozmiaru okna
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        // Obliczenie skalowania
        //double scaleX = (double) panelWidth / imageWidth;
       // double scaleY = (double) panelHeight / imageHeight;
       // double scale = Math.min(scaleX, scaleY);

        // Wyśrodkowanie obrazu
        int drawWidth = (int) (imageWidth);
        int drawHeight = (int) (imageHeight);
        int x = (panelWidth - drawWidth) / 2;
        int y = (panelHeight - drawHeight) / 2;

        g2.drawImage(image, 0, 0, imageWidth, imageHeight, null);

        g2.dispose();
    }




    public static void main(String[] args)
    {
        int width = 128;
        int height = 128;
        Noise noisePanel = new Noise(width, height);

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.setResizable(true);
        window.setTitle("Noise Generator");
        window.setSize(width, height);
        window.add(noisePanel);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
