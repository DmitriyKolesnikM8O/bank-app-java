package bd_objs;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * User entity which is used to store user information (id, username, password, current_balance)
 */
public class User {
    private final int id;
    private final String username;
    private final String password;
    private BigDecimal currentBalance;

    public User(int id, String username, String password, BigDecimal currentBalance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.currentBalance = currentBalance;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String returnPassword() {
        return password;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal newBalance) {
        currentBalance = newBalance.setScale(2, RoundingMode.FLOOR);
    }    
}
