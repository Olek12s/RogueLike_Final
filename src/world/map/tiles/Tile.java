package world.map.tiles;

import utilities.FileManipulation;
import utilities.Position;
import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Tile implements Serializable
{
    public static int tileSize = 64;
    private final short id;
    private Sprite currentSprite;
    private boolean isColliding;
    private Position worldPosition;


    public int getId() {return id;}

    private static int counter = 0;
    public Tile(int id, Position position) throws InterruptedException
    {
        this.id = (short)id;
        this.worldPosition = position;
        this.currentSprite = TileManager.getTileObject(id).getRandomVariation(0);
        this.isColliding = TileManager.getTileObject(id).isColliding();
    }

    public Tile(Position position)
    {
        this.id = 0;
        this.worldPosition = position;
        SpriteSheet defaultSS = new SpriteSheet(FileManipulation.loadImage("resources/default/defaultTile"), 64);
        this.currentSprite = defaultSS.extractFirst();
    }

    public Sprite getCurrentSprite() {return currentSprite;}
    public boolean isColliding() {return isColliding;}



    public static Sprite extractRandomVariation(SpriteSheet spriteSheet)
    {
        if (spriteSheet.countSpriteVariations() == 1)
        {
            return spriteSheet.extractFirst();
        }
        else
        {
            int randomVariation = (int) (Math.random() * spriteSheet.countSpriteVariations());
            return spriteSheet.extractSpriteByVariation(randomVariation);
        }
    }

    public boolean doesContainPosition(Position position)
    {
        int startX = worldPosition.x;
        int startY = worldPosition.y;
        int endX = startX + tileSize;
        int endY = startY + tileSize;

        return position.x >= startX && position.x < endX && position.y >= startY && position.y < endY;
    }

    public boolean isCavePassage() {
        TileID tileID = TileID.fromId(id);
        switch (tileID)
        {
            case CAVE_ENTRANCE,
                 CAVE_EXIT,
                 CAVE_DEEP_ENTRANCE,
                 CAVE_DEEP_EXIT,
                 CAVE_RUINS_ENTRANCE,
                 CAVE_RUINS_EXIT-> {return true;}
            default -> {return false;}
        }
    }

    public void addEdgeTexture()
    {
        TileManager.TileObject tileObject = TileManager.getTileObject(id);

        // Sprawdzenie, czy edgeSprites istnieją
        if (tileObject != null && tileObject.getEdgeSprites() != null) {
            // Wybierz losowy sprite z edgeSprites
            Sprite[] edgeSprites = tileObject.getEdgeSprites();
            int randomIndex = (int) (Math.random() * edgeSprites.length);
            Sprite edgeSprite = edgeSprites[randomIndex];

            // Połączenie obecnego Sprite (currentSprite) z edgeSprite
            this.currentSprite = combineSprites(this.currentSprite, edgeSprite);
        }
    }

    private Sprite combineSprites(Sprite baseSprite, Sprite overlaySprite) {
        // Tworzymy nowy BufferedImage o tych samych wymiarach co currentSprite
        BufferedImage combinedImage = new BufferedImage(
                baseSprite.resolutionX,
                baseSprite.resolutionY,
                BufferedImage.TYPE_INT_ARGB
        );

        // Łączymy obrazy przy pomocy Graphics2D
        Graphics2D g = combinedImage.createGraphics();
        g.drawImage(baseSprite.image, 0, 0, null); // Rysujemy podstawowy Sprite
        g.drawImage(overlaySprite.image, 0, 0, null); // Rysujemy na nim overlay (krawędź)
        g.dispose();

        // Zwracamy nowy Sprite
        return new Sprite(combinedImage, baseSprite.resolutionX, baseSprite.spriteScale);
    }
}
