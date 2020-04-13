package Model.UsersTypes;

import Model.Enums.AlertWay;
import Model.PersonalPage;
import Model.System_Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Fan extends Subscriber {

    private HashMap<String, PersonalPage> myPages;


    public Fan(String emailAddress,String password, Integer id, String firstName, String lastName) {
        setRegisteringDetails(emailAddress, password, id, firstName, lastName);
        myPages = new HashMap<>();
    }

    //    private void AddPageToMyList(PersonalPage pageToAdd) {
//        if (pageToAdd != null && !(myPages.contains(pageToAdd))) {
//            myPages.add(pageToAdd);
//            // write ti log - ask how we implements this
//        }
//
//    }
//    private void askForAlertsAboutGames() {
//        myController.displayAlertsTypes();
//
//        Scanner myObj = new Scanner(System.in);
//        String myChoise;
//        System.out.println("Enter username");
//        myChoise = myObj.nextLine();
//        AlertWay alertWay = AlertWay.valueOf(myChoise);
//        myController.AddFanToGetAlerts(alertWay, this);
//    }

    // getters & setters

    public HashMap<String, PersonalPage> getMyPages() {
        return myPages;
    }

    public void setMyPages(HashMap<String, PersonalPage> myPages) {
        this.myPages = myPages;
    }
}
