package Data;

import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.QualificationJudge;
import Model.Enums.Status;
import Model.Game;
import Model.JudgeSeasonLeague;
import Model.SeasonLeague;
import Model.UsersTypes.Judge;

import java.sql.*;
import java.util.List;

public class JudgeDbInServer implements JudgeDb
{
    @Override
    public void insertJudge(Judge judge) throws Exception
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into judge (email_address, qualification_judge)"
                    + " values (?,?)";

            // create the mysql insert preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, judge.getEmailAddress());
            preparedStmt.setString(2, judge.getQualificationJudge().toString());

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (Exception e)
        {
            throw new Exception("Judge already exists in the system");
        }
        finally
        {
            conn.close();
        }
    }

    @Override
    public Judge getJudge(String judgeEmailAddress) throws Exception
    {
        if (judgeEmailAddress == null)
        {
            throw new NullPointerException("Judge not found");
        }

        Connection conn = DbConnector.getConnection();

        String query = "select * from subscriber, judge where subscriber.email_address = judge.email_address and subscriber.email_address = \'" + judgeEmailAddress + "\'";

        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false)
        {
            throw new NotFoundException("Judge not found");
        }

        String userName = rs.getString("email_address");
        String password = rs.getString("password");
        Integer id = rs.getInt("id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String status = rs.getString("status");
        String judge_qualification = rs.getString("qualification_judge");
        conn.close();

        Judge judge = new Judge(userName, password, id, first_name, last_name, QualificationJudge.valueOf(judge_qualification));
        judge.setPassword(password);
        judge.setStatus(Status.valueOf(status));
        return judge;
    }

    @Override
    public void removeJudge(String judgeEmailAddress) throws Exception
    {

    }

    @Override
    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception
    {

    }

    @Override
    public void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {

    }

    @Override
    public void addGameToTheJudge(String judgeMail, Game gameToAdd) throws Exception
    {

    }

    @Override
    public List<String> getJudgeGames(String judgeId)
    {
        return null;
    }

    @Override
    public void deleteAll()
    {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql delete statement
            String query = " delete from judge";

            // create the mysql delete preparedStatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);

            // execute the preparedStatement
            preparedStmt.execute();
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        finally
        {
            try
            {
                conn.close();
            }
            catch (SQLException throwables)
            {
                throwables.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        JudgeDbInServer judgeDbInServer = new JudgeDbInServer();

        try
        {
            judgeDbInServer.insertJudge(judge1);
//
//            judgeDbInServer.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
