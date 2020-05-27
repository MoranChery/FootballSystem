package Data;

import Model.Enums.PlayerRole;
import Model.Enums.RoleType;
import Model.Enums.Status;
import Model.Role;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamOwner;

import java.sql.*;
import java.util.*;

public class RoleDbInServer implements RoleDb {

    private static RoleDbInServer ourInstance = new RoleDbInServer();

    public static RoleDbInServer getInstance() {
        return ourInstance;
    }

    @Override
    public void insertRole(String emailAddress, String teamName, RoleType roleType) throws SQLException {
        if(emailAddress == null || roleType == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into role (email_address,team_name,role_type,assigned_date)"
                    + " values (?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, emailAddress);
            preparedStmt.setString (2, teamName);
            preparedStmt.setString (3, roleType.name());
            preparedStmt.setLong (4, System.currentTimeMillis());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public void createRoleInSystem(String emailAddress, RoleType roleType) throws SQLException {
        if(emailAddress == null || roleType == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into role (email_address,role_type,assigned_date)"
                    + " values (?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, emailAddress);
//            preparedStmt.setString (2, roleType.name());
            preparedStmt.setString (2, roleType.toString());
            preparedStmt.setLong (3, System.currentTimeMillis());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public List<Role> getRoles(String emailAddress) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "select * from role where role.email_address = \'" + emailAddress + "\'";

        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);
        List<Role> roles =  new ArrayList<>();

        while(rs.next()){
            String userName = rs.getString("email_address");
            String team_name = rs.getString("team_name");
            String role_type = rs.getString("role_type");
            Long assigned_date = rs.getLong("assigned_date");
            Role role = new Role(userName,RoleType.valueOf(role_type));
            role.setTeamName(team_name);
            role.setAssignedDate(assigned_date);
            roles.add(role);
        }
        return roles;
    }

    @Override
    public void removeRoleFromTeam(String emailAddressToRemove, String teamName, RoleType roleType) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE role SET role.team_name = null WHERE role.email_address =  \'"+ emailAddressToRemove +
                "\' AND team_name = \'" + teamName + "\' AND role.role_type = \'" + roleType.name() + "\'"  ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void removeRoleFromSystem(String emailAddressToRemove) throws Exception {

    }

    @Override
    public void removeRole(String emailAddressToRemove, RoleType roleType) throws Exception {
        if(emailAddressToRemove == null || roleType == null) {
            throw new NullPointerException();
        }

        Connection conn = DbConnector.getConnection();
        String query = "delete from role where email_address = \'" + emailAddressToRemove + "\' and role_type = \'" + roleType + "\'";
        Statement preparedStmt = conn.createStatement();
        int deletedRows = preparedStmt.executeUpdate(query);

        if (deletedRows == 0) {
            throw new Exception("emailAddress not found");
        }
        conn.close();
    }

    @Override
    public Role getRole(String emailAddress) throws Exception {
        List<Role> roles = getRoles(emailAddress);
        if(roles.isEmpty()) {
            return new Role(null, RoleType.FAN);
        }
        // sort by date
        roles.sort(Comparator.comparing(Role::getAssignedDate).reversed());
        return roles.get(0);
    }

    @Override
    public void updateTeam(String teamName, String email) throws SQLException {
        Connection conn = DbConnector.getConnection();

        String query = "UPDATE role SET team_name = \'" + teamName + "\'  WHERE role.email_address =  \'"+  email + "\'" ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("delete from role");
        conn.close();
    }
}
