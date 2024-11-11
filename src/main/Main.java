package main;

import javax.swing.*;
import java.awt.*;


public class Main
{
    static private JFrame window;


    public static void main(String[] args)
    {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("A");
        GameController gameController = new GameController();
        window.setSize(1200, 800);
        window.add(gameController);
        gameController.startThread();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
