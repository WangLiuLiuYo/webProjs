package userHandle;

import commonObject.User;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLogIn extends HttpServlet {
    private UserDao userDao=new UserDao();
    private final int cookieLife=30*60;//second;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String phone =req.getParameter("phone");
    String pwd=req.getParameter("password");
    String select=req.getParameter("type");
    if(select.equals("user")){
        User user=userDao.findUser(phone,pwd);
        if(user!=null){
            Cookie cookie=new Cookie("cardId",user.getCardId());
            cookie.setMaxAge(cookieLife);
            resp.addCookie(cookie);
            req.getRequestDispatcher("userService.jsp").forward(req,resp);
        }
        else{
            req.setAttribute("flag",2);//flag=2登录验证失败
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        }
    }
    else if(select.equals("manager")){
        if(getServletConfig().getInitParameter(phone).equals(pwd)){
            Cookie cookie=new Cookie("root","root");
            cookie.setMaxAge(cookieLife);
            resp.addCookie(cookie);
            req.getRequestDispatcher("managerService.jsp").forward(req,resp);
        }
        else{
            req.setAttribute("flag",2);
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        }
    }

    }
}
