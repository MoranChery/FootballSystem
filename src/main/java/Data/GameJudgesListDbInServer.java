package Data;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class GameJudgesListDbInServer implements GameJudgesListDb
{
    private static GameJudgesListDbInServer ourInstance = new GameJudgesListDbInServer();

    public static GameJudgesListDbInServer getInstance() { return ourInstance; }

    @Override
    public void insertGameJudgeList(String gameID, Set<String> judgesOfTheGameList) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            for (String judgeEmail : judgesOfTheGameList)
            {

                // the mysql insert statement
                String query = " insert into game_judges_list (game_id, judges_email_address)"
                        + " values (?,?)";

                // create the mysql insert preparedStatement
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, gameID);
                preparedStmt.setString(2, judgeEmail);

                // execute the preparedStatement
                preparedStmt.execute();
            }
        }
        catch (Exception e)
        {
            throw new Exception("GameJudgeList already exist in system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public Set<String> getListJudgeEmailAddress_ByGameID(String gameID) throws Exception
    {
        Set<String> listJudgeEmailAddress = new HashSet<>();
        String judge_email_address;

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select judges_email_address from game_judges_list where game_judges_list.game_id = \'" + gameID + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() != false)
        {
            judge_email_address = rs.getString("judges_email_address");

            listJudgeEmailAddress.add(judge_email_address);

            while (rs.next() != false)
            {
                judge_email_address = rs.getString("judges_email_address");

                listJudgeEmailAddress.add(judge_email_address);
            }
        }
        conn.close();

        return listJudgeEmailAddress;
    }

    @Override
    public Set<String> getListGameID_ByJudgeEmailAddress(String judgeEmailAddress) throws Exception
    {
        Set<String> listGameID = new HashSet<>();
        String game_id;

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select game_id from game_judges_list where game_judges_list.judges_email_address = \'" + judgeEmailAddress + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() != false)
        {
            game_id = rs.getString("game_id");

            listGameID.add(game_id);

            while (rs.next() != false)
            {
                game_id = rs.getString("game_id");

                listGameID.add(game_id);
            }
        }
        conn.close();

        return listGameID;
    }

    @Override
    public void deleteAll() throws SQLException, SQLException
    {
        Connection conn = DbConnector.getConnection();
        Statement statement = conn.createStatement();
        /* TRUNCATE is faster than DELETE since
         * it does not generate rollback information and does not
         * fire any delete triggers
         */

        // the mysql delete statement
        String query = "delete from game_judges_list";

        // create the mysql delete Statement
        statement.executeUpdate(query);
        conn.close();
    }
}
