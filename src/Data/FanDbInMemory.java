package Data;

import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.PersonalPage;
import Model.UsersTypes.Fan;

import java.util.HashMap;
import java.util.Map;

import static Model.Enums.Status.OFFLINE;
import static Model.Enums.Status.ONLINE;

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
    public void addPageToFanList(String fanMail, PersonalPage pageToAdd) throws Exception {
        if(pageToAdd == null){
            throw new Exception("Page not found");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new Exception("Fan not found");
        }
        Map<String, PersonalPage> theFanPages = theFan.getMyPages();
        String pageToAddId = pageToAdd.getPageID();
        if(theFanPages.containsKey(pageToAddId)){
            throw new Exception("You are already follow this page");
        }
        theFanPages.put(pageToAddId,pageToAdd);
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
    public void logOut(String fanMail, Status status) throws Exception{
        if(fanMail == null){
            throw new Exception("Fan not found");
        }
        if(status == null || status.equals(ONLINE)){
            throw new Exception("bad status");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new Exception("Fan not found");
        }
        if(theFan.getStatus().equals(OFFLINE)){
            throw new Exception("You are already out of the system");
        }
        theFan.setStatus(OFFLINE);
        // alert that the fan logout succesfully
    }

    public void askToGetAlerts(String fanMail, GamesAlert alert, AlertWay alertWay) throws Exception {
        if(fanMail == null){
            throw new Exception("Fan not found");
        }
        if(alert == null || alert.equals(GamesAlert.ALERTS_OFF)){
            throw new Exception("bad status");
        }
        if(alertWay == null){
            throw new Exception("bad alert");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new Exception("Fan not found");
        }
        if(theFan.getAlertWay() != null || theFan.getGamesAlert().equals(GamesAlert.ALERTS_ON)){
            throw new Exception("You are already registered to get alerts about games");
        }
        theFan.setAlertWay(alertWay);
        theFan.setGamesAlert(alert);
        // alert that the fan registered to get alerts about games succesfully
    }

    @Override
    public void wantToEditMail(String fanMail, String newMailAddress) throws Exception {
        if(fanMail == null || newMailAddress == null){
            throw new Exception("Somthing went wrong");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NullPointerException("Coudn't get this fan");
        }
        if(theFan.getEmailAddress().equals(newMailAddress)){
            throw new Exception("You are already using this mail address");
        }
        theFan.setEmailAddress(newMailAddress);
    }

    @Override
    public void wantToEditPassword(String fanMail, String newPassword) throws Exception {
        if(fanMail == null || newPassword == null){
            throw new Exception("Somthing went wrong");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NullPointerException("Coudn't get this fan");
        }
        if(theFan.getPassword().equals(newPassword)){
            throw new Exception("You are already using password");
        }
        theFan.setPassword(newPassword);
    }

    @Override
    public void wantToEditID(String fanMail, Integer newID) throws Exception {
        if(fanMail == null || newID == null){
            throw new Exception("Somthing went wrong");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NullPointerException("Coudn't get this fan");
        }
        if(theFan.getId().equals(newID)){
            throw new Exception("You are already using this ID");
        }
        theFan.setId(newID);
    }

    @Override
    public void wantToEditFirstName(String fanMail, String newFirstName) throws Exception {
        if(fanMail == null || newFirstName == null){
            throw new Exception("Somthing went wrong");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NullPointerException("Coudn't get this fan");
        }
        if(theFan.getFirstName().equals(newFirstName)){
            throw new Exception("You are already using this name as first name");
        }
        theFan.setFirstName(newFirstName);
    }

    @Override
    public void wantToEditLastName(String fanMail, String newLastName) throws Exception {
        if(fanMail == null || newLastName == null){
            throw new Exception("Somthing went wrong");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NullPointerException("Coudn't get this fan");
        }
        if(theFan.getLastName().equals(newLastName)){
            throw new Exception("You are already using this name as last name");
        }
        theFan.setLastName(newLastName);
    }

//    public void editPersonalDetails(String fanMail,String password, Integer id, String firstName, String lastName) throws Exception {
//        if(fanMail == null || password == null || id == null || firstName == null || lastName == null){
//            throw new Exception("bad input");
//        }
//
//    }

}
