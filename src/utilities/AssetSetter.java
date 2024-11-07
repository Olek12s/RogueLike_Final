package utilities;

import main.GameController;
import main.entity.Entity;
import main.item.Item;

public class AssetSetter
{
    public GameController gc;

    public AssetSetter(GameController gc)
    {
        this.gc = gc;
    }

    public void initAssets()
    {
        Item item = new Item(gc);
        item.setWorldPosition(new Position(350, 350));

        Entity entity = new Entity(gc);
        entity.setWorldPosition(new Position(380, 380));


    }
}
