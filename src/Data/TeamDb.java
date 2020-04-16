package Data;

import Model.Court;
import Model.Enums.TeamStatus;
import Model.FinancialActivity;
import Model.Team;
import Model.TeamPage;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

public interface TeamDb extends Db {
    void createTeam(String teamName) throws Exception;

    void addPlayer(String teamName, Player player) throws Exception;

    void addTeamManager(String teamName, TeamManager player, String ownedByEmail) throws Exception;

    Team getTeam(String teamName) throws Exception;
    
    void addCourt(String teamName, Court court) throws Exception;

    void addCoach(String teamName, Coach coach) throws Exception;

    void removePlayer(String teamName, String playerEmailAddress) throws Exception;

    void removeTeamManager(String teamName, String teamManagerEmailAddress) throws Exception;

    void removeCoach(String teamName, String coachEmailAddress) throws Exception;

    void removeCourt(String teamName, String courtName) throws Exception;

    void addFinancialActivity(String teamName, FinancialActivity financialActivity) throws Exception;

    void changeStatus(String teamName, TeamStatus teamStatus) throws Exception;

    void addTeamPage(TeamPage teamPage) throws Exception;
}
