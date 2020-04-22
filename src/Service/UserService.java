package Service;

import Controller.UserController;
import Model.Search;

public class UserService {
    private UserController userController;

    public void showInfo(){
        userController.showInfo();
    }
    public Search searchInfo(){
        return userController.searchInfo();
    }
}
