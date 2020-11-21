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

@WebService
@SOAPBinding(style = Style.RPC)
public class Login {

    @Resource
    private WebServiceContext wsContext;

    @WebMethod
    public boolean login(String username, String password) throws Exception {
        MessageContext mc = wsContext.getMessageContext();
        HttpSession session = ((HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST)).getSession();

        if(AccountUtils.checkLoginSession(session)){
            throw new Exception("Already logged in.");
        }

        DaoAccount accountDao = DaoAccount.getInstance();
        Account account = new Account(-1, username, password, true);
        boolean found = accountDao.isExistUsernamePassword(account.getUsername(), account.getPassword());

        if(!found){
            throw new Exception("Account doesn't exist.");
        }

        AccountUtils.setLoginSession(session);
        return true;
    }
}