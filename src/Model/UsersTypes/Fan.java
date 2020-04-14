package Model.UsersTypes;

import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.PersonalPage;


import java.util.HashMap;

public class Fan extends Subscriber {

    private HashMap<String, PersonalPage> myPages;
    protected GamesAlert gamesAlert = GamesAlert.ALERTS_OFF;
    private AlertWay alertWay = null;


    public Fan(String emailAddress,String password, Integer id, String firstName, String lastName) {
        setRegisteringDetails(emailAddress, password, id, firstName, lastName);
        myPages = new HashMap<>();
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

}
