package services;

import dao.DaoChocolate;
import data.Chocolate;
import formats.ChocolateFormat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class GetAllChocolates {

    @WebMethod
    public ChocolateFormat[] getAllChocolates() throws Exception {
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