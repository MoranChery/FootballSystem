package Data;

import Controller.BaseEmbeddedSQL;
import Model.*;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.QualificationJudge;
import Model.UsersTypes.Judge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

public class GameJudgesListDbInServerTest extends BaseEmbeddedSQL
{
    private GameJudgesListDbInServer gameJudgesListDbInServer = GameJudgesListDbInServer.getInstance();

    private GameDbInServer gameDbInServer = GameDbInServer.getInstance();

    private SubscriberDbInServer subscriberDbInServer = SubscriberDbInServer.getInstance();
    private JudgeDbInServer judgeDbInServer = JudgeDbInServer.getInstance();

    private JudgeSeasonLeagueDbInServer judgeSeasonLeagueDbInServer = JudgeSeasonLeagueDbInServer.getInstance();

    private SeasonDbInServer seasonDbInServer = SeasonDbInServer.getInstance();
    private LeagueDbInServer leagueDbInServer = LeagueDbInServer.getInstance();
    private SeasonLeagueDbInServer seasonLeagueDbInServer = SeasonLeagueDbInServer.getInstance();

    private TeamDbInServer teamDbInServer = TeamDbInServer.getInstance();
    private CourtDbInServer courtDbInServer = CourtDbInServer.getInstance();

    @Before
    public void init() throws SQLException
    {
        final List<Db> dbs = new ArrayList<>();

        dbs.add(GameDbInServer.getInstance());
        dbs.add(SubscriberDbInServer.getInstance());
        dbs.add(JudgeDbInServer.getInstance());
        dbs.add(JudgeSeasonLeagueDbInServer.getInstance());
        dbs.add(SeasonDbInServer.getInstance());
        dbs.add(LeagueDbInServer.getInstance());
        dbs.add(SeasonLeagueDbInServer.getInstance());
        dbs.add(TeamDbInServer.getInstance());
        dbs.add(CourtDbInServer.getInstance());
        dbs.add(PageDbInServer.getInstance());

        for (Db db : dbs)
        {
            db.deleteAll();
        }
    }

