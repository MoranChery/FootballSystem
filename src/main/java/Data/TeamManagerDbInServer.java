package Data;

import Model.Enums.PermissionType;
import Model.Enums.PlayerRole;
import Model.Enums.Status;
import Model.Team;
import Model.UsersTypes.Player;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamManagerDbInServer implements TeamManagerDb{
    private static TeamManagerDbInServer ourInstance = new TeamManagerDbInServer();

    public static TeamManagerDbInServer getInstance() {
        return ourInstance;
    }

    @Override
    public void insertTeamManager(TeamManager teamManager) throws Exception {
        if(teamManager == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into team_manager(email_address,team,owned_by_email)"
                    + " values (?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, teamManager.getEmailAddress());
            preparedStmt.setString (2, teamManager.getTeam());
            preparedStmt.setString (3, teamManager.getOwnedByEmail());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public TeamManager getTeamManager(String teamManagerEmailAddress) throws Exception {
        if (teamManagerEmailAddress == null) {
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try{
            String query = "select * from subscriber, team_manager where subscriber.email_address = team_manager.email_address  and subscriber.email_address = \'" + teamManagerEmailAddress + "\'";
            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            // checking if ResultSet is empty
            if (rs.next() == false) {
                throw new NotFoundException("TeamManager not found");
            }

            String userName = rs.getString("email_address");
            String password = rs.getString("password");
            Integer id = rs.getInt("id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String status = rs.getString("status");
            String team = rs.getString("team");
            String owned_by_email = rs.getString("owned_by_email");

            TeamManager teamManager = new TeamManager(userName, id, first_name, last_name,owned_by_email);
            teamManager.setPassword(password);
            teamManager.setTeam(team);
            teamManager.setStatus(Status.valueOf(status));

            query = "select * from permission where permission.email_address = \'" + teamManagerEmailAddress + "\'";
            preparedStmt = conn.createStatement();
            rs = preparedStmt.executeQuery(query);
            List<PermissionType> permissionTypes = new ArrayList<>();

            while(rs.next()){
                String email_address = rs.getString("email_address");
                String permission_type = rs.getString("permission_type");
                PermissionType permissionType = PermissionType.valueOf(permission_type);
                permissionTypes.add(permissionType);
            }

            teamManager.setPermissionTypes(permissionTypes);
            return teamManager;
        } finally {
            conn.close();
        }
    }

    @Override
    public void subscriptionTeamManager(String team, String teamOwnerId, Subscriber subscriber, List<PermissionType> permissionTypes) throws Exception {
        if(team == null || teamOwnerId == null || subscriber == null || permissionTypes == null){
            throw new NullPointerException("bad input");
        }

        Connection conn = DbConnector.getConnection();
        try{
            String query = "select * from subscriber, team_manager where subscriber.email_address = team_manager.email_address and subscriber.email_address = \'" + subscriber.getEmailAddress() + "\'";

            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            // checking if ResultSet is empty
            if (rs.next()) {
                throw new Exception("Team Manager to add already exists");
            }
            query = "select * from subscriber, team_owner where subscriber.email_address = team_owner.email_address and subscriber.email_address = \'" + teamOwnerId + "\'";

            preparedStmt = conn.createStatement();
            rs = preparedStmt.executeQuery(query);

            if (rs.next() == false) {
                throw new Exception("Major Team Owner not found");
            }

            insertTeamManager(new TeamManager(team,subscriber,teamOwnerId,permissionTypes));
        } finally {
            conn.close();
        }
    }

    @Override
    public void removeSubscriptionTeamManager(String managerToRemoveEmail) throws Exception {
        if (managerToRemoveEmail == null) {
            throw new NullPointerException("bad input");
        }

        Connection conn = DbConnector.getConnection();
        try{
            String query = "DELETE FROM team_manager where email_address = \'" + managerToRemoveEmail + "\'";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int deletedRows = preparedStmt.executeUpdate();
            if (deletedRows == 0) {
                conn.close();
                throw new Exception("TeamManager not found");
            }
        } finally {
            conn.close();
        }
    }

    @Override
    public List<String> getAllTeamManagersOwnedBy(String ownerToRemove) throws SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            String query = "select * from  team_manager where team_manager.owned_by_email = \'" + ownerToRemove + "\'";

            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);
            List<String>  teamManagersByThis = new ArrayList<>();

            while(rs.next()){
                String currOwner = rs.getString("email_address");
                teamManagersByThis.add(currOwner);
            }
            return teamManagersByThis;
        } finally {
            conn.close();
        }
    }

    @Override
    public void updateTeamManagerDetails(String teamManagerEmailAddress, String firstName, String lastName, List<PermissionType> permissionTypes) throws NotFoundException, SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            String query = "UPDATE subscriber SET first_name = \'" + firstName + "\', last_name = \'" + lastName + "\' where email_address = \'" + teamManagerEmailAddress + "\'";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            int updatedRows = preparedStmt.executeUpdate();
            if(updatedRows == 0) {
                conn.close();
                throw new NotFoundException("TeamManager not found");
            }

            query = "DELETE from permission where email_address = \'" + teamManagerEmailAddress + "\'";
            preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeUpdate();

            if(permissionTypes != null && !permissionTypes.isEmpty()) {
                for(PermissionType permissionType : permissionTypes) {
                    query = "insert into permission (email_address, permission_type) values (?,?)";
                    preparedStmt = conn.prepareStatement(query);
                    preparedStmt.setString (1, teamManagerEmailAddress);
                    preparedStmt.setString (2, permissionType.name());
                    preparedStmt.execute();
                }
            }
        } finally {
            conn.close();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            Statement statement = conn.createStatement();
            statement.executeUpdate("delete from team_manager");
        } finally {
            conn.close();
        }
    }

    public static void main(String[] args) throws Exception {
        TeamManagerDbInServer teamManagerDbInServer = new TeamManagerDbInServer();
        TeamManager teamManager = new TeamManager( "email@gmail.com","1111", 1, "firstTeamManager", "lastTeamManager", "owner@gmail.com");
//
//        teamManagerDbInServer.insertTeamManager(teamManager);

        TeamManager teamManager1 = teamManagerDbInServer.getTeamManager("email@gmail.com");
        System.out.println(teamManager1);
    }
}
