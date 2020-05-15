package Controller;

import Data.FanDb;
import Data.FanDbInMemory;
import Model.Enums.AlertWay;
import Model.UsersTypes.Fan;

import static Model.Enums.GamesAlert.ALERTS_ON;

public class FanController {

    private FanDb fanDb;

    public FanController() {
        this.fanDb = FanDbInMemory.getInstance();
    }

    /**
     * This function creates new fan in the DB
     * @param theFan Fan the fan you want to add to the DB
     * @throws Exception NullPointerException if input is null
     */
    public void createFan(Fan theFan) throws Exception{
        if(theFan == null){
            throw new NullPointerException("Something went wrong with creating this fan");
        }
        fanDb.createFan(theFan);
    }


    /**
     * This function get string that represent fan id - his email address and returns Fan class instance
     * @param fanMail String - the id of the fan - his email address
     * @return Fan - the instance of the fan in the db
     * @throws Exception NullPointerException if the mail is null - the fan is not found in the db
     */
    public Fan getFan(String fanMail) throws Exception{
        if(fanMail == null){
            throw new NullPointerException("Fan not found");
        }
        return fanDb.getFan(fanMail);
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
            throw new NullPointerException("One or more of the inputs is null");
        }
        if(fanMail.isEmpty() || pageID.isEmpty()){
            throw new Exception("the input has no value");
        }
        Fan fan = fanDb.getFan(fanMail);
        if(fan.getMyPages().contains(pageID)){
            throw new Exception("You already following this page");
        }
        fanDb.addPageToFanListOfPages(fanMail,pageID);
    }


}
