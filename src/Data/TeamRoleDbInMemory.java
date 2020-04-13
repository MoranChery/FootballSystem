package Data;

import Model.Enums.TeamRoleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamRoleDbInMemory implements TeamRoleDb {
    private Map<String, List<TeamRole>> teamRoles;

    public TeamRoleDbInMemory() {
        this.teamRoles = new HashMap<>();
    }

    private static TeamRoleDbInMemory ourInstance = new TeamRoleDbInMemory();

    public static TeamRoleDbInMemory getInstance() {
        return ourInstance;
    }

    @Override
    public void createTeamRole(String emailAddress, String teamName, TeamRoleType teamRoleType){
        TeamRole teamRole = new TeamRole(teamName,teamRoleType);
        if(teamRoles.containsKey(emailAddress)){
            teamRoles.get(emailAddress).add(teamRole);
        } else {
            List<TeamRole> teamRolesList = new ArrayList<>();
            teamRolesList.add(teamRole);
            teamRoles.put(emailAddress,teamRolesList);
        }
    }

    @Override
    public List<TeamRole> getTeamRoles(String emailAddress) throws Exception {
        if(emailAddress == null || !teamRoles.containsKey(emailAddress)){
            return new ArrayList<>();
        }
        return teamRoles.get(emailAddress);
    }

    @Override
    public void removeTeamRole(String emailAddressToRemove, String teamName, TeamRoleType roleType) throws Exception {
        if(emailAddressToRemove == null || teamName == null || roleType == null) {
            throw new NullPointerException();
        }
        if(!teamRoles.containsKey(emailAddressToRemove)){
            throw new Exception("id not found");
        }
        List<TeamRole> teamRolesOfOwnerToRemove = teamRoles.get(emailAddressToRemove);
        for (TeamRole tr: teamRolesOfOwnerToRemove) {
            if(roleType.equals(tr.getTeamRoleType()) && teamName.equals(tr.getTeamName())){
                teamRolesOfOwnerToRemove.remove(tr);
                break;
            }
        }
    }

    @Override
    public void deleteAll() {
        teamRoles.clear();
    }

}
