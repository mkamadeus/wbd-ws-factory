package services;

import dao.DaoChocolate;
import dao.DaoStockRequest;
import data.Chocolate;
import data.StockRequest;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class DeliverStockRequest {

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
    public boolean deliverStockRequest(String uuid) throws Exception {
        checkSession();

        DaoStockRequest stockRequestDao = DaoStockRequest.getInstance();
        DaoChocolate chocolateDao = DaoChocolate.getInstance();

        Optional<StockRequest> optionalStockRequest = stockRequestDao.findByTrackingId(uuid);

        if(!optionalStockRequest.isPresent()) {
            throw new Exception("Chocolate stock request with trackingId=" + uuid + " doesn't exist.");
        }

        StockRequest stockRequest = optionalStockRequest.get();

        if(stockRequest.isDelivered()){
            throw new Exception("Stock request already delivered.");
        }

        Optional<Chocolate> optionalChocolate = chocolateDao.find(
                String.valueOf(stockRequest.getChocolateId())
        );

        if(!optionalChocolate.isPresent()){
            throw new Exception("Chocolate in request doesn't exist.");
        }

        Chocolate chocolate = optionalChocolate.get();

        int remainingStock = chocolate.getStock()-stockRequest.getAmount();

        if(remainingStock < 0){
            throw new Exception("Not enough chocolate stock, request not delivered.");
        }

        stockRequest.setDelivered(true);
        stockRequest.setUpdatedAt(new Date());
        chocolate.setStock(remainingStock);

        return stockRequestDao.update(stockRequest) && chocolateDao.update(chocolate);
    }
}