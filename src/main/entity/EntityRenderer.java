package main.entity;

import main.controller.DrawPriorities;
import main.controller.Drawable;
import main.entity.player.Player;
import utilities.Hitbox;
import utilities.pathfinding.astar.AStar;
import world.map.Chunk;
import world.map.MapRenderer;
import utilities.Position;
import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;
import utilities.camera.Camera;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class EntityRenderer implements Drawable {
    public boolean a = false;
    public Entity entity;
    //protected SpriteSheet spriteSheet;  // rendundant. replaced with static hashmap of spriteSheets
    private static final Map<Integer, SpriteSheet> spriteSheetsMap = new HashMap<>();
    private static final Map<Integer, Sprite[][]> spriteImagesMap = new HashMap<>();

    public Entity getEntity() {
        return entity;
    }

    //public SpriteSheet getSpriteSheet() {return spriteSheet;}
    public static SpriteSheet getSpriteSheetByID(int id) {
        return spriteSheetsMap.get(id);
    }

    public static Sprite[][] getSpriteImagesByID(int id) {
        return spriteImagesMap.get(id);
    }

    public static void putSpriteSheet(SpriteSheet spriteSheet, int id) {
        spriteSheetsMap.put(id, spriteSheet);
        loadSpriteImagesToMap(id, spriteSheet);
    }


    public EntityRenderer(Entity entity, SpriteSheet spriteSheet) {
        if (entity.getLevel() == entity.gc.mapController.getCurrentMap().getLevel()) {
            this.entity = entity;
            //this.spriteSheet = spriteSheet;
            spriteSheetsMap.put(entity.entityID, spriteSheet);
            loadSpriteImagesToMap(entity.entityID, spriteSheet);
            //loadSpriteImages();
            entity.gc.drawables.add(this);
        }
    }

    @Override
    public int getDrawPriority() {
        return DrawPriorities.Entity.value;
    }

    @Override
    public void draw(Graphics g2) {
        if (entity.getLevel() == entity.gc.mapController.getCurrentMap().getLevel()) {
            double scaleFactor = Camera.getScaleFactor();
            Position screenPosition = entity.gc.camera.applyCameraOffset(entity.worldPosition.x, entity.worldPosition.y);


            int scaledWidth = (int) (entity.currentSprite.image.getWidth() * scaleFactor);
            int scaledHeight = (int) (entity.currentSprite.image.getHeight() * scaleFactor);

            Chunk entityChunk = entity.gc.mapController.getCurrentMap().getChunk(entity.getWorldPosition());
            Chunk cameraChunk = entity.gc.mapController.getCurrentMap().getChunk(entity.gc.camera.getCameraPosition());

            if (entityChunk != null && cameraChunk != null) {
                //This condition checks whether the entity's chunk is within the rendering distance from the camera's chunk along both the x and y axes.
                if (Math.abs(entityChunk.getxIndex() - cameraChunk.getxIndex()) <= MapRenderer.chunkRenderDistance &&
                        Math.abs(entityChunk.getyIndex() - cameraChunk.getyIndex()) <= MapRenderer.chunkRenderDistance) {
                    g2.drawImage(entity.currentSprite.image, screenPosition.x, screenPosition.y, scaledWidth, scaledHeight, null);
                    entity.gc.incrementRenderCount();
                    if (entity.gc.isDebugMode()) {
                        drawEntityHitbox(g2);
                        drawEntityDetectionRadius(g2);
                        drawEntityLoseInterestRadius(g2);
                        drawEntityAttackHitbox(g2);
                        if (entity instanceof Player == false)
                        {
                            drawLineToPlayer(g2);
                            drawHealthBar(g2);
                        }
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
    protected static void loadSpriteImagesToMap(int entityID, SpriteSheet spriteSheet) {
        int ticks = spriteSheet.countAnimationTicks();
        int variations = spriteSheet.countSpriteVariations();
        Sprite[][] spriteImages = new Sprite[ticks][variations];

        for (int tick = 0; tick < ticks; tick++) {
            for (int variation = 0; variation < variations; variation++) {
                spriteImages[tick][variation] = spriteSheet.extractSprite(spriteSheet, tick, variation);
            }
        }
        spriteImagesMap.put(entityID, spriteImages);
    }

    private void drawEntityHitbox(Graphics g2)
    {
        drawHitbox(g2, entity.getHitbox(), Color.ORANGE);
        entity.gc.incrementRenderCount();
    }

    public void drawHitbox(Graphics g2, Hitbox hitbox, Color color)
    {
        drawHitbox(g2, hitbox, color, 0, 0);
    }

    private void drawHitbox(Graphics g2, Hitbox hitbox, Color color, int xOffset, int yOffset)
    {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position screenPosition = entity.gc.camera.applyCameraOffset(entity.hitbox.getHitboxRect().x - xOffset, entity.hitbox.getHitboxRect().y - yOffset);

        int scaledHitboxWidth = (int) (hitbox.getHitboxRect().width * scaleFactor);
        int scaledHitboxHeight = (int) (hitbox.getHitboxRect().height * scaleFactor);
        g2.setColor(color);
        g2.drawRect(screenPosition.x, screenPosition.y, scaledHitboxWidth, scaledHitboxHeight);
        entity.gc.incrementRenderCount();
    }

    private void drawEntityAttackHitbox(Graphics g2)
    {
        Hitbox hitbox = entity.getAttackHitbox();
        if (hitbox == null) return;
        int xOffset = entity.hitbox.getHitboxRect().x - entity.getAttackHitbox().getHitboxRect().x;
        int yOffset = entity.hitbox.getHitboxRect().y - entity.getAttackHitbox().getHitboxRect().y;
        drawHitbox(g2, entity.getAttackHitbox(), Color.RED, xOffset, yOffset);
    }

    private void drawEntityDetectionRadius(Graphics g2) {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position hitboxCenter = entity.getHitbox().getCenterWorldPosition();
        Position screenPosition = entity.gc.camera.applyCameraOffset(hitboxCenter.x, hitboxCenter.y);

        int radius = entity.getDetectionDiameter();
        int scaledRadius = (int) (radius * scaleFactor);

        g2.setColor(Color.red);
        g2.drawOval(screenPosition.x - scaledRadius / 2, screenPosition.y - scaledRadius / 2, scaledRadius, scaledRadius);
    }

    private void drawEntityLoseInterestRadius(Graphics g2) {
        double scaleFactor = entity.gc.camera.getScaleFactor();
        Position hitboxCenter = entity.getHitbox().getCenterWorldPosition();
        Position screenPosition = entity.gc.camera.applyCameraOffset(hitboxCenter.x, hitboxCenter.y);

        int radius = entity.getLoseInterestDiameter();
        int scaledRadius = (int) (radius * scaleFactor);

        g2.setColor(Color.YELLOW);
        g2.drawOval(screenPosition.x - scaledRadius / 2, screenPosition.y - scaledRadius / 2, scaledRadius, scaledRadius);
    }


    public void drawLineToPlayer(Graphics g2)
    {
        Position[] path = entity.getPathToFollow();

        // if (path.length > 3000)
        //if (path != null) System.out.println(path.length);
        g2.setColor(Color.BLUE);
        if (path != null) {
            for (int i = 0; i < path.length - 1; i++)
            {
                Position current = path[i];
                Position next = path[i + 1];

                Position screenCurrent = entity.gc.camera.applyCameraOffset(current.x, current.y);
                Position screenNext = entity.gc.camera.applyCameraOffset(next.x, next.y);

                g2.drawLine(screenCurrent.x, screenCurrent.y, screenNext.x, screenNext.y);
            }
        }
    }

    public void drawHealthBar(Graphics g2)
    {
         if (entity.getCurrentHealth() < entity.getMaximumHealth())
        {
            Position current = entity.getHitbox().getWorldPosition();

            int hitboxWidth = entity.getHitbox().getWidth();

            double healthPercentage = (double) entity.getCurrentHealth() / entity.getMaximumHealth();
            int healthBarWidth = (int) (hitboxWidth * healthPercentage);


            Position screenCurrent = entity.gc.camera.applyCameraOffset(current.x, current.y);
            Position screenNext = entity.gc.camera.applyCameraOffset(current.x + hitboxWidth, current.y);

            int lineWidth;
            if (Camera.getScaleFactor() < 0.33) lineWidth = 2;
            else if (Camera.getScaleFactor() < 1) lineWidth = 3;
            else if (Camera.getScaleFactor() < 1.25) lineWidth = 4;
            else lineWidth = 5;

            // gray line
            g2.setColor(Color.DARK_GRAY);
            Graphics2D g2d = (Graphics2D) g2;
            g2d.setStroke(new BasicStroke(lineWidth));
            g2d.drawLine(screenCurrent.x, screenCurrent.y, screenNext.x, screenNext.y);

            // red line
            Position screenNextRed = entity.gc.camera.applyCameraOffset(current.x + healthBarWidth, current.y);
            g2.setColor(Color.RED);
            g2d.drawLine(screenCurrent.x, screenCurrent.y, screenNextRed.x, screenNextRed.y);
            g2d.setStroke(new BasicStroke(1));
        }
    }
}
