package Data;

import java.util.HashMap;
import java.util.Map;
import Model.UsersTypes.Subscriber;

public class SubscriberDbInMemory implements SubscriberDb, Db {
    /*structure like the DB of subscriber*/
    //key: username
    //value: subscriber class
    private Map<Integer, Model.UsersTypes.Subscriber> subscribers;

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
        if(subscribers.containsKey(subscriber.getId())) {
            throw new Exception("subscriber already exists");
        }
        subscribers.put(subscriber.getId(), subscriber);
    }


    @Override
    public Subscriber getSubscriber(String emailAddress) throws Exception {
        if (!subscribers.containsKey(emailAddress)) {
            throw new Exception("Subscriber not found");
        }
        return subscribers.get(emailAddress);
    }

    @Override
    public void deleteAll() {

        subscribers.clear();
    }
}
