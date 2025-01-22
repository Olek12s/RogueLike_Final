package main.entity;

import main.controller.DrawPriorities;
import main.controller.Drawable;
import world.map.Chunk;
import world.map.MapRenderer;
import utilities.Position;
import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;
import utilities.camera.Camera;
import world.map.tiles.Tile;

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
        if (entity.getLevel() == entity.gc.mapController.getCurrentMap().getLevel())
        {
            this.entity = entity;
            //this.spriteSheet = spriteSheet;
            spriteSheetsMap.put(entity.entityID, spriteSheet);
            loadSpriteImagesToMap(entity.entityID, spriteSheet);
            //loadSpriteImages();

            entity.gc.drawables.add(this);
        }
    }

    @Override
    public int getDrawPriority() {return DrawPriorities.Entity.value;}

    @Override
    public void draw(Graphics g2)
    {
        if (entity.getLevel() == entity.gc.mapController.getCurrentMap().getLevel())
        {
            double scaleFactor = Camera.getScaleFactor();
            Position screenPosition = entity.gc.camera.applyCameraOffset(entity.worldPosition.x, entity.worldPosition.y);


            int scaledWidth = (int) (entity.currentSprite.image.getWidth() * scaleFactor);
            int scaledHeight = (int) (entity.currentSprite.image.getHeight() * scaleFactor);

            Chunk entityChunk = entity.gc.mapController.getCurrentMap().getChunk(entity.getWorldPosition());
            Chunk cameraChunk = entity.gc.mapController.getCurrentMap().getChunk(entity.gc.camera.getCameraPosition());


            if (entityChunk != null && cameraChunk != null)
            {
                //This condition checks whether the entity's chunk is within the rendering distance from the camera's chunk along both the x and y axes.
                if (Math.abs(entityChunk.getxIndex() - cameraChunk.getxIndex()) <= MapRenderer.chunkRenderDistance &&
                        Math.abs(entityChunk.getyIndex() - cameraChunk.getyIndex()) <= MapRenderer.chunkRenderDistance)
                {
                    g2.drawImage(entity.currentSprite.image, screenPosition.x, screenPosition.y, scaledWidth, scaledHeight, null);
                    entity.gc.incrementRenderCount();
                    if (entity.gc.isDebugMode())
                    {
                        drawEntityHitbox(g2);
                        drawEntityDetectionRadius(g2);
                        drawEntityLoseInterestRadius(g2);
                    }
                }

            }
            //g2.dispose();
        }
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

    private void drawEntityHitbox(Graphics g2)
    {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position screenPosition = entity.gc.camera.applyCameraOffset(entity.hitbox.getHitboxRect().x, entity.hitbox.getHitboxRect().y);

        int scaledHitboxWidth = (int) (entity.getHitbox().getHitboxRect().width * scaleFactor);
        int scaledHitboxHeight = (int) (entity.getHitbox().getHitboxRect().height * scaleFactor);
        g2.setColor (Color.ORANGE);
        g2.drawRect(screenPosition.x, screenPosition.y, scaledHitboxWidth, scaledHitboxHeight);
        entity.gc.incrementRenderCount();
    }

    /*
    private void drawEntityDetectionRadius(Graphics g2)
    {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position screenPosition = entity.gc.camera.applyCameraOffset(entity.hitbox.getHitboxRect().x, entity.hitbox.getHitboxRect().y);

        int x = entity.getHitbox().getCenterWorldPosition().x;
        int y = entity.getHitbox().getCenterWorldPosition().y;
        int radius = entity.getDetectionRadius();
        int scaledRadius = (int) (radius * scaleFactor);

        g2.drawOval(x, y, entity.getDetectionRadius(), radius);
    }

    private void drawEntityLoseInterestRadius(Graphics g2)
    {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position screenPosition = entity.gc.camera.applyCameraOffset(entity.hitbox.getHitboxRect().x, entity.hitbox.getHitboxRect().y);

        int x = entity.getHitbox().getCenterWorldPosition().x;
        int y = entity.getHitbox().getCenterWorldPosition().y;
        int radius = entity.getLoseInterestRadius();
        int scaledRadius = (int) (radius * scaleFactor);

        g2.drawOval(x, y, entity.getDetectionRadius(), radius);
    }
     */

    private void drawEntityDetectionRadius(Graphics g2)
    {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position hitboxCenter = entity.getHitbox().getCenterWorldPosition();
        Position screenPosition = entity.gc.camera.applyCameraOffset(hitboxCenter.x, hitboxCenter.y);

        int radius = entity.getDetectionRadius();
        int scaledRadius = (int) (radius * scaleFactor);

        g2.setColor(Color.red);
        g2.drawOval(screenPosition.x - scaledRadius/2, screenPosition.y - scaledRadius/2, scaledRadius, scaledRadius);
    }

    private void drawEntityLoseInterestRadius(Graphics g2)
    {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position hitboxCenter = entity.getHitbox().getCenterWorldPosition();
        Position screenPosition = entity.gc.camera.applyCameraOffset(hitboxCenter.x, hitboxCenter.y);

        int radius = entity.getLoseInterestRadius();
        int scaledRadius = (int) (radius * scaleFactor);

        g2.setColor(Color.YELLOW);
        g2.drawOval(screenPosition.x - scaledRadius/2, screenPosition.y - scaledRadius/2, scaledRadius, scaledRadius);
    }
}
