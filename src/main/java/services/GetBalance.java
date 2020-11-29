package services;

import dao.DaoBalance;

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
public class GetBalance {

    @Resource
    private WebServiceContext wsContext;

    private void checkSession() throws Exception{
        MessageContext mc = wsContext.getMessageContext();
        HttpSession session = ((HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST)).getSession();
        if(!AccountUtils.checkLoginSession(session)){
            throw new Exception("Not logged in yet.");
        }
    }

    @WebMethod
    public long getBalance() throws Exception {
        checkSession();
        DaoBalance dao = DaoBalance.getInstance();
        return dao.getBalance();
    }
}