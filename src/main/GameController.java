package main;

import main.cursor.CursorHUD;
import main.entity.Entity;
import main.entity.player.Player;
import main.map.MapController;
import main.map.TileManager;
import utilities.AssetSetter;
import utilities.Collisions;
import utilities.MouseHandler;
import utilities.camera.Camera;
import utilities.KeyHandler;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameController extends JPanel implements Runnable
{
    private Thread mainThread;
    private int targetDrawFrame =  100;
    private int targetLogicFrame = 60;

    //CLASS INSTANCES
    public KeyHandler keyHandler;
    public MouseHandler mouseHandler;
    public Entity player;
    public Camera camera;
    public TileManager tileManager;
    public MapController mapController;
    public Collisions collisions;
    public AssetSetter assetSetter;
    public CursorHUD cursor;

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
        this.addMouseWheelListener(mouseHandler);
        this.addMouseListener(mouseHandler);
        this.addMouseMotionListener(mouseHandler);
        hideCursor();
    }

    private void initAbstractCollections()
    {
        drawables = new ArrayList<>();
        updatables = new ArrayList<>();
    }
    private void initClassInstances()
    {
        keyHandler = new KeyHandler(this);
        mouseHandler = new MouseHandler(this);
        player = new Player(this);
        camera = new Camera(this);
        tileManager = new TileManager(this);
        mapController = new MapController(this);
        collisions = new Collisions(this);
        assetSetter = new AssetSetter(this);
        cursor = new CursorHUD(this);
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

    private void hideCursor()
    {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        BufferedImage cursorImg = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB); // Transparent image
        Cursor blankCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0), "cursor");
        this.setCursor(blankCursor);
    }
}
