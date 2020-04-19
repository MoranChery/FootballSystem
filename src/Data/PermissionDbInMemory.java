package Data;

import Model.Enums.PermissionType;
import Model.Enums.RoleType;
import Model.FinancialActivity;

import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionDbInMemory implements PermissionsDb{
    Map<String, List<PermissionType>> permissions;
    private static PermissionDbInMemory ourInstance = new PermissionDbInMemory();

    public static PermissionDbInMemory getInstance() {
        return ourInstance;
    }

    public PermissionDbInMemory() {
        this.permissions = new HashMap<>();
    }

    @Override
    public void createPermission(String emailAddress, PermissionType permissionType) throws Exception { ;
        if(permissions.containsKey(emailAddress)){
            List<PermissionType> rolesList = permissions.get(emailAddress);
            rolesList.add(permissionType);
        } else {
            List<PermissionType> permissionsList = new ArrayList<>();
            permissionsList.add(permissionType);
            permissions.put(emailAddress, permissionsList);
        }
    }

    @Override
    public List<PermissionType> getPermissions(String emailAddress) throws Exception {
       return permissions.getOrDefault(emailAddress,new ArrayList<>());
    }
}
