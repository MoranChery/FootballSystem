package Model.UsersTypes;

import Model.PersonalPage;

import java.util.ArrayList;

public class Fan extends Subscriber {


    private ArrayList<PersonalPage> myPages;
    private Integer id;

    @Override
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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






    public ArrayList<PersonalPage> getMyPages() {
        return myPages;
    }

    public void setMyPages(ArrayList<PersonalPage> myPages) {
        this.myPages = myPages;
    }
}
