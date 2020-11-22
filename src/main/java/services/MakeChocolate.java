package services;

import dao.*;
import data.Chocolate;
import data.ChocolateRecipe;
import data.IngredientStock;
import formats.ChocolateFormat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class MakeChocolate {

    private boolean isEnough(int ingredientId, int needed) throws Exception {
        Date now = new Date(System.currentTimeMillis());
        DaoIngredientStock ingredientStockDao = DaoIngredientStock.getInstance();
        int available = ingredientStockDao.getAvailableSumByIngredientId(ingredientId, now);
        return available >= needed;
    }

    private void decIngredientStock(int ingredientId, int decrement) throws Exception {
        Date now = new Date(System.currentTimeMillis());
        DaoIngredientStock ingredientStockDao = DaoIngredientStock.getInstance();
        List<IngredientStock> ingredientStockList = ingredientStockDao.getAvailableByIngredientId(ingredientId, now);

        for (IngredientStock stock : ingredientStockList) {
            if(decrement > 0){
                int currentDecrement = Math.min(decrement, stock.getStock());
                IngredientStock updated = new IngredientStock(
                        stock.getId(), stock.getIngredientId(), stock.getStock()-currentDecrement, stock.getExpiryDate()
                );
                decrement -= currentDecrement;
                boolean success = ingredientStockDao.update(updated);
                if(!success){
                    throw new Exception("Failed to decrement ingredient stock.");
                }
            }
        }
    }

    @WebMethod
    public boolean makeChocolate(String uuid, int amount) throws Exception {
        DaoChocolate chocolateDao = DaoChocolate.getInstance();
        DaoChocolateRecipe chocolateRecipeDao = DaoChocolateRecipe.getInstance();

        Optional<Chocolate> optionalChocolate = chocolateDao.findByUUID(uuid);

        if(!optionalChocolate.isPresent()){
            throw new Exception("No chocolate with uuid=" + uuid + ".");
        }

        Chocolate chocolate = optionalChocolate.get();

        List<ChocolateRecipe> recipeList = chocolateRecipeDao.findAllByChocolateId(chocolate.getId());


        boolean canBeMade = true;

        for (ChocolateRecipe recipe : recipeList) {
            boolean enough = isEnough(recipe.getIngredientId(), recipe.getIngredientAmount() * amount);
            if(!enough){
                canBeMade = false;
            }
        }

        if(!canBeMade){
            throw new Exception("Not enough ingredients to make chocolate.");
        }


        for (ChocolateRecipe recipe : recipeList) {
            decIngredientStock(recipe.getIngredientId(), recipe.getIngredientAmount() * amount);
        }

        Chocolate updatedChocolate = new Chocolate(
                chocolate.getId(),
                chocolate.getUUID(),
                chocolate.getName(),
                chocolate.getStock()+amount
        );


        return chocolateDao.update(updatedChocolate);
    }
}