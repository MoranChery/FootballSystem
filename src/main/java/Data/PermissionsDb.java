package Data;

import Model.Enums.PermissionType;

import java.util.List;

public interface PermissionsDb extends Db{

    void createPermission(String emailAddress, PermissionType permissionType) throws Exception;

    List<PermissionType> getPermissions(String emailAddress) throws Exception;

}
