package formats;

import java.io.Serializable;

public class RequestRecipeFormat implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int recipeCost;
    private RequestRecipeIngredientFormat[] ingredients;

    public RequestRecipeFormat() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRecipeCost() {
        return recipeCost;
    }

    public void setRecipeCost(int recipeCost) {
        this.recipeCost = recipeCost;
    }

    public RequestRecipeIngredientFormat[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(RequestRecipeIngredientFormat[] ingredients) {
        this.ingredients = ingredients;
    }
}
