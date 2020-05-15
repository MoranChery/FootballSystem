package Service;

import Controller.FanController;
import Model.Enums.AlertWay;
import Model.UsersTypes.Fan;

public class FanService {
    private FanController fanController;

    public FanService() {
        this.fanController = new FanController();
    }

    public Fan getFan(String fanMail) throws Exception{
        return fanController.getFan(fanMail);
    }

    public void createFan(Fan fan) throws Exception {
        fanController.createFan(fan);
    }
    public void askToGetAlerts(String fanMail, AlertWay alertWay) throws Exception {
        fanController.askToGetAlerts(fanMail,alertWay);
    }
    public void addPageToFanListOfPages(String fanMail, String pageID) throws Exception{
        fanController.addPageToFanListOfPages(fanMail, pageID);
    }
}
