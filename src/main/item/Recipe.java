package main.item;

import java.util.List;

public class Recipe
{
    private final List<RecipeIngredient> ingredients;
    private final Item result;

    public List<RecipeIngredient> getIngredients() {return ingredients;}
    public Item getResult() {return result;}

    public Recipe(List<RecipeIngredient> ingredients, Item result)
    {
        this.ingredients = ingredients;
        this.result = result;
    }
}
