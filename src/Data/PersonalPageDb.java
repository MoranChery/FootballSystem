package Data;

import Model.Page;
import Model.PersonalPage;
import Model.Team;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Subscriber;

public interface PersonalPageDb {
    void addPageToFanList(String pageID, Fan fanToAdd) throws Exception;
    Page getPage(String pageId) throws NotFoundException;
    //    void createPage(String pageID) throws Exception;
    void createTeamPage(String pageID, Team team) throws Exception;
    void createPersonalPage(String pageID, Subscriber subscriber) throws Exception;

    }
