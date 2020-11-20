package services;

import dao.DaoChocolate;
import dao.DaoIngredient;
import data.Chocolate;
import data.Ingredient;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.List;

@WebService
@SOAPBinding(style = Style.RPC)
public class GetAllIngredients {


    @WebMethod
    public Ingredient[] getAllIngredients() throws Exception {
        DaoIngredient ingredientDao = DaoIngredient.getInstance();
        List<Ingredient> ingredientList = ingredientDao.findAll();
        Ingredient[] ingredients = new Ingredient[ingredientList.size()];
        ingredients = ingredientList.toArray(ingredients);
        return ingredients;
    }
}