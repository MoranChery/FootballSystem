package Service.OutSystems;

public class ProxyTaxSystem implements ITaxSystem {

    private static ITaxSystem taxSystem = new RealTaxSystem();

    public static ITaxSystem getInstance() {
        return taxSystem;
    }

    @Override
    public double getTaxRate(double revenueAmount) {
        return taxSystem.getTaxRate(revenueAmount);
    }

    @Override
    public void connectTo(String serverHost) throws Exception {
        taxSystem.connectTo(serverHost);
        System.out.println("Tax system is connected!");
    }

    @Override
    public boolean isConnected() {
        return taxSystem.isConnected();
    }
}
