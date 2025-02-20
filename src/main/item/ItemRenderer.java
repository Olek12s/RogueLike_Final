package main.item;

import main.controller.DrawPriorities;
import main.controller.Drawable;
import utilities.Position;
import utilities.camera.Camera;
import utilities.sprite.Sprite;
import world.map.Chunk;
import world.map.MapRenderer;

import java.awt.*;

public class ItemRenderer implements Drawable
{
    private Item item;

    public ItemRenderer(Item item)
    {
        this.item = item;
        item.gc.drawables.add(this);
    }

    @Override
    public int getDrawPriority()
    {
        if (item.isOnGround()) return DrawPriorities.ItemGround.value;
        else return DrawPriorities.ItemInventory.value;
    }

    @Override
    public void draw(Graphics g2)
    {
        if (( item.getLevel() == item.gc.mapController.getCurrentMap().getLevel() ) && item.isOnGround())   // draw items on ground
        {
            double scaleFactor = Camera.getScaleFactor();
            Position screenPosition = item.gc.camera.applyCameraOffset(item.worldPosition.x, item.worldPosition.y);


            int scaledWidth = (int) (ItemManager.getItemSprite(item.itemID).image.getWidth() * scaleFactor);
            int scaledHeight = (int) (ItemManager.getItemSprite(item.itemID).image.getHeight() * scaleFactor);

            Chunk itemChunk = item.gc.mapController.getCurrentMap().getChunk(item.getWorldPosition());
            Chunk cameraChunk = item.gc.mapController.getCurrentMap().getChunk(item.gc.camera.getCameraPosition());


            if (itemChunk != null && cameraChunk != null)
            {
                //This condition checks whether the item's chunk is within the rendering distance from the camera's chunk along both the x and y axes.
                if (Math.abs(itemChunk.getxIndex() - cameraChunk.getxIndex()) <= MapRenderer.chunkRenderDistance &&
                        Math.abs(itemChunk.getyIndex() - cameraChunk.getyIndex()) <= MapRenderer.chunkRenderDistance)
                {
                    g2.drawImage(ItemManager.getItemSprite(item.itemID).image, screenPosition.x, screenPosition.y, scaledWidth, scaledHeight, null);
                    item.gc.incrementRenderCount();
                    if (item.gc.isDebugMode()) drawItenOnGroundHitbox(g2);
                }
            }
        }
    }

    private void drawItenOnGroundHitbox(Graphics g2)
    {
        double scaleFactor = item.gc.camera.getScaleFactor();
        Position screenPosition = item.gc.camera.applyCameraOffset(item.hitbox.getHitboxRect().x, item.hitbox.getHitboxRect().y);

        int scaledHitboxWidth = (int) (item.getHitbox().getHitboxRect().width * scaleFactor);
        int scaledHitboxHeight = (int) (item.getHitbox().getHitboxRect().height * scaleFactor);
        g2.setColor (Color.ORANGE);
        g2.drawRect(screenPosition.x, screenPosition.y, scaledHitboxWidth, scaledHitboxHeight);
        item.gc.incrementRenderCount();
    }
}
