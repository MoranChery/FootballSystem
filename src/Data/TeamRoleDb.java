package Data;

import Model.Enums.TeamRoleType;

import java.util.List;

public interface TeamRoleDb extends Db {

    void createTeamRole(String emailAddress, String teamName, TeamRoleType teamRoleType);
    List<TeamRole> getTeamRoles(String emailAddress) throws Exception;
    void removeTeamRole(String EmailAddressToRemove, String teamName, TeamRoleType roleType) throws Exception;
}
