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
    SpriteSheet spriteSheet;
    @Before
    public void initialize()
    {
        try
        {
             spriteSheet = new SpriteSheet(ImageIO.read(new File("resources/default/SpriteSheet.png")),48);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void isSpriteTest()
    {
        int padding = SpriteSheet.spriteSheetPadding;
        int startX = spriteSheet.textureResolution+padding;
        Assertions.assertTrue(spriteSheet.isSprite(spriteSheet, startX*0, padding), "[Field 0 was expected to be a sprite.]");
        Assertions.assertTrue(spriteSheet.isSprite(spriteSheet, startX*1, padding), "[Field 1 was expected to be a sprite.]");
        Assertions.assertTrue(spriteSheet.isSprite(spriteSheet, startX*2, padding), "[Field 2 was expected to be a sprite.]");
        Assertions.assertTrue(spriteSheet.isSprite(spriteSheet, startX*3, padding), "[Field 3 was expected to be a sprite.]");
        Assertions.assertFalse(spriteSheet.isSprite(spriteSheet, startX*4,padding), "[Field 4 was expected to not be a sprite.]");
    }

    @Test
    public void countAnimationTicks()
    {
        Assertions.assertEquals(4, spriteSheet.countAnimationTicks(), "[Unexpected animation ticks count.]");
    }

    @Test
    public void countSpriteVariations()
    {
        Assertions.assertEquals(8, spriteSheet.countSpriteVariations(), "[Unexpected sprite variations count.]");
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
                Assertions.assertNotNull(spriteSheet.extractSprite(spriteSheet, i, j), "[Extracted sprite was expected to be not null]");
            }
        }
        Assertions.assertNull(spriteSheet.extractSprite(spriteSheet, animationTicks, variations), "[Extracted sprite was expected to be null]");
        Assertions.assertNull(spriteSheet.extractSprite(spriteSheet,  animationTicks+1, variations), "[Extracted sprite was expected to be null]");
        Assertions.assertNull(spriteSheet.extractSprite(spriteSheet,  animationTicks, variations+1), "[Extracted sprite was expected to be null]");
        Assertions.assertNull(spriteSheet.extractSprite(spriteSheet, animationTicks+1, variations+1), "[Extracted sprite was expected to be null]");
    }
}
