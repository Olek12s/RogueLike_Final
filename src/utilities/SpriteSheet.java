package utilities;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    private BufferedImage spriteSheet;
    public static int spriteSheetPadding = 3;    // padding between sprites
    public static int spriteSheetOffset = 2;     // offset from spritesheet boundaries
    public int variations;
    public int ticks;
    public int textureResolution;

    public BufferedImage getImage() {return spriteSheet;}

    public SpriteSheet(BufferedImage spriteSheet, int textureResolution)
    {
        this.spriteSheet = spriteSheet;
        this.variations = countSpriteVariations();
        this.ticks = countAnimationTicks(this, textureResolution);
        this.textureResolution = textureResolution;
    }

    public Sprite extractSprite(SpriteSheet spriteSheet, int tick, int variation)
    {
        int spriteAndPaddingWidth = textureResolution + spriteSheetPadding;
        int spriteAndPaddingHeight = textureResolution + spriteSheetPadding;

        int startX = tick * spriteAndPaddingWidth + spriteSheetOffset;
        int startY = variation * spriteAndPaddingHeight + spriteSheetOffset;

        if (isSprite(spriteSheet, startX, startY))
        {
            BufferedImage spriteImage = spriteSheet.getImage().getSubimage(startX, startY, textureResolution, textureResolution);
            return new Sprite(spriteImage, textureResolution);
        }
        else
        {
            //throw new NoSuchElementException("No sprite found at tick: " + tick + ", variation: " + variation);
            return null;    // Maybe return default Sprite with given textureResolution
        }
    }

    public Sprite extractFirst(SpriteSheet spriteSheet)
    {
        return extractSprite(spriteSheet, 0, 0);
    }

    public int countAnimationTicks(SpriteSheet spriteSheet, int textureResolution)
    {
        int sheetWidth = spriteSheet.getImage().getWidth();

        int availableWidth = sheetWidth - spriteSheetOffset;
        int spriteAndPaddingWidth = textureResolution + spriteSheetPadding;
        int spriteCounter = 0;


        for (int col = 0; col * spriteAndPaddingWidth + spriteSheetOffset < availableWidth; col++)
        {
            int startX = col * spriteAndPaddingWidth + spriteSheetOffset;
            int startY = 0;

            if (isSprite(spriteSheet, startX, startY))
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

    public int countSpriteVariations()
    {
        int sheetHeight = spriteSheet.getHeight();

        int availableHeight = sheetHeight - spriteSheetOffset;
        int spriteAndPaddingHeight = textureResolution + spriteSheetPadding;
        int spriteCounter = 0;

        for (int row = 0; row * spriteAndPaddingHeight + spriteSheetOffset < availableHeight; row++)
        {
            int startX = 0;
            int startY = row * spriteAndPaddingHeight + spriteSheetOffset;

            if (isSprite(this, startX, startY))
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
    public boolean isSprite(SpriteSheet spriteSheet, int startX, int startY)
    {
        BufferedImage image = spriteSheet.getImage();
        int maxX = startX + textureResolution;
        int maxY = startY + textureResolution;

        for (int x = startX; x < maxX; x++)
        {
            for (int y = startY; y < maxY; y++)
            {
                int pixel = image.getRGB(x, y);
                if ((pixel >> 24) != 0x00)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
