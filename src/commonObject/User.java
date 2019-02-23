package commonObject;

public class User {
private String username;
private String password;
private String email;
private String phone;
private String cardId;
public User(){}
public User(String username,String password,String email,String phone,String cardId){
    this.username=username;
    this.cardId=cardId;
    this.email=email;
    this.password=password;
    this.phone=phone;
}
public void setUsername(String username){
    this.username=username;
   }
public String getUsername(){return this.username;}

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }
}
