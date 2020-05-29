package Data;

import Model.Enums.Status;
import Model.UsersTypes.Subscriber;

import java.util.HashMap;
import java.util.Map;

import static Model.Enums.Status.OFFLINE;

public class SubscriberDbInMemory implements SubscriberDb {
    /*structure like the DB of subscriber*/
    //key: username
    //value: subscriber class
    private Map<String, Subscriber> subscribers;

    public SubscriberDbInMemory() {
        this.subscribers = new HashMap<>();
    }

    private static SubscriberDbInMemory ourInstance = new SubscriberDbInMemory();

    public static SubscriberDbInMemory getInstance() {
        return ourInstance;
    }



    @Override
    public void insertSubscriber(Subscriber subscriber) throws Exception {
        if(subscriber == null){
            throw new NullPointerException("Can't create this subscriber");
        }
        if(subscribers.containsKey(subscriber.getEmailAddress())) {
            throw new Exception("subscriber already exists");
        }
        subscribers.put(subscriber.getEmailAddress(), subscriber);
    }


    @Override
    public Subscriber getSubscriber(String emailAddress) throws Exception {
//        Subscriber subscriber = subscribers.get(emailAddress);
        if(emailAddress == null){
            throw new NullPointerException("bad input");
        }
        if (!subscribers.containsKey(emailAddress)) {
            throw new NotFoundException("subscriber not found");
        }
        return subscribers.get(emailAddress);
    }

    @Override
    public boolean removeSubscriberFromDB(Subscriber subscriber) {
        if(!subscribers.containsKey(subscriber.getEmailAddress()))
            return false;
        subscribers.remove(subscriber.getEmailAddress());
        return true;
    }

    @Override
    public void deleteAll() {
        subscribers.clear();
    }

    @Override
    public void logOut(String subscriberMail) throws Exception{
        if(subscriberMail == null){
            throw new NullPointerException("subscriber not found");
        }
        Subscriber subscriber = subscribers.get(subscriberMail);
        if(subscriber == null){
            throw new Exception("subscriber not found");
        }
        if(subscriber.getStatus().equals(OFFLINE)){
            throw new Exception("You are already disconnected from the system");
        }
        subscriber.setStatus(OFFLINE);
        System.out.println("You are now disconnected");
    }
    @Override
    public void wantToEditPassword(String subscriberMail, String newPassword) throws Exception {
        if(subscriberMail == null || newPassword == null){
            throw new NullPointerException("Something went wrong in editing subscriber the password");
        }
        Subscriber subscriber = subscribers.get(subscriberMail);
        if(subscriber == null){
            throw new NotFoundException("Couldn't get this subscriber");
        }
        if(subscriber.getPassword().equals(newPassword)){
            throw new Exception("You are already using this password");
        }
        subscriber.setPassword(newPassword);
    }

    @Override
    public void wantToEditFirstName(String subscriberMail, String newFirstName) throws Exception {
        if(subscriberMail == null || newFirstName == null){
            throw new NullPointerException("Something went wrong in editing subscriber's the first name");
        }
        Subscriber subscriber = subscribers.get(subscriberMail);
        if(subscriber == null){
            throw new NotFoundException("Couldn't get this subscriber");
        }
        if(subscriber.getFirstName().equals(newFirstName)){
            throw new Exception("You are already using this name as first name");
        }
        subscriber.setFirstName(newFirstName);
    }

    @Override
    public void wantToEditLastName(String subscriberMail, String newLastName) throws Exception {
        if(subscriberMail == null || newLastName == null){
            throw new Exception("Something went wrong in editing the last name of the subscriber");
        }
        Subscriber subscriber = subscribers.get(subscriberMail);
        if(subscriber == null){
            throw new NotFoundException("Couldn't get this subscriber");
        }
        if(subscriber.getLastName().equals(newLastName)){
            throw new Exception("You are already using this name as last name");
        }
        subscriber.setLastName(newLastName);
    }

    @Override
    public void changeStatusToOnline(Subscriber subscriber) throws Exception{
        Subscriber onlineSubscriber = subscribers.get(subscriber.getEmailAddress());
        onlineSubscriber.setStatus(Status.ONLINE);
    }

    @Override
    public void setSubscriberWantAlert(String userMail)
    {

    }


}
