package Data;

import Model.Enums.TeamRoleType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamRoleDbInMemory implements TeamRoleDb {
    private Map<Integer, List<TeamRole>> teamRoles;

    public TeamRoleDbInMemory() {
        this.teamRoles = new HashMap<>();
    }

    @Override
    public void createTeamRole(Integer id, String teamName, TeamRoleType teamRoleType){
        TeamRole teamRole = new TeamRole(teamName,teamRoleType);
        if(teamRoles.containsKey(id)){
            teamRoles.get(id).add(teamRole);
        } else {
            List<TeamRole> teamRolesList = new ArrayList<>();
            teamRolesList.add(teamRole);
            teamRoles.put(id,teamRolesList);
        }
    }

    @Override
    public List<TeamRole> getTeamRoles(Integer id) throws Exception {
        if(id == null || !teamRoles.containsKey(id)){
            return new ArrayList<>();
        }
        return teamRoles.get(id);
    }

    @Override
    public void removeTeamRole(Integer idToRemove, String teamName, TeamRoleType roleType) throws Exception {
        if(idToRemove == null || teamName == null || roleType == null) {
            throw new NullPointerException();
        }
        if(!teamRoles.containsKey(idToRemove)){
            throw new Exception("id not found");
        }
        List<TeamRole> teamRolesOfOwnerToRemove = teamRoles.get(idToRemove);
        for (TeamRole tr: teamRolesOfOwnerToRemove) {
            if(roleType.equals(tr.getTeamRoleType()) && teamName.equals(tr.getTeamName())){
                teamRolesOfOwnerToRemove.remove(tr);
                break;
            }
        }
    }

}
