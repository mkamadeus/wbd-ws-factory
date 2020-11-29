package formats;

import data.Chocolate;

import java.io.Serializable;

public class ChocolateFormat implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private String uuid;
    private String name;
    private int stock;

    public static ChocolateFormat fromChocolate(Chocolate chocolate){
        return new ChocolateFormat(
                chocolate.getId(),
                chocolate.getUUID(),
                chocolate.getName(),
                chocolate.getStock()
        );
    }

    public ChocolateFormat(int id, String uuid, String name, int stock) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.stock = stock;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
