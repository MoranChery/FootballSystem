package Data;

import Controller.BaseEmbeddedSQL;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.League;
import Model.Season;
import Model.SeasonLeague;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeasonDbInServerTest extends BaseEmbeddedSQL
{
    private SeasonDbInServer seasonDbInServer = SeasonDbInServer.getInstance();

    private LeagueDbInServer leagueDbInServer = LeagueDbInServer.getInstance();
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

    //region insertSeason_Tests
    @Test
    public void insertSeason_null() throws Exception
    {
        Season season1 = null;
        try
        {
            seasonDbInServer.insertSeason(season1);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season already exists in the system", e.getMessage());
        }
    }

    @Test
    public void insertSeason_legal() throws Exception
    {
        Season season1 = new Season("season1");
        try
        {
            seasonDbInServer.insertSeason(season1);

            Assert.assertEquals("season1", season1.getSeasonName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season already exists in the system", e.getMessage());
        }
    }

    @Test
        public void insertSeason_exists() throws Exception
    {
        Season season1 = new Season("season1");
        Season season2 = new Season("season1");
        try
        {
            seasonDbInServer.insertSeason(season1);
            seasonDbInServer.insertSeason(season2);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season already exists in the system", e.getMessage());
        }
    }
    //endregion

    //region getSeason_Tests
    @Test
    public void getSeason_null() throws Exception
    {
        Season season1 = new Season("season1");

        String seasonNameNull = null;
        try
        {
            seasonDbInServer.insertSeason(season1);

            seasonDbInServer.getSeason(seasonNameNull);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season not found", e.getMessage());
        }
    }

    @Test
    public void getSeason_notExists() throws Exception
    {
        Season season1 = new Season("season1");

        String seasonNameNotExists = "seasonNotExists";
        try
        {
            seasonDbInServer.insertSeason(season1);

            seasonDbInServer.getSeason(seasonNameNotExists);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season not found", e.getMessage());
        }
    }

    @Test
    public void getSeason_exists_withoutLeague() throws Exception
    {
        Season season1 = new Season("season1");
        String seasonName1 = season1.getSeasonName();

        Season returnSeason1 = null;
        try
        {
            seasonDbInServer.insertSeason(season1);

            returnSeason1 = seasonDbInServer.getSeason(seasonName1);

            Assert.assertEquals("season1", returnSeason1.getSeasonName());
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season not found", e.getMessage());
        }
    }

    @Test
    public void getSeason_exists_withSeason() throws Exception
    {
        Season season1 = new Season("season1");

        String seasonName1 = season1.getSeasonName();

        League league1 = new League("league1");
        League league2 = new League("league2");

        String leagueName1 = league1.getLeagueName();
        String leagueName2 = league2.getLeagueName();

        SeasonLeague seasonLeague11 = new SeasonLeague(seasonName1, leagueName1, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague12 = new SeasonLeague(seasonName1, leagueName2, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        Season returnSeason1 = null;
        try
        {
            seasonDbInServer.insertSeason(season1);

            leagueDbInServer.insertLeague(league1);
            leagueDbInServer.insertLeague(league2);

            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague12);

            returnSeason1 = seasonDbInServer.getSeason(seasonName1);

            Assert.assertEquals("season1", returnSeason1.getSeasonName());
            Assert.assertEquals(true, returnSeason1.getLeagueName_SeasonLeagueId().keySet().contains("league1"));
            Assert.assertEquals(true, returnSeason1.getLeagueName_SeasonLeagueId().keySet().contains("league2"));
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season not found", e.getMessage());
        }
    }
    //endregion

    //region deleteAll_Tests
    @Test
    public void deleteAll_noSeason()
    {
        ArrayList<String> seasonName = new ArrayList<>();

        try
        {
            seasonName = seasonDbInServer.getAllSeasonNames();

            Assert.assertEquals(0, seasonName.size());

            seasonDbInServer.deleteAll();

            seasonName = seasonDbInServer.getAllSeasonNames();

            Assert.assertEquals(0, seasonName.size());
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }

    @Test
    public void deleteAll_listOfSeason()
    {
        Season season1 = new Season("season1");
        Season season2 = new Season("season2");

        ArrayList<String> seasonName = new ArrayList<>();

        try
        {
            seasonDbInServer.insertSeason(season1);
            seasonDbInServer.insertSeason(season2);

            seasonName = seasonDbInServer.getAllSeasonNames();

            Assert.assertEquals(2, seasonName.size());
            Assert.assertEquals(true, seasonName.contains(season1.getSeasonName()));
            Assert.assertEquals(true, seasonName.contains(season2.getSeasonName()));

            seasonDbInServer.deleteAll();

            seasonName = seasonDbInServer.getAllSeasonNames();

            Assert.assertEquals(0, seasonName.size());
            Assert.assertEquals(false, seasonName.contains(season1.getSeasonName()));
            Assert.assertEquals(false, seasonName.contains(season2.getSeasonName()));
        }
        catch (Exception e)
        {
//            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }
    //endregion
}
