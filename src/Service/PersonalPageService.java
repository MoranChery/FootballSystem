package Service;

import Controller.PersonalPageController;

public class PersonalPageService {

    private PersonalPageController pageController;

    public PersonalPageService() {
        pageController = new PersonalPageController();
    }

    public void addPageToFanList(Integer pageID, Integer fanId, String firstName, String lastName, String fanMail, String fanPassword) throws Exception {
        pageController.addPageToFanList(pageID,fanId,firstName,lastName,fanMail,fanPassword);
    }
}
