package dao;

import java.sql.*;

public class DaoBalance {
    private DaoBalance(){}

    private static class SingletonHelper {
        private static final DaoBalance INSTANCE = new DaoBalance();
    }

    public static DaoBalance getInstance(){
        return DaoBalance.SingletonHelper.INSTANCE;
    }

    public long getBalance() throws SQLException {
        long balance = 0;

        String query = "SELECT `balance` FROM `balance`";
        Connection conn = DataSourceFactory.getConn();
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(query);

        if(resultSet.next()){
            balance = resultSet.getLong("balance");
        }
        else{
            throw new SQLException("No available balance in table.");
        }
        return balance;
    }

    public boolean updateBalance(long balance) throws SQLException {
        if(balance >= 0){
            String query = "UPDATE `balance` SET `balance`=?";
            Connection conn = DataSourceFactory.getConn();
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, balance);
            return statement.executeUpdate() > 0;
        }
        return false;
    }

}
