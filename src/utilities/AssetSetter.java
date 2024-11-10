package utilities;

import main.GameController;
import main.entity.Entity;
import main.entity.npc.BitingSlime;
import main.item.Item;

public class AssetSetter    // FULL STATIC
{
    public static GameController gc;

    public AssetSetter(GameController gc)
    {
        this.gc = gc;
        initAssets();
    }

    public static void initAssets()
    {
        BitingSlime bitingSlime = new BitingSlime(gc);
        bitingSlime.setWorldPosition(new Position(435, 450));
        BitingSlime bitingSlime2 = new BitingSlime(gc);
        bitingSlime2.setWorldPosition(new Position(435, 450));
        BitingSlime bitingSlime3 = new BitingSlime(gc);
        bitingSlime3.setWorldPosition(new Position(435, 450));
        BitingSlime bitingSlime4 = new BitingSlime(gc);
        bitingSlime4.setWorldPosition(new Position(435, 450));
        BitingSlime bitingSlime5 = new BitingSlime(gc);
        bitingSlime5.setWorldPosition(new Position(435, 450));
        BitingSlime bitingSlime6 = new BitingSlime(gc);
        bitingSlime6.setWorldPosition(new Position(435, 450));
    }
}
