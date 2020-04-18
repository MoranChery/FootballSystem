package Data;

import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.PersonalPage;
import Model.Search;
import Model.TeamPage;
import Model.UsersTypes.Fan;

import java.util.Set;

public interface FanDb {

    void addPersonalPageToFanListOfPages(String fanMail, PersonalPage personalPageToAdd) throws Exception;
    void addTeamPageToFanListOfPages(String fanMail, TeamPage teamPageToAdd) throws Exception;
    Fan getFan(String fanMail) throws NotFoundException;
    void createFan(Fan theFan) throws Exception;
    void logOut(String fanMail) throws Exception;
    void askToGetAlerts(String fanMail, AlertWay alertWay) throws Exception;
    void wantToEditPassword(String fanMail, String newPassword) throws Exception;
    void wantToEditFirstName(String fanMail, String newFirstName) throws Exception;
    void wantToEditLastName(String fanMail, String newLastName) throws Exception;
    void removeFan(Fan fan) throws Exception;
    //Set<Search> watchMySearchHistory(String fanMail) throws Exception;
    //void addSearchToMyHistory(String fanMail, Search myNewSearch) throws Exception;


}
