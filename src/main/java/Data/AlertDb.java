package Data;

import Model.Alert;
import Model.Game;

import java.util.List;

public interface AlertDb extends Db {

    void insertAlertInDb(String email, Alert alert) throws Exception;
    Alert getAlert(String email, String alertID) throws Exception;
    List<Alert> getAlertsForUser(String emailAddress) throws Exception;
    boolean haveAlertInDB(String email) throws Exception;
    void removeUserFromTheAlertDB(String userMail) throws Exception;
    void removeAllTheAlertTheUserHave(String userMail) throws Exception;
    void removeAlertFromAllUsersInTheDB(String alertID) throws Exception;
    void removeAlertFromTheUserListOfAlerts(String userMail, String alertID) throws Exception;
}
