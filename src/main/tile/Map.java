package main.tile;

import main.Drawable;
import main.GameController;
import main.tile.Tile;
import utilities.DrawPriorities;
import utilities.Position;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

// multiple maps
public class Map implements Drawable
{
    GameController gc;
    public Tile[][] mapGrid;
    int mapWidth, mapHeight;
    //Enum level;

    public Map(GameController gc/*, int mapWidth, int mapHeight, String path*/)
    {
        this.mapWidth = 30;
        this.mapHeight = 30;
        this.gc = gc;
        gc.drawables.add(this);
        //this.mapWidth = mapWidth;
        //this.mapHeight = mapHeight;
        mapGrid = new Tile[mapWidth][mapHeight];
        loadMap("resources/maps/world0-0.txt");
    }

    public void loadMap(String path)
    {
        try
        {
            InputStream is = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //load tile by tile to the map grid
            for (int row = 0; row < mapHeight; row++)
            {
                String line = br.readLine();
                if(line == null || line.equals("")) continue;
                String[] tiles = line.split(" ");

                for (int col = 0; col < mapWidth; col++)
                {
                    try
                    {
                        if(tiles[col].equals(""))
                        {
                            //mapGrid[col][row] = null;
                            continue;
                        }
                        else
                        {
                            int tileID = Integer.parseInt(tiles[col]);
                            mapGrid[col][row] = gc.tileManager.tiles.get(tileID);
                        }
                    }
                    catch (IndexOutOfBoundsException ex)
                    {
                        //Default tile texture in the future?
                        //mapGrid[col][row] = null;
                    }
                }
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.mapGrid.value;}

    @Override
    public void draw(Graphics g2)
    {
        int tileSize = gc.tileManager.tileSize;

        for (int col = 0; col < mapWidth; col++)
        {
            for (int row = 0; row < mapHeight; row++)
            {
                Tile tile = mapGrid[col][row];
                if (tile != null && tile.getSprite() != null)
                {
                    // Tile's world cooridanets * tileSize
                    int worldX = col * tileSize;
                    int worldY = row * tileSize;

                    // Adjusting tiles position to the camera
                    Position screenPosition = gc.camera.applyCameraOffset(worldX, worldY);
                    g2.drawImage(tile.getSprite().image, screenPosition.x, screenPosition.y, tileSize, tileSize, null);   // tileSize - scale
                }
            }
        }
        //g2.dispose();
    }
}
