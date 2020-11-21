package formats;

import java.io.Serializable;

public class RequestRecipeFormat implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int price;
    private RequestRecipeIngredientFormat[] ingredients;

    public RequestRecipeFormat() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public RequestRecipeIngredientFormat[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(RequestRecipeIngredientFormat[] ingredients) {
        this.ingredients = ingredients;
    }
}
