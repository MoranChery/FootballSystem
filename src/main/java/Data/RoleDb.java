package Data;

import Model.Enums.RoleType;
import Model.Role;

import java.util.List;

public interface RoleDb extends Db {

    void createRole(String emailAddress, String teamName, RoleType roleType);

    void createRoleInSystem(String emailAddress, RoleType roleType);

    List<Role> getRoles(String emailAddress) throws Exception;
    void removeRoleFromTeam(String EmailAddressToRemove, String teamName, RoleType roleType) throws Exception;

    void removeRoleFromSystem(String emailAddressToRemove) throws Exception;

    void removeRole(String emailAddressToRemove, RoleType roleType) throws Exception;

    Role getRole(String emailAddress) throws Exception;
}
