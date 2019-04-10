package userHandle;

import Helper.RsaHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@WebServlet(
        urlPatterns = "/loginHandleServlet"
)
public class LoginReturnServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession();
        RsaHelper helper=new RsaHelper();
        RSAPrivateKey privateKey=helper.getPrivateKey();
        RSAPublicKey publicKey=helper.getPublicKey();
        req.setAttribute("pkExp",publicKey.getPublicExponent().toString(16));
        req.setAttribute("pkMod",publicKey.getModulus().toString(16));
        session.setAttribute("rsa",helper);
        req.getRequestDispatcher("loginPage").forward(req,resp);
    }
}
