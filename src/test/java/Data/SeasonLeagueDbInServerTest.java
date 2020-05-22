package Data;

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

public class SeasonLeagueDbInServerTest
{
    private SeasonLeagueDbInServer seasonLeagueDbInServer = SeasonLeagueDbInServer.getInstance();

    private SeasonDbInServer seasonDbInServer = SeasonDbInServer.getInstance();
    private LeagueDbInServer leagueDbInServer = LeagueDbInServer.getInstance();

    private SubscriberDbInServer subscriberDbInServer = SubscriberDbInServer.getInstance();

    private JudgeDbInServer judgeDbInServer = JudgeDbInServer.getInstance();
    private JudgeSeasonLeagueDbInServer judgeSeasonLeagueDbInServer = JudgeSeasonLeagueDbInServer.getInstance();

    @Before
    public void init() throws SQLException
    {
        final List<Db> dbs = new ArrayList<>();

//        dbs.add(RepresentativeAssociationDbInMemory.getInstance());
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

    //region insertSeasonLeague_Tests
    @Test
    public void insertSeasonLeague_null() throws Exception
    {
        SeasonLeague seasonLeague1 = null;
        try
        {
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague1);
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague already exists in the system", e.getMessage());
        }
    }

    @Test
    public void insertSeasonLeague_legal() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            Assert.assertEquals("season1_league1", seasonLeague11.getSeasonLeagueName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague already exists in the system", e.getMessage());
        }
    }

    @Test
    public void insertSeasonLeague_leagueNotExists() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        try
        {
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            Assert.assertEquals("season1_league1", seasonLeague11.getSeasonLeagueName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("League not found", e.getMessage());
        }
    }

    @Test
    public void insertSeasonLeague_seasonNotExists() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            Assert.assertEquals("season1_league1", seasonLeague11.getSeasonLeagueName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season not found", e.getMessage());
        }
    }

    @Test
    public void insertSeasonLeague_exists() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague11_2 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11_2);
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague already exists in the system", e.getMessage());
        }
    }
    //endregion

    //region getSeasonLeague_Tests
    @Test
    public void getSeasonLeague_null() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        String seasonLeagueNameNull = null;

        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            seasonLeagueDbInServer.getSeasonLeague(seasonLeagueNameNull);
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague not found", e.getMessage());
        }
    }

    @Test
    public void getSeasonLeague_notExists() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        String seasonNameNotExists = "seasonNotExists";

        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            seasonLeagueDbInServer.getSeasonLeague(seasonNameNotExists);
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague not found", e.getMessage());
        }
    }

    @Test
    public void getSeasonLeague_exists_withoutJudge() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();

        SeasonLeague returnSeasonLeague1 = null;
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            returnSeasonLeague1 = seasonLeagueDbInServer.getSeasonLeague(seasonLeagueName11);

            Assert.assertEquals("season1_league1", returnSeasonLeague1.getSeasonLeagueName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague not found", e.getMessage());
        }
    }

    @Test
    public void getSeasonLeague_exists_withJudge() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        String seasonLeagueName11 = seasonLeague11.getSeasonLeagueName();

        Judge judge1 = new Judge("judgeS1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        Judge judge2 = new Judge("judgeS2@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        String judgeEmailAddress1 = judge1.getEmailAddress();
        String judgeEmailAddress2 = judge2.getEmailAddress();

        JudgeSeasonLeague judgeSeasonLeague1_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress1);
        JudgeSeasonLeague judgeSeasonLeague2_11 = new JudgeSeasonLeague(seasonLeagueName11, judgeEmailAddress2);

        SeasonLeague returnSeasonLeague11 = null;
        try
        {
            seasonDbInServer.insertSeason(season1);
            leagueDbInServer.insertLeague(league1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            subscriberDbInServer.insertSubscriber(judge1);
            subscriberDbInServer.insertSubscriber(judge2);

            judgeDbInServer.insertJudge(judge1);
            judgeDbInServer.insertJudge(judge2);

            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague1_11);
            judgeSeasonLeagueDbInServer.insertJudgeSeasonLeague(judgeSeasonLeague2_11);

            returnSeasonLeague11 = seasonLeagueDbInServer.getSeasonLeague(seasonLeagueName11);

            Assert.assertEquals("season1_league1", returnSeasonLeague11.getSeasonLeagueName());
            Assert.assertEquals(true, returnSeasonLeague11.getJudgeEmailAddress_JudgeSeasonLeagueName().keySet().contains("judgeS1@gmail.com"));
            Assert.assertEquals(true, returnSeasonLeague11.getJudgeEmailAddress_JudgeSeasonLeagueName().keySet().contains("judgeS2@gmail.com"));
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague not found", e.getMessage());
        }
    }
    //endregion

    //region updateCalculateLeaguePointsPolicy_Tests
    @Test
    public void updateCalculateLeaguePointsPolicy_seasonLeague_notExists() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            seasonLeagueDbInServer.updateCalculateLeaguePointsPolicy("season1_league1_notExists", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1);
        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("SeasonLeague not found", e.getMessage());
        }
    }

    @Test
    public void updateCalculateLeaguePointsPolicy_policy_null() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            seasonLeagueDbInServer.updateCalculateLeaguePointsPolicy("season1_league1", null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("CalculateLeaguePoints not found", e.getMessage());
        }
    }

    @Test
    public void updateCalculateLeaguePointsPolicy_policy_legal() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        try
        {
            leagueDbInServer.insertLeague(league1);
            seasonDbInServer.insertSeason(season1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);

            seasonLeagueDbInServer.updateCalculateLeaguePointsPolicy("season1_league1", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1);

            SeasonLeague returnSeasonLeague = seasonLeagueDbInServer.getSeasonLeague("season1_league1");

            Assert.assertEquals("WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1", returnSeasonLeague.getCalculateLeaguePoints().toString());
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague not found", e.getMessage());
        }
    }
    //endregion

    //region deleteAll_Tests
    @Test
    public void deleteAll_noSeasonLeague()
    {
        ArrayList<String> seasonLeagueName = new ArrayList<>();

        try
        {
            seasonLeagueName = seasonLeagueDbInServer.getAllSeasonLeagueNames();

            Assert.assertEquals(0, seasonLeagueName.size());

            seasonLeagueDbInServer.deleteAll();

            seasonLeagueName = seasonLeagueDbInServer.getAllSeasonLeagueNames();

            Assert.assertEquals(0, seasonLeagueName.size());
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void deleteAll_listOfSeasonLeague()
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();
        Season season2 = new Season("season2");
        String seasonName2 = season2.getSeasonName();

        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague21 = new SeasonLeague(seasonName2, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        ArrayList<String> seasonLeagueName = new ArrayList<>();

        try
        {
            seasonDbInServer.insertSeason(season1);
            seasonDbInServer.insertSeason(season2);
            leagueDbInServer.insertLeague(league1);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague21);

            seasonLeagueName = seasonLeagueDbInServer.getAllSeasonLeagueNames();

            Assert.assertEquals(2, seasonLeagueName.size());
            Assert.assertEquals(true, seasonLeagueName.contains(seasonLeague11.getSeasonLeagueName()));
            Assert.assertEquals(true, seasonLeagueName.contains(seasonLeague21.getSeasonLeagueName()));

            seasonLeagueDbInServer.deleteAll();

            seasonLeagueName = seasonLeagueDbInServer.getAllSeasonLeagueNames();

            Assert.assertEquals(0, seasonLeagueName.size());
            Assert.assertEquals(false, seasonLeagueName.contains(seasonLeague11.getSeasonLeagueName()));
            Assert.assertEquals(false, seasonLeagueName.contains(seasonLeague21.getSeasonLeagueName()));
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }
    //endregion
}
