package Service.OutSystems;

public class RealAssociationAccountingSystem implements IAssociationAccountingSystem {
    private String serverHost;

    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        if(teamName==null|| date==null)
            return false;
        return true;
    }

    @Override
    public void connectTo(String serverHost)
    {
        this.serverHost=serverHost;
        System.out.println("Connecting to "+ serverHost);
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
