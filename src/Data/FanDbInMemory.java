package Data;

import Model.Enums.AlertWay;
import Model.PersonalPage;
import Model.Search;
import Model.TeamPage;
import Model.UsersTypes.Fan;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static Model.Enums.GamesAlert.ALERTS_ON;
import static Model.Enums.Status.OFFLINE;

public class FanDbInMemory implements FanDb{

    private Map<String, Fan> allFans;
    private static FanDbInMemory ourInstance = new FanDbInMemory();
    public FanDbInMemory() {
        this.allFans = new HashMap<>();
    }
    public static FanDbInMemory getInstance() {
        return ourInstance;
    }

    @Override
    public Fan getFan(String fanMail) throws NotFoundException {
        Fan fan = allFans.get(fanMail);
        if(fan == null){
            throw new NotFoundException("Fan not found");
        }
        return fan;
    }

    @Override
    public void createFan(Fan theFan) throws Exception {
        if(theFan == null){
            throw new Exception("Something went wrong with creating this fan");
        }
        String fanMail = theFan.getEmailAddress();
        if(allFans.containsKey(fanMail)){
            throw new Exception("Fan already exists");
        }
        allFans.put(fanMail, theFan);
    }

    @Override
    public void askToGetAlerts(String fanMail, AlertWay alertWay) throws Exception {
        if(fanMail == null || alertWay == null){
            throw new NullPointerException("One or more of the inputs is wrong");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new Exception("Fan not found");
        }
        if(theFan.getGamesAlert().equals(ALERTS_ON)){
            throw new Exception("You are already list to get alerts about games");
        }
        theFan.setGamesAlert(ALERTS_ON);
        theFan.setAlertWay(alertWay);

    }



    @Override
    public void removeFan(Fan fan) throws Exception {
        allFans.remove(fan.getEmailAddress());
    }

    @Override
    public void addPageToFanListOfPages(String fanMail, String pageID) throws Exception {
        if(fanMail == null || pageID == null){
            throw new NullPointerException("One or more of the inputs is null");
        }
        Fan fan = allFans.get(fanMail);
        if(fan == null){
            throw new NotFoundException("fan not found");
        }
        Set<String> theFanPages = fan.getMyPages();
        if(theFanPages.contains(pageID)){
            throw new Exception("You already following this page");
        }
        theFanPages.add(pageID);
        fan.setMyPages(theFanPages);
    }
}
