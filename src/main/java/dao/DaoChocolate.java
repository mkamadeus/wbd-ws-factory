package dao;

import data.Chocolate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoChocolate implements ChocolateDao{
    private DaoChocolate(){}

    private static class SingletonHelper {
        private static final DaoChocolate INSTANCE = new DaoChocolate();
    }

    public static DaoChocolate getInstance(){
        return SingletonHelper.INSTANCE;
    }

    @Override
    public Optional<Chocolate> find(String id) throws SQLException {
        String query = "SELECT name, uuid, stock FROM `chocolate` WHERE id=?";
        int stock;
        String name;
        String uuid;
        Optional<Chocolate> chocolate = Optional.empty();

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            name = resultSet.getString("name");
            uuid = resultSet.getString("uuid");
            stock = resultSet.getInt("stock");
            chocolate = Optional.of(new Chocolate(Integer.parseInt(id), uuid, name, stock));
        }
        return chocolate;
    }

    @Override
    public List<Chocolate> findAll() throws SQLException {
        List<Chocolate> chocolateList = new ArrayList<>();
        String query = "SELECT id, name, uuid, stock FROM `chocolate`";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String uuid = resultSet.getString("uuid");
            int stock = resultSet.getInt("stock");
            chocolateList.add(new Chocolate(id, uuid, name, stock));
        }
        return chocolateList;
    }

    @Override
    public int save(Chocolate chocolate) throws SQLException {
        String query = "INSERT INTO `chocolate` (name, uuid, stock) VALUES (?, ?, ?)";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, chocolate.getName());
        statement.setString(2, chocolate.getUUID());
        statement.setInt(3, chocolate.getStock());
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
    public boolean update(Chocolate chocolate) throws SQLException {
        String query = "UPDATE `chocolate` SET name = ?, uuid= ?, stock = ? WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, chocolate.getName());
        statement.setString(2, chocolate.getUUID());
        statement.setInt(3, chocolate.getStock());
        statement.setInt(4, chocolate.getId());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Chocolate chocolate) throws SQLException {
        String query = "DELETE FROM `chocolate` WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, chocolate.getId());

        return statement.executeUpdate() > 0;
    }

    public Optional<Chocolate> findByUUID(String uuid) throws SQLException {
        String query = "SELECT id, name, stock FROM `chocolate` WHERE uuid=?";
        int stock;
        int id;
        String name;
        Optional<Chocolate> chocolate = Optional.empty();

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, uuid);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            stock = resultSet.getInt("stock");
            chocolate = Optional.of(new Chocolate(id, uuid, name, stock));
        }
        return chocolate;
    }
}
