package userHandle;

import Helper.RsaHelper;
import Helper.cookieConverter;
import commonObject.User;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserLogIn extends HttpServlet {
    private UserDao userDao=new UserDao();
    private final int cookieLife=30*60;//second;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone =req.getParameter("phone");
        String pwd=req.getParameter("password");
        HttpSession session_temp=req.getSession();
        RsaHelper helper=(RsaHelper) session_temp.getAttribute("rsa");
        pwd=helper.decodeRsaPasswordForJS(pwd);
        String select=req.getParameter("type");
    if(select.equals("user")){
        User user=userDao.findUser(phone,pwd);
        if(user!=null){
            HttpSession session=req.getSession();
            cookieConverter converter=new cookieConverter();
            session.setAttribute("cookieConvert",converter);
            Cookie cookie=new Cookie("cardId",converter.encode(user.getCardId()));
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
