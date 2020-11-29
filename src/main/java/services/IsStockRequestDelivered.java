package services;

import dao.DaoChocolate;
import dao.DaoStockRequest;
import data.Chocolate;
import data.StockRequest;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class IsStockRequestDelivered {

    @WebMethod
    public boolean isStockRequestDelivered(String trackingId) throws Exception {
        DaoStockRequest stockRequestDao = DaoStockRequest.getInstance();
        Optional<StockRequest> optionalStockRequest = stockRequestDao.findByTrackingId(trackingId);

        if(!optionalStockRequest.isPresent()){
            throw new Exception("Chocolate stock request with tracking_id=" + trackingId + " doesn't exist.");
        }

        StockRequest stockRequest = optionalStockRequest.get();
        return stockRequest.isDelivered();
    }
}