package Data;

import Model.PersonalPage;
import Model.UsersTypes.Fan;

public interface PersonalPageDb {
    void addPageToFanList(String pageID, Fan fanToAdd) throws Exception;
    PersonalPage getPage(String pageId) throws NotFoundException;
    void createPage(String pageID) throws Exception;
}
