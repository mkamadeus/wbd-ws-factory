package data;

import java.util.UUID;

public class Ingredient {
    private final int id;
    private String name;
    private String uuid;

    public Ingredient(int id, String name, String uuid) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
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
