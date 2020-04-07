package Model;

import Model.UsersTypes.Fan;

import java.util.ArrayList;

public class PersonalPage {
    private ArrayList<Fan> fansFollowingThisPage;

    public PersonalPage() {
        fansFollowingThisPage = new ArrayList<>();
    }

    private void AddFanToListOfFollowers(Fan fanThatFollow){
        if(fanThatFollow != null && !(fansFollowingThisPage.contains(fanThatFollow))){
            fansFollowingThisPage.add(fanThatFollow);
            // write it in the log
        }
    }







    public ArrayList<Fan> getFansFollowingThisPage() {
        return fansFollowingThisPage;
    }

    public void setFansFollowingThisPage(ArrayList<Fan> fansFollowingThisPage) {
        this.fansFollowingThisPage = fansFollowingThisPage;
    }
}
