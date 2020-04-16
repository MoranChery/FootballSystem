package Model;

import Controller.System_Controller;
import Model.UsersTypes.Fan;

import java.util.HashMap;
import java.util.Map;

public class PersonalPage {
    private Map<String, Fan> fansFollowingThisPage;
    private System_Controller myController;
    private String pageID;

    public PersonalPage(String ownerID) throws Exception {
        myController = System_Controller.getInstance();
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
