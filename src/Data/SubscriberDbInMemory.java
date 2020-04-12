package Data;
import Model.UsersTypes.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class SubscriberDbInMemory implements SubscriberDb {
    /*structure like the DB of subscriber*/
    //key: username
    //value: subscriber class
    private Map<String, Model.UsersTypes.Subscriber> subscriberMap;

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
}
