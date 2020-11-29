package dao;

import data.ChocolateRecipe;
import data.Ingredient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoChocolateRecipe implements ChocolateRecipeDao{
    private DaoChocolateRecipe(){}

    private static class SingletonHelper {
        private static final DaoChocolateRecipe INSTANCE = new DaoChocolateRecipe();
    }

    public static DaoChocolateRecipe getInstance(){
        return SingletonHelper.INSTANCE;
    }

    @Override
    public Optional<ChocolateRecipe> find(String id) throws SQLException {
        String query = "SELECT chocolate_id, ingredient_id, amount FROM `chocolate_recipe` WHERE id=?";
        int chocolateId = 0;
        int ingredientId = 0;
        int amount = 0;
        Optional<ChocolateRecipe> chocolateRecipe = Optional.empty();

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            chocolateId = resultSet.getInt("chocolate_id");
            ingredientId = resultSet.getInt("ingredient_id");
            amount = resultSet.getInt("amount");
            chocolateRecipe = Optional.of(new ChocolateRecipe(Integer.parseInt(id), chocolateId, ingredientId, amount));
        }
        return chocolateRecipe;
    }

    @Override
    public List<ChocolateRecipe> findAll() throws SQLException {
        List<ChocolateRecipe> chocolateRecipeList = new ArrayList<>();
        String query = "SELECT id, chocolate_id, ingredient_id, amount FROM `chocolate_recipe`";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            int chocolateId = resultSet.getInt("chocolate_id");
            int ingredientId = resultSet.getInt("ingredient_id");
            int amount = resultSet.getInt("amount");
            chocolateRecipeList.add(new ChocolateRecipe(id, chocolateId, ingredientId, amount));
        }
        return chocolateRecipeList;
    }

    @Override
    public int save(ChocolateRecipe chocolateRecipe) throws SQLException {
        String query = "INSERT INTO `chocolate_recipe` (chocolate_id, ingredient_id, amount) VALUES (?, ?, ?)";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, chocolateRecipe.getChocolateId());
        statement.setInt(2, chocolateRecipe.getIngredientId());
        statement.setInt(3, chocolateRecipe.getIngredientAmount());

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
    public boolean update(ChocolateRecipe chocolateRecipe) throws SQLException {
        String query = "UPDATE `chocolate_recipe` SET chocolate_id = ?, ingredient_id = ?, amount = ? WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, chocolateRecipe.getChocolateId());
        statement.setInt(2, chocolateRecipe.getIngredientId());
        statement.setInt(3, chocolateRecipe.getIngredientAmount());
        statement.setInt(4, chocolateRecipe.getId());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(ChocolateRecipe chocolateRecipe) throws SQLException {
        String query = "DELETE FROM `chocolate_recipe` WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, chocolateRecipe.getId());

        return statement.executeUpdate() > 0;
    }

    public List<ChocolateRecipe> findAllByChocolateId(int chocolateId) throws SQLException {
        List<ChocolateRecipe> chocolateRecipeList = new ArrayList<>();
        String query = "SELECT id, ingredient_id, amount FROM `chocolate_recipe` WHERE chocolate_id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, chocolateId);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            int ingredientId = resultSet.getInt("ingredient_id");
            int amount = resultSet.getInt("amount");
            chocolateRecipeList.add(new ChocolateRecipe(id, chocolateId, ingredientId, amount));
        }
        return chocolateRecipeList;
    }
}
