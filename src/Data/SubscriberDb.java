package Data;

import Model.UsersTypes.Subscriber;

public interface SubscriberDb {
        void createSubscriber(Subscriber subscriber) throws Exception;
        Subscriber getSubscriber(String username) throws Exception;

        void deleteAll();
}
