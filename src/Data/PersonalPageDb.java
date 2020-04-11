package Data;

import Model.PersonalPage;
import Model.UsersTypes.Fan;

public interface PersonalPageDb {
    void addPageToFanList(Integer pageID, Fan fanToAdd) throws Exception;
    PersonalPage getPage(Integer pageId);
}
