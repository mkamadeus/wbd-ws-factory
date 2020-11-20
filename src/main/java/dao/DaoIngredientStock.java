package dao;

import data.IngredientStock;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoIngredientStock implements IngredientStockDao{
    private DaoIngredientStock(){}

    private static class SingletonHelper {
        private static final DaoIngredientStock INSTANCE = new DaoIngredientStock();
    }

    public static DaoIngredientStock getInstance(){
        return DaoIngredientStock.SingletonHelper.INSTANCE;
    }

    @Override
    public Optional<IngredientStock> find(String id) throws SQLException {
        String query = "SELECT ingredient_id, stock, expiry_date FROM `ingredients_stock` WHERE id=?";
        int ingredientId;
        int stock;
        Date expiryDate ;
        Optional<IngredientStock> ingredientStock = Optional.empty();

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            ingredientId = resultSet.getInt("ingredient_id");
            stock = resultSet.getInt("stock");
            expiryDate = resultSet.getDate("expiry_date");
            ingredientStock = Optional.of(new IngredientStock(Integer.parseInt(id), ingredientId, stock, expiryDate));
        }
        return ingredientStock;
    }

    @Override
    public List<IngredientStock> findAll() throws SQLException {
        List<IngredientStock> ingredientStockList = new ArrayList<>();
        String query = "SELECT id, ingredient_id, stock, expiry_date FROM `ingredients_stock`";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            int ingredientId = resultSet.getInt("ingredient_id");
            int stock = resultSet.getInt("stock");
            Date expiryDate = resultSet.getDate("expiry_date");
            ingredientStockList.add(new IngredientStock(id, ingredientId, stock, expiryDate));
        }
        return ingredientStockList;
    }

    @Override
    public int save(IngredientStock ingredientStock) throws SQLException {
        String query = "INSERT INTO `ingredients_stock` (ingredient_id, stock, expiry_date) VALUES (?, ?, ?)";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, ingredientStock.getIngredientId());
        statement.setInt(2, ingredientStock.getStock());
        Date dateExpiryDate = new Date(ingredientStock.getExpiryDate().getTime());
        statement.setDate(3, dateExpiryDate);

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
    public boolean update(IngredientStock ingredientStock) throws SQLException {
        String query = "UPDATE `ingredients_stock` SET ingredient_id = ?, stock = ?, expiry_date = ? WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, ingredientStock.getIngredientId());
        statement.setInt(1, ingredientStock.getStock());
        Date dateExpiryDate = new Date(ingredientStock.getExpiryDate().getTime());
        statement.setDate(1, dateExpiryDate);
        statement.setInt(2, ingredientStock.getId());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(IngredientStock ingredientStock) throws SQLException {
        String query = "DELETE FROM `ingredients_stock` WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, ingredientStock.getId());

        return statement.executeUpdate() > 0;
    }

    public int getAvailableSumByIngredientId(int queryIngredientId, Date expiryDate) throws SQLException {
        String query = "SELECT COALESCE(SUM(stock), 0) AS TOTAL FROM `ingredients_stock` WHERE ingredient_id=? AND expiry_date > ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, queryIngredientId);
        statement.setDate(2, expiryDate);
        ResultSet resultSet = statement.executeQuery();

        int total = -1;

        if(resultSet.next()) {
            total = resultSet.getInt("TOTAL");
        }

        return total;
    }
}
