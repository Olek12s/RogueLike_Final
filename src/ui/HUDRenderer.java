package ui;

import main.controller.DrawPriorities;
import main.controller.Drawable;
import main.controller.GameState;
import main.entity.EntityStatistics;
import main.inventory.Inventory;
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
            if (hud.gc.gameStateController.getCurrentGameState() == GameState.INVENTORY)
            {
                drawInventoryBar(g2);
                drawMainInventory(g2);
                drawStatisticsFrame(g2);
            }
            if (hud.gc.isDebugMode())
            {
                renderDebugInfoLeft(g2);
                renderDebugInfoLeftTop(g2);
            }
            hud.gc.incrementRenderCount();
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
        int baseFontSize = 14;
        int scaledFontSize = (int) (baseFontSize * hud.scale / 64);
        g2d.setFont(new Font("Arial", Font.BOLD, scaledFontSize));

        int baseX = 10;
        int baseY = 130;
        int scaledX = (int) (baseX * (hud.scale / 64.0));
        int scaledY = (int) (baseY * (hud.scale / 64.0));

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
        int baseFontSize = 14;
        int scaledFontSize = (int) (baseFontSize * hud.scale / 64.0);
        g2d.setFont(new Font("Arial", Font.BOLD, scaledFontSize));

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
        int slotSize = (baseSlotSize * hud.scale) / 64; // 64 - contractual value for HUD


        int totalWidth = slotCount * slotSize;


        int marginFromBottom = 10;
        int beltX = (hud.gc.getWidth() - totalWidth) / 2;
        int beltY = hud.gc.getHeight() - slotSize - marginFromBottom;

        for (int i = 0; i < slotCount; i++)
        {
            int frameX = beltX + i * slotSize;
            int frameY = beltY;


            renderFrame(g2d, frameX, frameY, slotSize, slotSize, 3, 3, 1, 0.5f);
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

        int slotSize = (baseSlotSize * hud.scale) / 64; // 64 - contractual value for HUD

        int totalWidth = widthSlots * slotSize;
        int totalHeight = heightSlots * slotSize;

        int beltSlotCount = Inventory.beltWidthSlots;
        int beltTotalWidth = beltSlotCount * slotSize;
        int marginFromInventoryBar = height/6;

        int beltY = height - slotSize - marginFromInventoryBar;


        // set main inventory position above inventory bar

        int inventoryFrameX = (width - totalWidth) / 2;
        int inventoryFrameY = beltY - totalHeight;

        // Drawing every inventory slot
        for (int i = 0; i < widthSlots; i++)
        {
            for (int j = 0; j < heightSlots; j++)
            {
                int slotX = inventoryFrameX + i * slotSize;
                int slotY = inventoryFrameY + j * slotSize;

                    renderFrame(g2d, slotX, slotY, slotSize, slotSize, 3, 3, 1, 0.7f);
            }
        }
      //  g2.dispose();
    }

    public void drawStatisticsFrame(Graphics g2)
    {
        Graphics2D g2d = (Graphics2D) g2.create();

        int width = hud.gc.getWidth();
        int height = hud.gc.getHeight();

        int slotSize = (baseSlotSize * hud.scale) / 64;

        int beltSlotCount = Inventory.beltWidthSlots;
        int marginFromInventoryBar = height / 6;
        int beltY = height - slotSize - marginFromInventoryBar;

        // size and position of main inventory
        int widthSlots = Inventory.INVENTORY_WIDTH_SLOTS;
        int heightSlots = Inventory.INVENTORY_HEIGHT_SLOTS;
        int totalWidth = widthSlots * slotSize;    // width of main inventory
        int totalHeight = heightSlots * slotSize;  // height of main inventory

        int inventoryFrameX = (width - totalWidth) / 2; // center of inventory frame
        int inventoryFrameY = beltY - totalHeight;


        float statsToInvRatio = 0.45f;
        int statsFrameWidth = (int) (statsToInvRatio * totalWidth);
        int statsFrameHeight = totalHeight+50;


        int statsFrameX = inventoryFrameX - statsFrameWidth;
        int statsFrameY = inventoryFrameY;


        if (statsFrameX < 0)
        {

            statsFrameWidth = inventoryFrameX;
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
        int baseFontSize = 14;
        int scaledFontSize = (int) (baseFontSize * hud.scale / 64.0);
        g2d.setFont(new Font("Arial", Font.BOLD, scaledFontSize));

        int textX = statsFrameX + 10;
        int textY = statsFrameY + 30;

        for (String stat : statTexts)
        {
            g2d.drawString(stat, textX, textY);
            textY += scaledFontSize + 5;
        }

        g2d.dispose();
    }
}