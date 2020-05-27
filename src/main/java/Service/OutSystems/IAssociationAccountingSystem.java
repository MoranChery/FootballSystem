package Service.OutSystems;

public interface IAssociationAccountingSystem {

    boolean addPayment(String teamName, String date, double amount);
    void connectTo(String serverhost) throws Exception;
    boolean isConnected();
}
