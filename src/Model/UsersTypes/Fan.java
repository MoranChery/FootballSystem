package Model.UsersTypes;

import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.PersonalPage;
import Model.Search;
import Model.TeamPage;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Fan extends Subscriber {

    private HashMap<String, PersonalPage> myPersonalPageFollowList;
    private Map<String, TeamPage> myTeamPageFollowList;
    private Set<Search> mySearchHistory;
    protected GamesAlert gamesAlert = GamesAlert.ALERTS_OFF;
    private AlertWay alertWay = null;


    public Fan(String emailAddress,String password, Integer id, String firstName, String lastName) {
        setRegisteringDetails(emailAddress, password, id, firstName, lastName);
        myPersonalPageFollowList = new HashMap<>();
        mySearchHistory = new HashSet<>();
    }

    // getters & setters
    public HashMap<String, PersonalPage> getMyPersonalPageFollowList() {
        return myPersonalPageFollowList;
    }
    public void setMyPersonalPageFollowList(HashMap<String, PersonalPage> myPersonalPageFollowList) {
        this.myPersonalPageFollowList = myPersonalPageFollowList;
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

    public Map<String, TeamPage> getMyTeamPageFollowList() {
        return myTeamPageFollowList;
    }

    public void setMyTeamPageFollowList(Map<String, TeamPage> myTeamPageFollowList) {
        this.myTeamPageFollowList = myTeamPageFollowList;
    }
}
