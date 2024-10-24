package org.example.main;

//import utilities.shapes.Polygon;

import javax.swing.*;
import java.awt.*;


public class Main
{
    static private JFrame window;


    public static void main(String[] args)
    {
        window = new JFrame();
        GameController gameController = new GameController();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("A");
        window.setSize(1200, 800);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.add(gameController);
        gameController.startThread();

    }
}