package userHandle;

import commonObject.Record;
import dao.RecordDao;

import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import Helper.*;
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
            HttpSession session=req.getSession();
            cookieConverter converter=(cookieConverter) session.getAttribute("cookieConvert");
            myCardId=converter.decode(myCardId);
            RecordDao recordDao=new RecordDao(myCardId);

            BigDecimal myBalance=recordDao.queryBalance();
            BigDecimal changedNum=BigDecimal.valueOf(Double.parseDouble(req.getParameter("changednum")));
            String toId=req.getParameter("cardId");
            RecordDao yourRD=new RecordDao(toId);
            BigDecimal yourBalance=yourRD.queryBalance();
            if(myBalance.compareTo(myBalance)<1){
                req.setAttribute("flag",4);//flag=4 :余额不足
                req.getRequestDispatcher("result.jsp").forward(req,resp);
            }
            else{
                Record mine=new Record(changedNum.multiply(BigDecimal.valueOf(-1)),new Date(),toId,WaterIdMaker.getWid(myCardId,toId),myBalance.subtract(changedNum));
                Record yours=new Record(changedNum,new Date(),myCardId,WaterIdMaker.getWid(toId,myCardId),yourBalance.add(changedNum));
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
                BigDecimal balance=recordDao.queryBalance();
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