    @Test
    public void insertGameJudgeList_using_insertGame_legal() throws Exception
    {
        //note-need first to insert game_id and judges_email_address,
        // to table game_judges_list
        // it is happens inside gameDbInServer.insertGame function
        Season season = new Season("seasonName");
        League league = new League("leagueName");
        SeasonLeague seasonLeague = new SeasonLeague(season.getSeasonName(), league.getLeagueName(), CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        Judge majorJudge1 = new Judge("majorJudge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        Judge judge2 = new Judge("judge2@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        Set<String> judgesOfTheGameList = new HashSet<>();
        judgesOfTheGameList.add(judge1.getEmailAddress());
        judgesOfTheGameList.add(judge2.getEmailAddress());

        Team teamHost = new Team();
        teamHost.setTeamName("teamHost");
        Team teamGuest = new Team();
        teamGuest.setTeamName("teamGuest");

        Court court1 = new Court();
        court1.setCourtName("court1");
        court1.setCourtCity("court1city1");

        Date dateStart = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        dateStart.setHours(10);
        dateStart.setMinutes(15);
        dateStart.setSeconds(00);

        Date dateEnd = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
        dateEnd.setHours(10);
        dateEnd.setMinutes(15);
        dateEnd.setSeconds(00);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        try
        {
            seasonDbInServer.insertSeason(season);
            leagueDbInServer.insertLeague(league);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague);

            subscriberDbInServer.insertSubscriber(majorJudge1);
            judgeDbInServer.insertJudge(majorJudge1);
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);
            subscriberDbInServer.insertSubscriber(judge2);
            judgeDbInServer.insertJudge(judge2);

            teamDbInServer.insertTeam(teamHost.getTeamName());
            teamDbInServer.insertTeam(teamGuest.getTeamName());

            courtDbInServer.insertCourt(court1);

            gameDbInServer.insertGame(game);

            Assert.assertEquals("game1", game.getGameID());
            Assert.assertEquals("game1", gameDbInServer.getGame("game1").getGameID());

            Set<String> judgesList = gameDbInServer.getGame("game1").getJudgesOfTheGameList();
            Assert.assertEquals(3, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));
        }
        catch (Exception e)
        {
            Assert.assertEquals("Game already exist in system", e.getMessage());
        }
    }

    @Test
    public void getListJudgeEmailAddress_ByGameID_legal() throws Exception
    {
        //note-need first to insert game_id and judges_email_address,
        // to table game_judges_list
        // it is happens inside gameDbInServer.insertGame function
        Season season = new Season("seasonName");
        League league = new League("leagueName");
        SeasonLeague seasonLeague = new SeasonLeague(season.getSeasonName(), league.getLeagueName(), CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        Judge majorJudge1 = new Judge("majorJudge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        Judge judge2 = new Judge("judge2@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        Set<String> judgesOfTheGameList = new HashSet<>();
        judgesOfTheGameList.add(judge1.getEmailAddress());
        judgesOfTheGameList.add(judge2.getEmailAddress());

        Team teamHost = new Team();
        teamHost.setTeamName("teamHost");
        Team teamGuest = new Team();
        teamGuest.setTeamName("teamGuest");

        Court court1 = new Court();
        court1.setCourtName("court1");
        court1.setCourtCity("court1city1");

        Date dateStart = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        dateStart.setHours(10);
        dateStart.setMinutes(15);
        dateStart.setSeconds(00);

        Date dateEnd = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
        dateEnd.setHours(10);
        dateEnd.setMinutes(15);
        dateEnd.setSeconds(00);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        try
        {
            seasonDbInServer.insertSeason(season);
            leagueDbInServer.insertLeague(league);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague);

            subscriberDbInServer.insertSubscriber(majorJudge1);
            judgeDbInServer.insertJudge(majorJudge1);
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);
            subscriberDbInServer.insertSubscriber(judge2);
            judgeDbInServer.insertJudge(judge2);

            teamDbInServer.insertTeam(teamHost.getTeamName());
            teamDbInServer.insertTeam(teamGuest.getTeamName());

            courtDbInServer.insertCourt(court1);

            gameDbInServer.insertGame(game);

            Assert.assertEquals("game1", game.getGameID());
            Assert.assertEquals("game1", gameDbInServer.getGame("game1").getGameID());

            Set<String> judgesList = gameDbInServer.getGame("game1").getJudgesOfTheGameList();
            Assert.assertEquals(3, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));

            Set<String> listJudgeEmailAddress_ByGameID = gameJudgesListDbInServer.getListJudgeEmailAddress_ByGameID("game1");
            Assert.assertEquals(3, listJudgeEmailAddress_ByGameID.size());
            Assert.assertEquals(true, listJudgeEmailAddress_ByGameID.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, listJudgeEmailAddress_ByGameID.contains(judge2.getEmailAddress()));
        }
        catch (Exception e)
        {
            Assert.assertEquals("Game already exist in system", e.getMessage());
        }
    }

    @Test
    public void getListGameID_ByJudgeEmailAddress_legal() throws Exception
    {
        //note-need first to insert game_id and judges_email_address,
        // to table game_judges_list
        // it is happens inside gameDbInServer.insertGame function
        Season season = new Season("seasonName");
        League league = new League("leagueName");
        SeasonLeague seasonLeague = new SeasonLeague(season.getSeasonName(), league.getLeagueName(), CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        Judge majorJudge1 = new Judge("majorJudge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        Judge judge2 = new Judge("judge2@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        Set<String> judgesOfTheGameList = new HashSet<>();
        judgesOfTheGameList.add(judge1.getEmailAddress());
        judgesOfTheGameList.add(judge2.getEmailAddress());

        Team teamHost = new Team();
        teamHost.setTeamName("teamHost");
        Team teamGuest = new Team();
        teamGuest.setTeamName("teamGuest");

        Court court1 = new Court();
        court1.setCourtName("court1");
        court1.setCourtCity("court1city1");

        Date dateStart = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        dateStart.setHours(10);
        dateStart.setMinutes(15);
        dateStart.setSeconds(00);

        Date dateEnd = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
        dateEnd.setHours(10);
        dateEnd.setMinutes(15);
        dateEnd.setSeconds(00);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        try
        {
            seasonDbInServer.insertSeason(season);
            leagueDbInServer.insertLeague(league);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague);

            subscriberDbInServer.insertSubscriber(majorJudge1);
            judgeDbInServer.insertJudge(majorJudge1);
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);
            subscriberDbInServer.insertSubscriber(judge2);
            judgeDbInServer.insertJudge(judge2);

            teamDbInServer.insertTeam(teamHost.getTeamName());
            teamDbInServer.insertTeam(teamGuest.getTeamName());

            courtDbInServer.insertCourt(court1);

            gameDbInServer.insertGame(game);

            Assert.assertEquals("game1", game.getGameID());
            Assert.assertEquals("game1", gameDbInServer.getGame("game1").getGameID());

            Set<String> judgesList = gameDbInServer.getGame("game1").getJudgesOfTheGameList();
            Assert.assertEquals(3, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));

            Set<String> listGameID_ByJudgeEmailAddress1 = gameJudgesListDbInServer.getListGameID_ByJudgeEmailAddress(judge1.getEmailAddress());
            Assert.assertEquals(1, listGameID_ByJudgeEmailAddress1.size());
            Assert.assertEquals(true, listGameID_ByJudgeEmailAddress1.contains("game1"));

            Set<String> listGameID_ByJudgeEmailAddress2 = gameJudgesListDbInServer.getListGameID_ByJudgeEmailAddress(judge2.getEmailAddress());
            Assert.assertEquals(1, listGameID_ByJudgeEmailAddress2.size());
            Assert.assertEquals(true, listGameID_ByJudgeEmailAddress2.contains("game1"));
        }
        catch (Exception e)
        {
            Assert.assertEquals("Game already exist in system", e.getMessage());
        }
    }

    @Test
    public void deleteAll_listOfGameJudgeList() throws Exception
    {
        //note-need first to insert game_id and judges_email_address,
        // to table game_judges_list
        // it is happens inside gameDbInServer.insertGame function
        Season season = new Season("seasonName");
        League league = new League("leagueName");
        SeasonLeague seasonLeague = new SeasonLeague(season.getSeasonName(), league.getLeagueName(), CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        Judge majorJudge1 = new Judge("majorJudge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        Judge judge2 = new Judge("judge2@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);

        Set<String> judgesOfTheGameList = new HashSet<>();
        judgesOfTheGameList.add(judge1.getEmailAddress());
        judgesOfTheGameList.add(judge2.getEmailAddress());

        Team teamHost = new Team();
        teamHost.setTeamName("teamHost");
        Team teamGuest = new Team();
        teamGuest.setTeamName("teamGuest");

        Court court1 = new Court();
        court1.setCourtName("court1");
        court1.setCourtCity("court1city1");

        Date dateStart = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        dateStart.setHours(10);
        dateStart.setMinutes(15);
        dateStart.setSeconds(00);

        Date dateEnd = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
        dateEnd.setHours(10);
        dateEnd.setMinutes(15);
        dateEnd.setSeconds(00);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        try
        {
            seasonDbInServer.insertSeason(season);
            leagueDbInServer.insertLeague(league);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague);

            subscriberDbInServer.insertSubscriber(majorJudge1);
            judgeDbInServer.insertJudge(majorJudge1);
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);
            subscriberDbInServer.insertSubscriber(judge2);
            judgeDbInServer.insertJudge(judge2);

            teamDbInServer.insertTeam(teamHost.getTeamName());
            teamDbInServer.insertTeam(teamGuest.getTeamName());

            courtDbInServer.insertCourt(court1);

            gameDbInServer.insertGame(game);

            Assert.assertEquals("game1", game.getGameID());
            Assert.assertEquals("game1", gameDbInServer.getGame("game1").getGameID());

            Set<String> judgesList = gameDbInServer.getGame("game1").getJudgesOfTheGameList();
            Assert.assertEquals(3, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));

            gameJudgesListDbInServer.deleteAll();
        }
        catch (Exception e)
        {
            Assert.assertEquals("Game already exist in system", e.getMessage());
        }
    }
}
