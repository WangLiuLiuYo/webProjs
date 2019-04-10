package Filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.awt.geom.Arc2D;
import java.io.IOException;
@WebFilter(
        filterName = "moneyValueFilter",
        urlPatterns = {"/userservice.do"}
)
public class MoneyValueFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        String money=request.getParameter("changednum");
        boolean flag=true;
        try{
            Double d=Double.parseDouble(money);
        }
        catch (Exception e){
            flag=false;
        }
        if(flag){
            filterChain.doFilter(servletRequest,servletResponse);
        }
        else{
            request.getRequestDispatcher("error.jsp").forward(servletRequest, servletResponse);
        }
    }
}
