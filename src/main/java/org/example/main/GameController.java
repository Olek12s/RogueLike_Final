package org.example.main;

import javax.swing.*;

public class GameController extends JPanel implements Runnable {
    private Thread mainThread;
    private int targetDrawFrame =  900;
    private int targetLogicFrame = 30;

    public void startThread() {
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
           updateDraw();
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

    private void updateUserInput()
    {

    }

    private void updateLogic()    // UPDATE LOGIC
    {
        updateUserInput();
    }

    private void updateDraw()         // UPDATE DRAWING
    {

    }
}
