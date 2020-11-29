package services;

import dao.DaoChocolate;
import data.Chocolate;
import formats.ChocolateFormat;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class GetAllChocolates {

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
    public ChocolateFormat[] getAllChocolates() throws Exception {
        checkSession();
        DaoChocolate chocoDao = DaoChocolate.getInstance();
        List<Chocolate> chocolateList = chocoDao.findAll();
        ChocolateFormat[] response = new ChocolateFormat[chocolateList.size()];
        for(int i=0; i<chocolateList.size(); i++){
            ChocolateFormat chocolateFormat = ChocolateFormat.fromChocolate(chocolateList.get(i));
            response[i] = chocolateFormat;
        }
        return response;
    }
}