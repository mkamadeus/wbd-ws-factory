package data;

import java.util.UUID;

public class Chocolate{
    private final int id;
    private String uuid;
    private String name;
    private int stock;
    private int recipeCost;

    public Chocolate(int id, String uuid, String name, int stock, int recipeCost) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.stock = stock;
        this.recipeCost = recipeCost;
    }

    public int getId() {
        return id;
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

    public int getRecipeCost() {
        return recipeCost;
    }

    public void setRecipeCost(int recipeCost) {
        this.recipeCost = recipeCost;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public void generateUUID(){
        UUID randomUUID = UUID.randomUUID();
        this.uuid = String.valueOf(randomUUID);
    }
}
