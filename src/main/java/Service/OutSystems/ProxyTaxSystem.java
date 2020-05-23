package Service.OutSystems;

import java.util.ArrayList;
import java.util.List;

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
    public void connectTo(String serverhost) throws Exception
    {
        taxSystem.connectTo(serverhost);
        System.out.println("Tax system is connected!");
    }
}
