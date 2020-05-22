package Data;

import Model.Enums.QualificationJudge;
import Model.Enums.Status;
import Model.Game;
import Model.JudgeSeasonLeague;
import Model.UsersTypes.Judge;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JudgeDbInServer implements JudgeDb
{
    private static JudgeDbInServer ourInstance = new JudgeDbInServer();

    public static JudgeDbInServer getInstance() { return ourInstance; }

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

        // the mysql select statement
        String query = "select * from subscriber, judge where subscriber.email_address = judge.email_address and subscriber.email_address = \'" + judgeEmailAddress + "\'";

        // create the mysql select resultSet
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
        judge.setSeasonLeagueName_JudgeSeasonLeagueName(addMap_seasonLeagueName_judgeSeasonLeagueName(judgeEmailAddress));

        return judge;
    }

    private Map<String, String> addMap_seasonLeagueName_judgeSeasonLeagueName(String judgeEmailAddress) throws SQLException
    {
        Map<String, String> seasonLeagueName_judgeSeasonLeagueName = new HashMap<>();
        String key_season_league_name;
        String value_judge_season_league_name;

        Connection conn = DbConnector.getConnection();

        // the mysql select statement
        String query = "select season_league_name, judge_season_league_name from judge_season_league where judge_email_address = \'" + judgeEmailAddress + "\'";

        // create the mysql select resultSet
        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() != false)
        {
            key_season_league_name = rs.getString("season_league_name");
            value_judge_season_league_name = rs.getString("judge_season_league_name");

            seasonLeagueName_judgeSeasonLeagueName.put(key_season_league_name, value_judge_season_league_name);

            while (rs.next() != false)
            {
                key_season_league_name = rs.getString("season_league_name");
                value_judge_season_league_name = rs.getString("judge_season_league_name");

                seasonLeagueName_judgeSeasonLeagueName.put(key_season_league_name, value_judge_season_league_name);
            }
        }
        conn.close();
        return seasonLeagueName_judgeSeasonLeagueName;
    }

    @Override
    public void removeJudge(String judgeEmailAddress) throws Exception
    {
        //todo
        throw new NotImplementedException();
    }

    @Override
    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception
    {
        Connection conn = DbConnector.getConnection();

        // the mysql update statement
        String query = "update judge set qualification_judge = \'" + newQualification + "\' where judge.email_address =  \'"+ judgeMail + "\'" ;

        // create the mysql update preparedStatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        //todo
        throw new NotImplementedException();
    }

    @Override
    public void addGameToTheJudge(String judgeMail, Game gameToAdd) throws Exception
    {
        //todo
        throw new NotImplementedException();
    }

    @Override
    public List<String> getJudgeGames(String judgeId)
    {
        //todo
        throw new NotImplementedException();
//        return null;
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
        SubscriberDbInServer subscriberDbInServer = new SubscriberDbInServer();

        try
        {
//            subscriberDbInServer.insertSubscriber(judge1);
//            judgeDbInServer.insertJudge(judge1);

//            Judge judge2 = judgeDbInServer.getJudge("judge1@gmail.com");
//            System.out.println(judge2.getEmailAddress());
//            System.out.println(judge2.getPassword());
//            System.out.println(judge2.getId());
//            System.out.println(judge2.getFirstName());
//            System.out.println(judge2.getLastName());
//            System.out.println(judge2.getQualificationJudge());

//            judgeDbInServer.wantToEditQualification("judge1@gmail.com", QualificationJudge.COUNTY.toString());
//            Judge judge3 = judgeDbInServer.getJudge("judge1@gmail.com");
//            System.out.println(judge3.getEmailAddress());
//            System.out.println(judge3.getQualificationJudge());


//            judgeDbInServer.deleteAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
