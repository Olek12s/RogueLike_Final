package ui;

import main.GameController;
import utilities.FileManipulation;
import utilities.Sprite;
import utilities.SpriteSheet;

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
}
