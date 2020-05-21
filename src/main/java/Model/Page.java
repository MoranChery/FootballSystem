package Model;

import Model.UsersTypes.Fan;

import java.util.HashMap;
import java.util.Map;

public abstract class Page {
    Map<String, Fan> fansFollowingThisPage;
    String pageID;
    PageType pageType;


    public Page(String ownerID,PageType pageType) throws Exception {
        fansFollowingThisPage = new HashMap<>();
        pageID = ownerID;
        this.pageType = pageType;
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

    public void addFanFollowingThisPage(Fan fan){
        fansFollowingThisPage.put(fan.getEmailAddress(),fan);
    }

}
