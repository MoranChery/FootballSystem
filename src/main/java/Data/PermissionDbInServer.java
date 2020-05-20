package Data;

import Model.Enums.CoachRole;
import Model.Enums.PermissionType;
import Model.Enums.QualificationCoach;
import Model.Enums.Status;
import Model.UsersTypes.Coach;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionDbInServer implements PermissionDb{
    private static PermissionDbInServer ourInstance = new PermissionDbInServer();

    public static PermissionDbInServer getInstance() {
        return ourInstance;
    }

    @Override
    public void insertPermission(String emailAddress, PermissionType permissionType) throws Exception {
        if(emailAddress == null ||permissionType == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into permission (email_address,permission_type)"
                    + " values (?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, emailAddress);
            preparedStmt.setString (2, permissionType.name());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public List<PermissionType> getPermissions(String emailAddress) throws Exception {

        String query = "select * from permission where permission.email_address = \'" + emailAddress + "\'";
        Connection conn = DbConnector.getConnection();
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);
        List<PermissionType> permissionTypes = new ArrayList<>();
        while(rs.next()){
            String email_address = rs.getString("email_address");
            String permission_type = rs.getString("permission_type");
            PermissionType permissionType = PermissionType.valueOf(permission_type);
            permissionTypes.add(permissionType);
        }
        conn.close();
        return permissionTypes;
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("delete from permission");
        conn.close();
    }
}
