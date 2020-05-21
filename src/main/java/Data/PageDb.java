package Data;

import Model.Page;
import Model.PageType;
import Model.Team;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Subscriber;

public interface PageDb extends Db {
    void addFanToPageFollowers(String pageID, Fan fanToAdd) throws Exception;
    Page getPage(String pageId) throws NotFoundException;
    void insertPage(String pageID, PageType pageType) throws Exception;
//    void createPersonalPage(String pageID, Subscriber subscriber) throws Exception;
    void removePersonalPageFromDb(String pageId) throws Exception;
    }
