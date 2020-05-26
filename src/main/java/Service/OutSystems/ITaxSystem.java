package Service.OutSystems;

public interface ITaxSystem {
        double getTaxRate(double revenueAmount);
        void connectTo(String serverhost) throws Exception;
        boolean isConnected();

}
