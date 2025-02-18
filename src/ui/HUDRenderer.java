package ui;

import main.controller.DrawPriorities;
import main.controller.Drawable;
import main.controller.GameState;
import main.entity.EntityStatistics;
import main.inventory.Inventory;
import main.inventory.Slot;
import main.inventory.SlotType;
import main.item.Item;
import utilities.Position;
import utilities.sprite.Sprite;
import world.map.tiles.Tile;

import java.awt.*;

public class HUDRenderer implements Drawable
{
    HUD hud;
    int renderDebugInfoCounter;
    private String renderTime = "-";
    private String updateTime = "-";
    private String summaryTime = "-";
    private String drawCount = "-";
    private final int baseSlotSize = 45;

    private Font HUDFont;
    private final int baseFontSize = 14;
    private int scaledFontSize;
    private int slotSize;

    // FRAME POSITIONS //
    private Position mainInventoryPosition;
    private Position equippedPosition;
    private Position beltPosition;
    private Position helmetSlotPosition;
    private Position chestplateSlotPosition;
    private Position pantsSlotPosition;
    private Position bootsSlotPosition;
    private Position shieldSlotPosition;
    private Position ring1SlotPosition;
    private Position ring2SlotPosition;
    private Position amuletSlotPosition;
    // FRAME POSITIONS //
    private Position[] levelUpIconPositions;

    public Position[] getLevelUpIconPositions() {return levelUpIconPositions;}
    public int getScaledFontSize() {return scaledFontSize;}

    public HUDRenderer(HUD hud)
    {
        this.hud = hud;
        hud.gc.drawables.add(this);
    }


    @Override
    public int getDrawPriority()
    {
        return DrawPriorities.HUD.value;
    }

    @Override
    public void draw(Graphics g2)
    {
        renderHealthBar(g2);
        renderInventorybelt(g2);
        renderFPSTopRight(g2);
        if (hud.gc.gameStateController.getCurrentGameState() == GameState.INVENTORY)
        {
           renderMainInventory(g2);
           renderStatisticsFrame(g2);
           renderEquippedFrame(g2);
           renderHeldItem(g2);
        }
        if (hud.gc.isDebugMode())
        {
            renderDebugInfoLeft(g2);
            renderDebugInfoLeftTop(g2);
        }
        hud.gc.incrementRenderCount();
    }

