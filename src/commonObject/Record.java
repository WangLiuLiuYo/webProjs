package commonObject;

import java.math.BigDecimal;
import java.util.Date;

public class Record {
    private BigDecimal change;
    private Date date;
    private String toId;
    private String waterId;
    private BigDecimal balance;
    public Record(){}
    public Record(BigDecimal change,Date date,String toId,String waterId,BigDecimal balance){
        this.balance=balance;
        this.change=change;
        this.date=date;
        this.toId=toId;
        this.waterId=waterId;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getToId() {
        return toId;
    }

    public String getWaterId() {
        return waterId;
    }

    public void setWaterId(String waterId) {
        this.waterId = waterId;
    }

    @Override
    public String toString() {
        return change+"|"+date+"|"+toId+"|"+waterId+"|"+balance;
    }
}
