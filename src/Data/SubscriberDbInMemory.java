package Data;

import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;

import java.util.HashMap;
import java.util.Map;

public class SubscriberDbInMemory implements SubscriberDb{
    private Map<Integer, Subscriber> subscribers;

    public SubscriberDbInMemory() {
        this.subscribers = new HashMap<>();
    }

    private static SubscriberDbInMemory ourInstance = new SubscriberDbInMemory();

    public static SubscriberDbInMemory getInstance() {
        return ourInstance;
    }

    @Override
    public void createSubscriber(Subscriber subscriber) throws Exception {
        Integer subscriberId = subscriber.getId();
        if (subscribers.containsKey(subscriberId)) {
            throw new Exception("subscriber already exists");
        }
        subscribers.put(subscriberId, subscriber);
    }
    @Override
    public Subscriber getSubscriber(Integer id) throws Exception {
        if(id == null || !subscribers.containsKey(id)){
            throw new Exception("subscriber not found");
        }
        return subscribers.get(id);
    }

    @Override
    public void deleteAll() {
        subscribers.clear();
    }
}
