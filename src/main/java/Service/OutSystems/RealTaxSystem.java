package Service.OutSystems;

public class RealTaxSystem implements ITaxSystem {

    @Override
    public double getTaxRate(double revenueAmount) {
        System.out.println("return getTaxRate");
        return 0;
    }

    @Override
    public void connectTo(String serverhost)
    {
        System.out.println("Connecting to "+ serverhost);
    }
}
