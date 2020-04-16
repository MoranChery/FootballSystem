package Data;

import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Enums.Status;
import Model.PersonalPage;
import Model.Search;
import Model.UsersTypes.Fan;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
    public void wantToEditPassword(String fanMail, String newPassword) throws Exception {
        if(fanMail == null || newPassword == null){
            throw new Exception("Something went wrong in editing fan the password");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NotFoundException("Couldn't get this fan");
        }
        if(theFan.getPassword().equals(newPassword)){
            throw new Exception("You are already using this password");
        }
        theFan.setPassword(newPassword);
    }

    @Override
    public void wantToEditFirstName(String fanMail, String newFirstName) throws Exception {
        if(fanMail == null || newFirstName == null){
            throw new Exception("Something went wrong in editing fan's the first name");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NotFoundException("Couldn't get this fan");
        }
        if(theFan.getFirstName().equals(newFirstName)){
            throw new Exception("You are already using this name as first name");
        }
        theFan.setFirstName(newFirstName);
    }

    @Override
    public void wantToEditLastName(String fanMail, String newLastName) throws Exception {
        if(fanMail == null || newLastName == null){
            throw new Exception("Something went wrong in editing the last name of the fan");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NotFoundException("Couldn't get this fan");
        }
        if(theFan.getLastName().equals(newLastName)){
            throw new Exception("You are already using this name as last name");
        }
        theFan.setLastName(newLastName);
    }

    @Override
    public Set<Search> watchMySearchHistory(String fanMail) throws Exception {
        if(fanMail == null){
            throw new NullPointerException("Can't display searches as one of the inputs is null");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NotFoundException("Couldn't get this fan");
        }
        Set<Search> searchSetToReturn = theFan.getMySearchHistory();
        if(searchSetToReturn == null){
            throw new NotFoundException("The search history is not found");
        }
        return searchSetToReturn;
    }

    @Override
    public void addSearchToMyHistory(String fanMail, Search myNewSearch) throws Exception {

        if(fanMail == null || myNewSearch == null){
            throw new NullPointerException("Can't add new search as one of the inputs is null");
        }
        Fan theFan = allFans.get(fanMail);
        if(theFan == null){
            throw new NotFoundException("Couldn't get this fan");
        }
        if(theFan.getMySearchHistory().contains(myNewSearch)){
            throw new Exception("This search is already in your list");
        }
        Set<Search> searchSet = theFan.getMySearchHistory();
        searchSet.add(myNewSearch);
        theFan.setMySearchHistory(searchSet);
    }

    @Override
    public void removeFan(Fan fan) throws Exception {
        allFans.remove(fan.getEmailAddress());
    }

//    public void editPersonalDetails(String fanMail,String password, Integer id, String firstName, String lastName) throws Exception {
//        if(fanMail == null || password == null || id == null || firstName == null || lastName == null){
//            throw new Exception("bad input");
//        }
//
//    }

}
