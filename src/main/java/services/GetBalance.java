package services;

import dao.DaoBalance;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public class GetBalance {

    @WebMethod
    public long getBalance() throws Exception {
        DaoBalance dao = DaoBalance.getInstance();
        return dao.getBalance();
    }
}