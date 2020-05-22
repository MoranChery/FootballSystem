package Service;

public class ProxyExternalSystems implements IProxyExternalSystems{

    @Override
    public boolean addPayment(String teamName, String date, double amount) {
        return false;
    }

    @Override
    public double getTaxRate(double revenueAmount) {
        return 0;
    }
}
