package Controller;

import Data.*;
import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.Page;
import Model.PersonalPage;
import Model.TeamPage;
import Model.UsersTypes.Fan;

import java.util.Map;
import java.util.Set;

import static Model.Enums.GamesAlert.ALERTS_ON;

public class FanController {

    private FanDb fanDb;
    private PageDb pageDb;

    public FanController() {
        this.fanDb = FanDbInMemory.getInstance();
        pageDb = PageDbInMemory.getInstance();
    }

    /**
     * This function creates new fan in the DB
     * @param theFan Fan the fan you want to add to the DB
     * @throws Exception NullPointerException if input is null
     */
    public void createFan(Fan theFan) throws Exception{
        if(theFan == null){
            throw new NullPointerException("Can't create this fan");
        }
        fanDb.createFan(theFan);
    }

    /**
     * This function enable the fan get alerts about games.
     * the status of gameAlerts changes to ON by his way (mail or system)
     * @param fanMail String the fan id - fan email
     * @param alertWay Enum AlertWay - the way the fan wants to get his alerts - by email or by the system
     * @throws Exception NullPointerException if the input is null
     * NotFoundException if the fan is not in the db
     * Exception if the fan's status is already ON to get alert about games
     */
    public void askToGetAlerts(String fanMail, AlertWay alertWay) throws Exception {
        if(fanMail == null || alertWay == null){
            throw new NullPointerException("One or more of the inputs is wrong");
        }
        Fan fan = fanDb.getFan(fanMail);
        if (fan == null){
            throw new NotFoundException("Fan not found");
        }
        if(fan.getGamesAlert().equals(ALERTS_ON)){
            throw new Exception("You are already signed to get alerts about games");
        }
        fanDb.askToGetAlerts(fanMail,alertWay);
    }

    /**
     * this function add page to the table of pages that are followed by this fan
     * @param fanMail String the fan id - email address
     * @param pageID String the id of the page - the email address of his owner
     * @throws Exception NullPointerException if the fanMail or the pageID is null
     * NotFoundException if the fan is not in the DB
     * Exception if the input is empty or if the page is exist in the DB
     */
    public void addPageToFanListOfPages(String fanMail, String pageID) throws Exception {
        if(fanMail == null || pageID == null){
            throw new NullPointerException("bad input");
        }
        if(fanMail.isEmpty() || pageID.isEmpty()){
            throw new Exception("the input has no value");
        }
        Fan fan = fanDb.getFan(fanMail);
        if(fan == null ){
            throw new NotFoundException("fan not found");
        }
        if(fan.getMyPages().contains(pageID)){
            throw new Exception("You already follow this page");
        }
        fanDb.addPageToFanListOfPages(fanMail,pageID);
    }


}
