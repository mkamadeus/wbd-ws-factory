package formats;

import data.Chocolate;
import data.Ingredient;

import java.io.Serializable;
import java.util.Date;

public class AvailableIngredientFormat implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String uuid;
    private String name;
    private int availableStock;

    public static AvailableIngredientFormat fromIngredient(Ingredient ingredient, int availableStock){
        return new AvailableIngredientFormat(
                ingredient.getId(),
                ingredient.getUUID(),
                ingredient.getName(),
                availableStock
        );
    }

    public AvailableIngredientFormat(int id, String uuid, String name, int availableStock){
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.availableStock = availableStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(int availableStock) {
        this.availableStock = availableStock;
    }
}
