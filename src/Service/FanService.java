package Service;

import Controller.FanController;
import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.PersonalPage;

public class FanService {
    private FanController fanController;

    public FanService() {
        this.fanController = new FanController();
    }
    public void addPageToFanList(String pageId, String pageName, String fanMail) throws Exception {
        fanController.addPageToFanList(pageId, pageName, fanMail);
    }
    public void logOut(String fanMail, Status status) throws Exception{
        fanController.logOut(fanMail, status);
    }
    //public void editPersonalDetails();
    public void askToGetAlerts(String fanMail, GamesAlert alert, AlertWay alertWay) throws Exception {
        fanController.askToGetAlerts(fanMail,alert,alertWay);
    }
}
