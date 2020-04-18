package Data;

import java.util.HashMap;
import java.util.Map;
import Model.UsersTypes.Subscriber;

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


    /**
     * for the tests - create player in DB
     * @param subscriber
     * @throws Exception
     */
    @Override
    public void createSubscriber(Subscriber subscriber) throws Exception {
        if(subscribers.containsKey(subscriber.getEmailAddress())) {
            throw new Exception("subscriber already exists");
        }
        subscribers.put(subscriber.getEmailAddress(), subscriber);
    }


    @Override
    public Subscriber getSubscriber(String emailAddress) throws Exception {
        if (!subscribers.containsKey(emailAddress)) {
            throw new NotFoundException("Subscriber not found");
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
}
