package Data;

import Controller.SubscriberController;
import Model.Alert;
import Model.Enums.RoleType;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;

import java.util.*;

public class TeamOwnerDbInMemory implements TeamOwnerDb{

    /*structure like the DB of teamOwners*/
    private Map<String, TeamOwner> teamOwners;

    public TeamOwnerDbInMemory() throws Exception {
        teamOwners = new HashMap<>();
        TeamOwner teamOwner = new TeamOwner( "teamOwner@gmail.com", "teamOwner", 123123123,"teamOwnerName", "teamOwnerLastName");
        Alert alert  = new Alert("new ", "new");
//        AlertDbInMemory alertDbInMemory = AlertDbInMemory.getInstance();
//        alertDbInMemory.insertAlertInDb("teamOwner@gmail.com" , alert);
        AlertDbInServer alertDbInServer = AlertDbInServer.getInstance();
        alertDbInServer.insertAlertInDb("teamOwner@gmail.com" , alert);
        SubscriberController subscriberController = new SubscriberController();
        subscriberController.createSubscriber(teamOwner);
        RoleDbInMemory.getInstance().createRoleInSystem("teamOwner@gmail.com", RoleType.TEAM_OWNER);
    }

    public static TeamOwnerDbInMemory ourInstance;

    static {
        try {
            ourInstance = new TeamOwnerDbInMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static TeamOwnerDbInMemory getInstance() {
        return ourInstance;
    }

    @Override
    public void updateTeamOwnerTeam(String team, String teamOwnerEmailAddress) throws Exception {
        TeamOwner teamOwner = teamOwners.get(teamOwnerEmailAddress);
        if(teamOwner.getTeam() != null){
            throw new Exception("This teamOwner has already team");
        }
        teamOwner.setTeam(team);
//        team.getTeamOwners().put(teamOwnerEmailAddress,teamOwner);
    }

    @Override
    public TeamOwner getTeamOwner(String teamOwnerEmailAddress) throws Exception {
        if(teamOwnerEmailAddress == null || !teamOwners.containsKey(teamOwnerEmailAddress)){
            throw new NotFoundException("TeamOwner not found");
        }
        return teamOwners.get(teamOwnerEmailAddress);
    }

    @Override
    public void insertTeamOwner(TeamOwner teamOwner) throws Exception {
        if(teamOwner == null) {
            throw new NullPointerException("bad input");
        }
        String teamOwnerEmailAddress = teamOwner.getEmailAddress();
        if (teamOwners.containsKey(teamOwnerEmailAddress)) {
            throw new Exception("TeamOwner already exists");
        }
        teamOwners.put(teamOwnerEmailAddress, teamOwner);
    }


    @Override
    public void subscriptionTeamOwner(String team, String teamOwnerEmail, Subscriber subscriber) throws Exception {
        if(team == null || teamOwnerEmail == null || subscriber == null){
            throw new NullPointerException();
        }
        if(teamOwners.containsKey(subscriber.getEmailAddress())){
            throw new Exception("TeamOwner to add already exists");
        }
        if(!teamOwners.containsKey(teamOwnerEmail)){
            throw new Exception("Major Team Owner not found");
        }
        TeamOwner teamOwner = new TeamOwner(team,subscriber,teamOwnerEmail);
        String emailAddressToAdd = teamOwner.getEmailAddress();
        TeamOwner teamOwnerMajor = teamOwners.get(teamOwnerEmail);
        teamOwnerMajor.getTeamOwnersByThis().add(teamOwner.getEmailAddress());
        teamOwners.put(emailAddressToAdd,teamOwner);
//        Map<String, TeamOwner> teamOwners = team.getTeamOwners();
//        teamOwners.put(emailAddressToAdd,teamOwner);
    }

    @Override
    public void removeSubscriptionTeamOwner(String ownerToRemoveEmail) throws Exception {
        if(ownerToRemoveEmail == null){
            throw new NullPointerException();
        }
        if(!teamOwners.containsKey(ownerToRemoveEmail)){
            throw new Exception("TeamOwner not found");
        }

        TeamOwner removeTeamOwner = teamOwners.remove(ownerToRemoveEmail);
        String ownedByEmailAddress = removeTeamOwner.getOwnedByEmailAddress();
        TeamOwner teamOwner = getTeamOwner(ownedByEmailAddress);
        List<String> teamOwnersByThis = teamOwner.getTeamOwnersByThis();
        teamOwnersByThis.remove(ownerToRemoveEmail);
//        Team team = removeTeamOwner.getTeam();
//        team.getTeamOwners().remove(ownerToRemoveEmail);
    }

    @Override
    public List<String> getAllTeamOwnersOwnedBy(String teamOwnerEmail) {
        List<String> teamOwnersOwnedBy = new ArrayList<>();
        for (TeamOwner tOwner: teamOwners.values()) {
            if(teamOwnerEmail.equals(tOwner.getOwnedByEmailAddress())){
                teamOwnersOwnedBy.add(tOwner.getEmailAddress());
            }
        }
        return teamOwnersOwnedBy;
    }

    @Override
    public Set<String> getAllTeamOwnersInDB() {
        return teamOwners.keySet();
    }

    @Override
    public void deleteAll() {
        teamOwners.clear();
    }

}
