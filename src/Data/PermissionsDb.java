package Data;

import Model.Enums.PermissionType;
import Model.FinancialActivity;

import java.util.List;

public interface PermissionsDb {

    void createPermission(String emailAddress, PermissionType permissionType) throws Exception;

    List<PermissionType> getPermissions(String emailAddress) throws Exception;
}
