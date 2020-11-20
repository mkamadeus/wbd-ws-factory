package services;

import dao.DaoChocolate;
import dao.DaoIngredient;
import dao.DaoIngredientStock;
import dao.DaoStockRequest;
import data.Chocolate;
import data.Ingredient;
import data.IngredientStock;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.Date;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class AddIngredientStock {

    @WebMethod
    public boolean addIngredientStock(String uuid, String name, int stock, Date expiryDate) throws Exception {
        DaoIngredient ingredientDao = DaoIngredient.getInstance();
        DaoIngredientStock ingredientStockDao = DaoIngredientStock.getInstance();

        Optional<Ingredient> optionalIngredient = ingredientDao.findByUUID(uuid);

        int ingredientId;

        if(!optionalIngredient.isPresent()){
            Ingredient ingredient = new Ingredient(-1, name, uuid);
            ingredientId = ingredientDao.save(ingredient);
            if(ingredientId<=0) {
                throw new Exception("Failed to create new Ingredient.");
            }
        }
        else{
            ingredientId = optionalIngredient.get().getId();
        }

        IngredientStock ingredientStock = new IngredientStock(
                -1, ingredientId, stock, expiryDate
        );

        return ingredientStockDao.save(ingredientStock)>0;
    }
}