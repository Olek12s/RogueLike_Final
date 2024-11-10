package main.tile;

import main.Drawable;
import main.GameController;
import main.entity.Entity;
import main.item.Item;
import utilities.DrawPriorities;
import utilities.Position;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

// multiple maps
public class Map implements Drawable {
    GameController gc;
    public Tile[][] mapGrid;
    public ArrayList<Entity> entities = new ArrayList<>();
    public ArrayList<Item> lyingItems = new ArrayList<>();
    int mapWidth, mapHeight;
    //Enum level;

    public Map(GameController gc/*, int mapWidth, int mapHeight, String path*/) {
        this.mapWidth = 30;
        this.mapHeight = 30;
        this.gc = gc;
        gc.drawables.add(this);
        //this.mapWidth = mapWidth;
        //this.mapHeight = mapHeight;
        mapGrid = new Tile[mapWidth][mapHeight];
        loadMap("resources/maps/world0-0.txt");
    }

    public void loadMap(String path) {
        try {
            InputStream is = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //load tile by tile to the map grid
            for (int row = 0; row < mapHeight; row++) {
                String line = br.readLine();
                if (line == null || line.equals("")) continue;
                String[] tiles = line.split(" ");

                for (int col = 0; col < mapWidth; col++) {
                    try {
                        if (tiles[col].equals("")) {
                            //mapGrid[col][row] = null;
                            continue;
                        } else {
                            int tileID = Integer.parseInt(tiles[col]);
                            mapGrid[col][row] = gc.tileManager.getTile(tileID);
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        //Default tile texture in the future?
                        //mapGrid[col][row] = null;
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Tile getMapTile(int x, int y) {
        return mapGrid[x][y];
    }

    public void removeItemFromTile(Position position) {
        int tileX = position.x / gc.tileManager.tileSize;
        int tileY = position.y / gc.tileManager.tileSize;

        lyingItems.removeIf(item ->
        {
            int itemTileX = item.getWorldPosition().x / gc.tileManager.tileSize;
            int itemTileY = item.getWorldPosition().y / gc.tileManager.tileSize;
            return (itemTileX == tileX && itemTileY == tileY);
        });
    }

    public void removeEntityFromTile(Position position) {
        int tileX = position.x / gc.tileManager.tileSize;
        int tileY = position.y / gc.tileManager.tileSize;

        entities.removeIf(entity ->
        {
            int entityTileX = entity.getWorldPosition().x / gc.tileManager.tileSize;
            int entityTileY = entity.getWorldPosition().y / gc.tileManager.tileSize;
            return (entityTileX == tileX && entityTileY == tileY);
        });
    }

    public String printItemsOnMap()
    {
        StringBuilder output = new StringBuilder();
        output.append("===== Items on the map =====\n");


        for (Item item : lyingItems)
        {
            output.append("Item: ").append(item.name)
                    .append(" at: ").append(item.getWorldPosition())
                    .append("\n");
        }
        output.append("===============================\n");
        return output.toString();
    }

    public String printEntitiesOnMap()
    {
        StringBuilder output = new StringBuilder();
        output.append("===== Entities on the map =====\n");


        for (Entity entity : entities)
        {
            output.append("Entity: ").append(entity.name)
                    .append(" at: ").append(entity.getWorldPosition())
                    .append("\n");

        }
        output.append("===============================\n");
        return output.toString();
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.mapGrid.value;}

    @Override
    public void draw(Graphics g2)
    {
        int scaledTileSize = (int)(gc.tileManager.tileSize * gc.camera.getScaleFactor());

        for (int col = 0; col < mapWidth; col++)
        {
            for (int row = 0; row < mapHeight; row++)
            {
                Tile tile = mapGrid[col][row];
                if (tile != null && tile.getCurrentSprite() != null)
                {
                    // Tile's world cooridanets * tileSize
                    int worldX = col * TileManager.tileSize;
                    int worldY = row * TileManager.tileSize;

                    // Adjusting tiles position to the camera
                    Position screenPosition = gc.camera.applyCameraOffset(worldX, worldY);
                    g2.drawImage(tile.getCurrentSprite().image, screenPosition.x, screenPosition.y, scaledTileSize, scaledTileSize, null);   // tileSize - scale
                }
            }
        }
        //g2.dispose();
    }

    public Tile getTileAt(int col, int row)
    {
        if (col >= 0 && col < gc.map.mapWidth && row >= 0 && row < gc.map.mapHeight)
        {
            return gc.map.getMapTile(col, row);
        }
        return null;
    }
}
