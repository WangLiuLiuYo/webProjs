package userHandle;

import commonObject.Record;
import dao.RecordDao;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import Helper.WaterIdMaker;
public class UserServiceServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies=req.getCookies();
        String myCardId=null;
        for(Cookie c:cookies){
            if(c.getName().equals("cardId")){
                myCardId=c.getValue();
                break;
            }
        }
        if(myCardId!=null){
            RecordDao recordDao=new RecordDao(myCardId);

            double myBalance=recordDao.queryBalance();
            double changedNum=Double.valueOf(req.getParameter("changednum"));
            String toId=req.getParameter("cardId");
            RecordDao yourRD=new RecordDao(toId);
            double yourBalance=yourRD.queryBalance();
            if(myBalance<changedNum){
                req.setAttribute("flag",4);//flag=4 :余额不足
                req.getRequestDispatcher("result.jsp").forward(req,resp);
            }
            else{
                Record mine=new Record(-changedNum,new Date(),toId,WaterIdMaker.getWid(myCardId,toId),myBalance-changedNum);
                Record yours=new Record(changedNum,new Date(),myCardId,WaterIdMaker.getWid(toId,myCardId),yourBalance+changedNum);
                recordDao.insertRecord(mine);
                yourRD.insertRecord(yours);
                req.setAttribute("flag",5);//flag=5:转账成功
                req.getRequestDispatcher("result.jsp").forward(req,resp);
            }
        }
        else{
            req.setAttribute("flag",7);//flag=7:cookie找不到，没有授权
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q=req.getParameter("q");
        String myCardId=null;
        for(Cookie c:req.getCookies()){
            if(c.getName().equals("cardId")){
                myCardId=c.getValue();
                break;
            }
        }
        if(myCardId!=null){
            RecordDao recordDao=new RecordDao(myCardId);
            String param=req.getParameter("q");
            if(param.equals("balance")){
                double balance=recordDao.queryBalance();
                req.setAttribute("balance",balance);
                req.setAttribute("flag",8);//flag=8:显示余额查询结果
                req.getRequestDispatcher("result.jsp").forward(req,resp);
            }
            else if(param.equals("records")){
                List<Record> recordList=recordDao.getRecords(0,50);
                req.setAttribute("records",recordList);
                req.setAttribute("flag",9);//flag=9:显示最近50条记录
                req.getRequestDispatcher("result.jsp").forward(req,resp);


            }
        }
    }
}
