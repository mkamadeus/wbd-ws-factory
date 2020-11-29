package formats;

import java.io.Serializable;

public class IngredientAmountPair implements Serializable {
    private AvailableIngredientFormat ingredient;
    private int amountNeeded;

    public IngredientAmountPair(AvailableIngredientFormat ingredient, int amountNeeded) {
        this.ingredient = ingredient;
        this.amountNeeded = amountNeeded;
    }

    public int getAmountNeeded() {
        return amountNeeded;
    }

    public void setAmountNeeded(int amountNeeded) {
        this.amountNeeded = amountNeeded;
    }

    public AvailableIngredientFormat getIngredient() {
        return ingredient;
    }

    public void setIngredient(AvailableIngredientFormat ingredient) {
        this.ingredient = ingredient;
    }
}
