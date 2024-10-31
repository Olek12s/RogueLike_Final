package utilities;

import java.awt.image.BufferedImage;

public class Sprite
{
    public static BufferedImage extractSprite(BufferedImage spriteSheet, int textureResolution, int col, int row)
    {
        return null;
    }

    public static int countAnimationTicks(BufferedImage spriteSheet, int textureResolution)
    {
        int offset = 2;
        int padding = 3; // padding between sprites
        int sheetWidth = spriteSheet.getWidth();

        int availableWidth = sheetWidth - offset;
        int spriteAndPaddingWidth = textureResolution + padding;
        int spriteCounter = 0;


        for (int col = 0; col * spriteAndPaddingWidth + offset < availableWidth; col++)
        {
            int startX = col * spriteAndPaddingWidth + offset;
            int startY = 0;

            if (isSprite(spriteSheet, startX, startY, textureResolution))
            {
                spriteCounter++;
            }
            else
            {
                break;
            }
        }

        return spriteCounter;
    }

    // If the area has a non-transparent pixel, return true
    public static boolean isSprite(BufferedImage spriteSheet, int startX, int startY, int resolution)
    {
        for (int x = startX; x < startX + resolution; x++)
        {
            for (int y = startY; y < startY + resolution; y++)
            {
                int pixel = spriteSheet.getRGB(x, y);
                if ((pixel >> 24) != 0x00) // check if pixel is transparent (ARGB, 8 bits for every part)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
