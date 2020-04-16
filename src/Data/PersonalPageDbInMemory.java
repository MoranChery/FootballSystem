package Data;

import Model.Page;
import Model.PersonalPage;
import Model.Team;
import Model.TeamPage;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class PersonalPageDbInMemory implements PersonalPageDb {

    private Map<String, Page> allPages;


    private static PersonalPageDbInMemory ourInstance = new PersonalPageDbInMemory();

    public PersonalPageDbInMemory() {
        this.allPages = new HashMap<>();
    }

    public static PersonalPageDbInMemory getInstance() {
        return ourInstance;
    }


    @Override
    public void addFanToPageListOfFans(String pageID, Fan fanToAdd) throws Exception {
        if(fanToAdd == null || pageID == null){
            throw new NullPointerException("One or more of the input was null");
        }
        Page page = allPages.get(pageID);
        if(page == null){
            throw new NotFoundException("Page not found");
        }
        Map<String, Fan> fansFollowingThisPage = page.getFansFollowingThisPage();
        String fanToAddEmailAddress = fanToAdd.getEmailAddress();
        if(fansFollowingThisPage.containsKey(fanToAddEmailAddress)){
            throw new Exception("This fan is already a follower");
        }
        fansFollowingThisPage.put(fanToAddEmailAddress,fanToAdd);
    }

    @Override
    public Page getPage(String pageId) throws NotFoundException {
        Page page = allPages.get(pageId);
        if(page == null){
            throw new NotFoundException("Page not found");
        }
        return page;
    }

    @Override
    public void createPersonalPage(String pageID, Subscriber subscriber) throws Exception {
        if(allPages.containsKey(pageID)){
            throw new Exception("Page already exist in the system");
        }
        PersonalPage personalPage = new PersonalPage(pageID,subscriber);
        allPages.put(pageID,personalPage);
    }



    @Override
    public void createTeamPage(String pageID, Team team) throws Exception {
        if(allPages.containsKey(pageID)){
            throw new Exception("Page already exist in the system");
        }
        TeamPage personalPage = new TeamPage(pageID ,team);
        allPages.put(pageID,personalPage);
    }
}
