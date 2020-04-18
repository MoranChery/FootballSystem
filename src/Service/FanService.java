package Service;

import Controller.FanController;
import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.PersonalPage;
import Model.UsersTypes.Fan;

public class FanService {
    private FanController fanController;

    public FanService() {
        this.fanController = new FanController();
    }
    public void logOut(String fanMail) throws Exception{
        fanController.logOut(fanMail);
    }
    public void wantToEditPassword(String fanMail, String newPassword) throws Exception {
        fanController.wantToEditPassword(fanMail, newPassword);
    }
    public void wantToEditFirstName(String fanMail, String newFirstName) throws Exception {
        fanController.wantToEditFirstName(fanMail, newFirstName);
    }
    public void wantToEditLastName(String fanMail, String newLastName) throws Exception {
        fanController.wantToEditLastName(fanMail, newLastName);
    }

    public void askToGetAlerts(String fanMail, AlertWay alertWay) throws Exception {
        fanController.askToGetAlerts(fanMail,alertWay);
    }
    public void addPageToFanListOfPages(String fanMail, String pageID) throws Exception{
        fanController.addPageToFanListOfPages(fanMail, pageID);
    }

//    public void logOut(String fanMail, Status status) throws Exception{
//        fanController.logOut(fanMail, status);
//    }
//    public void askToGetAlerts(String fanMail, GamesAlert alert, AlertWay alertWay) throws Exception {
//        fanController.askToGetAlerts(fanMail,alert,alertWay);
//    }


//    public void editPersonalDetails(String fanMail,String password, Integer id, String firstName, String lastName) throws Exception {
//        fanController.editPersonalDetails(fanMail, password, id, firstName, lastName);
//    }
}
