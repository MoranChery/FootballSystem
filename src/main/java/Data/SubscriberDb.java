package Data;

import Model.UsersTypes.Subscriber;

import java.sql.SQLException;

public interface SubscriberDb extends Db{
        void insertSubscriber(Subscriber subscriber) throws Exception;
        Subscriber getSubscriber(String username) throws Exception;
        boolean removeSubscriberFromDB(Subscriber subscriber);
        void wantToEditPassword(String subscriberMail, String newPassword) throws Exception;
        void wantToEditFirstName(String subscriberMail, String newFirstName) throws Exception;
        void wantToEditLastName(String subscriberMail, String newLastName) throws Exception;
        void logOut(String subscriberMail) throws Exception;
        void changeStatusToOnline(Subscriber subscriber) throws Exception;

    void setSubscriberWantAlert(String userMail) throws Exception;
}
