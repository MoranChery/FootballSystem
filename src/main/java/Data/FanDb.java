package Data;

import Model.Enums.AlertWay;
import Model.UsersTypes.Fan;

public interface FanDb extends Db{

    Fan getFan(String fanMail) throws Exception;
    void createFan(Fan theFan) throws Exception;
    void askToGetAlerts(String fanMail, AlertWay alertWay) throws Exception;
    void removeFan(Fan fan) throws Exception;
    void addPageToFanListOfPages(String fanMail, String pageID) throws Exception;

    //Set<Search> watchMySearchHistory(String fanMail) throws Exception;
    //void addSearchToMyHistory(String fanMail, Search myNewSearch) throws Exception;


}
