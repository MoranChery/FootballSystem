package Data;

import Model.Court;
import Model.Enums.*;
import Model.FinancialActivity;
import Model.Team;
import Model.TeamPage;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TeamDbInServer implements TeamDb{
    private static TeamDbInServer ourInstance = new TeamDbInServer();

    public static TeamDbInServer getInstance() {
        return ourInstance;
    }

    @Override
    public void insertTeam(String teamName) throws Exception {
        insertTeam(teamName, 0.0, TeamStatus.ACTIVE);
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
        } catch(SQLIntegrityConstraintViolationException e) {
            throw new Exception("Team already exist in the system");
        }
        finally {
            conn.close();
        }
    }

    @Override
    public void addPlayer(String teamName, Player player) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE player  SET team = \'" + teamName +  "\'  WHERE player.email_address =  \'"+ player.getEmailAddress() + "\'" ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void addTeamManager(String teamName, TeamManager teamManager, List<PermissionType> permissionTypes, String ownedByEmail) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE team_manager  SET team = \'" + teamName +  "\'  WHERE team_manager.email_address =  \'"+ teamManager.getEmailAddress() + "\'" ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public Team getTeam(String teamName) throws Exception {
        if (teamName == null) {
            throw new NullPointerException("bad input");
        }

        Team team = new Team();

        Connection conn = DbConnector.getConnection();

        String query = "select * from team where team.team_name = \'" + teamName + "\'";

        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false) {
            throw new NotFoundException("Team not found");
        }

        String team_name = rs.getString("team_name");
        Double budget = rs.getDouble("budget");
        String team_status = rs.getString("team_status");
        String court = rs.getString("court");


        query = "select * from team_owner join subscriber on team_owner.email_address = subscriber.email_address where team_owner.team = \'" + teamName + "\'";

        preparedStmt = conn.createStatement();
        rs = preparedStmt.executeQuery(query);
        Map<String,TeamOwner> owners = new HashMap<>();

        while(rs.next()){
            String userName = rs.getString("email_address");
            String password = rs.getString("password");
            Integer id = rs.getInt("id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String status = rs.getString("status");
            String owned_by_email_address = rs.getString("owned_by_email_address");
            TeamOwner teamOwner = new TeamOwner(userName, password, id, first_name, last_name);
            teamOwner.setTeam(rs.getString("team"));
            teamOwner.setStatus(Status.valueOf(status));
            teamOwner.setOwnedByEmailAddress(owned_by_email_address);

            owners.put(userName, teamOwner);
        }
        query = "select * from player join subscriber on player.email_address = subscriber.email_address where player.team = \'" + teamName + "\'";

        preparedStmt = conn.createStatement();
        rs = preparedStmt.executeQuery(query);
        // checking if ResultSet is empty

        Map<String,Player> players = new HashMap<>();

        while(rs.next()){
            String userName = rs.getString("email_address");
            String password = rs.getString("password");
            Integer id = rs.getInt("id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String status = rs.getString("status");
            java.util.Date birth_date =  new java.sql.Date(rs.getDate("birth_date").getTime());
            String player_role = rs.getString("player_role");
            Player player = new Player(userName, id, first_name, last_name, birth_date, PlayerRole.valueOf(player_role));
            player.setPassword(password);
            player.setTeam(rs.getString("team"));
            player.setStatus(Status.valueOf(status));

            players.put(userName, player);
        }

        query = "select * from team_manager join subscriber on team_manager.email_address = subscriber.email_address where team_manager.team = \'" + teamName + "\'";

        preparedStmt = conn.createStatement();
        rs = preparedStmt.executeQuery(query);
        Map<String,TeamManager> teamManagers = new HashMap<>();

        while(rs.next()){
            String userName = rs.getString("email_address");
            String password = rs.getString("password");
            Integer id = rs.getInt("id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String status = rs.getString("status");
            String owned_by_email = rs.getString("owned_by_email");
            TeamManager teamManager = new TeamManager(userName, id, first_name, last_name,owned_by_email);
            teamManager.setPassword(password);
            teamManager.setTeam(rs.getString("team"));
            teamManager.setStatus(Status.valueOf(status));

            String queryPermission = "select * from permission where permission.email_address = \'" + userName + "\'";
            Statement preparedStmtPermission = conn.createStatement();
            ResultSet rsPermissions = preparedStmtPermission.executeQuery(queryPermission);
            List<PermissionType> permissionTypes = new ArrayList<>();

            while(rsPermissions.next()){
                String email_address = rsPermissions.getString("email_address");
                String permission_type = rsPermissions.getString("permission_type");
                PermissionType permissionType = PermissionType.valueOf(permission_type);
                permissionTypes.add(permissionType);
            }
            teamManager.setPermissionTypes(permissionTypes);
            teamManagers.put(userName, teamManager);
        }

        query = "select * from coach join subscriber on coach.email_address = subscriber.email_address where coach.team = \'" + teamName + "\'";

        preparedStmt = conn.createStatement();
        rs = preparedStmt.executeQuery(query);
        Map<String,Coach> coaches = new HashMap<>();

        while(rs.next()){
            String userName = rs.getString("email_address");
            String password = rs.getString("password");
            Integer id = rs.getInt("id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String status = rs.getString("status");
            String coach_role = rs.getString("coach_role");
            String coach_qualification = rs.getString("qualification_coach");
            Coach coach = new Coach(userName, id, first_name, last_name, CoachRole.valueOf(coach_role),QualificationCoach.valueOf(coach_qualification));
            coach.setPassword(password);
            coach.setTeam(rs.getString("team"));
            coach.setStatus(Status.valueOf(status));

            coaches.put(userName, coach);
        }

        query = "select * from court where court.court_name = \'" + court + "\'";

        preparedStmt = conn.createStatement();
        rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next()) {
            String court_name = rs.getString("court_name");
            String court_city = rs.getString("court_city");
            Court courtToAdd = new Court(court_name, court_city);
            team.setCourt(courtToAdd);

        }
        conn.close();
        team.setTeamName(team_name);
        team.setBudget(budget);
        team.setTeamStatus(TeamStatus.valueOf(team_status));
        team.setTeamOwners(owners);
        team.setTeamManagers(teamManagers);
        team.setPlayers(players);
        team.setCoaches(coaches);
        return team;    }

    @Override
    public void addCourt(String teamName, Court court) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE team  SET court = \'" + court.getCourtName() +  "\'  WHERE team.team_name =  \'"+ teamName + "\'" ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void addCoach(String teamName, Coach coach) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE coach  SET team = \'" + teamName +  "\'  WHERE coach.email_address =  \'"+ coach.getEmailAddress() + "\'" ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void removePlayer(String teamName, String playerEmailAddress) throws Exception {

    }

    @Override
    public void removeTeamManager(String teamName, String teamManagerEmailAddress) throws Exception {

    }

    @Override
    public void removeCoach(String teamName, String coachEmailAddress) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE coach  SET team = null WHERE coach.email_address =  \'"+ coachEmailAddress + "\' AND team = \'" + teamName + "\'" ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void removeCourt(String teamName, String courtName) throws Exception {

    }

    @Override
    public void addFinancialActivity(String teamName, FinancialActivity financialActivity) throws Exception {

    }

    @Override
    public void changeStatus(String teamName, TeamStatus teamStatus) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE team  SET team_status = \'" + teamStatus.name() + "\' WHERE team.team_name =  \'"+ teamName + "\'";
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void addTeamPage(TeamPage teamPage) throws Exception {

    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();

        statement.executeUpdate("delete from team");
        conn.close();
    }

    public static void main(String[] args) throws Exception {
        TeamDbInServer teams = new TeamDbInServer();
//        teams.insertTeam("noy",100.0,TeamStatus.ACTIVE);
        Court court = new Court("courtName", "courtCity");
        teams.addCourt("noy",court);
        Coach coach = new Coach("coach@gmail.com", "1234",1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);

        teams.addCoach("noy",coach);
        teams.changeStatus("EXISTS", TeamStatus.ACTIVE);

    }
}

