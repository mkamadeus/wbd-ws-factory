package services;

import dao.DaoAccount;
import dao.DaoBalance;
import dao.DaoIngredientStock;
import data.Account;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.lang.reflect.AccessibleObject;

@WebService
@SOAPBinding(style = Style.RPC)
public class Register {

    @Resource
    private WebServiceContext wsContext;

    @WebMethod
    public boolean register(String username, String password) throws Exception {
        MessageContext mc = wsContext.getMessageContext();
        HttpSession session = ((HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST)).getSession();

        DaoAccount accountDao = DaoAccount.getInstance();
        Account account = new Account(-1, username, password, true);

        if(accountDao.isExistUsername(username)){
            throw new Exception("Account already exists.");
        }

        boolean success = accountDao.save(account) >= 0;
        if(success){
            AccountUtils.setLoginSession(session);
        }
        return success;
    }
}