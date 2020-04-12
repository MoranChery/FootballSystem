package Data;
<<<<<<< HEAD

import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;
=======
import Model.UsersTypes.Subscriber;
>>>>>>> 5c8d1e9a788270f33fd370de7e0a433d3d2e0c1d

import java.util.HashMap;
import java.util.Map;

public class SubscriberDbInMemory implements SubscriberDb {
    /*structure like the DB of subscriber*/
    //key: username
    //value: subscriber class
    private Map<String, Model.UsersTypes.Subscriber> subscriberMap;

    public SubscriberDbInMemory() {
        this.subscribers = new HashMap<>();
    }

    private static SubscriberDbInMemory ourInstance = new SubscriberDbInMemory();

    public static SubscriberDbInMemory getInstance() {
        return ourInstance;
    }

    public SubscriberDbInMemory() {
        subscriberMap = new HashMap<>();
    }

    /**
     * for the tests - create player in DB
     * @param subscriber
     * @throws Exception
     */
    @Override
    public void createSubscriber(Subscriber subscriber) throws Exception {
        if(subscriberMap.containsKey(subscriber.getUsername())) {
            throw new Exception("subscriber already exists");
        }
        subscriberMap.put(subscriber.getUsername(), subscriber);
    }


    @Override
    public Subscriber getSubscriber(String username) throws Exception {
        if (!subscriberMap.containsKey(username)) {
            throw new Exception("Subscriber not found");
        }
        return subscriberMap.get(username);
    }
    @Override
    public void deleteAll() {
        subscribers.clear();
    }
}
