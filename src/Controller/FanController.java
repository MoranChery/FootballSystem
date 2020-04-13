package Controller;

import Data.FanDb;
import Data.FanDbInMemory;
import Data.PersonalPageDb;
import Data.PersonalPageDbInMemory;
import Model.PersonalPage;
import Model.UsersTypes.Fan;

import java.util.ArrayList;
import java.util.Map;

public class FanController {

    private FanDb fanDb;
    private PersonalPageDb personalPageDb;

    public FanController() {
        this.fanDb = FanDbInMemory.getInstance();
        personalPageDb = PersonalPageDbInMemory.getInstance();
    }

    public void addPageToFanList(Integer pageId, String pageName, Integer fanId) throws Exception {
        if(pageId == null || pageName == null || fanId == null){
            throw new NullPointerException("bad input");
        }
        Fan fan = fanDb.getFan(fanId);
        PersonalPage pageToAdd = new PersonalPage(pageId,pageName);
        Map<Integer, PersonalPage> fansPages = fan.getMyPages();
        PersonalPage testPage = personalPageDb.getPage(pageId);
        if(!testPage.equals(pageToAdd)){
            throw new Exception("One or more of the details incorrect");
        }
        if(fansPages.containsKey(pageId)){
            throw new Exception("You are already follow this page");
        }
        fanDb.addPageToFanList(fanId, pageToAdd);


    }
}
