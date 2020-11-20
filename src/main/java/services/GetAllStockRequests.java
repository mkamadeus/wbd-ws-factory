package services;

import dao.DaoChocolate;
import dao.DaoStockRequest;
import data.Chocolate;
import data.StockRequest;
import formats.ChocolateFormat;
import formats.StockRequestFormat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class GetAllStockRequests {

    @WebMethod
    public StockRequestFormat[] getAllStockRequests() throws Exception {
        DaoStockRequest stockRequestDao = DaoStockRequest.getInstance();
        DaoChocolate chocolateDao = DaoChocolate.getInstance();
        List<StockRequest> stockRequestList = stockRequestDao.findAll();
        StockRequestFormat[] response = new StockRequestFormat[stockRequestList.size()];
        for(int i=0; i<stockRequestList.size(); i++){
            Optional<Chocolate> optionalChocolate = chocolateDao.find(
                    String.valueOf(stockRequestList.get(i).getChocolateId())
            );
            if(!optionalChocolate.isPresent()){
                throw new Exception("Chocolate in request doesn't exist.");
            }
            StockRequestFormat stockRequestFormat = StockRequestFormat.fromStockRequest(
                    stockRequestList.get(i), optionalChocolate.get()
            );
            response[i] = stockRequestFormat;
        }
        return response;
    }
}