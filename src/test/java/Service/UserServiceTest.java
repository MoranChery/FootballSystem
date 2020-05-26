package Service;

import Controller.BaseEmbeddedSQL;
import org.junit.Test;

public class UserServiceTest extends BaseEmbeddedSQL
{

    private UserService userService = new UserService();

    @Test
    public void showInfoTest(){
        //TODO
        userService.showInfo();
    }
    @Test
    public void searchInfoTest(){
        //TODO
        userService.searchInfo();
    }

}