    private void renderHealthBar(Graphics g2) {
        Graphics2D g2d = (Graphics2D) g2;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));

        if (hud.gc.player.statistics.getHitPoints() < 0) hud.gc.player.statistics.setHitPoints(0);
        int healthPercent = (int) ((hud.gc.player.statistics.getHitPoints() / (double) hud.gc.player.getMaxHitPoints()) * 100);

        int spriteSize = hud.heart.image.getWidth() / Math.max(hud.scale, 1);
        int width = hud.gc.getWidth();
        int height = hud.gc.getHeight();

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillRect((int)(hud.scale/1.4f), (int)(hud.scale/5f), hud.scale*4, (int)(hud.scale/2.6f));
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)(hud.scale/1.4f), (int)(hud.scale/5f), hud.scale*4, (int)(hud.scale/2.6f));
        g2d.setColor(Color.RED);
        g2d.fillRect((int)(hud.scale/1.4f)+1, (int)(hud.scale/5f)+1, (healthPercent * hud.scale*4) / 100, (int)(hud.scale/2.6f)-1);

        g2d.drawImage(hud.heart.image, 0, 0, hud.scale, hud.scale, null);
    }
    private void renderDebugInfoLeft(Graphics g2) {
        renderDebugInfoCounter++;
        if (renderDebugInfoCounter >= 60)
        {
            updateTimers();
            updateDrawCount();
            renderDebugInfoCounter = 0;
        }

        Graphics2D g2d = (Graphics2D) g2;
        g2d.setColor(Color.WHITE);
        g2d.setFont(HUDFont);

        int baseX = 10;
        int baseY = (int)(hud.gc.getHeight()/1.8f);
        int scaledX = (int) (baseX * (hud.scale / 64.0));
        int scaledY = baseY;

        String debugInfo = getTimers() + "\n" + drawCount;
        String[] debugLines = debugInfo.split("\\n");

        for (int i = 0; i < debugLines.length; i++) {
            g2d.drawString(debugLines[i], scaledX, scaledY + i * (scaledFontSize + 5));
        }
    }
    private void renderFPSTopRight(Graphics g2) {
        Graphics2D g2d = (Graphics2D) g2.create(); // Tworzymy nowy kontekst graficzny

        int x = hud.gc.getWidth() - hud.gc.getWidth() / 11;
        int y = 40;


        g2d.setColor(Color.WHITE);
        g2d.setFont(HUDFont);

        long totalTimePerFrame = hud.gc.getRenderTime() + hud.gc.getUpdateTime();
        int fpsVal = totalTimePerFrame > 0 ? (int) (1_000_000_000L / totalTimePerFrame) : 0;
        fpsVal = Math.min(hud.gc.getTargetDrawFrame(), fpsVal);

        String fps = String.format("FPS: %d", fpsVal);
        g2d.drawString(fps, x, y);

    }
    private void renderDebugInfoLeftTop(Graphics g2) {
        Graphics2D g2d = (Graphics2D) g2;
        g2d.setColor(Color.WHITE);
        g2d.setFont(HUDFont);

        int baseX = 10;
        int baseY = 80;
        int scaledX = (int) (baseX * (hud.scale / 64.0));
        int scaledY = (int) (baseY * (hud.scale / 64.0));

        int positionX = hud.gc.camera.getCameraPosition().x;
        int positionY = hud.gc.camera.getCameraPosition().y;

        String cameraPosX = "X: " + positionX / Tile.tileSize + "  " + positionX;
        String cameraPosY = "Y: " + positionY / Tile.tileSize + "  " + positionY;

        g2d.drawString(cameraPosX, scaledX, scaledY);
        g2d.drawString(cameraPosY, scaledX, scaledY + (scaledFontSize + 5));
    }
    private void updateTimers() {
        renderTime = String.format("Render time: %.2f ms (%.2f%%)",
                hud.gc.getRenderTime() / 1_000_000.0f,
                (hud.gc.getRenderTime() / 1_000_000.0f) / (1000.0f / hud.gc.getTargetDrawFrame()) * 100.0f);

        updateTime = String.format("Update time: %.2f ms (%.2f%%)",
                hud.gc.getUpdateTime() / 1_000_000.0f,
                (hud.gc.getUpdateTime() / 1_000_000.0f) / (1000.0f / hud.gc.getTargetLogicFrame()) * 100.0f);

        summaryTime = String.format("Summary time: %.2f ms (%.2f%%)",
                (hud.gc.getRenderTime() + hud.gc.getUpdateTime()) / 1_000_000.0f,
                ((hud.gc.getRenderTime() / 1_000_000.0f) / (1000.0f / hud.gc.getTargetDrawFrame()) * 100.0f) +
                        ((hud.gc.getUpdateTime() / 1_000_000.0f) / (1000.0f / hud.gc.getTargetLogicFrame()) * 100.0f));
    }
    private String getTimers()
    {
        return renderTime + "\n" + updateTime + "\n" + summaryTime;
    }
    private void updateDrawCount() {
        long count = hud.gc.getRenderCount();
        drawCount = "Draw count: " + count;
    }

    private void renderFrame(Graphics g, int x, int y, int width, int height, int innerPadding, int outerWidth, int innerWidth, float opacity)
    {
        Graphics2D g2d = (Graphics2D)g.create();

        // rounding parameters
        int arcWidth = 10;
        int arcHeight = 10;

        // inner part of frame
        g2d.setStroke(new BasicStroke());
        g2d.setColor(new Color(0f, 0f, 0f, opacity));
        g2d.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

        // outer part of frame
        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(outerWidth));
        g2d.drawRoundRect(x, y, width, height, arcWidth, arcHeight);

        // middle part of frame
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(innerWidth));
        g2d.drawRoundRect(x + innerPadding, y + innerPadding, width  - (innerPadding*2), height - (innerPadding*2), arcWidth, arcHeight);
        // g2d.dispose();
    }

    public Slot getSlotAtPosition(Position pos)
    {
        // checking main inventory slots
        for (int i = 0; i < hud.gc.player.getInventory().getInventorySlots().length; i++)
        {
            for (int j = 0; j < hud.gc.player.getInventory().getInventorySlots()[i].length; j++)
            {
                Slot slot = hud.gc.player.getInventory().getInventorySlots()[i][j];
                if (isPositionWithinSlot(pos, i, j, mainInventoryPosition, SlotType.getWidthMultipler(SlotType.mainInvSlot), SlotType.getHeightMultipler(SlotType.mainInvSlot)))
                {
                    return slot;
                }
            }
        }

        // checking belt slots
        for (int i = 0; i < hud.gc.player.getInventory().getBeltSlots().length; i++)
        {
            Slot slot = hud.gc.player.getInventory().getBeltSlots()[i];
            if (isPositionWithinSlot(pos, i, 0, beltPosition, SlotType.getWidthMultipler(SlotType.mainInvSlot), SlotType.getHeightMultipler(SlotType.mainInvSlot)))
            {
                return slot;
            }
        }

        // Checking equipment slots
        if (isPositionWithinSlot(pos, 0, 0, helmetSlotPosition, SlotType.getWidthMultipler(SlotType.helmetSlot), SlotType.getHeightMultipler(SlotType.helmetSlot)))
            return hud.gc.player.getInventory().getHelmetSlot();

        if (isPositionWithinSlot(pos, 0, 0, chestplateSlotPosition, SlotType.getWidthMultipler(SlotType.chestplateSlot), SlotType.getHeightMultipler(SlotType.chestplateSlot)))
            return hud.gc.player.getInventory().getChestplateSlot();

        if (isPositionWithinSlot(pos, 0, 0, pantsSlotPosition, SlotType.getWidthMultipler(SlotType.pantsSlot), SlotType.getHeightMultipler(SlotType.pantsSlot)))
            return hud.gc.player.getInventory().getPantsSlot();

        if (isPositionWithinSlot(pos, 0, 0, bootsSlotPosition, SlotType.getWidthMultipler(SlotType.bootsSlot), SlotType.getHeightMultipler(SlotType.bootsSlot)))
            return hud.gc.player.getInventory().getBootsSlot();

        if (isPositionWithinSlot(pos, 0, 0, shieldSlotPosition, SlotType.getWidthMultipler(SlotType.shieldSlot), SlotType.getHeightMultipler(SlotType.shieldSlot)))
            return hud.gc.player.getInventory().getShieldSlot();

        if (isPositionWithinSlot(pos, 0, 0, ring1SlotPosition, SlotType.getWidthMultipler(SlotType.ring1Slot), SlotType.getHeightMultipler(SlotType.ring1Slot)))
            return hud.gc.player.getInventory().getRing1Slot();

        if (isPositionWithinSlot(pos, 0, 0, ring2SlotPosition, SlotType.getWidthMultipler(SlotType.ring2Slot), SlotType.getHeightMultipler(SlotType.ring2Slot)))
            return hud.gc.player.getInventory().getRing2Slot();

        if (isPositionWithinSlot(pos, 0, 0, amuletSlotPosition, SlotType.getWidthMultipler(SlotType.amuletSlot), SlotType.getHeightMultipler(SlotType.amuletSlot)))
            return hud.gc.player.getInventory().getAmuletSlot();

        return null; // if no slot was found
    }

    private boolean isPositionWithinSlot(Position pos, int slotX, int slotY, Position basePosition, int widthMultiplier, int heightMultiplier)
    {
        int startX = basePosition.x + slotX * slotSize;
        int startY = basePosition.y + slotY * slotSize;
        int endX = startX + widthMultiplier * slotSize;
        int endY = startY + heightMultiplier * slotSize;

        return pos.x >= startX && pos.x < endX &&
                pos.y >= startY && pos.y < endY;
    }



    /**
     *
     * @param g2d            - Graphics2D object
     * @param item              - Item to draw
     * @param inventoryX        - starting X of frame
     * @param inventoryY        - starting Y of frame
     * @param scaleToFitSlot    - should scale on draw to fit 1x1 slot (depending on item's width and height)
     */
    public void renderInventoryItem(Graphics2D g2d, Item item, int inventoryX, int inventoryY, boolean scaleToFitSlot)
    {
        if (item == null) return;
        int itemSlotWidth = item.getSlotWidth();    // width of item
        int itemSlotHeight = item.getSlotHeight();  // height of item

        // counting new position and item's sprite size in pixels
        int itemPixelX = inventoryX;
        int itemPixelY = inventoryY;
        int itemPixelWidth = itemSlotWidth * slotSize;
        int itemPixelHeight = itemSlotHeight * slotSize;

        Sprite sprite = item.getSprite();
        int spriteWidth = sprite.image.getWidth();
        int spriteHeight = sprite.image.getHeight();

        if (scaleToFitSlot)    // scaling and centering
        {
            float scaleX = (float) itemPixelWidth / spriteWidth;
            float scaleY = (float) itemPixelHeight / spriteHeight;
            float scale = Math.min(scaleX, scaleY);
            int scaledSpriteWidth = (int) (spriteWidth * scale);
            int scaledSpriteHeight = (int) (spriteHeight * scale);

            int drawX = itemPixelX + (itemPixelWidth - scaledSpriteWidth) / 2;      // centering X
            int drawY = itemPixelY + (itemPixelHeight - scaledSpriteHeight) / 2;    // centering Y
            int drawWidth = scaledSpriteWidth/item.getSlotWidth();
            int drawHeight = scaledSpriteHeight/item.getSlotHeight();
            g2d.drawImage(sprite.image, drawX, drawY, drawWidth, drawHeight, null);
        }
        else
        {
            g2d.drawImage(sprite.image, itemPixelX, itemPixelY, itemPixelWidth, itemPixelHeight, null);
        }
    }

    private void renderInventorybelt(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2;
        int slotCount = Inventory.INVENTORY_BELT_SLOTS;
        int totalWidth = slotCount * slotSize;

        int marginFromBottom = 10;
        int beltX = (hud.gc.getWidth() - totalWidth) / 2;
        int beltY = hud.gc.getHeight() - slotSize - marginFromBottom;

        if (beltPosition == null) beltPosition = new Position(beltX, beltY);
        else
        {
            beltPosition.x = beltX;
            beltPosition.y = beltY;
        }

        for (int i = 0; i < slotCount; i++)
        {
            int frameX = beltX + i * slotSize;
            int frameY = beltY;


            if (i == hud.gc.player.getCurrentBeltSlotIndex())   // highlight currently selected slot
            {
                renderFrame(g2d, frameX, frameY, slotSize, slotSize, 3, 6, 3, 0.5f);
            }
            else renderFrame(g2d, frameX, frameY, slotSize, slotSize, 3, 3, 1, 0.5f);
        }

        for (int i = 0; i < hud.gc.player.getInventory().getBeltItemList().size(); i++)
        {
            Item item = hud.gc.player.getInventory().getBeltItemList().get(i);
            if (item != null)
            {
                int x = beltX + i * slotSize;
                renderInventoryItem(g2d, item, x, beltY, true);
            }
        }
    }

    private void renderMainInventory(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2.create();

        //window size
        int width = hud.gc.getWidth();
        int height = hud.gc.getHeight();

        int widthSlots = Inventory.INVENTORY_WIDTH_SLOTS;
        int heightSlots = Inventory.INVENTORY_HEIGHT_SLOTS;

        int totalWidth = widthSlots * slotSize;
        int totalHeight = heightSlots * slotSize;

        int beltSlotCount = Inventory.INVENTORY_BELT_SLOTS;
        int beltTotalWidth = beltSlotCount * slotSize;
        int beltY = beltPosition.y - height/2;


        // set main inventory position above inventory bar
        int inventoryFrameX = (width - totalWidth) / 2;
        int inventoryFrameY = beltY - totalHeight;

        if (mainInventoryPosition == null) mainInventoryPosition = new Position(inventoryFrameX, inventoryFrameY);
        else
        {
            mainInventoryPosition.x = inventoryFrameX;
            mainInventoryPosition.y = inventoryFrameY;
        }

        // Drawing every inventory slot
        for (int i = 0; i < widthSlots; i++)
        {
            for (int j = 0; j < heightSlots; j++)
            {
                int slotX = inventoryFrameX + i * slotSize;
                int slotY = inventoryFrameY + j * slotSize;

                renderFrame(g2d, slotX, slotY, slotSize, slotSize, 0, 0, 1, 0.7f);
            }
        }

        // Drawing item sprites in main inventory
        for (int i = 0; i < hud.gc.player.getInventory().getInventoryItemList().size(); i++)
        {
            Item item = hud.gc.player.getInventory().getInventoryItemList().get(i);
            if (item != null)
            {
                Position pos = item.getInventoryPosition();
                int itemX = inventoryFrameX + pos.x * slotSize;
                int itemY = inventoryFrameY + pos.y * slotSize;

                renderInventoryItem(g2d, item, itemX, itemY, false);;
            }
        }
    }

    private boolean test = true;
    public void renderStatisticsFrame(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2.create();

        int width = hud.gc.getWidth();
        int height = hud.gc.getHeight();

        int beltSlotCount = Inventory.INVENTORY_BELT_SLOTS;
        int beltY = beltPosition.y - height / 2;

        int totalWidth = Inventory.INVENTORY_WIDTH_SLOTS * slotSize;    // width of main inventory
        int totalHeight =  Inventory.INVENTORY_HEIGHT_SLOTS * slotSize;  // height of main inventory

        float statsToInvRatio = 0.62f;
        int statsFrameWidth = (int) (statsToInvRatio * totalWidth);
        int statsFrameHeight = totalHeight + slotSize;

        int statsFrameX = mainInventoryPosition.x - statsFrameWidth;
        int statsFrameY = mainInventoryPosition.y;

        if (statsFrameX < 0)
        {

            statsFrameWidth = mainInventoryPosition.x;
            statsFrameX = 0;
            if (statsFrameWidth < 0)
            {
                statsFrameWidth = 0;
            }
        }

        renderFrame(g2d, statsFrameX, statsFrameY, statsFrameWidth, statsFrameHeight, 3, 3, 1, 0.7f);



        EntityStatistics stats = hud.gc.player.statistics;

        int hp = stats.getHitPoints();
        int maxHp = stats.getMaxHitPoints();
        int mana = stats.getMana();
        int maxMana = stats.getMaxMana();
        int regen = stats.getRegeneration();
        int movementSpeed = stats.getCurrentMovementSpeed();
        int maxMovementSpeed = stats.getMaxMovementSpeed();
        int armour = stats.getArmour();
        int magicArmour = stats.getMagicArmour();
        int strength = stats.getStrength();
        int dexterity = stats.getDexterity();
        int intellect = stats.getIntellect();
        int stamina = stats.getStamina();
        int exp = stats.getExp();
        int nextLevelExp = stats.getNextLevelExp();

        String[] statTexts = {
                "HP: " + hp + " / " + maxHp,
                "Mana: " + mana + " / " + maxMana,
                "Regeneration: " + regen,
                "Movement Speed: " + movementSpeed + " / " + maxMovementSpeed,
                "Armour: " + armour,
                "Magic Armour: " + magicArmour,
                "Strength: " + strength,
                "Dexterity: " + dexterity,
                "Intellect: " + intellect,
                "Stamina: " + stamina,
                "EXP: " + exp + " / " + nextLevelExp
        };

        g2d.setColor(Color.WHITE);
        g2d.setFont(HUDFont);

        FontMetrics fm = g2d.getFontMetrics(HUDFont);

        int leftMargin = 10;
        int iconTextSpacing = 5;
        int iconSize = scaledFontSize;

        int textX = statsFrameX + leftMargin + iconSize + iconTextSpacing;
        int textY = statsFrameY + 30;

        if (test)
        {
            levelUpIconPositions = new Position[statTexts.length];
        }
        else
        {
            levelUpIconPositions = null;
        }

        for (int i = 0; i < statTexts.length; i++)
        {
            int currentY = textY + i * (scaledFontSize + 5);
            if (test)
            {
                int iconX = statsFrameX + leftMargin;
                int iconY = currentY - fm.getAscent() + (fm.getHeight() - iconSize) / 2;
                g2d.drawImage(hud.levelUp.image, iconX, iconY, iconSize, iconSize, null);
                levelUpIconPositions[i] = new Position(iconX, iconY);
            }
            g2d.drawString(statTexts[i], textX, currentY);
        }

        g2d.dispose();
    }

    public void renderEquippedFrame(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2.create();
        int equippedFrameWidth = slotSize * 5;
        int equippedFrameHeight = slotSize * 10 + slotSize / 4;
        Inventory playerInventory = hud.gc.player.getInventory();

        // positions of equipped frame
        int width = hud.gc.getWidth();
        int height = hud.gc.getHeight();
        int widthSlots = Inventory.INVENTORY_WIDTH_SLOTS;
        int heightSlots = Inventory.INVENTORY_HEIGHT_SLOTS;
        int totalWidth = widthSlots * slotSize;    // width of main inventory
        int totalHeight = heightSlots * slotSize;  // height of main inventory
        int beltY = beltPosition.y - height/2;

        // position of equipped frame
        int equippedFrameX = mainInventoryPosition.x + totalWidth;
        int equippedFrameY = mainInventoryPosition.y;

        if (equippedPosition == null) equippedPosition = new Position(equippedFrameX, equippedFrameY);
        else
        {
            equippedPosition.x = equippedFrameX;
            equippedPosition.y = equippedFrameY;
        }

        renderFrame(g2d, equippedFrameX, equippedFrameY, equippedFrameWidth, equippedFrameHeight, 3, 3, 1, 0.7f);

        // 1. Helmet (2x2)
        int helmetX = equippedFrameX + (slotSize / 4);
        int helmetY = equippedFrameY + (slotSize / 4);
        if (helmetSlotPosition == null) helmetSlotPosition = new Position(helmetX, helmetY);
        else
        {
            helmetSlotPosition.x = helmetX;
            helmetSlotPosition.y = helmetY;
        }
        renderFrame(g2d, helmetX, helmetY, slotSize * 2, slotSize * 2, 0, 0, 1, 0.7f);
        renderInventoryItem(g2d, playerInventory.getHelmetSlot().getStoredItem(), helmetX, helmetY, false);


        // 2. Chestplate (2x3)
        int chestX = helmetX;
        int chestY = helmetY + slotSize * 2 + (slotSize / 4);
        renderFrame(g2d, chestX, chestY, slotSize * 2, slotSize * 3, 0, 0, 1, 0.7f);
        renderInventoryItem(g2d, playerInventory.getChestplateSlot().getStoredItem(), chestX, chestY, false);
        if (chestplateSlotPosition == null) chestplateSlotPosition = new Position(chestX, chestY);
        else
        {
            chestplateSlotPosition.x = chestX;
            chestplateSlotPosition.y = chestY;
        }

        // 3. Pants (2x3)
        int pantsX = chestX;
        int pantsY = chestY + slotSize * 3 + (slotSize / 4);
        renderFrame(g2d, pantsX, pantsY, slotSize * 2, slotSize * 3, 0, 0, 1, 0.7f);
        renderInventoryItem(g2d, playerInventory.getPantsSlot().getStoredItem(), pantsX, pantsY, false);
        if (pantsSlotPosition == null) pantsSlotPosition = new Position(pantsX, pantsY);
        else
        {
            pantsSlotPosition.x = pantsX;
            pantsSlotPosition.y = pantsY;
        }

        // 4. Boots (2x1)
        int bootsX = pantsX;
        int bootsY = pantsY + slotSize * 3 + (slotSize / 4);
        renderFrame(g2d, bootsX, bootsY, slotSize * 2, slotSize, 0, 0, 1, 0.7f);
        renderInventoryItem(g2d, playerInventory.getBootsSlot().getStoredItem(), bootsX, bootsY, false);
        if (bootsSlotPosition == null) bootsSlotPosition = new Position(bootsX, bootsY);
        else
        {
            bootsSlotPosition.x = bootsX;
            bootsSlotPosition.y = bootsY;
        }

        // 5. Ring1 (1x1)
        int ring1X = equippedFrameX + slotSize * 2 + (slotSize / 2);
        int ring1Y = equippedFrameY + (equippedFrameHeight / 2) - (slotSize * 2);
        renderFrame(g2d, ring1X, ring1Y, slotSize, slotSize, 0, 0, 1, 0.7f);
        renderInventoryItem(g2d, playerInventory.getRing1Slot().getStoredItem(), ring1X, ring1Y, false);
        if (ring1SlotPosition == null) ring1SlotPosition = new Position(ring1X, ring1Y);
        else
        {
            ring1SlotPosition.x = ring1X;
            ring1SlotPosition.y = ring1Y;
        }

        // 6. Ring2 (1x1)
        int ring2X = ring1X + slotSize + (slotSize / 4);
        int ring2Y = ring1Y;
        renderFrame(g2d, ring2X, ring2Y, slotSize, slotSize, 0, 0, 1, 0.7f);
        renderInventoryItem(g2d, playerInventory.getRing2Slot().getStoredItem(), ring2X, ring2Y, false);
        if (ring2SlotPosition == null) ring2SlotPosition = new Position(ring2X, ring2Y);
        else
        {
            ring2SlotPosition.x = ring2X;
            ring2SlotPosition.y = ring2Y;
        }

        // 7. Amulet (1x1)
        int amuletX = ring1X + (slotSize / 2) + ((slotSize / 4) / 2);
        int amuletY = ring1Y - slotSize - (slotSize / 4);
        renderFrame(g2d, amuletX, amuletY, slotSize, slotSize, 0, 0, 1, 0.7f);
        renderInventoryItem(g2d, playerInventory.getAmuletSlot().getStoredItem(), amuletX, amuletY, false);
        if (amuletSlotPosition == null) amuletSlotPosition = new Position(amuletX, amuletY);
        else
        {
            amuletSlotPosition.x = amuletX;
            amuletSlotPosition.y = amuletY;
        }

        // 8. Shield (2x2)
        int shieldX = ring1X + ((slotSize / 4) / 2);
        int shieldY = ring2Y + slotSize + (slotSize / 4);
        renderFrame(g2d, shieldX, shieldY, slotSize * 2, slotSize * 2, 0, 0, 1, 0.7f);
        renderInventoryItem(g2d, playerInventory.getShieldSlot().getStoredItem(), shieldX, shieldY, false);
        if (shieldSlotPosition == null) shieldSlotPosition = new Position(shieldX, shieldY);
        else
        {
            shieldSlotPosition.x = shieldX;
            shieldSlotPosition.y = shieldY;
        }

        g2d.dispose();
    }

    public void renderHeldItem(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2.create();
        int mouseX = hud.gc.mouseHandler.getMouseX();
        int mouseY = hud.gc.mouseHandler.getMouseY();

        Item heldItem = hud.gc.player.getInventory().getHeldItem();

        if (heldItem == null) return;

        int itemSlotWidth = heldItem.getSlotWidth();    // width of item
        int itemSlotHeight = heldItem.getSlotHeight();  // height of item
        int itemPixelWidth = itemSlotWidth * slotSize;
        int itemPixelHeight = itemSlotHeight * slotSize;
        Sprite sprite = heldItem.getSprite();

        // scaling and centering
        int spriteWidth = sprite.image.getWidth();
        int spriteHeight = sprite.image.getHeight();
        float scaleX = (float) itemPixelWidth / spriteWidth;
        float scaleY = (float) itemPixelHeight / spriteHeight;
        float scale = Math.min(scaleX, scaleY);

        int drawWidth = (int) (spriteWidth * scale);
        int drawHeight = (int) (spriteHeight * scale);

        int drawX = mouseX + (itemPixelWidth - drawWidth) / 2; // Centered X
        int drawY = mouseY + (itemPixelHeight - drawHeight) / 2; // Centered Y

        g2d.drawImage(sprite.image, drawX, drawY, drawWidth, drawHeight, null);
    }

    public void updateSizes()
    {
        scaledFontSize = (int) (baseFontSize * hud.scale / 64);
        HUDFont = new Font("Monospaced", Font.BOLD, scaledFontSize);

        slotSize = (baseSlotSize * hud.scale) / 64;
    }
}