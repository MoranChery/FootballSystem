package Data;

import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.PersonalPage;
import Model.UsersTypes.Fan;

public interface FanDb {

    void addPageToFanList(String fanMail, PersonalPage pageToAdd) throws Exception;
    Fan getFan(String fanMail) throws NotFoundException;
    void createFan(Fan theFan) throws Exception;
    void logOut(String fanMail, Status status) throws Exception;
    void askToGetAlerts(String fanMail, GamesAlert alert, AlertWay alertWay) throws Exception;
    void wantToEditMail(String fanMail, String newMailAddress) throws Exception;
    void wantToEditPassword(String fanMail, String newPassword) throws Exception;
    void wantToEditID(String fanMail, Integer newID) throws Exception;
    void wantToEditFirstName(String fanMail, String newFirstName) throws Exception;
    void wantToEditLastName(String fanMail, String newLastName) throws Exception;



    //void editPersonalDetails(String fanMail,String password, Integer id, String firstName, String lastName) throws Exception;
    //void addComplaint

}
