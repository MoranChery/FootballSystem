package Model.UsersTypes;

import Model.Enums.AlertWay;
import Model.PersonalPage;
import Model.System_Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Fan extends Subscriber {

    private HashMap<Integer, PersonalPage> myPages;
    private System_Controller myController;
    // personal data
    private String password;
    private String emailAddress;


    public Fan(Integer id, String firstName, String lastName, String password, String emailAddress) {
        super(id, firstName, lastName);
        this.password = password;
        this.emailAddress = emailAddress;
        myController = System_Controller.getInstance();
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


    @Override
    public Integer getId() {
        return null;
    }

    // getters & setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public HashMap<Integer, PersonalPage> getMyPages() {
        return myPages;
    }

    public void setMyPages(HashMap<Integer, PersonalPage> myPages) {
        this.myPages = myPages;
    }
}
