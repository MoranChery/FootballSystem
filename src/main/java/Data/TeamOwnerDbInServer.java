package Data;

import Model.Enums.Status;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TeamOwnerDbInServer implements TeamOwnerDb {


    private static TeamOwnerDbInServer ourInstance = new TeamOwnerDbInServer();

    public static TeamOwnerDbInServer getInstance() {
        return ourInstance;
    }

    @Override
    public void insertTeamOwner(TeamOwner teamOwner) throws Exception {
        if(teamOwner == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into team_owner (email_address,team,owned_by_email_address)"
                    + " values (?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, teamOwner.getEmailAddress());
            preparedStmt.setString (2, teamOwner.getTeam());
            preparedStmt.setString (3, teamOwner.getOwnedByEmailAddress());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public void updateTeamOwnerTeam(String  team, String teamOwnerEmailAddress) throws Exception {
        if(team == null || teamOwnerEmailAddress == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try{
            String query = "UPDATE team_owner SET team = \'" + team + "\' WHERE email_address = \'"+ teamOwnerEmailAddress + "\'" ;
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.executeUpdate();
        } finally {
            conn.close();
        }
    }

    @Override
    public TeamOwner getTeamOwner(String teamOwnerEmailAddress) throws Exception {
        if (teamOwnerEmailAddress == null) {
            throw new NullPointerException("bad input");
        }

        Connection conn = DbConnector.getConnection();
        try{
            String query = "select * from subscriber, team_owner where subscriber.email_address = team_owner.email_address and subscriber.email_address = \'" + teamOwnerEmailAddress + "\'";

            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            // checking if ResultSet is empty
            if (rs.next() == false) {
                throw new NotFoundException("TeamOwner not found");
            }

            String userName = rs.getString("email_address");
            String password = rs.getString("password");
            Integer id = rs.getInt("id");
            String first_name = rs.getString("first_name");
            String last_name = rs.getString("last_name");
            String status = rs.getString("status");
            String team = rs.getString("team");
            String owned_by_email = rs.getString("owned_by_email_address");
            TeamOwner teamOwner = new TeamOwner(userName, password, id, first_name, last_name,team);
            teamOwner.setStatus(Status.valueOf(status));
            teamOwner.setOwnedByEmailAddress(owned_by_email);


            query = "select * from  team_owner where team_owner.owned_by_email_address = \'" + teamOwnerEmailAddress + "\'";

            preparedStmt = conn.createStatement();
            rs = preparedStmt.executeQuery(query);
            List<String>  teamOwnersByThis = new ArrayList<>();

            while(rs.next()){
                String currOwner = rs.getString("owned_by_email_address");
                teamOwnersByThis.add(currOwner);
            }
            teamOwner.setTeamOwnersByThis(teamOwnersByThis);
            return teamOwner;

        } finally {
            conn.close();
        }
    }

    @Override
    public void subscriptionTeamOwner(String team, String teamOwnerId, Subscriber subscriber) throws Exception {
        if(team == null || teamOwnerId == null || subscriber == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try{
            String query = "select * from subscriber, team_owner where subscriber.email_address = team_owner.email_address and subscriber.email_address = \'" + subscriber.getEmailAddress() + "\'";

            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);

            // checking if ResultSet is empty
            if (rs.next()) {
                throw new Exception("TeamOwner to add already exists");
            }
            query = "select * from subscriber, team_owner where subscriber.email_address = team_owner.email_address and subscriber.email_address = \'" + teamOwnerId + "\'";

            preparedStmt = conn.createStatement();
            rs = preparedStmt.executeQuery(query);

            if (rs.next() == false) {
                throw new Exception("Major Team Owner not found");
            }
            insertTeamOwner(new TeamOwner(team,subscriber,teamOwnerId));
        } finally {
            conn.close();
        }
    }

    @Override
    public void removeSubscriptionTeamOwner(String ownerToRemoveEmail) throws Exception {
        if(ownerToRemoveEmail == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try{
            String query = "delete from team_owner where email_address = ? ";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, ownerToRemoveEmail);

            // execute the preparedstatement
            preparedStmt.execute();

        } finally {
            conn.close();
        }

    }

    @Override
    public List<String> getAllTeamOwnersOwnedBy(String teamOwnerEmail) throws SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            String query = "select * from  team_owner where team_owner.owned_by_email_address = \'" + teamOwnerEmail + "\'";

            Statement preparedStmt = conn.createStatement();
            ResultSet rs = preparedStmt.executeQuery(query);
            List<String>  teamOwnersByThis = new ArrayList<>();

            while(rs.next()){
                String currOwner = rs.getString("email_address");
                teamOwnersByThis.add(currOwner);
            }
            return teamOwnersByThis;
        } finally {
            conn.close();
        }
    }

    @Override
    public Set<String> getAllTeamOwnersInDB() {
        return null;
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        try{
            Statement statement = conn.createStatement();
            statement.executeUpdate("delete from team_owner");
        } finally {
            conn.close();
        }    }

//    public static void main(String[] args) throws Exception {
//        String ownerEmail = "owner@gmail.com";
//        TeamOwner teamOwner = new TeamOwner(null, new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName"), "owner@gmail.com");
////        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
//        TeamOwnerDbInServer teamOwnerDbInServer = new TeamOwnerDbInServer();
////        teamOwnerDbInServer.insertTeamOwner(teamOwner);
//        TeamOwner teamOwner1 = teamOwnerDbInServer.getTeamOwner(ownerEmail);
//        System.out.println(teamOwner1);
//
//        teamOwnerDbInServer.removeSubscriptionTeamOwner(ownerEmail);
//    }
}
