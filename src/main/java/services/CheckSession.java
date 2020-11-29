package services;

import dao.DaoAccount;
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
public class CheckSession {

    @Resource
    private WebServiceContext wsContext;

    @WebMethod
    public boolean checkSession() throws Exception {
        MessageContext mc = wsContext.getMessageContext();
        HttpSession session = ((HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST)).getSession();

        return AccountUtils.checkLoginSession(session);
    }
}