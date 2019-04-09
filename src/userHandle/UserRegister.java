package userHandle;

import commonObject.User;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

class CardId{
    static private int head=1001;//4
    static private int tail=10000000;//8
    public static String makeId(String phone){
        String mid=phone.substring(phone.length()-6,phone.length());//6
        return head+mid+(tail++);//18
    }

}
public class UserRegister extends HttpServlet {
    private UserDao userDao=new UserDao();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           String username=req.getParameter("username");
           String password=req.getParameter("password");
           String email=req.getParameter("email");
           String phone=req.getParameter("phone");
           String cardId=CardId.makeId(phone);
           User user=new User(username,password,email,phone,cardId,"");
           if(userDao.insertUser(user)){
               req.setAttribute("flag",1);//flag=1，注册成功
               req.setAttribute("username",username);
               req.setAttribute("cardId",cardId);
           }
           else{
               req.setAttribute("flag",0);//flag=0 注册失败
           }

           req.getRequestDispatcher("result.jsp").forward(req,resp);
    }
}
