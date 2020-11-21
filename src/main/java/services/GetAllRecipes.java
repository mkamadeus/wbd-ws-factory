package services;

import dao.*;
import data.Chocolate;
import data.ChocolateRecipe;
import data.Ingredient;
import formats.*;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class GetAllRecipes {

    @Resource
    private WebServiceContext wsContext;

    private void checkSession() throws Exception{
        MessageContext mc = wsContext.getMessageContext();
        HttpSession session = ((HttpServletRequest)mc.get(MessageContext.SERVLET_REQUEST)).getSession();
        if(!AccountUtils.checkLoginSession(session)){
            throw new Exception("Not logged in yet.");
        }
    }

    private AvailableIngredientFormat getAvailableIngredients(Ingredient ingredient) throws Exception {
        DaoIngredientStock ingredientStockDao = DaoIngredientStock.getInstance();
        Date now = new Date(System.currentTimeMillis());

        int stock = ingredientStockDao.getAvailableSumByIngredientId(ingredient.getId(), now);
        return AvailableIngredientFormat.fromIngredient(
                ingredient, stock
        );
    }

    @WebMethod
    public ResponseRecipeFormat[] getAllRecipes() throws Exception {
        checkSession();
        DaoChocolate chocolateDao = DaoChocolate.getInstance();
        DaoChocolateRecipe chocolateRecipeDao = DaoChocolateRecipe.getInstance();
        DaoIngredient ingredientDao = DaoIngredient.getInstance();

        List<ResponseRecipeFormat> responseRecipeFormatList = new ArrayList<>();
        List<Chocolate> chocolateList = chocolateDao.findAll();

        for (Chocolate chocolate : chocolateList) {
            List<ChocolateRecipe> chocolateRecipeList = chocolateRecipeDao.findAllByChocolateId(chocolate.getId());
            List<IngredientAmountPair> ingredientAmountPairs = new ArrayList<>();
            for (ChocolateRecipe chocolateRecipe : chocolateRecipeList) {
                Optional<Ingredient> ingredientOptional = ingredientDao.find(String.valueOf(chocolateRecipe.getIngredientId()));

                if (!ingredientOptional.isPresent()) {
                    throw new Exception("Cannot find ingredient needed for a recipe.");
                }
                Ingredient ingredient = ingredientOptional.get();

                ingredientAmountPairs.add(
                        new IngredientAmountPair(
                                this.getAvailableIngredients(ingredient),
                                chocolateRecipe.getIngredientAmount()
                        )
                );

            }

            IngredientAmountPair[] pairArray = new IngredientAmountPair[ingredientAmountPairs.size()];
            responseRecipeFormatList.add(
                    new ResponseRecipeFormat(
                            ChocolateFormat.fromChocolate(chocolate),
                            ingredientAmountPairs.toArray(pairArray)
                    )
            );
        }

        ResponseRecipeFormat[] response = new ResponseRecipeFormat[responseRecipeFormatList.size()];
        response = responseRecipeFormatList.toArray(response);
        return response;
    }
}