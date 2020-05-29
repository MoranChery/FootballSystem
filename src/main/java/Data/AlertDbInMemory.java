package Data;

import Model.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlertDbInMemory implements AlertDb{

    private Map<String, List<Alert>> allSavedAlerts;
    private static AlertDbInMemory ourInstance = new AlertDbInMemory();
    public static AlertDbInMemory getInstance() {
        return ourInstance;
    }

    public AlertDbInMemory() {
        this.allSavedAlerts = new HashMap<>();
    }

    @Override
    public void insertAlertInDb(String email, Alert alert) throws Exception {
        if(alert == null){
            throw new Exception("cant add to db");
        }
        if(allSavedAlerts.containsKey(email)){
            List<Alert> alertList = allSavedAlerts.get(email);
            if(!alertList.contains(alert)){
                alertList.add(alert);

            }
        }
        // first alert for this email
        else {
            List<Alert> newAlertList = new ArrayList<>();
            newAlertList.add(alert);
            allSavedAlerts.put(email, newAlertList);
        }
    }

    @Override
    public Alert getAlert(String email, String alertID) throws Exception {
        List<Alert> alerts = getAlertsForUser(email);
        if(alerts != null || !alerts.isEmpty()){
            for (Alert a: alerts) {
                if (a.getAlertId().equals(alertID)){
                    return a;
                }
            }
        }
        return null;
    }


    @Override
    public List<Alert> getAlertsForUser(String emailAddress) throws Exception {
        if(emailAddress.isEmpty() || !allSavedAlerts.containsKey(emailAddress)){
            throw new Exception("Can't get alerts for this user");
        }
        return allSavedAlerts.get(emailAddress);
    }

    @Override
    public boolean haveAlertInDB(String email) throws Exception {

            if (allSavedAlerts.containsKey(email)) {

                if (allSavedAlerts.values().isEmpty() || allSavedAlerts.values().size() == 0) {

                    return false;
                }
                return true;
            }


        return false;
    }

    @Override
    public void removeUserFromTheAlertDB(String userMail) throws Exception {
        if(userMail.isEmpty()){
            throw new Exception("bad input");
        }
        if(!allSavedAlerts.containsKey(userMail)){
            throw new Exception("not in db");
        }
        if(!haveAlertInDB(userMail)){
            allSavedAlerts.remove(userMail);
        }
    }

    @Override
    public void removeAllTheAlertTheUserHave(String userMail) throws Exception {
        if(userMail.isEmpty()){
            throw new Exception("bad input");
        }
        if(!allSavedAlerts.containsKey(userMail)){
            throw new Exception("not in db");
        }
        allSavedAlerts.replace(userMail, null);
    }

    @Override
    public void removeAlertFromAllUsersInTheDB(String alertID) throws Exception {
        if(alertID.isEmpty()){
            throw new Exception("bad input");
        }
        for (String id: allSavedAlerts.keySet()) {
            List<Alert> alerts = getAlertsForUser(id);
            for (Alert a: alerts) {
                if(a.equals(alertID)){
                    alerts.remove(a);
                    if(alerts.isEmpty()){
                        removeAllTheAlertTheUserHave(id);
                        removeUserFromTheAlertDB(id);
                    }
                    else {
                        allSavedAlerts.replace(id, alerts);
                    }
                }
            }
        }

    }

    @Override
    public void removeAlertFromTheUserListOfAlerts(String userMail, String alertID) throws Exception {
        if(userMail.isEmpty() || alertID.isEmpty()){
            throw new Exception("bad input");
        }
        if(!allSavedAlerts.containsKey(userMail)){
            throw new Exception("not in db");
        }
        List<Alert> userAlerts = getAlertsForUser(userMail);
        for (Alert a: userAlerts) {
            if (a.getAlertId().equals(alertID)){
                userAlerts.remove(a);
                allSavedAlerts.replace(userMail, userAlerts);
            }
        }
    }

    @Override
    public void deleteAll() throws SQLException
    {

    }
}
