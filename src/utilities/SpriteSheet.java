package utilities;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    public static int spriteSheetPadding = 3;    // padding between sprites
    public static int spriteSheetOffset = 2;     // offset from spritesheet boundaries

    public static BufferedImage extractSprite(BufferedImage spriteSheet, int textureResolution, int tick, int variation)
    {
        int spriteAndPaddingWidth = textureResolution + spriteSheetPadding;
        int spriteAndPaddingHeight = textureResolution + spriteSheetPadding;

        int startX = tick * spriteAndPaddingWidth + spriteSheetOffset;
        int startY = variation * spriteAndPaddingHeight + spriteSheetOffset;

        if (isSprite(spriteSheet, startX, startY, textureResolution))
        {
            return spriteSheet.getSubimage(startX, startY, textureResolution, textureResolution);
        }
        else
        {
            //throw new NoSuchElementException("No sprite found at tick: " + tick + ", variation: " + variation);
            return null;    // Maybe return default Sprite with given textureResolution
        }
    }

    public static BufferedImage extractFirst(BufferedImage spriteSheet, int textureResolution)
    {
        return extractSprite(spriteSheet, textureResolution, 0, 0);
    }

    public static int countAnimationTicks(BufferedImage spriteSheet, int textureResolution)
    {
        int sheetWidth = spriteSheet.getWidth();

        int availableWidth = sheetWidth - spriteSheetOffset;
        int spriteAndPaddingWidth = textureResolution + spriteSheetPadding;
        int spriteCounter = 0;


        for (int col = 0; col * spriteAndPaddingWidth + spriteSheetOffset < availableWidth; col++)
        {
            int startX = col * spriteAndPaddingWidth + spriteSheetOffset;
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

    public static int countSpriteVariations(BufferedImage spriteSheet, int textureResolution)
    {
        int sheetHeight = spriteSheet.getHeight();

        int availableHeight = sheetHeight - spriteSheetOffset;
        int spriteAndPaddingHeight = textureResolution + spriteSheetPadding;
        int spriteCounter = 0;

        for (int row = 0; row * spriteAndPaddingHeight + spriteSheetOffset < availableHeight; row++)
        {
            int startX = 0;
            int startY = row * spriteAndPaddingHeight + spriteSheetOffset;

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
