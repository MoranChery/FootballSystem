package Data;

import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.PersonalPage;
import Model.Search;
import Model.UsersTypes.Fan;

import java.util.Set;

public interface FanDb {

    void addPageToFanList(String fanMail, PersonalPage pageToAdd) throws Exception;
    Fan getFan(String fanMail) throws NotFoundException;
    void createFan(Fan theFan) throws Exception;
    void logOut(String fanMail, Status status) throws Exception;
    void askToGetAlerts(String fanMail, GamesAlert alert, AlertWay alertWay) throws Exception;
    void wantToEditPassword(String fanMail, String newPassword) throws Exception;
    void wantToEditFirstName(String fanMail, String newFirstName) throws Exception;
    void wantToEditLastName(String fanMail, String newLastName) throws Exception;
    Set<Search> watchMySearchHistory(String fanMail) throws Exception;
    void addSearchToMyHistory(String fanMail, Search myNewSearch) throws Exception;



    //void editPersonalDetails(String fanMail,String password, Integer id, String firstName, String lastName) throws Exception;
    //void addComplaint

}
