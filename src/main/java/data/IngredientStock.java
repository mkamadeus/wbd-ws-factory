package data;


import java.util.Date;

public class IngredientStock {
    private final int id;
    private int ingredientId;
    private int stock;
    private Date expiryDate;

    public IngredientStock(int id, int ingredientId, int stock, Date expiryDate) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.stock = stock;
        this.expiryDate = expiryDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public int getId() {
        return id;
    }
}
