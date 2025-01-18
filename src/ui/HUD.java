package ui;

import main.controller.GameController;
import utilities.FileManipulation;
import utilities.Position;
import utilities.sprite.Sprite;
import utilities.sprite.SpriteSheet;

import java.util.ArrayList;

public class HUD
{
    public GameController gc;
    protected HUDRenderer hudRenderer;
    protected HUDUpdater hudUpdater;
    public SpriteSheet heartSpriteSheet;
    public Sprite heart;

    int scaleX;
    int scaleY;
    int scale;

    public HUD(GameController gc)
    {
        this.gc = gc;
        hudRenderer = new HUDRenderer(this);
        hudUpdater = new HUDUpdater(this);
        this.heartSpriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/hud/heartsSpriteSheet"), 27);
        this.heart = heartSpriteSheet.extractFirst();
    }

    public ScreenSlot getScreenSlotAt(Position position)
    {
        ArrayList<ScreenSlot> screenSlots = hudRenderer.getScreenSlots();

        for (ScreenSlot slot : screenSlots)
        {
            if (slot != null && slot.isWithinSlot(position))
            {
                return slot;
            }
        }
        return null;
    }

    public ScreenSlot getScreenSlotAt(int x, int y)
    {
        return getScreenSlotAt(new Position(x, y));
    }
}
