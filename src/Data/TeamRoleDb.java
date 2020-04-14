package Data;

import Model.Enums.RoleType;

import java.util.List;

public interface TeamRoleDb extends Db {

    void createRole(String emailAddress, String teamName, RoleType roleType);

    void createRoleInSystem(String emailAddress, RoleType roleType);

    List<Role> getRoles(String emailAddress) throws Exception;
    void removeRoleFromTeam(String EmailAddressToRemove, String teamName, RoleType roleType) throws Exception;

    void removeRoleFromSystem(String emailAddressToRemove) throws Exception;

    Role getRole(String emailAddress) throws Exception;
}
