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
    }
}
