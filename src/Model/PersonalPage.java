package Model;

import Controller.System_Controller;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class PersonalPage {
    private Map<String, Fan> fansFollowingThisPage;
    private String pageID;


    public PersonalPage(String ownerID) throws Exception {
        fansFollowingThisPage = new HashMap<>();
        pageID = ownerID;
    }

    public Map<String, Fan> getFansFollowingThisPage() {
        return fansFollowingThisPage;
    }

    public void setFansFollowingThisPage(Map<String, Fan> fansFollowingThisPage) {
        this.fansFollowingThisPage = fansFollowingThisPage;
    }

    public String getPageID() {
        return pageID;
    }

    public void setPageID(String pageID) {
        this.pageID = pageID;
    }



}
