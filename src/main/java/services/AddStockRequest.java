package services;

import dao.DaoBalance;
import dao.DaoChocolate;
import dao.DaoStockRequest;
import data.Chocolate;
import data.Ingredient;
import data.StockRequest;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class AddStockRequest {

    @WebMethod
    public String addStockRequest(String chocolateUUID, int amount) throws Exception {
        DaoChocolate chocoDao = DaoChocolate.getInstance();
        Optional<Chocolate> optionalChocolate = chocoDao.findByUUID(chocolateUUID);

        if(!optionalChocolate.isPresent()){
            throw new Exception("Chocolate with uuid=" + chocolateUUID + " doesn't exist.");
        }

        int chocolateId = optionalChocolate.get().getId();

        DaoStockRequest stockRequestDao = DaoStockRequest.getInstance();
        Date date = new Date();

        StockRequest stockRequest = new StockRequest(
                0,
                chocolateId,
                "",
                amount,
                false,
                new Timestamp(date.getTime()),
                new Timestamp(date.getTime())
        );
        stockRequest.generateTrackingId();
        stockRequestDao.save(stockRequest);

        return stockRequest.getTrackingId();
    }
}