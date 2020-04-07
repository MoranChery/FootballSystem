package Model;

import Model.Enums.AlertWay;
import Model.UsersTypes.Fan;

import java.util.ArrayList;
import java.util.HashMap;

public class System_Controller {

    private static System_Controller ourInstance = new System_Controller();

    public static System_Controller getInstance() {
        return ourInstance;
    }

    private HashMap<Fan,AlertWay> fansToGetAlert;

    /**
     * this function adds fan to the list of fan that want to get alerts about games
     * @param choosenWay the type of the way to get alerts that the fan wants
     * @param fanWantsAlerts the fan
     */
    public void AddFanToGetAlerts(AlertWay choosenWay, Fan fanWantsAlerts){
        if(choosenWay != null && fanWantsAlerts != null){
            if(!(fansToGetAlert.containsKey(fanWantsAlerts))){
                fansToGetAlert.put(fanWantsAlerts,choosenWay);
                // write to log
                System.out.println(fanWantsAlerts.getFirstName() + " Now you will get alerts about games");
            }
            else {
                System.out.println("You are already registered to get alerts about games");
            }

        }
    }

    public void displayAlertsTypes(){
        // test this later to see if it works-after the commit because of all the errors
        System.out.println(java.util.Arrays.asList(AlertWay.values()));
    }



    private System_Controller() {

    }

    public static void main(String[] args){
        System.out.println("try");
    }

}
