package Data;

import Model.Enums.PermissionType;

import java.util.List;

public interface PermissionDb extends Db{

    void createPermission(String emailAddress, PermissionType permissionType) throws Exception;

    List<PermissionType> getPermissions(String emailAddress) throws Exception;

}
