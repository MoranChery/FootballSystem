package Data;

import Model.Enums.PermissionType;

import java.util.List;

public interface PermissionDb extends Db{

    void insertPermission(String emailAddress, PermissionType permissionType) throws Exception;

    List<PermissionType> getPermissions(String emailAddress) throws Exception;

    void insertSetPermission(String emailAddress, List<PermissionType> permissions) throws Exception;
}
