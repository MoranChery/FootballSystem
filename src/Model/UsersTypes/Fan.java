package Model.UsersTypes;

import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.PersonalPage;
import Model.Search;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Fan extends Subscriber {

    private HashMap<String, PersonalPage> myPages;
    private Set<Search> mySearchHistory;
    protected GamesAlert gamesAlert = GamesAlert.ALERTS_OFF;
    private AlertWay alertWay = null;


    public Fan(String emailAddress,String password, Integer id, String firstName, String lastName) {
        setRegisteringDetails(emailAddress, password, id, firstName, lastName);
        myPages = new HashMap<>();
        mySearchHistory = new HashSet<>();
    }

    // getters & setters
    public HashMap<String, PersonalPage> getMyPages() {
        return myPages;
    }
    public void setMyPages(HashMap<String, PersonalPage> myPages) {
        this.myPages = myPages;
    }
    public GamesAlert getGamesAlert() {
        return gamesAlert;
    }
    public void setGamesAlert(GamesAlert gamesAlert) {
        this.gamesAlert = gamesAlert;
    }
    public AlertWay getAlertWay() {
        return alertWay;
    }
    public void setAlertWay(AlertWay alertWay) {
        this.alertWay = alertWay;
    }
    public Set<Search> getMySearchHistory() {
        return mySearchHistory;
    }
    public void setMySearchHistory(Set<Search> mySearchHistory) {
        this.mySearchHistory = mySearchHistory;
    }
}
