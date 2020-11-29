package services;

import dao.DaoBalance;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public class AddBalance {

    @WebMethod
    public long addBalance(long balanceAdded) throws Exception {
        if(balanceAdded < 0){
            throw new Exception("Balance added must be greater or equal to 0.");
        }

        DaoBalance dao = DaoBalance.getInstance();
        long currentBalance = dao.getBalance();
        boolean success = dao.updateBalance(currentBalance + balanceAdded);

        if(!success){
            throw new Exception("Failed to update balance.");
        }

        return currentBalance + balanceAdded;
    }
}