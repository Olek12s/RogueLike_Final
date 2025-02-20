package main.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crafting
{
    public static final Map<Item, Crafting> craftings = new HashMap<>();
    private final List<RecipeIngredient> ingredients;
    private final Item result;

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public Item getResult() {
        return result;
    }

    private Crafting(List<RecipeIngredient> ingredients, Item result)
    {
        this.ingredients = new ArrayList<>(ingredients);
        this.result = result;
    }

    public static Crafting getOrCreate(List<RecipeIngredient> ingredients, Item result)
    {
        return craftings.computeIfAbsent(result, key -> new Crafting(ingredients, result));
    }
}
