package Data;

import Controller.BaseEmbeddedSQL;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.QualificationJudge;
import Model.JudgeSeasonLeague;
import Model.League;
import Model.Season;
import Model.SeasonLeague;
import Model.UsersTypes.Judge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JudgeSeasonLeagueDbInServerTest extends BaseEmbeddedSQL
{
    private JudgeSeasonLeagueDbInServer judgeSeasonLeagueDbInServer = JudgeSeasonLeagueDbInServer.getInstance();

    private JudgeDbInServer judgeDbInServer = JudgeDbInServer.getInstance();
    private SubscriberDbInServer subscriberDbInServer = SubscriberDbInServer.getInstance();

    private SeasonLeagueDbInServer seasonLeagueDbInServer = SeasonLeagueDbInServer.getInstance();
    private SeasonDbInServer seasonDbInServer = SeasonDbInServer.getInstance();
    private LeagueDbInServer leagueDbInServer = LeagueDbInServer.getInstance();

    @Before
    public void init() throws SQLException
    {
        final List<Db> dbs = new ArrayList<>();

//        dbs.add(RepresentativeAssociationDbInServer.getInstance());
//        dbs.add(RoleDbInMemory.getInstance());
        dbs.add(SubscriberDbInServer.getInstance());
        dbs.add(LeagueDbInServer.getInstance());
        dbs.add(SeasonDbInServer.getInstance());
        dbs.add(SeasonLeagueDbInServer.getInstance());
        dbs.add(JudgeDbInServer.getInstance());
        dbs.add(JudgeSeasonLeagueDbInServer.getInstance());
//
        for (Db db : dbs)
        {
            db.deleteAll();
        }
    }

    //region insertJudgeSeasonLeague_Tests
    @Test
    public void insertJudgeSeasonLeague_null() throws Exception
    {
        JudgeSeasonLeague judgeSeasonLeague = null;

        try
        {
            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague);
        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("JudgeSeasonLeague already exists in the system", e.getMessage());
        }
    }

    @Test
    public void insertJudgeSeasonLeague_legal() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddress1 = judge1.getEmailAddress();

        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();

        JudgeSeasonLeague judgeSeasonLeague1_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress1);
        String judgeSeasonLeagueName1_11 = judgeSeasonLeague1_11.getJudgeSeasonLeagueName();
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11);

            Assert.assertEquals("season1_league1_judge1@gmail.com", judgeSeasonLeague1_11.getJudgeSeasonLeagueName());
            Assert.assertEquals("season1_league1_judge1@gmail.com", judgeSeasonLeagueName1_11);
            Assert.assertEquals("season1_league1_judge1@gmail.com", judgeSeasonLeagueDbInServer.getJudgeSeasonLeague("season1_league1_judge1@gmail.com").getJudgeSeasonLeagueName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void insertJudgeSeasonLeague_seasonLeagueNotExists() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddress1 = judge1.getEmailAddress();

        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();

        JudgeSeasonLeague judgeSeasonLeague1_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress1);
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);

            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11);
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague not found", e.getMessage());
        }
    }

    @Test
    public void insertJudgeSeasonLeague_judgeNotExists() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddress1 = judge1.getEmailAddress();

        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();

        JudgeSeasonLeague judgeSeasonLeague1_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress1);
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            subscriberDbInServer.insertSubscriber(judge1);

            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }

    @Test
    public void insertJudge_exists() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddress1 = judge1.getEmailAddress();

        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();

        JudgeSeasonLeague judgeSeasonLeague1_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress1);
        JudgeSeasonLeague judgeSeasonLeague1_11_2 = new JudgeSeasonLeague("season1_league1", "judge1@gmail.com");

        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11);
            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11_2);
        }
        catch (Exception e)
        {
            Assert.assertEquals("JudgeSeasonLeague already exists in the system", e.getMessage());
        }
    }
    //endregion

    //region getJudgeSeasonLeague_Tests
    @Test
    public void getJudgeSeasonLeague_null() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddress1 = judge1.getEmailAddress();

        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();

        JudgeSeasonLeague judgeSeasonLeague1_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress1);
        String judgeSeasonLeagueName1_11 = judgeSeasonLeague1_11.getJudgeSeasonLeagueName();

        String judgeSeasonLeagueNameNull = null;

        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11);

            judgeSeasonLeagueDbInServer.getJudgeSeasonLeague(judgeSeasonLeagueNameNull);
        }
        catch (Exception e)
        {
            Assert.assertEquals("JudgeSeasonLeague not found", e.getMessage());
        }
    }

    @Test
    public void getJudgeSeasonLeague_notExists() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddress1 = judge1.getEmailAddress();

        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();

        JudgeSeasonLeague judgeSeasonLeague1_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress1);
        String judgeSeasonLeagueName1_11 = judgeSeasonLeague1_11.getJudgeSeasonLeagueName();

        String judgeSeasonLeagueNameNotExists = "judgeSeasonLeagueNameNotExists";

        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11);

            judgeSeasonLeagueDbInServer.getJudgeSeasonLeague(judgeSeasonLeagueNameNotExists);
        }
        catch (Exception e)
        {
            Assert.assertEquals("JudgeSeasonLeague not found", e.getMessage());
        }
    }

    @Test
    public void getSeasonLeague_exists() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddress1 = judge1.getEmailAddress();

        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();

        JudgeSeasonLeague judgeSeasonLeague1_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress1);
        String judgeSeasonLeagueName1_11 = judgeSeasonLeague1_11.getJudgeSeasonLeagueName();

        JudgeSeasonLeague returnJudgeSeasonLeague1 = null;
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11);

            returnJudgeSeasonLeague1 = judgeSeasonLeagueDbInServer.getJudgeSeasonLeague(judgeSeasonLeagueName1_11);

            Assert.assertEquals("season1_league1_judge1@gmail.com", returnJudgeSeasonLeague1.getJudgeSeasonLeagueName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("JudgeSeasonLeague not found", e.getMessage());
        }
    }
    //endregion

    //region deleteAll_Tests
    @Test
    public void deleteAll_noJudgeSeasonLeague()
    {
        ArrayList<String> judgeSeasonLeagueName = new ArrayList<>();

        try
        {
            judgeSeasonLeagueName = judgeSeasonLeagueDbInServer.getAllJudgeSeasonLeagueNames();

            Assert.assertEquals(0, judgeSeasonLeagueName.size());

            judgeSeasonLeagueDbInServer.deleteAll();

            judgeSeasonLeagueName = judgeSeasonLeagueDbInServer.getAllJudgeSeasonLeagueNames();

            Assert.assertEquals(0, judgeSeasonLeagueName.size());
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void deleteAll_listOfJudgeSeasonLeague()
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddress1 = judge1.getEmailAddress();

        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();
        Season season2 = new Season("season2");
        String seasonName2 = season2.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();
        SeasonLeague seasonLeague21 = new SeasonLeague(seasonName2, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName21 = seasonLeague21.getSeasonLeagueName();

        JudgeSeasonLeague judgeSeasonLeague1_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress1);
        JudgeSeasonLeague judgeSeasonLeague1_21 = new JudgeSeasonLeague(seasonLeagueName21, judgeEmailAddress1);

        ArrayList<String> judgeSeasonLeagueName = new ArrayList<>();

        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonDbInServer.insertSeason(season2);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague21);

            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11);
            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_21);

            judgeSeasonLeagueName = judgeSeasonLeagueDbInServer.getAllJudgeSeasonLeagueNames();

            Assert.assertEquals(2, judgeSeasonLeagueName.size());
            Assert.assertEquals(true, judgeSeasonLeagueName.contains(judgeSeasonLeague1_11.getJudgeSeasonLeagueName()));
            Assert.assertEquals(true, judgeSeasonLeagueName.contains(judgeSeasonLeague1_21.getJudgeSeasonLeagueName()));

            judgeSeasonLeagueDbInServer.deleteAll();

            judgeSeasonLeagueName = judgeSeasonLeagueDbInServer.getAllJudgeSeasonLeagueNames();

            Assert.assertEquals(0, judgeSeasonLeagueName.size());
            Assert.assertEquals(false, judgeSeasonLeagueName.contains(judgeSeasonLeague1_11.getJudgeSeasonLeagueName()));
            Assert.assertEquals(false, judgeSeasonLeagueName.contains(judgeSeasonLeague1_21.getJudgeSeasonLeagueName()));
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }
    //endregion
}
