package formats;

import java.io.Serializable;

public class RequestRecipeIngredientFormat implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String name;
    private int amountNeeded;

    public RequestRecipeIngredientFormat() {}

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

    public int getAmountNeeded() {
        return amountNeeded;
    }

    public void setAmountNeeded(int amountNeeded) {
        this.amountNeeded = amountNeeded;
    }
}
