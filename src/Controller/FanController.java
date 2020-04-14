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
        PersonalPage pageToAdd = new PersonalPage(pageId);
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


    public void wantToEditMail(String fanMail, String newMailAddress) throws Exception {
        if(fanMail == null || newMailAddress == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        if(fan == null){
            throw new NotFoundException("Fan not found");
        }
        if(fan.getPassword().equals(newMailAddress)){
            throw new Exception("This E-mail address is the same as the old one");
        }
        fanDb.wantToEditMail(fanMail, newMailAddress);


    }
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
    public void wantToEditID(String fanMail, Integer newID) throws Exception {
        if(fanMail == null || newID == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanMail);
        if(fan == null){
            throw new NotFoundException("Fan not found");
        }
        if(fan.getId().equals(newID)){
            throw new Exception("This id is the same as the old one");
        }
        fanDb.wantToEditID(fanMail, newID);
    }
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

}
