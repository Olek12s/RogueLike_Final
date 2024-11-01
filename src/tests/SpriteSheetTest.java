package tests;

import test.annotations.Before;
import test.annotations.Test;
import test.runner.Assertions;
import utilities.SpriteSheet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheetTest
{
    BufferedImage spriteSheet;
    @Before
    public void initialize()
    {
        try
        {
            spriteSheet = ImageIO.read(new File("resources/default/SpriteSheet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void isSpriteTest()
    {
        int resolution = 48;
        int padding = SpriteSheet.spriteSheetPadding;
        int startX = resolution+padding;
        Assertions.assertTrue(SpriteSheet.isSprite(spriteSheet, startX*0, padding, resolution), "[Field 0 was expected to be a sprite.]");
        Assertions.assertTrue(SpriteSheet.isSprite(spriteSheet, startX*1, padding, resolution), "[Field 1 was expected to be a sprite.]");
        Assertions.assertTrue(SpriteSheet.isSprite(spriteSheet, startX*2, padding, resolution), "[Field 2 was expected to be a sprite.]");
        Assertions.assertTrue(SpriteSheet.isSprite(spriteSheet, startX*3, padding, resolution), "[Field 3 was expected to be a sprite.]");
        Assertions.assertFalse(SpriteSheet.isSprite(spriteSheet, startX*4,padding, resolution), "[Field 4 was expected to not be a sprite.]");
    }

    @Test
    public void countAnimationTicks()
    {
        int resolution = 48;
        Assertions.assertEquals(4, SpriteSheet.countAnimationTicks(spriteSheet, 48), "[Unexpected animation ticks count.]");
    }

    @Test
    public void countSpriteVariations()
    {
        int resolution = 48;
        Assertions.assertEquals(8, SpriteSheet.countSpriteVariations(spriteSheet, 48), "[Unexpected sprite variations count.]");
    }

    @Test
    public void extractSprite()
    {
        int resolution = 48;
        int animationTicks = 4;
        int variations = 8;
        for (int i = 0; i < animationTicks; i++)
        {
            for (int j = 0; j < variations; j++)
            {
                Assertions.assertNotNull(SpriteSheet.extractSprite(spriteSheet, 48, i, j), "[Extracted sprite was expected to be not null]");
            }
        }
        Assertions.assertNull(SpriteSheet.extractSprite(spriteSheet, 48, animationTicks, variations), "[Extracted sprite was expected to be null]");
        Assertions.assertNull(SpriteSheet.extractSprite(spriteSheet, 48, animationTicks+1, variations), "[Extracted sprite was expected to be null]");
        Assertions.assertNull(SpriteSheet.extractSprite(spriteSheet, 48, animationTicks, variations+1), "[Extracted sprite was expected to be null]");
        Assertions.assertNull(SpriteSheet.extractSprite(spriteSheet, 48, animationTicks+1, variations+1), "[Extracted sprite was expected to be null]");
    }
}
