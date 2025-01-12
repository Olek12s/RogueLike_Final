package utilities.sprite;

import java.awt.image.BufferedImage;

public class SpriteSheet
{
    private BufferedImage spriteSheetImage;
    public static int spriteSheetPadding = 3;    // padding between sprites
    public static int spriteSheetOffset = 2;     // offset from spritesheet boundaries
    public int variations;
    public int ticks;
    public int textureResolution;

    public BufferedImage getSpriteSheetImage() {return spriteSheetImage;}

    public SpriteSheet(BufferedImage spriteSheetImage, int textureResolution)
    {
        this.spriteSheetImage = spriteSheetImage;
        this.textureResolution = textureResolution;
        this.variations = countSpriteVariations();
        this.ticks = countAnimationTicks();
    }

    // Extracts Sprite from 2D spriteSheet.
    public Sprite extractSprite(SpriteSheet spriteSheet, int tick, int variation)
    {
        int startX = spriteSheetOffset + tick * (textureResolution + spriteSheetPadding);
        int startY = spriteSheetOffset + variation * (textureResolution + spriteSheetPadding);

        if (isSprite(spriteSheet, startX, startY))
        {
            BufferedImage spriteImage = spriteSheet.getSpriteSheetImage().getSubimage(startX, startY, textureResolution, textureResolution);
            return new Sprite(spriteImage, textureResolution);
        }
        else
        {
            //throw new NoSuchElementException("No sprite found at tick: " + tick + ", variation: " + variation);
            return null;    // Maybe return default Sprite with given textureResolution
        }
    }

    // Extracts Sprite from 1D spriteSheet.
    public Sprite extractSpriteByVariation(int variation)
    {
        int startX = spriteSheetOffset;
        int startY = spriteSheetOffset + (variation * (textureResolution + spriteSheetPadding));

        if (isSprite(this, startX, startY))
        {
            BufferedImage spriteImage = spriteSheetImage.getSubimage(startX, startY, textureResolution, textureResolution);
            return new Sprite(spriteImage, textureResolution);
        }
        else
        {
            return null;
        }
    }

    public Sprite extractFirst()
    {
        return extractSprite(this, 0, 0);
    }

    public int countAnimationTicks()
    {
        int sheetWidth = spriteSheetImage.getWidth();

        int availableWidth = sheetWidth - spriteSheetOffset;
        int spriteAndPaddingWidth = textureResolution + spriteSheetPadding;
        int spriteCounter = 0;


        for (int col = 0; col * spriteAndPaddingWidth + spriteSheetOffset < availableWidth; col++)
        {
            int startX = col * spriteAndPaddingWidth + spriteSheetOffset;
            int startY = 0;

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

    public int countSpriteVariations()
    {
        int sheetHeight = spriteSheetImage.getHeight();

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

    // If the area has a non-transparent pixel, return true. Used in 2D SpriteSheets
    private boolean isSprite(SpriteSheet spriteSheet, int startX, int startY)
    {
        BufferedImage image = spriteSheet.getSpriteSheetImage();
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
