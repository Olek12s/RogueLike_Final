package main.item;

public class RecipeIngredient
{
    private final Item item;
    private final int quantity;

    public Item getItem() {return item;}
    public int getQuantity() {return quantity;}

    public RecipeIngredient(Item item, int quantity)
    {
        this.item = item;
        this.quantity = quantity;
    }
}
