package Controller;

import Data.FanDb;
import Data.FanDbInMemory;
import Data.PersonalPageDb;
import Data.PersonalPageDbInMemory;
import Model.PersonalPage;
import Model.UsersTypes.Fan;

import java.util.Map;

public class PersonalPageController {

    private PersonalPageDb pageDb;
    private FanDb fanDb;

    public PersonalPageController() {
        this.fanDb = FanDbInMemory.getInstance();
        pageDb = PersonalPageDbInMemory.getInstance();
    }

    public void addPageToFanList(Integer pageID, Integer fanId, String firstName, String lastName, String fanMail, String fanPassword) throws Exception {

        if(pageID == null || fanId == null || firstName == null || lastName == null || fanMail == null || fanPassword == null){
            throw new NullPointerException("bad input");
        }
        PersonalPage page = pageDb.getPage(pageID);
        Fan fanToAdd = new Fan(fanId, firstName,lastName,fanPassword,fanMail);
        Map<Integer, Fan> fanOfThisPage = page.getFansFollowingThisPage();
        Fan testFan = fanDb.getFan(fanId);
        if(!testFan.equals(fanToAdd)){
            throw new Exception("One or more of the details incorrect");
        }
        if(fanOfThisPage.containsKey(fanId)){
            throw new Exception("You are already follow this page");
        }
        pageDb.addPageToFanList(pageID,fanToAdd);
    }
}