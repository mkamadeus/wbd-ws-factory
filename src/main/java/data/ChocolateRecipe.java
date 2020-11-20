package data;

public class ChocolateRecipe {
    private final int id;
    private int chocolateId;
    private int ingredientId;
    private int ingredientAmount;

    public ChocolateRecipe(int id, int chocolateId, int ingredientId, int ingredientAmount) {
        this.id = id;
        this.chocolateId = chocolateId;
        this.ingredientId = ingredientId;
        this.ingredientAmount = ingredientAmount;
    }

    public int getId() {
        return id;
    }

    public int getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(int ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public int getChocolateId() {
        return chocolateId;
    }

    public void setChocolateId(int chocolateId) {
        this.chocolateId = chocolateId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }
}
