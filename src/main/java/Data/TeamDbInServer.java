package Data;

import Model.Court;
import Model.Enums.PermissionType;
import Model.Enums.TeamStatus;
import Model.FinancialActivity;
import Model.Team;
import Model.TeamPage;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Consumer;

public class TeamDbInServer implements TeamDb{


    @Override
    public void insertTeam(String teamName) throws Exception {

    }

    @Override
    public void insertTeam(String teamName, Double budget, TeamStatus active) throws Exception {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into team (team_name,budget,team_status)"
                    + " values (?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, teamName);
            preparedStmt.setDouble (2, budget);
            preparedStmt.setString (3, active.name());
            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public void addPlayer(String teamName, Player player) throws Exception {

    }

    @Override
    public void addTeamManager(String teamName, TeamManager teamManager, List<PermissionType> permissionTypes, String ownedByEmail) throws Exception {

    }

    @Override
    public Team getTeam(String teamName) throws Exception {
        return null;
    }

    @Override
    public void addCourt(String teamName, Court court) throws Exception {

    }

    @Override
    public void addCoach(String teamName, Coach coach) throws Exception {

    }

    @Override
    public void removePlayer(String teamName, String playerEmailAddress) throws Exception {

    }

    @Override
    public void removeTeamManager(String teamName, String teamManagerEmailAddress) throws Exception {

    }

    @Override
    public void removeCoach(String teamName, String coachEmailAddress) throws Exception {

    }

    @Override
    public void removeCourt(String teamName, String courtName) throws Exception {

    }

    @Override
    public void addFinancialActivity(String teamName, FinancialActivity financialActivity) throws Exception {

    }

    @Override
    public void changeStatus(String teamName, TeamStatus teamStatus) throws Exception {

    }

    @Override
    public void addTeamPage(TeamPage teamPage) throws Exception {

    }

    @Override
    public void deleteAll() {

    }

    public static void main(String[] args) throws Exception {
        TeamDbInServer teams = new TeamDbInServer();
        teams.insertTeam("noy",100.0,TeamStatus.ACTIVE);

    }
}

