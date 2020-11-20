package services;

import dao.DaoChocolate;
import data.Chocolate;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;

@WebService
@SOAPBinding(style = Style.RPC)
public class GetAllChocolateRecipes {


    @WebMethod
    public Chocolate[] getAllChocolates() throws Exception {
        DaoChocolate chocoDao = DaoChocolate.getInstance();
        List<Chocolate> chocolateList = chocoDao.findAll();
        Chocolate[] chocolates = new Chocolate[chocolateList.size()];
        chocolates = chocolateList.toArray(chocolates);
        return chocolates;
    }
}