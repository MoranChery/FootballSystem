package Service.OutSystems;

public class RealTaxSystem implements ITaxSystem {
    private String serverHost;
    @Override
    public double getTaxRate(double revenueAmount) {
        System.out.println("return getTaxRate");
        return 0;
    }

    @Override
    public void connectTo(String serverhost)
    {
        this.serverHost=serverHost;
        System.out.println("Connecting to "+ serverhost);
    }

    @Override
    public boolean isConnected() {
//        return !serverHost.equals("");
        return true;
    }
}
