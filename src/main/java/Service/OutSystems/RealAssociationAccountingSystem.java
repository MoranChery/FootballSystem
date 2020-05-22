package Service.OutSystems;

public class RealAssociationAccountingSystem implements IAssociationAccountingSystem {
    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        if(teamName==null|| date==null)
            return false;
        return true;
    }

    @Override
    public void connectTo(String serverhost)
    {
        System.out.println("Connecting to "+ serverhost);
    }
}
