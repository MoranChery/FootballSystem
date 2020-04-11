package Data;

import Model.PersonalPage;
import Model.UsersTypes.Fan;

import java.util.HashMap;
import java.util.Map;

public class PersonalPageDbInMemory implements PersonalPageDb {

    private Map<Integer, PersonalPage> allPages;


    private static PersonalPageDbInMemory ourInstance = new PersonalPageDbInMemory();

    public PersonalPageDbInMemory() {
        this.allPages = new HashMap<>();
    }

    public static PersonalPageDbInMemory getInstance() {
        return ourInstance;
    }


    @Override
    public void addPageToFanList(Integer pageID, Fan fanToAdd) throws Exception {
        if(fanToAdd == null){
            throw new Exception("Fan not found");
        }
        PersonalPage page = allPages.get(pageID);
        if(page == null){
            throw new Exception("Page not found");
        }
        Map<Integer, Fan> fansFollowingThisPage = page.getFansFollowingThisPage();
        Integer fanID = fanToAdd.getId();
        if(fansFollowingThisPage.containsKey(fanID)){
            throw new Exception("You are already follow this page");
        }
        fansFollowingThisPage.put(fanID,fanToAdd);
    }

    @Override
    public PersonalPage getPage(Integer pageId) {
        PersonalPage page = allPages.get(pageId);
        if(page == null){
            //throw new NotFoundException("Page not found");
        }
        return page;
    }
}
