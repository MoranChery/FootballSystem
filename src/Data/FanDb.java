package Data;

import Model.PersonalPage;
import Model.UsersTypes.Fan;

public interface FanDb {

    void addPageToFanList(Integer fanId, PersonalPage pageToAdd) throws Exception;
    Fan getFan(Integer fanId);

}
