package dao;

import data.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoIngredient implements IngredientDao{
    private DaoIngredient(){}

    private static class SingletonHelper {
        private static final DaoIngredient INSTANCE = new DaoIngredient();
    }

    public static DaoIngredient getInstance(){
        return DaoIngredient.SingletonHelper.INSTANCE;
    }

    @Override
    public Optional<Ingredient> find(String id) throws SQLException {
        String query = "SELECT name, uuid FROM `ingredients` WHERE id=?";
        String name;
        String uuid;
        Optional<Ingredient> ingredient = Optional.empty();

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            name = resultSet.getString("name");
            uuid = resultSet.getString("uuid");
            ingredient = Optional.of(new Ingredient(Integer.parseInt(id), name, uuid));
        }
        return ingredient;
    }

    @Override
    public List<Ingredient> findAll() throws SQLException {
        List<Ingredient> ingredientList = new ArrayList<>();
        String query = "SELECT id, name, uuid FROM `ingredients`";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String uuid = resultSet.getString("uuid");
            ingredientList.add(new Ingredient(id, name, uuid));
        }
        return ingredientList;
    }

    @Override
    public int save(Ingredient ingredient) throws SQLException {
        String query = "INSERT INTO `ingredients` (name, uuid) VALUES (?, ?)";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, ingredient.getName());
        statement.setString(2, ingredient.getUUID());
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
    public boolean update(Ingredient ingredient) throws SQLException {
        String query = "UPDATE `ingredients` SET name = ?, uuid = ? WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, ingredient.getName());
        statement.setString(2, ingredient.getUUID());
        statement.setInt(3, ingredient.getId());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Ingredient ingredient) throws SQLException {
        String query = "DELETE FROM `ingredients` WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, ingredient.getId());

        return statement.executeUpdate() > 0;
    }

    public Optional<Ingredient> findByUUID(String uuid) throws SQLException {
        String query = "SELECT id, name FROM `ingredients` WHERE uuid = ?";
        int id;
        String name;
        Optional<Ingredient> ingredient = Optional.empty();

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, uuid);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            ingredient = Optional.of(new Ingredient(id, name, uuid));
        }
        return ingredient;
    }
}
