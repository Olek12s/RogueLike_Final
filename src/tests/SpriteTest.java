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
        int padding = 3;
        int startX = resolution+padding;
        Assertions.assertTrue(Sprite.isSprite(spriteSheet, startX*0, padding, resolution), "[Field 0 was expected to be a sprite.]");
        Assertions.assertTrue(Sprite.isSprite(spriteSheet, startX*1, padding, resolution), "[Field 1 was expected to be a sprite.]");
        Assertions.assertTrue(Sprite.isSprite(spriteSheet, startX*2, padding, resolution), "[Field 2 was expected to be a sprite.]");
        Assertions.assertTrue(Sprite.isSprite(spriteSheet, startX*3, padding, resolution), "[Field 3 was expected to be a sprite.]");
        Assertions.assertFalse(Sprite.isSprite(spriteSheet, startX*4,padding, resolution), "[Field 4 was expected to not be a sprite.]");
    }
}
