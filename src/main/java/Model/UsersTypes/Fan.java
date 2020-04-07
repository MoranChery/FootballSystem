package Model.UsersTypes;

import Model.Enums.AlertWay;
import Model.PersonalPage;
import Model.System_Controller;

import java.util.ArrayList;
import java.util.Scanner;

public class Fan extends Subscriber {

    private ArrayList<PersonalPage> myPages;
    private Integer idInTheSystem;
    System_Controller myController;
    // personal data
    private String firstName;
    private String lastName;
    private String password;
    private String emailAddress;


    public Fan(Integer idInTheSystem, String firstName, String lastName, String password, String emailAddress) {
        this.idInTheSystem = idInTheSystem;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.emailAddress = emailAddress;
        myController = System_Controller.getInstance();
        myPages = new ArrayList<>();
    }


    @Override
    public Integer getId() {
        return idInTheSystem;
    }
    public void setId(Integer id) {
        this.idInTheSystem = id;
    }


    /**
     * this function add a selected page to the list of pages that the fan is following
     * @param pageToAdd is the personal page
     */
    private boolean AddPageToMyList(PersonalPage pageToAdd){
        if (pageToAdd != null && !(myPages.contains(pageToAdd))){
            myPages.add(pageToAdd);
            // write ti log - ask how we implements this
            return true;
        }
        return false;
    }


    private void askForAlertsAboutGames(){

        myController.displayAlertsTypes();

        Scanner myObj = new Scanner(System.in);
        String myChoise;
        System.out.println("Enter username");
        myChoise = myObj.nextLine();
        AlertWay alertWay = AlertWay.valueOf(myChoise);
        myController.AddFanToGetAlerts(alertWay,this);
    }


    public ArrayList<PersonalPage> getMyPages() {
        return myPages;
    }

    public void setMyPages(ArrayList<PersonalPage> myPages) {
        this.myPages = myPages;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

}
