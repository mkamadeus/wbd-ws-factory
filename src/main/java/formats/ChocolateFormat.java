package formats;

import data.Chocolate;

import java.io.Serializable;

public class ChocolateFormat implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String uuid;
    private String name;
    private int stock;
    private int recipeCost;

    public static ChocolateFormat fromChocolate(Chocolate chocolate){
        return new ChocolateFormat(
                chocolate.getId(),
                chocolate.getUUID(),
                chocolate.getName(),
                chocolate.getStock(),
                chocolate.getRecipeCost()
        );
    }

    public ChocolateFormat(int id, String uuid, String name, int stock, int recipeCost) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.stock = stock;
        this.recipeCost = recipeCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public int getRecipeCost() {
        return recipeCost;
    }

    public void setRecipeCost(int recipeCost) {
        this.recipeCost = recipeCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
