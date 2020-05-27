package Data;

import Controller.BaseEmbeddedSQL;
import Model.*;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.GameEventType;
import Model.Enums.InlayGames;
import Model.Enums.QualificationJudge;
import Model.UsersTypes.Judge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

public class GameEventsDbInServerTest
{
    private GameEventsDbInServer gameEventsDbInServer = GameEventsDbInServer.getInstance();

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

        dbs.add(GameEventsDbInServer.getInstance());
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
    public void insertGameEvent_legal() throws Exception
    {
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

        Date dateStart = new Date(20, 10, 8);
        Date dateEnd = new Date(20, 11, 8);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        int event_minute = 55;
        String description = "red card to number 7";
        GameEventType game_event_type = GameEventType.RED_CARD;

        GameEvent gameEvent = new GameEvent(game.getGameID(), game.getGameDate(), game.getGameDate(), event_minute, game_event_type, description);

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

            gameEventsDbInServer.insertGameEvent(gameEvent);

            String getGameEvent_game_id = gameEventsDbInServer.getGameEvent(gameEvent.getEventId()).getGameId();

            Assert.assertEquals("game1", getGameEvent_game_id);

            Game getGame = gameDbInServer.getGame(getGameEvent_game_id);
            Set<String> judgesList = getGame.getJudgesOfTheGameList();
            Assert.assertEquals(2, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));
        }
        catch (Exception e)
        {
            Assert.assertEquals("GameEvent already exist in system", e.getMessage());
        }
    }

    @Test
    public void getGameEvent_legal() throws Exception
    {
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

        Date dateStart = new Date(20, 10, 8);
        Date dateEnd = new Date(20, 11, 8);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        int event_minute = 55;
        String description = "red card to number 7";
        GameEventType game_event_type = GameEventType.RED_CARD;

        GameEvent gameEvent = new GameEvent(game.getGameID(), game.getGameDate(), game.getGameDate(), event_minute, game_event_type, description);

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

            gameEventsDbInServer.insertGameEvent(gameEvent);

            String getGameEvent_game_id = gameEventsDbInServer.getGameEvent(gameEvent.getEventId()).getGameId();

            Assert.assertEquals("game1", getGameEvent_game_id);

            Game getGame = gameDbInServer.getGame(getGameEvent_game_id);
            Set<String> judgesList = getGame.getJudgesOfTheGameList();
            Assert.assertEquals(2, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));
        }
        catch (Exception e)
        {
            Assert.assertEquals("GameEvent already exist in system", e.getMessage());
        }
    }

    @Test
    public void updateGameEventDetails_legal() throws Exception
    {
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

        Date dateStart = new Date(20, 10, 8);
        Date dateEnd = new Date(20, 11, 8);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        int event_minute = 55;
        String description = "red card to number 7";
        GameEventType game_event_type = GameEventType.RED_CARD;

        GameEvent gameEvent = new GameEvent(game.getGameID(), game.getGameDate(), game.getGameDate(), event_minute, game_event_type, description);

        int update_event_minute = 60;
        String update_description = "yellow card to number 7";
        GameEventType update_game_event_type = GameEventType.YELLOW_CARD;

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

            gameEventsDbInServer.insertGameEvent(gameEvent);

            String getGameEvent_game_id = gameEventsDbInServer.getGameEvent(gameEvent.getEventId()).getGameId();

            Assert.assertEquals("game1", getGameEvent_game_id);

            Game getGame = gameDbInServer.getGame(getGameEvent_game_id);
            Set<String> judgesList = getGame.getJudgesOfTheGameList();
            Assert.assertEquals(2, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));

            gameEvent.setGameEventType(update_game_event_type);
            gameEvent.setDescription(update_description);
            gameEvent.setEventMinute(update_event_minute);

            gameEventsDbInServer.updateGameEventDetails(gameEvent);

            GameEvent getGameEvent = gameEventsDbInServer.getGameEvent(gameEvent.getEventId());
            Assert.assertEquals(update_game_event_type, getGameEvent.getGameEventType());
            Assert.assertEquals(update_description, getGameEvent.getDescription());
        }
        catch (Exception e)
        {
            Assert.assertEquals("GameEvent already exist in system", e.getMessage());
        }
    }

    @Test
    public void deleteAll_listOfGameEvent() throws Exception
    {
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

        Date dateStart = new Date(20, 10, 8);
        Date dateEnd = new Date(20, 11, 8);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        int event_minute = 55;
        String description = "red card to number 7";
        GameEventType game_event_type = GameEventType.RED_CARD;

        GameEvent gameEvent = new GameEvent(game.getGameID(), game.getGameDate(), game.getGameDate(), event_minute, game_event_type, description);

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

            gameEventsDbInServer.insertGameEvent(gameEvent);

            String getGameEvent_game_id = gameEventsDbInServer.getGameEvent(gameEvent.getEventId()).getGameId();

            Assert.assertEquals("game1", getGameEvent_game_id);

            Game getGame = gameDbInServer.getGame(getGameEvent_game_id);
            Set<String> judgesList = getGame.getJudgesOfTheGameList();
            Assert.assertEquals(2, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));

            gameEventsDbInServer.deleteAll();
        }
        catch (Exception e)
        {
            Assert.assertEquals("GameEvent already exist in system", e.getMessage());
        }
    }
}
