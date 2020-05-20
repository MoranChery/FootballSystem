package Data;

import Model.Enums.RoleType;
import Model.Role;
import Model.UsersTypes.TeamOwner;

import java.sql.SQLException;
import java.util.List;

public interface RoleDb extends Db {

    void insertRole(String emailAddress, String teamName, RoleType roleType) throws SQLException;

    void createRoleInSystem(String emailAddress, RoleType roleType) throws SQLException;

    List<Role> getRoles(String emailAddress) throws Exception;
    void removeRoleFromTeam(String EmailAddressToRemove, String teamName, RoleType roleType) throws Exception;

    void removeRoleFromSystem(String emailAddressToRemove) throws Exception;

    void removeRole(String emailAddressToRemove, RoleType roleType) throws Exception;

    Role getRole(String emailAddress) throws Exception;

    void updateTeam(String teamName, String email) throws SQLException;
}
