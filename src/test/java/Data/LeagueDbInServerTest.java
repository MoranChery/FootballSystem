package Data;

import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.League;
import Model.Season;
import Model.SeasonLeague;
import Model.UsersTypes.RepresentativeAssociation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeagueDbInServerTest
{
    private LeagueDbInServer leagueDbInServer = LeagueDbInServer.getInstance();

    private SeasonDbInServer seasonDbInServer = SeasonDbInServer.getInstance();
    private SeasonLeagueDbInServer seasonLeagueDbInServer = SeasonLeagueDbInServer.getInstance();

    @Before
    public void init() throws SQLException
    {
        final List<Db> dbs = new ArrayList<>();

//        dbs.add(RepresentativeAssociationDbInMemory.getInstance());
//        dbs.add(RoleDbInMemory.getInstance());
//        dbs.add(SubscriberDbInMemory.getInstance());
        dbs.add(LeagueDbInServer.getInstance());
        dbs.add(SeasonDbInServer.getInstance());
        dbs.add(SeasonLeagueDbInServer.getInstance());
//        dbs.add(JudgeDbInMemory.getInstance());
//        dbs.add(JudgeSeasonLeagueDbInMemory.getInstance());
//
        for (Db db : dbs)
        {
            db.deleteAll();
        }
    }

    //region insertLeague_Tests
    @Test
    public void insertLeague_null() throws Exception
    {
        League league1 = null;
        try
        {
            leagueDbInServer.insertLeague(league1);
        }
        catch (Exception e)
        {
            Assert.assertEquals("League already exists in the system", e.getMessage());
        }
    }

    @Test
    public void insertLeague_legal() throws Exception
    {
        League league1 = new League("league1");
        try
        {
            leagueDbInServer.insertLeague(league1);

            Assert.assertEquals("league1", league1.getLeagueName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("League already exists in the system", e.getMessage());
        }
    }

    @Test
    public void insertLeague_exists() throws Exception
    {
        League league1 = new League("league1");
        League league2 = new League("league1");
        try
        {
            leagueDbInServer.insertLeague(league1);
            leagueDbInServer.insertLeague(league2);
        }
        catch (Exception e)
        {
            Assert.assertEquals("League already exists in the system", e.getMessage());
        }
    }
    //endregion

    //region getLeague_Tests
    @Test
    public void getLeague_null() throws Exception
    {
        League league1 = new League("league1");

        String leagueNameNull = null;
        try
        {
            leagueDbInServer.insertLeague(league1);

            leagueDbInServer.getLeague(leagueNameNull);
        }
        catch (Exception e)
        {
            Assert.assertEquals("League not found", e.getMessage());
        }
    }

    @Test
    public void getLeague_notExists() throws Exception
    {
        League league1 = new League("league1");

        String leagueNameNotExists = "leagueNotExists";
        try
        {
            leagueDbInServer.insertLeague(league1);

            leagueDbInServer.getLeague(leagueNameNotExists);
        }
        catch (Exception e)
        {
            Assert.assertEquals("League not found", e.getMessage());
        }
    }

    @Test
    public void getLeague_exists_withoutSeason() throws Exception
    {
        League league1 = new League("league1");
        String leagueName1 = league1.getLeagueName();

        League returnLeague1 = null;
        try
        {
            leagueDbInServer.insertLeague(league1);

            returnLeague1 = leagueDbInServer.getLeague(leagueName1);

            Assert.assertEquals("league1", returnLeague1.getLeagueName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("League not found", e.getMessage());
        }
    }

    @Test
    public void getLeague_exists_withSeason() throws Exception
    {
        League league1 = new League("league1");

        String leagueName1 = league1.getLeagueName();

        Season season1 = new Season("season1");
        Season season2 = new Season("season2");

        String seasonName1 = season1.getSeasonName();
        String seasonName2 = season2.getSeasonName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague21 = new SeasonLeague(seasonName2, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        League returnLeague1 = null;
        try
        {
            leagueDbInServer.insertLeague(league1);

            seasonDbInServer.insertSeason(season1);
            seasonDbInServer.insertSeason(season2);

            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague21);

            returnLeague1 = leagueDbInServer.getLeague(leagueName1);

            Assert.assertEquals("league1", returnLeague1.getLeagueName());
            Assert.assertEquals(true, returnLeague1.getSeasonName_SeasonLeagueId().keySet().contains("season1"));
            Assert.assertEquals(true, returnLeague1.getSeasonName_SeasonLeagueId().keySet().contains("season2"));
        }
        catch (Exception e)
        {
            Assert.assertEquals("League not found", e.getMessage());
        }
    }
    //endregion

    //region deleteAll_Tests
    @Test
    public void deleteAll_noLeague()
    {
        ArrayList<String> leagueName = new ArrayList<>();

        try
        {
            leagueName = leagueDbInServer.getAllLeagueNames();

            Assert.assertEquals(0, leagueName.size());

            leagueDbInServer.deleteAll();

            leagueName = leagueDbInServer.getAllLeagueNames();

            Assert.assertEquals(0, leagueName.size());
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void deleteAll_listOfLeague()
    {
        League league1 = new League("league1");
        League league2 = new League("league2");

        ArrayList<String> leagueName = new ArrayList<>();

        try
        {
            leagueDbInServer.insertLeague(league1);
            leagueDbInServer.insertLeague(league2);

            leagueName = leagueDbInServer.getAllLeagueNames();

            Assert.assertEquals(2, leagueName.size());
            Assert.assertEquals(true, leagueName.contains(league1.getLeagueName()));
            Assert.assertEquals(true, leagueName.contains(league1.getLeagueName()));

            leagueDbInServer.deleteAll();

            leagueName = leagueDbInServer.getAllLeagueNames();

            Assert.assertEquals(0, leagueName.size());
            Assert.assertEquals(false, leagueName.contains(league1.getLeagueName()));
            Assert.assertEquals(false, leagueName.contains(league1.getLeagueName()));
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }
    //endregion
}
