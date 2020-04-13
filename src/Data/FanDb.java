package Data;

import Model.Enums.Status;
import Model.PersonalPage;
import Model.UsersTypes.Fan;

public interface FanDb {

    void addPageToFanList(String fanMail, PersonalPage pageToAdd) throws Exception;
    Fan getFan(String fanMail) throws NotFoundException;
    void logOut(String fanMail, Status status) throws Exception;
    //void addComplaint

}
