package Service.OutSystems;
import Data.CoachDbInMemory;

import java.util.ArrayList;
import java.util.List;


public class ProxyAssociationAccountingSystem implements IAssociationAccountingSystem{
    private static IAssociationAccountingSystem associationAccountingSystem = new RealAssociationAccountingSystem();

    public static IAssociationAccountingSystem getInstance() {
        return associationAccountingSystem;
    }

    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        if(teamName==null||date==null)
            return false;
        return associationAccountingSystem.addPayment(teamName,date,amount);
    }

    @Override
        public void connectTo(String serverhost) throws Exception
        {
            associationAccountingSystem.connectTo(serverhost);
            System.out.println("Association Accounting System is connected!");
        }

}
