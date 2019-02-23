package commonObject;

import java.util.Date;

public class Record {
    private double change;
    private Date date;
    private String toId;
    private String waterId;
    private double balance;
    public Record(){}
    public Record(double change,Date date,String toId,String waterId,double balance){
        this.balance=balance;
        this.change=change;
        this.date=date;
        this.toId=toId;
        this.waterId=waterId;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getChange() {
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
