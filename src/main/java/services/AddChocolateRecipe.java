package services;

import dao.DaoChocolate;
import dao.DaoChocolateRecipe;
import dao.DaoIngredient;
import data.Chocolate;
import data.ChocolateRecipe;
import data.Ingredient;
import formats.RequestRecipeFormat;
import formats.RequestRecipeIngredientFormat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class AddChocolateRecipe {

    private int addOrFindIngredient(String name, String uuid) throws Exception {
        DaoIngredient ingredientDao = DaoIngredient.getInstance();
        Optional<Ingredient> optionalIngredient = ingredientDao.findByUUID(uuid);

        if(optionalIngredient.isPresent()){
            return optionalIngredient.get().getId();
        }

        Ingredient ingredient = new Ingredient(-1, name, uuid);
        return DaoIngredient.getInstance().save(ingredient);
    }

    @WebMethod
    public String addChocolateRecipe(RequestRecipeFormat requestRecipeFormat) throws Exception {
        DaoChocolate chocolateDao = DaoChocolate.getInstance();
        DaoChocolateRecipe chocolateRecipeDao = DaoChocolateRecipe.getInstance();

        Chocolate chocolate = new Chocolate(
                -1,
                "",
                requestRecipeFormat.getName(),
                0,
                requestRecipeFormat.getPrice()
        );

        chocolate.generateUUID();
        int chocolateId = chocolateDao.save(chocolate);

        RequestRecipeIngredientFormat[] ingredients = requestRecipeFormat.getIngredients();

        for (RequestRecipeIngredientFormat ingredient : ingredients) {
            int ingredientId = addOrFindIngredient(ingredient.getName(), ingredient.getUuid());
            int amountNeeded = ingredient.getAmountNeeded();
            chocolateRecipeDao.save(new ChocolateRecipe(-1, chocolateId, ingredientId, amountNeeded));
        }

        return chocolate.getUUID();
    }
}