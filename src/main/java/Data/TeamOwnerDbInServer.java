package Data;

import Model.Enums.Status;
import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;
import Service.TeamOwnerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TeamOwnerDbInServer implements TeamOwnerDb {
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

    }

    @Override
    public TeamOwner getTeamOwner(String teamOwnerEmailAddress) throws Exception {
        if (teamOwnerEmailAddress == null) {
            throw new NullPointerException("bad input");
        }

        Connection conn = DbConnector.getConnection();

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

        query = "select * from  team_owner where team_owner.owned_by_email_address = \'" + teamOwnerEmailAddress + "\'";

         preparedStmt = conn.createStatement();
         rs = preparedStmt.executeQuery(query);
         List<String>  teamOwnersByThis = new ArrayList<>();

        while(rs.next()){
            String currOwner = rs.getString("owned_by_email_address");
            teamOwnersByThis.add(currOwner);
        }

        conn.close();

        TeamOwner teamOwner = new TeamOwner(userName, password, id, first_name, last_name,team);
        teamOwner.setStatus(Status.valueOf(status));
        teamOwner.setTeamOwnersByThis(teamOwnersByThis);
        return teamOwner;
    }

    @Override
    public void subscriptionTeamOwner(String team, String teamOwnerId, Subscriber subscriber) throws Exception {

    }

    @Override
    public void removeSubscriptionTeamOwner(String ownerToRemoveEmail) throws Exception {

    }

    @Override
    public List<String> getAllTeamOwnersOwnedBy(String teamOwnerEmail) {
        return null;
    }

    @Override
    public Set<String> getAllTeamOwnersInDB() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    public static void main(String[] args) throws Exception {
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(null, new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName"), "owner@gmail.com");
//        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        TeamOwnerDbInServer teamOwnerDbInServer = new TeamOwnerDbInServer();
//        teamOwnerDbInServer.insertTeamOwner(teamOwner);
        TeamOwner teamOwner1 = teamOwnerDbInServer.getTeamOwner(ownerEmail);
        System.out.println(teamOwner1);
    }
}
