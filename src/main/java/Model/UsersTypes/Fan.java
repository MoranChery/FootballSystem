package Model.UsersTypes;

import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Search;

import java.util.HashSet;
import java.util.Set;

public class Fan extends Subscriber {


    private Set<String> myPages;
    private Set<Search> mySearchHistory;
    protected GamesAlert gamesAlert;
    private AlertWay alertWay = null;


    public Fan(String emailAddress,String password, Integer id, String firstName, String lastName) {
        super();
        setRegisteringDetails(emailAddress, password, id, firstName, lastName);
        myPages = new HashSet<>();
        mySearchHistory = new HashSet<>();
        gamesAlert = GamesAlert.ALERTS_OFF;
    }

    // getters & setters
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
    public Set<String> getMyPages() {
        return myPages;
    }

    public void setMyPages(Set<String> myPages) {
        this.myPages = myPages;
    }
}
