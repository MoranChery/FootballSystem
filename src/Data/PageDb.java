package Data;

import Model.Page;
import Model.PersonalPage;
import Model.Team;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Subscriber;

public interface PageDb extends Db {
    void addFanToPageListOfFans(String pageID, Fan fanToAdd) throws Exception;
    Page getPage(String pageId) throws NotFoundException;
    void createTeamPage(String pageID, Team team) throws Exception;
    void createPersonalPage(String pageID, Subscriber subscriber) throws Exception;
    void removePersonalPageFromDb(String pageId) throws Exception;
    }
