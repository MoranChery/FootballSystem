package Data;

import Model.Enums.TeamRoleType;

import java.util.List;

public interface TeamRoleDb extends Db {

    void createTeamRole(Integer id, String teamName, TeamRoleType teamRoleType);
    List<TeamRole> getTeamRoles(Integer id) throws Exception;
    void removeTeamRole(Integer idToRemove, String teamName, TeamRoleType roleType) throws Exception;
}
