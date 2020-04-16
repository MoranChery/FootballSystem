package Controller;

import Data.*;
import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.PersonalPage;
import Model.Search;
import Model.UsersTypes.Fan;

import java.util.Map;
import java.util.Set;

import static Model.Enums.AlertWay.EmailAlert;
import static Model.Enums.GamesAlert.ALERTS_ON;

public class FanController {

    private FanDb fanDb;
    private PersonalPageDb personalPageDb;

    public FanController() {
        this.fanDb = FanDbInMemory.getInstance();
        personalPageDb = PersonalPageDbInMemory.getInstance();
    }

    public void createFan(Fan theFan) throws Exception{
        if(theFan == null){
            throw new NullPointerException("Can't create this fan");
        }
        fanDb.createFan(theFan);
    }

    public void addPageToFanList(String pageId, String fanMail) throws Exception {
        if(pageId == null || fanMail == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        if(fan == null){
            throw new NotFoundException("fan not found");
        }
        PersonalPage pageToAdd = new PersonalPage(pageId); // may change
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


    /**
     * function for the fan to logout of the system
     * the function set the fan's status to offline
     * @param fanMail String the fan id - fan email
     * @throws Exception nullPointerException if the input is null
     * NotFoundException if the fan is not in the db
     * Exception if the fan's status is already null
     */
    public void logOut(String fanMail) throws Exception {
        if(fanMail == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        if (fan == null){
            throw new NotFoundException("Fan not found");
        }
        if(fan.getStatus().equals(Status.OFFLINE)){
            throw new Exception("You are already disconnected to the system");
        }
        fanDb.logOut(fanMail);
    }

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
     * this function enable the fan to edit his password
     * @param fanMail String the fan id- email address
     * @param newPassword String the new password the fan want to change to
     * @throws Exception NullPointerException - if one or more of the inputs is null
     * NotFoundException - if the fan is not in the db
     * Exception - if the new password is equal to the current password of the fan
     */
    public void wantToEditPassword(String fanMail, String newPassword) throws Exception {
        if(fanMail == null || newPassword == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        if(fan == null){
            throw new NotFoundException("Fan not found");
        }
        if(fan.getPassword().equals(newPassword)){
            throw new Exception("This password is the same as the old one");
        }
        fanDb.wantToEditPassword(fanMail, newPassword);
    }

    /**
     * this function enable the fan to edit his first name
     * @param fanMail String the fan id- email address
     * @param newFirstName String the new first name the fan want to change to
     * @throws Exception NullPointerException - if one or more of the inputs is null
     * NotFoundException - if the fan is not in the db
     * Exception - if the new first name is equal to the current first name of the fan
     */
    public void wantToEditFirstName(String fanMail, String newFirstName) throws Exception {
        if(fanMail == null || newFirstName == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        if(fan == null){
            throw new NotFoundException("Fan not found");
        }
        if(fan.getFirstName().equals(newFirstName)){
            throw new Exception("This name is the same as the old one");
        }
        fanDb.wantToEditFirstName(fanMail, newFirstName);
    }

    /**
     * this function enable the fan to edit his last name
     * @param fanMail String the fan id- email address
     * @param newLastName String the new last name the fan want to change to
     * @throws Exception NullPointerException - if one or more of the inputs is null
     * NotFoundException - if the fan is not in the db
     * Exception - if the new last name is equal to the current last name of the fan
     */
    public void wantToEditLastName(String fanMail, String newLastName) throws Exception {
        if(fanMail == null || newLastName == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        if(fan == null){
            throw new NotFoundException("Fan not found");
        }
        if(fan.getLastName().equals(newLastName)){
            throw new Exception("This password is the same as the old one");
        }
        fanDb.wantToEditLastName(fanMail, newLastName);
    }

    public void watchMySearchHistory(String fanMail) throws Exception {

        fanDb.watchMySearchHistory(fanMail);
    }



//    public void editPersonalDetails(String fanMail,String password, Integer id, String firstName, String lastName) throws Exception {
//        if(fanMail == null || password == null || id == null || firstName == null || lastName == null){
//            throw new Exception("bad input");
//        }
//        Fan fan = fanDb.getFan(fanMail);
//        if(fan == null){
//            throw new NotFoundException("Fan not found");
//        }
//
//    }



//    /**
//     * function for the fan to logout the system
//     * @param fanMail the id of the fan in the system
//     * @param status the status he want
//     * @throws Exception
//     */
//    public void logOut(String fanMail, Status status) throws Exception {
//        if(status == null || fanMail == null){
//            throw new NullPointerException("bad input");
//        }
//        Fan fan = fanDb.getFan(fanMail);
//        if (fan == null){
//            throw new NotFoundException("Fan not found");
//        }
//        if(fan.getStatus().equals(Status.OFFLINE)){
//            throw new Exception("You are already disconnected to the system");
//        }
//        if (status.equals(Status.ONLINE)) {
//            throw new Exception("Wrong status");
//        }
//        fanDb.logOut(fanMail, status);
//    }


//    public void askToGetAlerts(String fanMail, GamesAlert alert, AlertWay alertWay) throws Exception {
//        if(fanMail == null || alert == null || alertWay == null){
//            throw new NullPointerException("bad input");
//        }
//        Fan fan = fanDb.getFan(fanMail);
//        if(fan == null){
//            throw new NotFoundException("Fan not found");
//        }
//        if(fan.getAlertWay() != null){
//            throw new Exception("You are already choose the way to get alerts about games");
//        }
//        if(fan.getGamesAlert().equals(GamesAlert.ALERTS_ON)){
//            throw new Exception("You are already registered to get alerts about games");
//        }
//        fanDb.askToGetAlerts(fanMail,alert,alertWay);
//    }

}
