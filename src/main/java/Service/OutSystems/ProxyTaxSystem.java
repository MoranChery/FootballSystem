package Service.OutSystems;

import Model.LoggerHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyTaxSystem implements ITaxSystem {

    private Logger logger = Logger.getLogger("TaxSystem");

    private static ITaxSystem taxProxySystem = new ProxyTaxSystem();

    private ITaxSystem taxSystem;

    public ProxyTaxSystem() {
        logger.addHandler(LoggerHandler.loggerErrorFileHandler);
        logger.addHandler(LoggerHandler.loggerEventFileHandler);
        this.taxSystem = new RealTaxSystem();

    }

    public static ITaxSystem getInstance() {
        return taxProxySystem;
    }


    @Override
    public double getTaxRate(double revenueAmount) {
        return taxSystem.getTaxRate(revenueAmount);
    }

    @Override
    public void connectTo(String serverHost) throws Exception {
        try {
            taxSystem.connectTo(serverHost);
            logger.log(Level.INFO, "Tax system is connected!");
        }catch (Exception e){
            logger.log(Level.WARNING, "Tax system is NOT connected!");

        }
    }

    @Override
    public boolean isConnected() {
        return taxSystem.isConnected();
    }
}
