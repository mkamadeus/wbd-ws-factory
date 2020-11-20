package dao;

import data.StockRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoStockRequest implements StockRequestDao{
    private DaoStockRequest(){}

    private static class SingletonHelper {
        private static final DaoStockRequest INSTANCE = new DaoStockRequest();
    }

    public static DaoStockRequest getInstance(){
        return DaoStockRequest.SingletonHelper.INSTANCE;
    }

    @Override
    public Optional<StockRequest> find(String id) throws SQLException {
        String query = "SELECT chocolate_id, tracking_id, amount, is_delivered, created_at, updated_at FROM `stock_request` WHERE id=?";
        int chocolateId;
        String trackingId;
        int amount;
        boolean isDelivered;
        Timestamp createdAt;
        Timestamp updatedAt;
        Optional<StockRequest> stockRequest = Optional.empty();

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            chocolateId = resultSet.getInt("chocolate_id");
            trackingId = resultSet.getString("tracking_id");
            amount = resultSet.getInt("amount");
            isDelivered = resultSet.getBoolean("is_delivered");
            createdAt = resultSet.getTimestamp("created_at");
            updatedAt = resultSet.getTimestamp("updated_at");
            stockRequest = Optional.of(
                    new StockRequest(Integer.parseInt(id), chocolateId, trackingId, amount, isDelivered, createdAt, updatedAt)
            );
        }
        return stockRequest;
    }

    @Override
    public List<StockRequest> findAll() throws SQLException {
        List<StockRequest> stockRequestList = new ArrayList<>();
        String query = "SELECT id, chocolate_id, tracking_id, amount, is_delivered, created_at, updated_at FROM `stock_request`";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            int chocolateId = resultSet.getInt("chocolate_id");
            String trackingId = resultSet.getString("tracking_id");
            int amount = resultSet.getInt("amount");
            boolean isDelivered = resultSet.getBoolean("is_delivered");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            Timestamp updatedAt = resultSet.getTimestamp("updated_at");
            stockRequestList.add(new StockRequest(id, chocolateId, trackingId, amount, isDelivered, createdAt, updatedAt));
        }
        return stockRequestList;
    }

    @Override
    public int save(StockRequest stockRequest) throws SQLException {
        String query = "INSERT INTO `stock_request` (chocolate_id, tracking_id, amount, is_delivered, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, stockRequest.getChocolateId());
        statement.setString(2, stockRequest.getTrackingId());
        statement.setInt(3, stockRequest.getAmount());
        statement.setBoolean(4, stockRequest.isDelivered());
        Timestamp timestampCreatedAt = new Timestamp(stockRequest.getCreatedAt().getTime());
        statement.setTimestamp(5, timestampCreatedAt);
        Timestamp timestampUpdatedAt = new Timestamp(stockRequest.getUpdatedAt().getTime());
        statement.setTimestamp(6, timestampUpdatedAt);

        statement.executeUpdate();

        int id;
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }
        else{
            throw new SQLException("No rows affected.");
        }
        rs.close();

        return id;
    }

    @Override
    public boolean update(StockRequest stockRequest) throws SQLException {
        String query = "UPDATE `stock_request` SET chocolate_id = ?, tracking_id = ?, amount = ?, is_delivered = ?, created_at = ?, updated_at = ? WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, stockRequest.getChocolateId());
        statement.setString(2, stockRequest.getTrackingId());
        statement.setInt(3, stockRequest.getAmount());
        statement.setBoolean(4, stockRequest.isDelivered());
        Timestamp timestampCreatedAt = new Timestamp(stockRequest.getCreatedAt().getTime());
        statement.setTimestamp(5, timestampCreatedAt);
        Timestamp timestampUpdatedAt = new Timestamp(stockRequest.getUpdatedAt().getTime());
        statement.setTimestamp(6, timestampUpdatedAt);
        statement.setInt(7, stockRequest.getId());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(StockRequest stockRequest) throws SQLException {
        String query = "DELETE FROM `stock_request` WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, stockRequest.getId());

        return statement.executeUpdate() > 0;
    }

    public Optional<StockRequest> findByTrackingId(String trackingId) throws SQLException {
        String query = "SELECT id, chocolate_id, amount, is_delivered, created_at, updated_at FROM `stock_request` WHERE tracking_id=?";
        int id;
        int chocolateId;
        int amount;
        boolean isDelivered;
        Timestamp createdAt;
        Timestamp updatedAt;
        Optional<StockRequest> stockRequest = Optional.empty();

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, trackingId);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            id = resultSet.getInt("id");
            chocolateId = resultSet.getInt("chocolate_id");
            amount = resultSet.getInt("amount");
            isDelivered = resultSet.getBoolean("is_delivered");
            createdAt = resultSet.getTimestamp("created_at");
            updatedAt = resultSet.getTimestamp("updated_at");
            stockRequest = Optional.of(
                    new StockRequest(id, chocolateId, trackingId, amount, isDelivered, createdAt, updatedAt)
            );
        }
        return stockRequest;
    }

}
