package main;

import main.entity.Entity;
import main.entity.player.Player;
import utilities.camera.Camera;
import utilities.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameController extends JPanel implements Runnable
{
    private Thread mainThread;
    private int targetDrawFrame =  240;
    private int targetLogicFrame = 60;

    //CLASS INSTANCES
    public KeyHandler keyHandler;
    public Camera camera;
    public Entity player;


    //ABSTRACT COLLECTIONS
    public ArrayList<Drawable> drawables;
    public ArrayList<Updatable> updatables;


    //GETTERS AND SETTERS

    private static GameController instance;

    public GameController()
    {
        this.setPreferredSize(new Dimension(getWidth(), getHeight()));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);


        initAbstractCollections();
        initClassInstances();
        this.addKeyListener(keyHandler);
        this.addMouseWheelListener(keyHandler);
    }

    private void initAbstractCollections()
    {
        drawables = new ArrayList<>();
        updatables = new ArrayList<>();
    }
    private void initClassInstances()
    {
        keyHandler = new KeyHandler(this);
        camera = new Camera(this);
        player = new Player(this);
    }


    public void startThread()
    {
        mainThread = new Thread(this);
        mainThread.start();
    }

    @Override
    public void run()       // MAIN LOOP
    {
        long logicInterval = 1000000000 / targetLogicFrame;   // logic ns
        long drawInterval = 1000000000 / targetDrawFrame;     // draw ns
        int maxLogicUpdatesPerFrame = Math.max(targetDrawFrame / targetLogicFrame, 1);

        long lastTime = System.nanoTime();
        long accumulator = 0;
        long maxAccumulator = logicInterval * maxLogicUpdatesPerFrame;

        while (mainThread != null)
        {
            long currentTime = System.nanoTime();
            long deltaT = currentTime - lastTime;
            lastTime = currentTime;

            accumulator += deltaT;

            if (accumulator > maxAccumulator)
            {
                accumulator = maxAccumulator;
            }

            int logicUpdates = 0;

            while (accumulator >= logicInterval && logicUpdates < maxLogicUpdatesPerFrame)
            {
                updateLogic();
                accumulator -= logicInterval;
                logicUpdates++;
            }
            repaint();
            try
            {
                long sleepTime = drawInterval - (System.nanoTime() - currentTime);
                if (sleepTime > 0)
                {
                    Thread.sleep(sleepTime / 1000000);  // ns -> ms
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void updateLogic()    // UPDATE LOGIC
    {
        for (Updatable updatable : updatables)
        {
            updatable.update();
        }
    }



    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        drawables.sort((e1, e2) -> Integer.compare(e1.getDrawPriority(), e2.getDrawPriority()));
        for (Drawable drawable : drawables)
        {
            drawable.draw(g2);
        }
        g2.dispose();
    }
}
