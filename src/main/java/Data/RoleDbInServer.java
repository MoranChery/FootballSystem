package Data;

import Model.Enums.PlayerRole;
import Model.Enums.RoleType;
import Model.Enums.Status;
import Model.Role;
import Model.UsersTypes.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleDbInServer implements RoleDb {
    @Override
    public void insertRole(String emailAddress, String teamName, RoleType roleType) throws SQLException {
        if(emailAddress == null || roleType == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into role (email_address,team_name,role_type)"
                    + " values (?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, emailAddress);
            preparedStmt.setString (2, teamName);
            preparedStmt.setString (3, roleType.name());

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
            String query = " insert into role (email_address,role_type)"
                    + " values (?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, emailAddress);
            preparedStmt.setString (2, roleType.name());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public List<Role> getRoles(String emailAddress) throws Exception {
        Connection conn = DbConnector.getConnection();

        String query = "select * from role where role.email_address = /'" + emailAddress + "/'";

        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);
        List<Role> roles =  new ArrayList<>();

        while(rs.next()){
            String userName = rs.getString("email_address");
            String team_name = rs.getString("team_name");
            String role_type = rs.getString("role_type");
            Role role = new Role(userName,RoleType.valueOf(role_type));
            role.setTeamName(team_name);
            roles.add(role);
        }
        return roles;
    }

    @Override
    public void removeRoleFromTeam(String EmailAddressToRemove, String teamName, RoleType roleType) throws Exception {

    }

    @Override
    public void removeRoleFromSystem(String emailAddressToRemove) throws Exception {

    }

    @Override
    public void removeRole(String emailAddressToRemove, RoleType roleType) throws Exception {

    }

    @Override
    public Role getRole(String emailAddress) throws Exception {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
