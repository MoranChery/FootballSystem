package Controller;

import Data.*;
import Model.Enums.*;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Judge;
import Model.UsersTypes.Subscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubscriberControllerTest {

    private SubscriberController subscriberController = new SubscriberController();
    private SubscriberDb subscriberDb = SubscriberDbInMemory.getInstance();
    private JudgeDb judgeDb = JudgeDbInMemory.getInstance();

    @Before
    public void init() {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(SubscriberDbInMemory.getInstance());
        dbs.add(JudgeDbInMemory.getInstance());
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
        Subscriber testSubscriber = SubscriberDbInMemory.getInstance().getSubscriber("email");
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
        subscriberController.createSubscriber(newSubscriber);
        newSubscriber.setStatus(Status.ONLINE);
        subscriberController.logOut(newSubscriber.getEmailAddress());
        Assert.assertEquals(newSubscriber.getStatus(), Status.OFFLINE);
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
            subscriberDb.createSubscriber(newSubscriber);
            subscriberController.wantToEditPassword(newSubscriber.getEmailAddress(), "fan1234");
        }
        catch (Exception e){
            Assert.assertEquals("You are already using this password", e.getMessage());
        }
    }

    @Test
    public void wantToEditPasswordLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.createSubscriber(newSubscriber);
        subscriberController.wantToEditPassword(newSubscriber.getEmailAddress(), "newPsw");
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
            subscriberDb.createSubscriber(newSubscriber);
            subscriberController.wantToEditFirstName(newSubscriber.getEmailAddress(), "first");
        }
        catch (Exception e){
            Assert.assertEquals("You are already using this name as first name", e.getMessage());
        }
    }

    @Test
    public void wantToEditFirstNameLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.createSubscriber(newSubscriber);
        subscriberController.wantToEditFirstName(newSubscriber.getEmailAddress(), "newName");
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
            subscriberDb.createSubscriber(newSubscriber);
            subscriberController.wantToEditLastName(newSubscriber.getEmailAddress(), "last");
        }
        catch (Exception e){
            Assert.assertEquals("You are already using this name as last name", e.getMessage());
        }
    }

    @Test
    public void wantToEditLastNameLegal() throws Exception {
        Subscriber newSubscriber = new Fan("email@gmail.com", "fan1234", 1, "first", "last");
        subscriberDb.createSubscriber(newSubscriber);
        subscriberController.wantToEditLastName(newSubscriber.getEmailAddress(), "newName");
        Assert.assertEquals("newName",newSubscriber.getLastName());
    }




}
