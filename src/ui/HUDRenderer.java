package ui;

import main.controller.DrawPriorities;
import main.controller.Drawable;
import main.controller.GameState;
import main.entity.EntityStatistics;
import main.inventory.Inventory;
import main.item.Item;
import utilities.Position;
import utilities.sprite.Sprite;
import world.map.tiles.Tile;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
    Position mainInventoryPosition;
    Position equippedPosition;
    Position barPosition;
    // FRAME POSITIONS //

    // SLOTS POSITIONS //
    ScreenSlot[][] mainInventorySlots;
    ScreenSlot[] beltSlots;
    ScreenSlot helmetSlot;
    ScreenSlot chestplateSlot;
    ScreenSlot pantsSlot;
    ScreenSlot bootsSlot;
    ScreenSlot shieldSlot;
    ScreenSlot ring1Slot;
    ScreenSlot ring2Slot;
    ScreenSlot amuletSLot;
    // SLOTS POSITIONS //

    ArrayList<ScreenSlot> screenSlots;
    public ArrayList<ScreenSlot> getScreenSlots() {return screenSlots;}

    public HUDRenderer(HUD hud)
    {
        this.hud = hud;
        hud.gc.drawables.add(this);
        initScreenSlotsArrayList();
    }

    private void initScreenSlotsArrayList()
    {
        mainInventorySlots = new ScreenSlot[Inventory.INVENTORY_WIDTH_SLOTS][Inventory.INVENTORY_HEIGHT_SLOTS];
        beltSlots = new ScreenSlot[Inventory.beltWidthSlots];
        screenSlots = new ArrayList<>();

        for (int i = 0; i < mainInventorySlots.length; i++) {
            for (int j = 0; j < mainInventorySlots[0].length; j++) {
                mainInventorySlots[i][j] = new ScreenSlot(slotSize, SlotType.mainInvSlot);
                screenSlots.add(mainInventorySlots[i][j]);
            }
        }

        for (int i = 0; i < beltSlots.length; i++) {
            beltSlots[i] = new ScreenSlot(slotSize, SlotType.beltSlot);
            screenSlots.add(beltSlots[i]);
        }

        helmetSlot = new ScreenSlot(slotSize, SlotType.helmetSlot);
        chestplateSlot = new ScreenSlot(slotSize, SlotType.chestplateSlot);
        pantsSlot = new ScreenSlot(slotSize, SlotType.pantsSlot);
        bootsSlot = new ScreenSlot(slotSize, SlotType.bootsSlot);
        shieldSlot = new ScreenSlot(slotSize, SlotType.shieldSlot);
        ring1Slot = new ScreenSlot(slotSize, SlotType.ring1Slot);
        ring2Slot = new ScreenSlot(slotSize, SlotType.ring2Slot);
        amuletSLot = new ScreenSlot(slotSize, SlotType.amuletSLot);

        screenSlots.add(helmetSlot);
        screenSlots.add(chestplateSlot);
        screenSlots.add(pantsSlot);
        screenSlots.add(bootsSlot);
        screenSlots.add(shieldSlot);
        screenSlots.add(ring1Slot);
        screenSlots.add(ring2Slot);
        screenSlots.add(amuletSLot);
    }

    @Override
    public int getDrawPriority()
    {
        return DrawPriorities.HUD.value;
    }

    @Override
    public void draw(Graphics g2)
    {
        updateSizes();
        renderHealthBar(g2);
        drawInventoryBar(g2);
        if (hud.gc.gameStateController.getCurrentGameState() == GameState.INVENTORY)
        {
            drawMainInventory(g2);
            drawStatisticsFrame(g2);
            drawEquippedFrame(g2);
        }
        if (hud.gc.isDebugMode())
        {
            renderDebugInfoLeft(g2);
            renderDebugInfoLeftTop(g2);
        }
        hud.gc.incrementRenderCount();
    }

    private void updateSizes()
    {
        scaledFontSize = (int) (baseFontSize * hud.scale / 64);
        HUDFont = new Font("Monospaced", Font.BOLD, scaledFontSize);

        slotSize = (baseSlotSize * hud.scale) / 64;
    }

    private void renderHealthBar(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2;
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(3));

        if (hud.gc.player.statistics.hitPoints < 0) hud.gc.player.statistics.hitPoints = 0;
        int healthPercent = (int) ((hud.gc.player.statistics.hitPoints / (double) hud.gc.player.getMaxHitPoints()) * 100);

        g2d.drawRect(45, 24, 300, 26);
        g2d.setColor(new Color(75, 61, 61, 210));
        g2d.fillRect(46, 25, 299, 25);
        g2d.setColor(Color.RED);
        g2d.fillRect(46, 25, (healthPercent * 299) / 100, 25);
        g2d.drawImage(hud.heart.image, 0, 0, hud.scale, hud.scale, null);
    }

    private void renderDebugInfoLeft(Graphics g2)
    {
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

    private void renderDebugInfoLeftTop(Graphics g2)
    {
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

    private void updateTimers()
    {
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

    private void updateDrawCount()
    {
        long count = hud.gc.getRenderCount();
        drawCount = "Draw count: " + count;
    }

    public void renderFrame(Graphics g, int x, int y, int width, int height, int innerPadding, int outerWidth, int innerWidth, float opacity)
    {
        Graphics2D g2d = (Graphics2D)g.create();

        // rounding parameters
        int arcWidth = 10;
        int arcHeight = 10;

        //int innerOpacity = 2;
        //int outerWidth = 3;
        //int innerWidth = 1;


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

    public void drawInventoryBar(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2;
        int slotCount = Inventory.beltWidthSlots;
        int totalWidth = slotCount * slotSize;


        int marginFromBottom = 10;
        int beltX = (hud.gc.getWidth() - totalWidth) / 2;
        int beltY = hud.gc.getHeight() - slotSize - marginFromBottom;

        if (barPosition == null) barPosition = new Position(beltX, beltY);
        else
        {
            barPosition.x = beltX;
            barPosition.y = beltY;
        }

        for (int i = 0; i < slotCount; i++)
        {
            int frameX = beltX + i * slotSize;
            int frameY = beltY;


            renderFrame(g2d, frameX, frameY, slotSize, slotSize, 3, 3, 1, 0.5f);
            beltSlots[i].updateSlot(slotSize, frameX, frameY);
        }
        //   g2.dispose();
    }

    public void drawMainInventory(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2.create();

        //window size
        int width = hud.gc.getWidth();
        int height = hud.gc.getHeight();

        int widthSlots = Inventory.INVENTORY_WIDTH_SLOTS;
        int heightSlots = Inventory.INVENTORY_HEIGHT_SLOTS;

        int totalWidth = widthSlots * slotSize;
        int totalHeight = heightSlots * slotSize;

        int beltSlotCount = Inventory.beltWidthSlots;
        int beltTotalWidth = beltSlotCount * slotSize;
        int beltY = barPosition.y - height/2;


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
                mainInventorySlots[i][j].updateSlot(slotSize, slotX, slotY);
            }
        }

        for (Item item : hud.gc.player.getInventory().getItems())
        {
            if (item != null)
            {
                drawItem(g2d, item, inventoryFrameX, inventoryFrameY);
            }
        }
        //  g2.dispose();
    }

    /**
     *
     * @param g2d           - Graphics2D object
     * @param item          - Item to draw
     * @param inventoryX    - starting X of frame
     * @param inventoryY    - starting Y of frame
     */
    private void drawItem(Graphics2D g2d, Item item, int inventoryX, int inventoryY)
    {
        Position position = item.getInventoryPosition(); // position of item in inventory
        if (position == null)
        {
            return;
        }

        int itemSlotWidth = item.getSlotWidth();    // width of item
        int itemSlotHeight = item.getSlotHeight();  // height of item

        // counting new position and item's sprite size in pixels
        int itemPixelX = inventoryX + position.x * slotSize;
        int itemPixelY = inventoryY + position.y * slotSize;
        int itemPixelWidth = itemSlotWidth * slotSize;
        int itemPixelHeight = itemSlotHeight * slotSize;

        Sprite sprite = item.getSprite();

        // scaling and centering
        int spriteWidth = sprite.image.getWidth();
        int spriteHeight = sprite.image.getHeight();
        float scaleX = (float) itemPixelWidth / spriteWidth;
        float scaleY = (float) itemPixelHeight / spriteHeight;
        float scale = Math.min(scaleX, scaleY);

        int drawWidth = (int) (spriteWidth * scale);
        int drawHeight = (int) (spriteHeight * scale);

        int drawX = itemPixelX + (itemPixelWidth - drawWidth) / 2; // Centered X
        int drawY = itemPixelY + (itemPixelHeight - drawHeight) / 2; // Centered Y

        g2d.drawImage(sprite.image, drawX, drawY, drawWidth, drawHeight, null);
    }

    public void drawStatisticsFrame(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2.create();

        int width = hud.gc.getWidth();
        int height = hud.gc.getHeight();

        int beltSlotCount = Inventory.beltWidthSlots;
        int beltY = barPosition.y - height / 2;

        int totalWidth = Inventory.INVENTORY_WIDTH_SLOTS * slotSize;    // width of main inventory
        int totalHeight =  Inventory.INVENTORY_HEIGHT_SLOTS * slotSize;  // height of main inventory

        float statsToInvRatio = 0.52f;
        int statsFrameWidth = (int) (statsToInvRatio * totalWidth);
        int statsFrameHeight = totalHeight+50;


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

        int hp = stats.hitPoints;
        int maxHp = stats.getMaxHitPoints();
        int mana = stats.mana;
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

        int textX = statsFrameX + 10;
        int textY = statsFrameY + 30;

        for (String stat : statTexts)
        {
            g2d.drawString(stat, textX, textY);
            textY += scaledFontSize + 5;
        }

        g2d.dispose();
    }

    public void drawEquippedFrame(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2.create();



        int equippedFrameWidth = slotSize * 5;
        int equippedFrameHeight = slotSize * 10 + slotSize / 4;

        // positions of equipped frame
        int width = hud.gc.getWidth();
        int height = hud.gc.getHeight();
        int widthSlots = Inventory.INVENTORY_WIDTH_SLOTS;
        int heightSlots = Inventory.INVENTORY_HEIGHT_SLOTS;
        int totalWidth = widthSlots * slotSize;    // width of main inventory
        int totalHeight = heightSlots * slotSize;  // height of main inventory
        int beltY = barPosition.y - height/2;

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
        renderFrame(g2d, helmetX, helmetY, slotSize * 2, slotSize * 2, 0, 0, 1, 0.7f);
        helmetSlot.updateSlot(slotSize, helmetX, helmetY);


        // 2. Chestplate (2x3)
        int chestX = helmetX;
        int chestY = helmetY + slotSize * 2 + (slotSize / 4);
        renderFrame(g2d, chestX, chestY, slotSize * 2, slotSize * 3, 0, 0, 1, 0.7f);
        chestplateSlot.updateSlot(slotSize, chestX, chestY);

        // 3. Pants (2x3)
        int pantsX = chestX;
        int pantsY = chestY + slotSize * 3 + (slotSize / 4);
        renderFrame(g2d, pantsX, pantsY, slotSize * 2, slotSize * 3, 0, 0, 1, 0.7f);
        pantsSlot.updateSlot(slotSize, pantsX, pantsY);

        // 4. Boots (2x1)
        int bootsX = pantsX;
        int bootsY = pantsY + slotSize * 3 + (slotSize / 4);
        renderFrame(g2d, bootsX, bootsY, slotSize * 2, slotSize, 0, 0, 1, 0.7f);
        bootsSlot.updateSlot(slotSize, bootsX, bootsY);

        // 5. Ring1 (1x1)
        int ring1X = equippedFrameX + slotSize * 2 + (slotSize / 2);
        int ring1Y = equippedFrameY + (equippedFrameHeight / 2) - (slotSize * 2);
        renderFrame(g2d, ring1X, ring1Y, slotSize, slotSize, 0, 0, 1, 0.7f);
        ring1Slot.updateSlot(slotSize, ring1X, ring1Y);

        // 6. Ring2 (1x1)
        int ring2X = ring1X + slotSize + (slotSize / 4);
        int ring2Y = ring1Y;
        renderFrame(g2d, ring2X, ring2Y, slotSize, slotSize, 0, 0, 1, 0.7f);
        ring2Slot.updateSlot(slotSize, ring2X, ring2Y);

        // 7. Amulet (1x1)
        int amuletX = ring1X + (slotSize / 2) + ((slotSize / 4) / 2);
        int amuletY = ring1Y - slotSize - (slotSize / 4);
        renderFrame(g2d, amuletX, amuletY, slotSize, slotSize, 0, 0, 1, 0.7f);
        amuletSLot.updateSlot(slotSize, amuletX, amuletY);

        // 8. Shield (2x2)
        int shieldX = ring1X + ((slotSize / 4) / 2);
        int shieldY = ring2Y + slotSize + (slotSize / 4);
        renderFrame(g2d, shieldX, shieldY, slotSize * 2, slotSize * 2, 0, 0, 1, 0.7f);
        shieldSlot.updateSlot(slotSize, shieldX, shieldY);

        g2d.dispose();
    }
}