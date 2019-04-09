package managerHandle;

import commonObject.User;
import dao.UserDao;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpRequest;
import java.util.*;

class myFilter implements Filter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Map<String,String> map=new HashMap<>();
        Enumeration<String> e=servletRequest.getParameterNames();

        Iterator<String> it=e.asIterator();
        while (it.hasNext()){
            String p=it.next();
            map.put(p,servletRequest.getParameter(p));
        }
        System.out.println(map);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
}
public class ManagerServiceServlet extends HttpServlet {
    UserDao userDao=new UserDao();
    private boolean checkRoot(HttpServletRequest request){
        String rootPwd=getServletConfig().getInitParameter("root");

        boolean b=false;
        for(Cookie cookie:request.getCookies()){
            if(cookie.getName().equals("root")&&cookie.getValue().equals("root")){
                b=true;
                break;
            }
        }
        return b;
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        myFilter f=new myFilter();
        f.doFilter(req,resp,null);
        if(!checkRoot(req)){
            req.setAttribute("flag",20);//flag=20根用户验证失败
            req.getRequestDispatcher("result.jsp").forward(req,resp);
            return;
        }
        String phone=req.getParameter("phone");
        if(userDao.deleteUser(phone)){
            req.setAttribute("flag",11);//flag=11,删除用户成功
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        }
        else{
            req.setAttribute("flag",12);//flag=12用户注销失败！
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!checkRoot(req)){
            req.setAttribute("flag",20);//flag=20根用户验证失败
            req.getRequestDispatcher("result.jsp").forward(req,resp);
            return;
        }
        String symbol=req.getParameter("symbol");
        String phone=req.getParameter("phone");
        if(symbol.equals("update")){
            boolean result=userDao.updateUser(phone,req.getParameter("username"),req.getParameter("password"),req.getParameter("email"));
            if(result){
                req.setAttribute("flag",13);//flag=13修改成功
            }
            else{
                req.setAttribute("flag",14);//flag=14修改失败
            }
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        }
        else if(symbol.equals("money")){
            BigDecimal changedNum=BigDecimal.valueOf(Double.parseDouble(req.getParameter("changednum")));
            String type=req.getParameter("type");
            if(type.equals("sub"))
                changedNum.subtract(new BigDecimal(1));
            boolean r=userDao.changeBalance(phone,changedNum);
            if(r){
                req.setAttribute("flag",15);//flag=15,充值/扣款成功
            }
            else{
                req.setAttribute("flag",16);//flag=16,充值/扣款失败
            }
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        }
    }
}
