package Service;

import Data.*;
import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Page;
import Model.Team;
import Model.TeamPage;
import Model.UsersTypes.Fan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FanServiceTest {

    private FanService fanService = new FanService();
    private PageDb pageDb = PageDbInMemory.getInstance();

    @Before
    public void init()
    {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(SubscriberDbInMemory.getInstance());
        dbs.add(FanDbInMemory.getInstance());
        dbs.add(PageDbInMemory.getInstance());
        for (Db db : dbs)
        {
            db.deleteAll();
        }
    }
    @Test
    public void getFanNull() throws Exception {
        try {
            fanService.getFan(null);
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Fan not found", e.getMessage());
        }
    }
    @Test
    public void getFanLegal() throws Exception {
        Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
        fanService.createFan(newFan);
        Fan fan = fanService.getFan("email@gmail.com");
        Assert.assertEquals(fan.getEmailAddress(), newFan.getEmailAddress());
    }

    /** Tests for the function addPageToFan **/
    @Test
    public void addPageToFanWithNullFanMailInput() throws Exception{
        try {
            fanService.addPageToFanListOfPages(null, "id");
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the inputs is null", e.getMessage());
        }
    }
    @Test
    public void addPageToFanWithEmptyFanMailInput() throws Exception{
        try {
            fanService.addPageToFanListOfPages("", "PageID");
        }
        catch (Exception e){
            Assert.assertEquals("the input has no value", e.getMessage());
        }
    }
    @Test
    public void addPageToFanNotFoundFan() throws Exception{
        try {
            fanService.addPageToFanListOfPages("fanMail", "PageID");
        }
        catch (Exception e){
            Assert.assertEquals("Fan not found", e.getMessage());
        }
    }
    @Test
    public void addPageToFanAlreadyFollow() throws Exception{
        try {
            Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
            fanService.createFan(newFan);
            Set<String> pages = new HashSet<>();
            pages.add("pageID1");
            newFan.setMyPages(pages);
            fanService.addPageToFanListOfPages("email@gmail.com", "pageID1");
        }
        catch (Exception e){
            Assert.assertEquals("You already following this page", e.getMessage());
        }
    }

    @Test
    public void addPageToFanLegal() throws Exception{

        Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
        fanService.createFan(newFan);
        Page testPage = new TeamPage("teamName");
        pageDb.createTeamPage("teamName", new Team());
        fanService.addPageToFanListOfPages(newFan.getEmailAddress(),testPage.getPageID());
        Set<String> fanPages = newFan.getMyPages();
        Assert.assertEquals(1, fanPages.size());
        Assert.assertTrue(fanPages.contains("teamName"));
    }

    /** Test for the function askToGetAlerts**/
    @Test
    public void askToGetAlertsLegal() throws Exception{
        try {
            Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
            fanService.createFan(newFan);
            fanService.askToGetAlerts(newFan.getEmailAddress(), AlertWay.EmailAlert);
            Assert.assertEquals(newFan.getAlertWay(), AlertWay.EmailAlert);
            Assert.assertEquals(newFan.getGamesAlert(), GamesAlert.ALERTS_ON);
        }
        catch (Exception e){
            Assert.assertEquals("You are already signed to get alerts about games", e.getMessage());
        }
    }
}
