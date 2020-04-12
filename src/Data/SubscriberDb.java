package Data;

import Model.UsersTypes.Subscriber;

public interface SubscriberDb {
        void createSubscriber(Subscriber subscriber) throws Exception;
        Subscriber getSubscriber(Integer username) throws Exception;

        void deleteAll();
}
