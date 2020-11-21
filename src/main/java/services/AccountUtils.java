package services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.handler.MessageContext;

public class AccountUtils {
    public static void setLoginSession(HttpSession session){
        session.setAttribute("login", true);
    }

    public static boolean checkLoginSession(HttpSession session){
        Boolean login = (Boolean)session.getAttribute("login");
        if (login == null) {
            login = false;
            session.setAttribute("login", false);
        }
        return login;
    }

    public static void clearLoginSession(HttpSession session){
        session.setAttribute("login", false);
    }

}
