package Data;

import Model.FinancialActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class FinancialActivityDbInServer implements FinancialActivityDb {

    private static FinancialActivityDbInServer ourInstance = new FinancialActivityDbInServer();

    public static FinancialActivityDbInServer getInstance() {
        return ourInstance;
    }

    @Override
    public void insertFinancialActivity(FinancialActivity financialActivity) throws Exception {
        if(financialActivity == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into financial_activity (financial_activity_id,financial_activity_amount,description,financial_activity_type,team)"
                    + " values (?,?,?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, financialActivity.getFinancialActivityId());
            preparedStmt.setDouble (2, financialActivity.getFinancialActivityAmount());
            preparedStmt.setString (3, financialActivity.getDescription());
            preparedStmt.setString (4, financialActivity.getFinancialActivityType().name());
            preparedStmt.setString (5, financialActivity.getTeam());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate("delete from financial_activity");
        conn.close();
    }
}
