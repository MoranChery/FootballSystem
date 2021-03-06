package Controller;

import Data.*;
import Model.Alert;
import Model.Enums.Status;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Subscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriberControllerTest extends BaseEmbeddedSQL {

    private SubscriberController subscriberController = new SubscriberController();
    private SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();
    private AlertDb alertDb = AlertDbInServer.getInstance();


    @Before
    public void init() throws SQLException {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(subscriberDb);
        dbs.add(alertDb);
        for (Db db : dbs) {
            db.deleteAll();
        }
    }
    @Test
    public void createSubscriber_Null() throws Exception {
        try {
            subscriberController.createSubscriber(null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Can't create this subscriber", e.getMessage());
        }
    }
    @Test
    public void createSubscriberExist() throws Exception{
        Subscriber subscriber = new Fan("email", "1234",1,"first","last");
        subscriberController.createSubscriber(subscriber);
        try {
            Subscriber newSubscriber = new Fan("email", "1234",1,"first","last");
            subscriberController.createSubscriber(newSubscriber);
        }
        catch (Exception e){
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }
    }
    @Test
    public void createSubscriberLegal() throws Exception {
        Subscriber subscriber = new Fan("email", "1234",1,"first","last");
        subscriberController.createSubscriber(subscriber);
        Subscriber testSubscriber = subscriberDb.getSubscriber("email");
        subscriber = subscriberDb.getSubscriber("email");

        Assert.assertEquals(subscriber.getEmailAddress(), testSubscriber.getEmailAddress());
    }

    @Test
    public void getSubscriberNull() throws Exception {
        try {
            subscriberController.getSubscriber(null);
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Subscriber not found", e.getMessage());
        }
    }
    @Test
    public void getSubscriberLegal() throws Exception {
        try {
            Subscriber subscriberNew = new Fan("email", "1234",1,"first","last");
            subscriberController.createSubscriber(subscriberNew);
            Subscriber subscriber = subscriberController.getSubscriber("email");
            Assert.assertEquals(subscriber.getEmailAddress(),subscriberNew.getEmailAddress());
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Subscriber not found", e.getMessage());
        }
    }

    /** Tests for the function logOut **/

    @Test
    public void logOutNullInput() throws Exception {
        try {
            subscriberController.logOut( null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }
    @Test
    public void logOutNotFound() throws Exception {
        try {
            subscriberController.logOut("id");
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }
    @Test
    public void logOutAlreadyOff() throws Exception {
        try {
            Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234",1,"first","last");
            subscriberController.createSubscriber(newSubscriber);
            newSubscriber.setStatus(Status.OFFLINE);
            subscriberController.logOut(newSubscriber.getEmailAddress());
        }
        catch (Exception e){
            Assert.assertEquals("You are already disconnected from the system", e.getMessage());
        }
    }

    @Test
    public void logOutLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.insertSubscriber(newSubscriber);
        subscriberDb.changeStatusToOnline(newSubscriber);

        subscriberController.logOut(newSubscriber.getEmailAddress());
        newSubscriber = subscriberDb.getSubscriber("email@gmail.com");
        Assert.assertEquals(Status.OFFLINE,newSubscriber.getStatus());
    }

    /** Tests for the function wantToEditPassword **/

    @Test
    public void wantToEditPasswordNullInput() throws Exception{
        try {
            subscriberController.wantToEditPassword("id", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Something went wrong in editing subscriber the password", e.getMessage());
        }
    }
    @Test
    public void wantToEditPasswordNotFound() throws Exception{
        try {
            subscriberController.wantToEditPassword("id", "psw");
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }
    @Test
    public void wantToEditPasswordSamePassword() throws Exception{
        try {
            Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
            subscriberDb.insertSubscriber(newSubscriber);
            subscriberController.wantToEditPassword(newSubscriber.getEmailAddress(), "fan1234");
        }
        catch (Exception e){
            Assert.assertEquals("You are already using this password", e.getMessage());
        }
    }

    @Test
    public void wantToEditPasswordLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.insertSubscriber(newSubscriber);
        subscriberController.wantToEditPassword(newSubscriber.getEmailAddress(), "newPsw");
        newSubscriber = subscriberDb.getSubscriber("email@gmail.com");
        Assert.assertEquals("newPsw", newSubscriber.getPassword());
    }

    /** Tests for the function wantToEditFirstName **/

    @Test
    public void wantToEditFirstNameNullInput() throws Exception{
        try {
            subscriberController.wantToEditFirstName("id", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Something went wrong in editing subscriber's the first name", e.getMessage());
        }
    }
    @Test
    public void wantToEditFirstNameNotFound() throws Exception{
        try {
            subscriberController.wantToEditFirstName("id", "name");
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }
    @Test
    public void wantToEditFirstNameSameName() throws Exception{
        try {
            Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
            subscriberDb.insertSubscriber(newSubscriber);
            subscriberController.wantToEditFirstName(newSubscriber.getEmailAddress(), "first");
        }
        catch (Exception e){
            Assert.assertEquals("You are already using this name as first name", e.getMessage());
        }
    }

    @Test
    public void wantToEditFirstNameLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.insertSubscriber(newSubscriber);
        subscriberController.wantToEditFirstName(newSubscriber.getEmailAddress(), "newName");
        newSubscriber = subscriberDb.getSubscriber("email@gmail.com");

        Assert.assertEquals("newName",newSubscriber.getFirstName());
    }

    /** Tests for the function wantToEditLastName **/

    @Test
    public void wantToEditLastNameNullInput() throws Exception{
        try {
            subscriberController.wantToEditLastName("id", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Something went wrong in editing the last name of the subscriber", e.getMessage());
        }
    }
    @Test
    public void wantToEditLastNameNotFound() throws Exception{
        try {
            subscriberController.wantToEditLastName("id", "name");
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }
    @Test
    public void wantToEditLastNameSameName() throws Exception{
        try {
            Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
            subscriberDb.insertSubscriber(newSubscriber);
            subscriberController.wantToEditLastName(newSubscriber.getEmailAddress(), "last");
        }
        catch (Exception e){
            Assert.assertEquals("You are already using this name as last name", e.getMessage());
        }
    }

    @Test
    public void wantToEditLastNameLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.insertSubscriber(newSubscriber);
        subscriberController.wantToEditLastName(newSubscriber.getEmailAddress(), "newName");
        newSubscriber = subscriberDb.getSubscriber("email@gmail.com");
        Assert.assertEquals("newName",newSubscriber.getLastName());
    }

    @Test
    public void getAlertsEmptyInput() throws Exception{
        try {
            subscriberController.getAlerts("");
        }
        catch (Exception e){
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void getAlertsNoAlerts() throws Exception{
        try {
            Subscriber subscriber = new Subscriber("sub@gmail.com", "password", 5555555, "first_name", "last_name", Status.ONLINE);
            subscriberDb.insertSubscriber(subscriber);
            subscriberController.getAlerts("sub@gmail.com");
        }
        catch (Exception e){
            Assert.assertEquals(null, e.getMessage());
        }
    }

    @Test
    public void getAlertsLegal() throws Exception {
        Subscriber subscriber = new Subscriber("sub@gmail.com", "password", 5555555, "first_name", "last_name", Status.ONLINE);
        subscriberDb.insertSubscriber(subscriber);
        List<Alert> alerts = new ArrayList<>();
        Alert firstAlert = new Alert("subject1", "body1");
        Alert secAlert = new Alert("subject2", "body2");
        alerts.add(firstAlert);
        alerts.add(secAlert);
        alertDb.insertAlertInDb(subscriber.getEmailAddress(), firstAlert);
        alertDb.insertAlertInDb(subscriber.getEmailAddress(), secAlert);
        List<Alert> subAlerts = subscriberController.getAlerts("sub@gmail.com");
        Assert.assertEquals(alerts.size(), subAlerts.size());
    }
    @Test
    public void wantedByMailEmptyInput() throws Exception{
        try {
            subscriberController.wantedByMail("");
        }
        catch (Exception e){
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void wantedByMailNotFound() throws Exception{
        try {
            subscriberController.wantedByMail("sub@gmail.com");
        }
        catch (Exception e){
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }
    @Test
    public void wantedByMail_LegalNotWant() throws Exception{
            Subscriber subscriber = new Subscriber("sub@gmail.com", "password", 5555555, "first_name", "last_name", Status.ONLINE);
            subscriberDb.insertSubscriber(subscriber);
            boolean notWant = subscriberController.wantedByMail("sub@gmail.com");
            Assert.assertFalse(notWant);
    }
    @Test
    public void wantedByMail_LegalWant() throws Exception{
        Subscriber subscriber = new Subscriber("sub@gmail.com", "password", 5555555, "first_name", "last_name", Status.ONLINE);
        subscriber.setWantAlertInMail(true);
        subscriberDb.insertSubscriber(subscriber);
        boolean wantMail = subscriberController.wantedByMail("sub@gmail.com");
        Assert.assertTrue(wantMail);
    }
    @Test
    public void setSubscriberWantAlertEmptyInput() throws Exception{
        try {
            subscriberController.setSubscriberWantAlert("");
        }
        catch (Exception e){
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void setSubscriberWantAlertNotFound() throws Exception{
        try {
            subscriberController.setSubscriberWantAlert("sub@gmail.com");
        }
        catch (Exception e){
            Assert.assertEquals("Subscriber not found", e.getMessage());
        }
    }
    @Test
    public void setSubscriberWantAlertLegal() throws Exception{
        Subscriber subscriber = new Subscriber("sub@gmail.com", "password", 5555555, "first_name", "last_name", Status.ONLINE);
        subscriberDb.insertSubscriber(subscriber);
        subscriberController.setSubscriberWantAlert("sub@gmail.com");

        boolean want = subscriberController.wantedByMail(subscriber.getEmailAddress());
        Assert.assertEquals(true, want);


    }




}
