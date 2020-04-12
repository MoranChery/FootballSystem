package Data;

public interface SubscriberDb {
        void createSubscriber(Model.UsersTypes.Subscriber subscriber) throws Exception;
        Model.UsersTypes.Subscriber getSubscriber(String username) throws Exception;

        void deleteAll();
}
