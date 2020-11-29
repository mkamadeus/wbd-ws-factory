package data;

import java.util.Date;
import java.util.UUID;

public class StockRequest {
    private final int id;
    private int chocolateId;
    private String trackingId;
    private int amount;
    private boolean isDelivered;
    private Date createdAt;
    private Date updatedAt;

    public StockRequest(int id, int chocolateId, String trackingId, int amount, boolean isDelivered, Date createdAt, Date updatedAt) {
        this.id = id;
        this.chocolateId = chocolateId;
        this.trackingId = trackingId;
        this.amount = amount;
        this.isDelivered = isDelivered;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public int getChocolateId() {
        return chocolateId;
    }

    public void setChocolateId(int chocolateId) {
        this.chocolateId = chocolateId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public void generateTrackingId(){
        UUID uuid = UUID.randomUUID();
        this.trackingId = String.valueOf(uuid);
    }
}
