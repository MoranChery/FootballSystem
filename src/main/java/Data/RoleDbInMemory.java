package Data;

import Model.Enums.RoleType;
import Model.Role;

import java.sql.SQLException;
import java.util.*;

public class RoleDbInMemory implements RoleDb {
    private Map<String, List<Role>> teamRoles;

    public RoleDbInMemory() {
        this.teamRoles = new HashMap<>();
    }

    private static RoleDbInMemory ourInstance = new RoleDbInMemory();

    public static RoleDbInMemory getInstance() {
        return ourInstance;
    }


    @Override
    public void insertRole(String emailAddress, String teamName, RoleType roleType){
        Role role = new Role(teamName, roleType);
        if(teamRoles.containsKey(emailAddress)){
            List<Role> rolesList = teamRoles.get(emailAddress);
            rolesList.removeIf(tr -> roleType.equals(tr.getRoleType()));
            rolesList.add(role);
        } else {
            List<Role> rolesList = new ArrayList<>();
            rolesList.add(role);
            teamRoles.put(emailAddress, rolesList);
        }
    }

    @Override
    public void createRoleInSystem(String emailAddress, RoleType roleType){
        insertRole(emailAddress,null, roleType);
    }

    @Override
    public List<Role> getRoles(String emailAddress) throws Exception {
        if(emailAddress == null || !teamRoles.containsKey(emailAddress)){
            return new ArrayList<>();
        }
        return teamRoles.get(emailAddress);
    }

    @Override
    public void removeRoleFromTeam(String emailAddressToRemove, String teamName, RoleType roleType) throws Exception {
        if(emailAddressToRemove == null || teamName == null || roleType == null) {
            throw new NullPointerException();
        }
        if(!teamRoles.containsKey(emailAddressToRemove)){
            throw new Exception("emailAddress not found");
        }
        List<Role> rolesOfOwnerToRemove = teamRoles.get(emailAddressToRemove);
        for (Role tr: rolesOfOwnerToRemove) {
            if(roleType.equals(tr.getRoleType()) && teamName.equals(tr.getTeamName())){
                tr.setTeamName(null);
                break;
            }
        }
    }

    @Override
    public void removeRoleFromSystem(String emailAddressToRemove) throws Exception {
        if(emailAddressToRemove == null) {
            throw new NullPointerException();
        }
        if(!teamRoles.containsKey(emailAddressToRemove)){
            throw new Exception("emailAddress not found");
        }
        teamRoles.remove(emailAddressToRemove);
    }



    @Override
    public void removeRole(String emailAddressToRemove, RoleType roleType) throws Exception {
        if(emailAddressToRemove == null || roleType == null) {
            throw new NullPointerException();
        }
        if(!teamRoles.containsKey(emailAddressToRemove)){
            throw new Exception("emailAddress not found");
        }
        List<Role> rolesListOfUserToRemove = teamRoles.get(emailAddressToRemove);
        for (Role tr: rolesListOfUserToRemove) {
            if(roleType.equals(tr.getRoleType())){
                rolesListOfUserToRemove.remove(tr);
                break;
            }
        }
        if(rolesListOfUserToRemove.isEmpty()){
            rolesListOfUserToRemove.add(new Role(RoleType.FAN));
        }
    }

    @Override
    public Role getRole(String emailAddress) throws Exception {
        List<Role> roles = getRoles(emailAddress);
        if(roles.isEmpty()) {
            Role role = new Role(null, RoleType.FAN);
            return role;
        }
        // sort by date
        roles.sort(Comparator.comparing(Role::getAssignedDate).reversed());
        return roles.get(0);
    }

    @Override
    public void updateTeam(String teamName, String email) throws SQLException {

    }


    @Override
    public void deleteAll() {
        teamRoles.clear();
    }

}
