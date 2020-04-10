package Data;

import Model.Enums.TeamRoleType;

import java.util.List;

public interface TeamRoleDb {

    void createTeamRole(Integer id, String teamName, TeamRoleType teamRoleType);
    List<TeamRole> getTeamRoles(Integer id) throws Exception;
    void removeTeamRole(Integer ownerToRemove, String teamName, TeamRoleType owner) throws Exception;
}
