package bd_objs;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {
    private final int userId;
    private final String transactionType;
    private final BigDecimal transcationAmount;
    private final Date transactionDate;

    public Transaction(int userId, String transactionType, BigDecimal transcactionAmount, Date transactionDate) {
        this.userId = userId;
        this.transactionType = transactionType;
        this.transcationAmount = transcactionAmount;
        this.transactionDate = transactionDate;
    }

    public int getUserId() {
        return userId;
    }

    public String getTranscationType() {
        return transactionType;
    }

    public BigDecimal getTransactionAmount() {
        return transcationAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
