package tests;

import test.annotations.Before;
import test.annotations.Test;
import test.runner.Assertions;
import utilities.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteTest
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
        int padding = Sprite.spriteSheetPadding;
        int startX = resolution+padding;
        Assertions.assertTrue(Sprite.isSprite(spriteSheet, startX*0, padding, resolution), "[Field 0 was expected to be a sprite.]");
        Assertions.assertTrue(Sprite.isSprite(spriteSheet, startX*1, padding, resolution), "[Field 1 was expected to be a sprite.]");
        Assertions.assertTrue(Sprite.isSprite(spriteSheet, startX*2, padding, resolution), "[Field 2 was expected to be a sprite.]");
        Assertions.assertTrue(Sprite.isSprite(spriteSheet, startX*3, padding, resolution), "[Field 3 was expected to be a sprite.]");
        Assertions.assertFalse(Sprite.isSprite(spriteSheet, startX*4,padding, resolution), "[Field 4 was expected to not be a sprite.]");
    }

    @Test
    public void countAnimationTicks()
    {
        int resolution = 48;
        Assertions.assertEquals(4, Sprite.countAnimationTicks(spriteSheet, 48), "[Unexpected animation ticks count.]");
    }

    @Test
    public void countSpriteVariations()
    {
        int resolution = 48;
        Assertions.assertEquals(8, Sprite.countSpriteVariations(spriteSheet, 48), "[Unexpected sprite variations count.]");
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
                Assertions.assertNotNull(Sprite.extractSprite(spriteSheet, 48, i, j));
            }
        }
    }
}
