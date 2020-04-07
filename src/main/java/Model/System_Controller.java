package Model;

import Model.UsersTypes.Fan;

import java.util.ArrayList;

public class System_Controller {

    private static System_Controller ourInstance = new System_Controller();

    public static System_Controller getInstance() {
        return ourInstance;
    }
    private ArrayList<Fan> alertTofans;
    private void AddFanToGetAlerts(Fan newFan){

        if(newFan != null && !(alertTofans.contains(newFan))){
            alertTofans.add(newFan);
        }

    }

    private System_Controller() {

    }

    public static void main(String[] args){
        System.out.println("try");
    }

}
