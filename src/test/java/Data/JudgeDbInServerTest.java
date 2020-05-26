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

public class JudgeDbInServerTest extends BaseEmbeddedSQL
{
    private JudgeDbInServer judgeDbInServer = JudgeDbInServer.getInstance();

    private SubscriberDbInServer subscriberDbInServer = SubscriberDbInServer.getInstance();

    private SeasonLeagueDbInServer seasonLeagueDbInServer = SeasonLeagueDbInServer.getInstance();
    private SeasonDbInServer seasonDbInServer = SeasonDbInServer.getInstance();
    private LeagueDbInServer leagueDbInServer = LeagueDbInServer.getInstance();

    private JudgeSeasonLeagueDbInServer judgeSeasonLeagueDbInServer = JudgeSeasonLeagueDbInServer.getInstance();

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

    //region insertJudge_Tests
    @Test
    public void insertJudge_null() throws Exception
    {
        Judge judge = null;

        try
        {
            judgeDbInServer.insertJudge(judge);
        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Judge already exists in the system", e.getMessage());
        }
    }

    @Test
    public void insertJudge_legal() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
//        Judge judge2 = new Judge("judge2@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);
            Assert.assertEquals("judge1@gmail.com", judge1.getEmailAddress());
            Assert.assertEquals("judge1@gmail.com", judgeDbInServer.getJudge("judge1@gmail.com").getEmailAddress());
        }
        catch (Exception e)
        {
            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void insertJudge_subscriberExists() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        Judge judge2 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);
            subscriberDbInServer.insertSubscriber(judge2);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Duplicate entry '" + judge2.getEmailAddress() + "' for key 'subscriber.PRIMARY'", e.getMessage());
        }
    }

    @Test
    public void insertJudge_judgeExists() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        Judge judge2 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);
            judgeDbInServer.insertJudge(judge2);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge already exists in the system", e.getMessage());
        }
    }
    //endregion

    //region getJudge_Tests
    @Test
    public void getJudge_null() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddressNull = null;

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            judgeDbInServer.getJudge(judgeEmailAddressNull);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }

    @Test
    public void getJudge_notExists() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddressExists = "judgeEmailAddressExists";

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            judgeDbInServer.getJudge(judgeEmailAddressExists);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }

    @Test
    public void getJudge_exists_withoutSeasonLeague() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        String judgeEmailAddress1 = judge1.getEmailAddress();

        Judge returnJudge = null;

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            returnJudge = judgeDbInServer.getJudge(judgeEmailAddress1);

            Assert.assertEquals("judge1@gmail.com", returnJudge.getEmailAddress());
            Assert.assertEquals(false, returnJudge.getSeasonLeagueName_JudgeSeasonLeagueName().keySet().contains("season1_league1"));
            Assert.assertEquals(false, returnJudge.getSeasonLeagueName_JudgeSeasonLeagueName().keySet().contains("season2_league1"));
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }

    @Test
    public void getJudge_exists_withSeasonLeague() throws Exception
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
        String judgeSeasonLeagueName1_11 = judgeSeasonLeague1_11.getJudgeSeasonLeagueName();

        JudgeSeasonLeague judgeSeasonLeague1_21 = new JudgeSeasonLeague(seasonLeagueName21, judgeEmailAddress1);
        String judgeSeasonLeagueName1_21 = judgeSeasonLeague1_21.getJudgeSeasonLeagueName();

        Judge returnJudge = null;

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

            returnJudge = judgeDbInServer.getJudge(judgeEmailAddress1);

            Assert.assertEquals("judge1@gmail.com", returnJudge.getEmailAddress());
            Assert.assertEquals(true, returnJudge.getSeasonLeagueName_JudgeSeasonLeagueName().keySet().contains("season1_league1"));
            Assert.assertEquals(true, returnJudge.getSeasonLeagueName_JudgeSeasonLeagueName().keySet().contains("season2_league1"));
            Assert.assertEquals(true, returnJudge.getSeasonLeagueName_JudgeSeasonLeagueName().values().contains("season1_league1_judge1@gmail.com"));
            Assert.assertEquals(true, returnJudge.getSeasonLeagueName_JudgeSeasonLeagueName().values().contains("season2_league1_judge1@gmail.com"));
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }

    @Test
    public void getJudge_exists_withoutGame() throws Exception
    {

    }

    @Test
    public void getJudge_exists_withGame() throws Exception
    {

    }
    //endregion





    //region deleteAll_Tests
    @Test
    public void deleteAll_noJudge()
    {
        ArrayList<String> judgeEmailAddress = new ArrayList<>();

        try
        {
            judgeEmailAddress = judgeDbInServer.getAllJudgeEmailAddress();

            Assert.assertEquals(0, judgeEmailAddress.size());

            judgeDbInServer.deleteAll();

            judgeEmailAddress = judgeDbInServer.getAllJudgeEmailAddress();

            Assert.assertEquals(0, judgeEmailAddress.size());
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void deleteAll_listOfJudge()
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        Judge judge2 = new Judge("judge2@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        ArrayList<String> judgeEmailAddress = new ArrayList<>();

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);
            subscriberDbInServer.insertSubscriber(judge2);
            judgeDbInServer.insertJudge(judge2);

            judgeEmailAddress = judgeDbInServer.getAllJudgeEmailAddress();

            Assert.assertEquals(2, judgeEmailAddress.size());
            Assert.assertEquals(true, judgeEmailAddress.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgeEmailAddress.contains(judge2.getEmailAddress()));

            judgeDbInServer.deleteAll();

            judgeEmailAddress = judgeDbInServer.getAllJudgeEmailAddress();

            Assert.assertEquals(2, judgeEmailAddress.size());
            Assert.assertEquals(true, judgeEmailAddress.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgeEmailAddress.contains(judge2.getEmailAddress()));
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }
    //endregion
}
