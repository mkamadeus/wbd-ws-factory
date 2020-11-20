package services;

import dao.DaoChocolate;
import data.Chocolate;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.util.Optional;

@WebService
@SOAPBinding(style = Style.RPC)
public class AddChocoRecipe {

//    private boolean addChoco(String chocoName, ) throws SQLException {
//        DaoChocolate.getInstance().save(
//                new Chocolate(0, chocoName, 0, 0)
//        );
//        return true;
//    }

//    NANTI GANTI JADI BY ID BUKAN BY NAME

//    @WebMethod
//    public boolean addChocoRecipe(String chocoName, int recipeCost) throws Exception {
//        DaoChocolate chocoDao = DaoChocolate.getInstance();
//        Optional<Chocolate> optionalChocolate = chocoDao.findByName(chocoName);
//
//        if(optionalChocolate.isPresent()){
//            System.out.println(optionalChocolate.get().getId());
//            throw new Exception("Chocolate " + chocoName + " already exists.");
//        }
//
//        boolean success = chocoDao.save(
//                new Chocolate(0, uuid, chocoName, 0, recipeCost)
//        );
//
////        add juga recipenya
//
//        return success;
//    }
}