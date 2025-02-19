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
    public Sprite levelUp;
    public Sprite arrowRight;
    public Sprite arrowLeft;
    public Sprite heart;

    int scaleX;
    int scaleY;
    int scale;

    public HUDRenderer getHudRenderer() {return hudRenderer;}
    public HUDUpdater getHudUpdater() {return hudUpdater;}

    public HUD(GameController gc)
    {
        this.gc = gc;
        hudRenderer = new HUDRenderer(this);
        hudUpdater = new HUDUpdater(this);
        this.heartSpriteSheet = new SpriteSheet(FileManipulation.loadImage("resources/hud/heartsSpriteSheet"), 27);
        this.levelUp = new Sprite(FileManipulation.loadImage("resources/hud/LevelUp"), 9);
        this.arrowRight = new Sprite(FileManipulation.loadImage("resources/hud/ArrowRight"), 9);
        this.arrowLeft = new Sprite(FileManipulation.loadImage("resources/hud/ArrowLeft"), 9);
        this.heart = heartSpriteSheet.extractFirst();
    }
}
