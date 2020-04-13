package Model;

import Model.UsersTypes.Fan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PersonalPage {
    private Map<String, Fan> fansFollowingThisPage;
    private System_Controller myController;
    private String pageID;
    private String name;

    public PersonalPage(String ownerID, String pageOwnerName) {
        myController = System_Controller.getInstance();
        fansFollowingThisPage = new HashMap<>();
        pageID = ownerID;
        name = pageOwnerName;
    }

//    private void AddFanToListOfFollowers(Fan fanThatFollow){
//        if(fanThatFollow != null && !(fansFollowingThisPage.contains(fanThatFollow))){
//            fansFollowingThisPage.add(fanThatFollow);
//            // write it in the log
//        }
//    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
