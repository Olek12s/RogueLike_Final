package main.item;

import main.entity.Entity;
import main.entity.player.Player;
import main.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crafting
{
    public static final Map<ItemID, Crafting> craftings = new HashMap<>();
    private final List<RecipeIngredient> ingredients;
    private final Item result;

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }
    public Item getResultItem() {
        return result;
    }

    private Crafting(List<RecipeIngredient> ingredients, Item result)
    {
        this.ingredients = new ArrayList<>(ingredients);
        this.result = result;
    }

    public static Crafting getOrCreate(List<RecipeIngredient> ingredients, Item result)
    {
        return craftings.computeIfAbsent(result.getItemID(), key -> new Crafting(ingredients, result));
    }

    public boolean canCraft(Inventory inventory)
    {
        for (RecipeIngredient ingredient : ingredients)
        {
            int requiredQuantity = ingredient.getQuantity();
            int availableQuantity = 0;

            for (Item item : inventory.getInventoryItemList())
            {
                if (item.getItemID().equals(ingredient.getItem().getItemID()))
                {
                    availableQuantity++;
                    if (availableQuantity >= requiredQuantity)
                    {
                        break;
                    }
                }
            }

            if (availableQuantity < requiredQuantity)
            {
                return false;
            }
        }
        return true;
    }

    public void craft(Inventory inventory, Entity entity)
    {
        if (!canCraft(inventory))
        {
            return;
        }

        for (RecipeIngredient ingredient : ingredients)
        {
            int quantityToRemove = ingredient.getQuantity();

            for (int i = 0; i < inventory.getInventoryItemList().size() && quantityToRemove > 0; i++)
            {
                Item item = inventory.getInventoryItemList().get(i);
                if (item.getItemID().equals(ingredient.getItem().getItemID()))
                {
                    inventory.removeItemFromMainInv(item);
                    quantityToRemove--;
                    i--;
                }
            }
        }

        boolean addedToInventory = inventory.addItem(result);
        if (!addedToInventory)
        {
            entity.getInventory().dropItemOnGround(result);
        }
    }
}
