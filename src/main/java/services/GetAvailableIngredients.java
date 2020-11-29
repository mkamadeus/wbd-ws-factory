package services;

import dao.DaoChocolate;
import dao.DaoIngredient;
import dao.DaoIngredientStock;
import data.Chocolate;
import data.Ingredient;
import data.IngredientStock;
import formats.AvailableIngredientFormat;
import formats.ChocolateFormat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.sql.Date;
import java.util.List;

@WebService
@SOAPBinding(style = Style.RPC)
public class GetAvailableIngredients {


    @WebMethod
    public AvailableIngredientFormat[] getAvailableIngredients() throws Exception {
        DaoIngredient ingredientDao = DaoIngredient.getInstance();
        DaoIngredientStock ingredientStockDao = DaoIngredientStock.getInstance();

        List<Ingredient> ingredientList = ingredientDao.findAll();
        AvailableIngredientFormat[] response = new AvailableIngredientFormat[ingredientList.size()];

        Date now = new Date(System.currentTimeMillis());

        for(int i=0; i<ingredientList.size(); i++){
            Ingredient item = ingredientList.get(i);
            int stock = ingredientStockDao.getAvailableSumByIngredientId(item.getId(), now);
            AvailableIngredientFormat availableIngredientFormat = AvailableIngredientFormat.fromIngredient(
                    ingredientList.get(i), stock
            );
            response[i] = availableIngredientFormat;
        }
        return response;
    }
}