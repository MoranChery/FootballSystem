package Data;

import Model.Enums.PermissionType;

import java.util.*;

public class PermissionDbInMemory implements PermissionDb {
    Map<String, List<PermissionType>> permissions;
    private static PermissionDbInMemory ourInstance = new PermissionDbInMemory();

    public static PermissionDbInMemory getInstance() {
        return ourInstance;
    }

    public PermissionDbInMemory() {
        this.permissions = new HashMap<>();
    }

    @Override
    public void insertPermission(String emailAddress, PermissionType permissionType) throws Exception { ;
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

    @Override
    public void insertSetPermission(String emailAddress, List<PermissionType> permissions) {

    }

    @Override
    public void deleteAll() {
        permissions.clear();
    }
}
