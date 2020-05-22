package Service;

public interface IProxyExternalSystems {

    boolean addPayment(String teamName, String date, double amount);

    double getTaxRate(double revenueAmount);
}
