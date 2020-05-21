package Controller;

import Data.*;
import Model.Enums.AlertWay;
import Model.Enums.GamesAlert;
import Model.Page;
import Model.PageType;
import Model.TeamPage;
import Model.UsersTypes.Fan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FanControllerTest {

    private FanController fanController = new FanController();
    private FanDb fanDb = FanDbInMemory.getInstance();
    private PageDb pageDb = PageDbInMemory.getInstance();



    @Before
    public void init() {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(FanDbInMemory.getInstance());
        dbs.add(PageDbInMemory.getInstance());
        for (Db db : dbs) {
//            db.deleteAll();
        }
    }

    @Test
    public void createFanNull() throws Exception {
        try {
            fanController.createFan(null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Something went wrong with creating this fan", e.getMessage());
        }
    }
    @Test
    public void createFanLegal() throws Exception {
        Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
        fanDb.createFan(newFan);
        Fan testFan = FanDbInMemory.getInstance().getFan("email@gmail.com");
        Assert.assertEquals(newFan.getEmailAddress(), testFan.getEmailAddress());
    }
    @Test
    public void createFanExist() throws Exception{
        Fan fan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
        fanController.createFan(fan);
        try {
            Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
            fanController.createFan(newFan);
        }
        catch (Exception e){
            Assert.assertEquals("Fan already exists", e.getMessage());
        }
    }

    @Test
    public void getFanNull() throws Exception {
        try {
            fanController.getFan(null);
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Fan not found", e.getMessage());
        }
    }
    @Test
    public void getFanLegal() throws Exception {
        Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
        fanDb.createFan(newFan);
        Fan fan = fanController.getFan("email@gmail.com");
        Assert.assertEquals(fan.getEmailAddress(), newFan.getEmailAddress());
    }

    /** Tests for the function addPageToFanListOfPages**/

    @Test
    public void addPageToFanWithNullFanMailInput() throws Exception{
        try {
            fanController.addPageToFanListOfPages(null, "id");
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the inputs is null", e.getMessage());
        }
    }

    @Test
    public void addPageToFanWithEmptyFanMailInput() throws Exception{
        try {
            fanController.addPageToFanListOfPages("", "PageID");
        }
        catch (Exception e){
            Assert.assertEquals("the input has no value", e.getMessage());
        }
    }
    @Test
    public void addPageToFanNotFoundFan() throws Exception{
        try {
            fanController.addPageToFanListOfPages("fanMail", "PageID");
        }
        catch (Exception e){
            Assert.assertEquals("Fan not found", e.getMessage());
        }
    }
    @Test
    public void addPageToFanAlreadyFollow() throws Exception{
        try {
            Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
            fanController.createFan(newFan);
            Set<String> pages = new HashSet<>();
            pages.add("pageID1");
            newFan.setMyPages(pages);
            fanController.addPageToFanListOfPages("email@gmail.com", "pageID1");
        }
        catch (Exception e){
            Assert.assertEquals("You already following this page", e.getMessage());
        }
    }

    @Test
    public void addPageToFanLegal() throws Exception{

        Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
        fanController.createFan(newFan);
        Page testPage = new TeamPage("teamName" ,PageType.TEAM);
        pageDb.insertPage("teamName", PageType.TEAM);
        fanController.addPageToFanListOfPages(newFan.getEmailAddress(),testPage.getPageID());
        Set<String> fanPages = newFan.getMyPages();
        Assert.assertEquals(1, fanPages.size());
        Assert.assertTrue(fanPages.contains("teamName"));
    }
    /** Tests for the function askToGetAlerts **/

    @Test
    public void askToGetAlertsWithNullFanMailInput() throws Exception{
        try {
            fanController.askToGetAlerts(null, AlertWay.EmailAlert);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the inputs is wrong", e.getMessage());
        }
    }
    @Test
    public void askToGetAlertsNotFoundFan() throws Exception{
        try {
            fanController.askToGetAlerts("fanMail", AlertWay.EmailAlert);
        }
        catch (Exception e){
            Assert.assertEquals("Fan not found", e.getMessage());
        }
    }
    @Test
    public void askToGetAlertsAlreadyGetAlerts() throws Exception{
        try {
            Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
            fanController.createFan(newFan);
            newFan.setGamesAlert(GamesAlert.ALERTS_ON);
            fanController.askToGetAlerts(newFan.getEmailAddress(), AlertWay.EmailAlert);
        }
        catch (Exception e){
            Assert.assertEquals("You are already signed to get alerts about games", e.getMessage());
        }
    }
    @Test
    public void askToGetAlertsLegal() throws Exception{
        try {
            Fan newFan = new Fan("email@gmail.com", "fan1234",1,"Lionel","Messi");
            fanController.createFan(newFan);
            fanController.askToGetAlerts(newFan.getEmailAddress(), AlertWay.EmailAlert);
            Assert.assertEquals(newFan.getAlertWay(), AlertWay.EmailAlert);
            Assert.assertEquals(newFan.getGamesAlert(), GamesAlert.ALERTS_ON);
        }
        catch (Exception e){
            Assert.assertEquals("You are already signed to get alerts about games", e.getMessage());
        }
    }






}
