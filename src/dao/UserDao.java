package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.sql.SqlUtils;
import commonObject.Record;
import commonObject.User;
import Helper.WaterIdMaker;
import managerHandle.ManagerServiceServlet;

import java.sql.*;
import java.util.Date;
import java.util.List;

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
       PreparedStatement ps=conn.prepareStatement("insert into users(username,password,email,phone,cardId)values (?,?,?,?,?)");
       ps.setString(1,user.getUsername());
       ps.setString(2,user.getPassword());
       ps.setString(3,user.getEmail());
       ps.setString(4,user.getPhone());
       ps.setString(5,user.getCardId());
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
   public boolean changeBalance(String phone ,double changednum){
      try{
         PreparedStatement ps=conn.prepareStatement("select * from users where phone=?");
         ps.setString(1,phone);
         ResultSet rs=ps.executeQuery();
         rs.next();
         String cardId=rs.getString("cardId");
         RecordDao recordDao=new RecordDao(cardId);
         double currentBalance=recordDao.queryBalance();
         if(changednum<0&& Math.abs(changednum)>currentBalance)
            return false;
         Record record=new Record(changednum,new Date(),cardId,WaterIdMaker.getWid(cardId,cardId),currentBalance+changednum);
         recordDao.insertRecord(record);
         return true;
      }catch (SQLException e){
         e.printStackTrace();
         throw new RuntimeException(e);
      }
   }
   public User findUser(String phone,String pwd){
      try{
         PreparedStatement ps=conn.prepareStatement("select * from users where phone=? and password=?");
         ps.setString(1,phone);
         ps.setString(2,pwd);
         ResultSet rs=ps.executeQuery();
         if(rs==null)
            return null;
         else{
            rs.next();
            return new User(rs.getNString("username"),
                    rs.getNString("password"),
                    rs.getNString("email"),
                    rs.getNString("phone"),
                    rs.getNString("cardId"));
         }
      }
      catch (SQLException e){
         e.printStackTrace();
         throw new RuntimeException(e);
      }


   }

}
