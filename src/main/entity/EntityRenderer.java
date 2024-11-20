package main.entity;

import main.DrawPriorities;
import main.Drawable;
import utilities.Position;
import utilities.Sprite;
import utilities.SpriteSheet;
import utilities.camera.Camera;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EntityRenderer implements Drawable
{
    private Entity entity;
    //protected SpriteSheet spriteSheet;  // rendundant. replaced with static hashmap of spriteSheets
    private static final Map<Integer, SpriteSheet> spriteSheetsMap = new HashMap<>();
    private static final Map<Integer, Sprite[][]> spriteImagesMap = new HashMap<>();

    public Entity getEntity() {return entity;}
    //public SpriteSheet getSpriteSheet() {return spriteSheet;}
    public static SpriteSheet getSpriteSheetByID(int id) {return spriteSheetsMap.get(id);}
    public static Sprite[][] getSpriteImagesByID(int id) {return spriteImagesMap.get(id);}
    public static void putSpriteSheet(SpriteSheet spriteSheet, int id)
    {
        spriteSheetsMap.put(id, spriteSheet);
        loadSpriteImagesToMap(id, spriteSheet);
    }


    public EntityRenderer(Entity entity, SpriteSheet spriteSheet)
    {
        this.entity = entity;
        //this.spriteSheet = spriteSheet;
        spriteSheetsMap.put(entity.entityID, spriteSheet);
        loadSpriteImagesToMap(entity.entityID, spriteSheet);
        //loadSpriteImages();

        entity.gc.drawables.add(this);
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.Entity.value;}

    @Override
    public void draw(Graphics g2)
    {
        double scaleFactor = Camera.getScaleFactor();
        Position screenPosition = entity.gc.camera.applyCameraOffset(entity.worldPosition.x, entity.worldPosition.y);


        int scaledWidth = (int) (entity.currentSprite.image.getWidth() * scaleFactor);
        int scaledHeight = (int) (entity.currentSprite.image.getHeight() * scaleFactor);

        g2.drawImage(entity.currentSprite.image, screenPosition.x, screenPosition.y, scaledWidth, scaledHeight, null);
        //g2.dispose();
    }

    /*
    protected void loadSpriteImages()
    {
        int ticks = spriteSheet.countAnimationTicks();
        int variations = spriteSheet.countSpriteVariations();
        spriteImages = new Sprite[ticks][variations];

        for (int tick = 0; tick < ticks; tick++)
        {
            for (int variation = 0; variation < variations; variation++)
            {
                spriteImages[tick][variation] = spriteSheet.extractSprite(spriteSheet, tick, variation);
            }
        }
    }
     */
    protected static void loadSpriteImagesToMap(int entityID, SpriteSheet spriteSheet)
    {
        int ticks = spriteSheet.countAnimationTicks();
        int variations = spriteSheet.countSpriteVariations();
        Sprite[][] spriteImages = new Sprite[ticks][variations];

        for (int tick = 0; tick < ticks; tick++)
        {
            for (int variation = 0; variation < variations; variation++)
            {
                spriteImages[tick][variation] = spriteSheet.extractSprite(spriteSheet, tick, variation);
            }
        }
        spriteImagesMap.put(entityID, spriteImages);
    }

    protected void drawEntityHitbox(Graphics g2)
    {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position screenPosition = entity.gc.camera.applyCameraOffset(entity.hitbox.getHitboxRect().x, entity.hitbox.getHitboxRect().y);

        int scaledHitboxWidth = (int) (entity.getHitbox().getHitboxRect().width * scaleFactor);
        int scaledHitboxHeight = (int) (entity.getHitbox().getHitboxRect().height * scaleFactor);
        g2.setColor (Color.ORANGE);
        g2.drawRect(screenPosition.x, screenPosition.y, scaledHitboxWidth, scaledHitboxHeight);
    }
}
