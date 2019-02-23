package dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import commonObject.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import Helper.WaterIdMaker;
public class RecordDao {
    private Connection conn;
    private  boolean haveTable;
    private String tableName;
    private String myCardId;
    private static HashSet<String> createdTables=new HashSet<>();
    public RecordDao(String cardId){
        try{
            conn= new ComboPooledDataSource().getConnection();
            haveTable=false;
            tableName="records"+cardId;
            myCardId=cardId;
            if(!createdTables.contains(tableName)){
                createTable();
            }
        }catch (SQLException sqle){
            throw new RuntimeException(sqle);
        }
    }
    public boolean haveTable(){
        return haveTable;
    }
    private Connection getConnection(){
        return this.conn;
    }
    public boolean deleteTable(){
        try{
            PreparedStatement ps=conn.prepareStatement("drop table if exists "+tableName);
            ps.execute();
            createdTables.remove(tableName);
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    private void insertFirstRecord(){
        String from="0".repeat(18),to=myCardId;
        String wid=WaterIdMaker.getWid(from,to);
        insertRecord(new Record(0,new java.util.Date(),to,wid,0));

    }



    private boolean createTable(){
        try{
            String sql="create table if not exists "+tableName;
            sql=sql+" (changednum double not null,changeddate timestamp ,cardId char(18) not null,wid char(30) not null,balance double not null,primary key(wid));";
            PreparedStatement ps=conn.prepareStatement(sql);
            boolean r=ps.execute();
            this.haveTable=true;
            createdTables.add(tableName);
            if(r||!r){
                insertFirstRecord();

            }

            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    private String getTableName(){
        return this.tableName;
    }


    public boolean insertRecord(Record record){
        try{
            if(!createdTables.contains(tableName)){
                createTable();

            }
            PreparedStatement ps=conn.prepareStatement("insert into "+tableName+" (changednum,changeddate,cardId,wid,balance) values " +
                    "(?,?,?,?,?)");
            ps.setDouble(1,record.getChange());
            ps.setTimestamp(2,new Timestamp(record.getDate().getTime()));
            ps.setString(3,record.getToId());
            ps.setString(4,record.getWaterId());
            ps.setDouble(5,record.getBalance());
            ps.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }

    public List<Record> getRecords(int from,int num){
        try{
            if(!createdTables.contains(tableName)){
                createTable();

            }
            PreparedStatement ps=conn.prepareStatement("select * from "+tableName+" order by  wid limit ? offset ?");
            ps.setInt(1,num);
            ps.setInt(2,from);
            ResultSet rs=ps.executeQuery();
            ArrayList<Record> records=new ArrayList<>();
            while (rs.next()){
                records.add(new Record(rs.getDouble("changednum"),
                        rs.getDate("changeddate"),
                        rs.getString("cardId"),
                        rs.getString("wid"),
                        rs.getDouble("balance")));
            }
            return records;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }

    }
    public double queryBalance(){
        try{
            if(!createdTables.contains(tableName)){
                createTable();

            }
            PreparedStatement ps=conn.prepareStatement("select * from "+tableName+"  order by wid desc limit 1;");
            ResultSet rs=ps.executeQuery();
            rs.next();
            double balance=rs.getDouble("balance");
            return balance;
        }
        catch (SQLException sqle){
            sqle.printStackTrace();
            throw new RuntimeException(sqle);
        }
    }

}
