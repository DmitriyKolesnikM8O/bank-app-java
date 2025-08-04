package bd_objs;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * JDBC class is used to interact with our MySQL database to perform operations.
 */
public class MyJDBC {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/banking_app?useSSL=false&serverTimezone=UTC";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "pass";

    public static User validateLogin(String username, String password) {
        try {
            
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);


            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ? AND password = ?"
            );

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("id");

                BigDecimal currentBalance = resultSet.getBigDecimal("current_balance");

                return new User(userId, username, password, currentBalance);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        

        return null;
    }

    public static boolean register(String username, String password) {
        try {
            if (checkUser(username)) {
                return false;
            }

            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO users(username, password, current_balance) VALUES (?, ?, ?)"
            );

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setBigDecimal(3, new BigDecimal(0));

            preparedStatement.executeUpdate();
            return true;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * false - user not exist
     * true - user exist
     */
    private static boolean checkUser(String username) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE username=?"
            );           
            preparedStatement.setString(1, username);

            ResultSet result = preparedStatement.executeQuery();
            if (!result.next()) {
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static boolean addTransactionToDatabase(Transaction transaction) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO transactions(user_id, transaction_type, transaction_amount, transaction_date) " +
                "VALUES(?, ?, ?, NOW())"
            );
            preparedStatement.setInt(1, transaction.getUserId());
            preparedStatement.setString(2, transaction.getTranscationType());
            preparedStatement.setBigDecimal(3, transaction.getTransactionAmount());

            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean updateCurrentBalance(User user) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE users SET current_balance = ? WHERE id = ?"
            );

            preparedStatement.setBigDecimal(1, user.getCurrentBalance());
            preparedStatement.setInt(2, user.getId());

            preparedStatement.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean transfer(User user, String transferredUsername, float transferAmount) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ?"
            );

            preparedStatement.setString(1, transferredUsername);
            ResultSet result = preparedStatement.executeQuery();
            
            while (result.next()) {
                User transferredUser = new User(
                    result.getInt("id"),
                    transferredUsername,
                    result.getString("password"),
                    result.getBigDecimal("current_balance")
                );

                Transaction transferTransaction = new Transaction(
                    user.getId(),
                    "Transfer",
                    new BigDecimal(-transferAmount),
                    null
                );

                Transaction receivedTransaction = new Transaction(
                    user.getId(),
                    "Transfer",
                    new BigDecimal(transferAmount),
                    null
                );

                transferredUser.setCurrentBalance(transferredUser.getCurrentBalance().add(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(transferredUser);

                user.setCurrentBalance(user.getCurrentBalance().subtract(BigDecimal.valueOf(transferAmount)));
                updateCurrentBalance(user);

                addTransactionToDatabase(transferTransaction);
                addTransactionToDatabase(receivedTransaction);
                
            
                return true;
            }
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static ArrayList<Transaction> getPastTransactions(User user) {
        ArrayList<Transaction> pastTransactions = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM transactions WHERE user_id = ?"
            );
            preparedStatement.setInt(1, user.getId());
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                Transaction transaction = new Transaction(
                    user.getId(),
                    result.getString("transaction_type"),
                    result.getBigDecimal("transaction_amount"),
                    result.getDate("transaction_date")
                );
                pastTransactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pastTransactions;
    }
    
}
