package Data;

import Model.PersonalPage;
import Model.UsersTypes.Fan;

import java.util.HashMap;
import java.util.Map;

public class PersonalPageDbInMemory implements PersonalPageDb {

    private Map<String, PersonalPage> allPages;


    private static PersonalPageDbInMemory ourInstance = new PersonalPageDbInMemory();

    public PersonalPageDbInMemory() {
        this.allPages = new HashMap<>();
    }

    public static PersonalPageDbInMemory getInstance() {
        return ourInstance;
    }


    @Override
    public void addPageToFanList(String pageID, Fan fanToAdd) throws Exception {
        if(fanToAdd == null){
            throw new Exception("Fan not found");
        }
        PersonalPage page = allPages.get(pageID);
        if(page == null){
            throw new Exception("Page not found");
        }
        Map<String, Fan> fansFollowingThisPage = page.getFansFollowingThisPage();
        String fanToAddEmailAddress = fanToAdd.getEmailAddress();
        if(fansFollowingThisPage.containsKey(fanToAddEmailAddress)){
            throw new Exception("You are already follow this page");
        }
        fansFollowingThisPage.put(fanToAddEmailAddress,fanToAdd);
    }

    @Override
    public PersonalPage getPage(String pageId) throws NotFoundException {
        PersonalPage page = allPages.get(pageId);
        if(page == null){
            throw new NotFoundException("Page not found");
        }
        return page;
    }

    @Override
    public void createPage(String pageID) throws Exception {
        if(allPages.containsKey(pageID)){
            throw new Exception("Page already exist in the system");
        }
        PersonalPage personalPage = new PersonalPage(pageID);
        allPages.put(pageID,personalPage);
    }
}
