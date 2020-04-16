package Model;

import Model.UsersTypes.Fan;

import java.util.HashMap;
import java.util.Map;

public abstract class Page {
    Map<String, Fan> fansFollowingThisPage;
    String pageID;


    public Page(String ownerID) throws Exception {
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
