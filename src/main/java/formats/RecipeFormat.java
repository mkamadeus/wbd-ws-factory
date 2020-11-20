package formats;

import java.io.Serializable;

public class RecipeFormat implements Serializable {
    private static final long serialVersionUID = 1L;
    private ChocolateFormat chocolate;
    private IngredientAmountPair[] ingredients;

    public RecipeFormat(ChocolateFormat chocolate, IngredientAmountPair[] ingredients) {
        this.chocolate = chocolate;
        this.ingredients = ingredients;
    }

    public ChocolateFormat getChocolate() {
        return chocolate;
    }

    public void setChocolate(ChocolateFormat chocolate) {
        this.chocolate = chocolate;
    }

    public IngredientAmountPair[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(IngredientAmountPair[] ingredients) {
        this.ingredients = ingredients;
    }
}
