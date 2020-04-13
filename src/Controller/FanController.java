package Controller;

import Data.*;
import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.PersonalPage;
import Model.UsersTypes.Fan;

import java.util.Map;

public class FanController {

    private FanDb fanDb;
    private PersonalPageDb personalPageDb;

    public FanController() {
        this.fanDb = FanDbInMemory.getInstance();
        personalPageDb = PersonalPageDbInMemory.getInstance();
    }

    public void addPageToFanList(String pageId, String pageName, String fanMail) throws Exception {
        if(pageId == null || pageName == null || fanMail == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        PersonalPage pageToAdd = new PersonalPage(pageId,pageName);
        Map<String, PersonalPage> fansPages = fan.getMyPages();
        PersonalPage testPage = personalPageDb.getPage(pageId);
        if(!testPage.equals(pageToAdd)){
            throw new Exception("One or more of the details incorrect");
        }
        if(fansPages.containsKey(pageId)){
            throw new Exception("You are already follow this page");
        }
        fanDb.addPageToFanList(fanMail, pageToAdd);
    }

    public void logOut(String fanMail, Status status) throws Exception {
        if(status == null || fanMail == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        if (fan == null){
            throw new NotFoundException("Fan not found");
        }
        if(fan.getStatus().equals(Status.OFFLINE)){
            throw new Exception("You are already disconnected to the system");
        }
        fanDb.logOut(fanMail, status);
    }
    public void askToGetAlerts(String fanMail, GamesAlert alert, AlertWay alertWay) throws Exception {
        if(fanMail == null || alert == null || alertWay == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        if(fan == null){
            throw new NotFoundException("Fan not found");
        }
        if(fan.getAlertWay() != null){
            throw new Exception("You are already choose the way to get alerts about games");
        }
        if(fan.getGamesAlert().equals(GamesAlert.ALERTS_ON)){
            throw new Exception("You are already registered to get alerts about games");
        }
        fanDb.askToGetAlerts(fanMail,alert,alertWay);
    }

}
