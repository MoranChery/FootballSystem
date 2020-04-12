package Data;

import Model.UsersTypes.Subscriber;

public interface SubscriberDb extends Db {
    void createSubscriber(Subscriber subscriber) throws Exception;

    Subscriber getSubscriber(Integer ownerToAdd) throws Exception;
}
