package formats;

import data.Chocolate;
import data.StockRequest;

import java.io.Serializable;
import java.util.Date;

public class StockRequestFormat implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private Chocolate chocolate;
    private String trackingId;
    private int amount;
    private boolean isDelivered;
    private Date createdAt;
    private Date updatedAt;

    public static StockRequestFormat fromStockRequest(StockRequest stockRequest, Chocolate chocolate){
        return new StockRequestFormat(
                stockRequest.getId(),
                chocolate,
                stockRequest.getTrackingId(),
                stockRequest.getAmount(),
                stockRequest.isDelivered(),
                stockRequest.getCreatedAt(),
                stockRequest.getUpdatedAt()
        );
    }

    public StockRequestFormat(int id, Chocolate chocolate, String trackingId, int amount, boolean isDelivered, Date createdAt, Date updatedAt) {
        this.id = id;
        this.chocolate = chocolate;
        this.trackingId = trackingId;
        this.amount = amount;
        this.isDelivered = isDelivered;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Chocolate getChocolate() {
        return chocolate;
    }

    public void setChocolate(Chocolate chocolate) {
        this.chocolate = chocolate;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
