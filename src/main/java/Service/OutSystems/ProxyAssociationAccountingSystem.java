package Service.OutSystems;
import java.util.ArrayList;
import java.util.List;


public class ProxyAssociationAccountingSystem implements IAssociationAccountingSystem{
        private IAssociationAccountingSystem associationAccountingSystem = new RealAssociationAccountingSystem();


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
