package services;

import dao.*;
import data.Chocolate;
import data.ChocolateRecipe;
import data.Ingredient;
import data.StockRequest;
import formats.*;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class GetAllRecipes {

    private AvailableIngredientFormat getAvailableIngredients(Ingredient ingredient) throws Exception {
        DaoIngredientStock ingredientStockDao = DaoIngredientStock.getInstance();
        Date now = new Date(System.currentTimeMillis());

        int stock = ingredientStockDao.getAvailableSumByIngredientId(ingredient.getId(), now);
        return AvailableIngredientFormat.fromIngredient(
                ingredient, stock
        );
    }

    @WebMethod
    public RecipeFormat[] getAllRecipes() throws Exception {

        DaoChocolate chocolateDao = DaoChocolate.getInstance();
        DaoChocolateRecipe chocolateRecipeDao = DaoChocolateRecipe.getInstance();
        DaoIngredient ingredientDao = DaoIngredient.getInstance();

        List<RecipeFormat> recipeFormatList = new ArrayList<>();
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
            recipeFormatList.add(
                    new RecipeFormat(
                            ChocolateFormat.fromChocolate(chocolate),
                            ingredientAmountPairs.toArray(pairArray)
                    )
            );
        }

        RecipeFormat[] response = new RecipeFormat[recipeFormatList.size()];
        response = recipeFormatList.toArray(response);
        return response;
    }
}