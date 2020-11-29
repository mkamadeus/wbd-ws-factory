package dao;

import data.Account;
import data.Chocolate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoAccount implements AccountDao{
    private DaoAccount(){}

    @Override
    public Optional<Account> find(String id) throws SQLException {
        String query = "SELECT username, password FROM `accounts` WHERE id=?";
        String username;
        String password;
        Optional<Account> account = Optional.empty();

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            username = resultSet.getString("username");
            password = resultSet.getString("password");
            account = Optional.of(new Account(Integer.parseInt(id), username, password, false));
        }
        return account;
    }

    @Override
    public List<Account> findAll() throws SQLException {
        List<Account> accountList = new ArrayList<>();
        String query = "SELECT id, username, password FROM `accounts`";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            accountList.add(new Account(id, username, password, false));
        }
        return accountList;
    }

    @Override
    public int save(Account account) throws SQLException {
        String query = "INSERT INTO `accounts` (username, password) VALUES (?, ?)";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, account.getUsername());
        statement.setString(2, account.getPassword());
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
    public boolean update(Account account) throws SQLException {
        String query = "UPDATE `accounts` SET username = ?, password = ? WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, account.getUsername());
        statement.setString(2, account.getPassword());
        statement.setInt(3, account.getId());

        return statement.executeUpdate() > 0;
    }

    @Override
    public boolean delete(Account account) throws SQLException {
        String query = "DELETE FROM `accounts` WHERE id = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setInt(1, account.getId());

        return statement.executeUpdate() > 0;
    }

    public boolean isExistUsernamePassword(String username, String password) throws SQLException {
        String query = "SELECT * FROM `accounts` WHERE username = ? AND password = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }

    public boolean isExistUsername(String username) throws SQLException {
        String query = "SELECT * FROM `accounts` WHERE username = ?";

        Connection conn = DataSourceFactory.getConn();
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }

    private static class SingletonHelper {
        private static final DaoAccount INSTANCE = new DaoAccount();
    }

    public static DaoAccount getInstance(){
        return DaoAccount.SingletonHelper.INSTANCE;
    }


}
