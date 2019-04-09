package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.sql.SqlUtils;
import commonObject.Record;
import commonObject.User;
import Helper.WaterIdMaker;
import managerHandle.ManagerServiceServlet;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.List;
import Helper.hashKeyConverter;
public class UserDao {
   private Connection conn;
   //使用conn的PreparedStatement类的execute时，注意插入/更新 返回值为false，查询才是true；
   public UserDao(){
      try{
         conn=new ComboPooledDataSource().getConnection();
      }catch (SQLException sqle){
         throw new RuntimeException(sqle);
      }
   }
   public boolean insertUser(User user){
    try{
       PreparedStatement ps=conn.prepareStatement("insert into users(username,password,email,phone,cardId)values (?,?,?,?,?,?)");
       ps.setString(1,user.getUsername());
       ps.setString(2,user.getPassword());
       ps.setString(3,user.getEmail());
       ps.setString(4,user.getPhone());
       ps.setString(5,user.getCardId());
       ps.setString(6,hashKeyConverter.string2MD5(user.getPhone()+user.getPassword()));
       return !ps.execute();
    }catch (SQLException e){
       e.printStackTrace();
       return false;
    }

   }

   public boolean deleteUser(String phone){
       try{
          PreparedStatement ps=conn.prepareStatement("select * from users where phone=?");
          ps.setString(1,phone);
          ResultSet resultSet=ps.executeQuery();
          resultSet.next();
          String cardId=resultSet.getString("cardId");
          RecordDao rd=new RecordDao(cardId);
          boolean deleted=rd.deleteTable();
          if(!deleted)
             throw new SQLException();
          ps=conn.prepareStatement("delete from users where phone=?");
          ps.setString(1,phone);
          ps.execute();
          return true;
       }catch (SQLException e){
          e.printStackTrace();
          return false;
       }

   }
   public boolean updateUser(String phone,String username,String password,String email){
      //由于账户的性质，只可以更改username，password，email这三项。其余两项基本不可以改变。
      try{
         PreparedStatement ps=conn.prepareStatement("update users set username=?, " +
                 " password=?,email=? where phone=? ");
         ps.setString(1,username);
         ps.setString(2,password);
         ps.setString(3,email);
         ps.setString(4,phone);
         ps.execute();
         return true;
      }catch (SQLException e){
         e.printStackTrace();
         return  false;
      }
   }
   public boolean changeBalance(String phone ,BigDecimal changednum){
      try{

         PreparedStatement ps=conn.prepareStatement("select * from users where phone=?");
         ps.setString(1,phone);
         ResultSet rs=ps.executeQuery();
         rs.next();
         String cardId=rs.getString("cardId");
         RecordDao recordDao=new RecordDao(cardId);
         BigDecimal currentBalance=recordDao.queryBalance();
         if(changednum.compareTo(BigDecimal.valueOf(0))<0&& changednum.abs().compareTo(currentBalance)>0)
            return false;
         Record record=new Record(changednum,new Date(),cardId,WaterIdMaker.getWid(cardId,cardId),currentBalance.add(currentBalance));
         recordDao.insertRecord(record);
         return true;
      }catch (SQLException e){
         e.printStackTrace();
         throw new RuntimeException(e);
      }
   }
   public User findUser(String phone,String pwd){
      try{
         String hashkey=hashKeyConverter.string2MD5(phone+pwd);
         PreparedStatement ps=conn.prepareStatement("select * from users where hashkey=?");
         ps.setString(1,hashkey);

         ResultSet rs=ps.executeQuery();
         if(rs==null)
            return null;
         else{
            rs.next();
            return new User(rs.getNString("username"),
                    rs.getNString("password"),
                    rs.getNString("email"),
                    rs.getNString("phone"),
                    rs.getNString("cardId"),hashkey);
         }
      }
      catch (SQLException e){
         e.printStackTrace();
         throw new RuntimeException(e);
      }

   }

}
