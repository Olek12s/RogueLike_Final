package world.map.tiles;

import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TileObject
{
    private Sprite sprite;
    private Sprite[] edgedSprites;
    private SpriteSheet spriteSheet;
    private SpriteSheet edgedSpriteSheet;
    private boolean isColliding;
    private String name;
    private int id;
    private TileEdge tileEdge;

    /*
    public Sprite getRandomVariation()
    {
        int variation = (int) (Math.random() * (spriteSheet.variations));

        return sprites[variation];
    }
     */

    public Sprite getSprite() {return sprite;}
    public Sprite[] getEdgedSprites() {return edgedSprites;}

    public int getId() {return id;}
    public boolean isCollidable() {return isColliding;}
    public TileEdge getTileEdge() {return tileEdge;}

    public void setSprite(Sprite sprite) {this.sprite = sprite;}

    public TileObject(SpriteSheet spriteSheet, boolean isColliding, String name, int id)
    {
        this.sprite = spriteSheet.extractFirst();
        this.spriteSheet = spriteSheet;
        this.isColliding = isColliding;
        this.name = name;
        this.id = id;
    }

    public TileObject(SpriteSheet spriteSheet, SpriteSheet edgedSpriteSheet, TileEdge tileEdge, boolean isColliding, String name, int id)
    {
        this.sprite = spriteSheet.extractFirst();
        this.spriteSheet = spriteSheet;
        this.edgedSpriteSheet = edgedSpriteSheet;
        this.tileEdge = tileEdge;
        edgedSprites = initializeEdges();
        this.isColliding = isColliding;
        this.name = name;
        this.id = id;

       // for (int variation = 0; variation < edgedSpriteSheet.variations; variation++)
       // {
       //     edgedSprites[variation] = edgedSpriteSheet.extractSprite(edgedSpriteSheet, 0, variation);
       // }
    }

    private Sprite[] initializeEdges()
    {
        int sideCount = 4;
        int combinations = (int) Math.pow(2, sideCount);
        edgedSprites = new Sprite[combinations];

        BufferedImage baseImage = sprite.image; // base image
        int width = baseImage.getWidth();
        int height = baseImage.getHeight();

        for (int i = 0; i < combinations; i++)
        {
            boolean[] sides = decodeEdges(i);
            TileSide tileSide = new TileSide(sides[0], sides[1], sides[2], sides[3]);

            BufferedImage modifiedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = modifiedImage.createGraphics();

            g2d.drawImage(baseImage, 0, 0, null);   // draw base image

            //paste edges depending on current combination
            if (edgedSpriteSheet != null)
            {
                if (tileSide.isUp())    // top
                {
                    Sprite edgeSprite = edgedSpriteSheet.extractSprite(edgedSpriteSheet, 0, 0);
                    if (edgeSprite != null)
                    {
                        BufferedImage edgeImage = edgeSprite.image;
                        g2d.drawImage(edgeImage, 0, 0, width, height, null);
                    }
                }
                if (tileSide.isDown())  // bottom
                {
                    Sprite edgeSprite = edgedSpriteSheet.extractSprite(edgedSpriteSheet, 0, 1);
                    if (edgeSprite != null)
                    {
                        BufferedImage edgeImage = edgeSprite.image;
                        g2d.drawImage(edgeImage, 0, 0, width, height, null);
                    }
                }
                if (tileSide.isLeft()) // left
                {
                    Sprite edgeSprite = edgedSpriteSheet.extractSprite(edgedSpriteSheet, 0, 2);
                    if (edgeSprite != null)
                    {
                        BufferedImage edgeImage = edgeSprite.image;
                        g2d.drawImage(edgeImage, 0, 0, width, height, null);
                    }
                }
                if (tileSide.isRight()) // right
                {
                    Sprite edgeSprite = edgedSpriteSheet.extractSprite(edgedSpriteSheet, 0, 3);
                    if (edgeSprite != null)
                    {
                        BufferedImage edgeImage = edgeSprite.image;
                        g2d.drawImage(edgeImage, 0, 0, width, height, null);
                    }
                }
            }

            g2d.dispose();
            edgedSprites[i] = new Sprite(modifiedImage, width);
        }
        return edgedSprites;
    }

    /**
     * Converts digit from 0 to 15 into boolean array.
     *
     * @param sideCode sideCode
     * @return Boolean array representing edge states:
     * UP DOWN LEFT RIGHT
     *
     * 0: FFFF
     * 1: TFFF		UP
     * 2: FTFF		DOWN
     * 3: TTFF 	    UP-DOWN
     * 4: FFTF		LEFT
     * 5: TFTF		LEFT-UP
     * 6: FTTF		LEFT-DOWN
     * 7: TTTF		UP-DOWN-LEFT
     * 8: FFFT		RIGHT
     * 9: TFFT		UP-RIGHT
     * 10:FTFT		DOWN-RIGHT
     * 11:TTFT		UP-DOWN-RIGHT
     * 12:FFTT		LEFT-RIGHT
     * 13:TFTT		UP-LEFT-RIGHT
     * 14:FTTT		DOWN-LEFT-RIGHT
     * 15:TTTT		UP-DOWN-LEFT-RIGHT
     */
    public static boolean[] decodeEdges(int sideCode)
    {
        boolean[] sides = new boolean[4];
        for (int i = 0; i < 4; i++)
        {
            sides[i] = (sideCode & (1 << i)) != 0;
        }
        return sides;
        //return new boolean[]{false, true, true, true};
    }

    /**
     * Converts 4 neighbors of 1 source tile into sideCode representing, which
     * sides of source tile should be covered with edge texture
     *

     * @return integer representing sideCode. Values are:
     *
     * 0: FFFF
     * 1: TFFF		UP
     * 2: FTFF		DOWN
     * 3: TTFF 	    UP-DOWN
     * 4: FFTF		LEFT
     * 5: TFTF		LEFT-UP
     * 6: FTTF		LEFT-DOWN
     * 7: TTTF		UP-DOWN-LEFT
     * 8: FFFT		RIGHT
     * 9: TFFT		UP-RIGHT
     * 10:FTFT		DOWN-RIGHT
     * 11:TTFT		UP-DOWN-RIGHT
     * 12:FFTT		LEFT-RIGHT
     * 13:TFTT		UP-LEFT-RIGHT
     * 14:FTTT		DOWN-LEFT-RIGHT
     * 15:TTTT		UP-DOWN-LEFT-RIGHT
     */
    public static EdgeCode getEdgeCode(int sourceID, int up, int down, int left, int right)
    {
        TileEdge sourceEdge = TileManager.getTileObject(sourceID).getTileEdge();
        if (sourceEdge == null) return EdgeCode.NONE;

        TileEdge upEdge = TileManager.getTileObject(up).getTileEdge();
        TileEdge downEdge = TileManager.getTileObject(down).getTileEdge();
        TileEdge leftEdge = TileManager.getTileObject(left).getTileEdge();
        TileEdge rightEdge = TileManager.getTileObject(right).getTileEdge();

        int code = 0;

        if (upEdge == null || sourceEdge.getId() != upEdge.getId()) code += 1;
        if (downEdge == null || sourceEdge.getId() != downEdge.getId()) code += 2;
        if (leftEdge == null || sourceEdge.getId() != leftEdge.getId()) code += 4;
        if (rightEdge == null || sourceEdge.getId() != rightEdge.getId()) code += 8;


        return EdgeCode.fromId(code);
    }
}
