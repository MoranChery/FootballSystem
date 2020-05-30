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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class GameEventsDbInServerTest /*extends BaseEmbeddedSQL*/
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

    private Date convertToDateViaInstant(LocalDateTime dateToConvert)
    {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
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

        Date dateStart = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        dateStart.setHours(10);
        dateStart.setMinutes(15);
        dateStart.setSeconds(00);

        Date dateEnd = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        dateEnd.setHours(10);
        dateEnd.setMinutes(15);
        dateEnd.setSeconds(00);

        Date eventTime = new Date();
        eventTime.setHours(11);
        eventTime.setMinutes(10);
        eventTime.setSeconds(00);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(eventTime);
        LocalTime timePart = LocalTime.parse(time);
        String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(dateStart);
        LocalDate datePart = LocalDate.parse(startingDate);
        LocalDateTime dt = LocalDateTime.of(datePart, timePart);
        eventTime = convertToDateViaInstant(dt);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        int event_minute = 55;
        String description = "red card to number 7";
        GameEventType game_event_type = GameEventType.RED_CARD;

        GameEvent gameEvent = new GameEvent(game.getGameID(), game.getGameDate(), eventTime, event_minute, game_event_type, description);

        int event_minute2 = 26;
        String description2 = "goal of number 3";
        GameEventType game_event_type2 = GameEventType.GOAL;

        GameEvent gameEvent2 = new GameEvent(game.getGameID(), game.getGameDate(), eventTime, event_minute2, game_event_type2, description2);

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
            gameEventsDbInServer.insertGameEvent(gameEvent2);

            GameEvent gameEventReturn1 = gameEventsDbInServer.getGameEvent(gameEvent.getEventId());
            String getGameEvent_game_id = gameEventsDbInServer.getGameEvent(gameEvent.getEventId()).getGameId();

            Assert.assertEquals("game1", getGameEvent_game_id);

            Game getGame = gameDbInServer.getGame(getGameEvent_game_id);
            Set<String> judgesList = getGame.getJudgesOfTheGameList();
            Assert.assertEquals(3, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(majorJudge1.getEmailAddress()));

            Map<String, GameEvent> getMap_eventId_GameEvent_ByGameId = gameEventsDbInServer.getMap_eventId_GameEvent_ByGameId("game1");
            Assert.assertEquals(2, getMap_eventId_GameEvent_ByGameId.size());

            ArrayList<GameEvent> gameEvents = new ArrayList<>();
            for (String game_id: getMap_eventId_GameEvent_ByGameId.keySet())
            {
                gameEvents.add(getMap_eventId_GameEvent_ByGameId.get(game_id));
            }
            Assert.assertEquals(2, gameEvents.size());
            GameEvent ge1 = gameEvents.remove(0);
            GameEvent ge2 = gameEvents.remove(0);

            int ge1_em = ge1.getEventMinute();
            int ge2_em = ge2.getEventMinute();
            Assert.assertEquals(true, (55 == ge1_em || 55 == ge2_em));
            Assert.assertEquals(true, (26 == ge1_em || 26 == ge2_em));

            String ge1_d = ge1.getDescription();
            String ge2_d = ge2.getDescription();

            Assert.assertEquals(true, (ge1_d.equals("red card to number 7") || ge2_d.equals("red card to number 7")));
            Assert.assertEquals(true, (ge1_d.equals("goal of number 3") || ge2_d.equals("goal of number 3")));

            String ge1_get = ge1.getGameEventType().toString();
            String ge2_get = ge2.getGameEventType().toString();

            String red_card = GameEventType.RED_CARD.toString();
            String goal = GameEventType.GOAL.toString();

            Boolean b1 = (ge1_get.equals(red_card) || ge1_get.equals(red_card));
            Boolean b2 = (ge2_get.equals(goal) || ge2_get.equals(goal));

//            Assert.assertEquals(true, b1);
//            Assert.assertEquals(true, b2);
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

        Date dateStart = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        dateStart.setHours(10);
        dateStart.setMinutes(15);
        dateStart.setSeconds(00);

        Date dateEnd = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
        dateEnd.setHours(10);
        dateEnd.setMinutes(15);
        dateEnd.setSeconds(00);
        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        int event_minute = 55;
        String description = "red card to number 7";
        GameEventType game_event_type = GameEventType.RED_CARD;

        GameEvent gameEvent = new GameEvent(game.getGameID(), game.getGameDate(), game.getGameDate(), event_minute, game_event_type, description);

        int event_minute2 = 26;
        String description2 = "goal of number 3";
        GameEventType game_event_type2 = GameEventType.GOAL;

        GameEvent gameEvent2 = new GameEvent(game.getGameID(), game.getGameDate(), game.getGameDate(), event_minute2, game_event_type2, description2);

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
            gameEventsDbInServer.insertGameEvent(gameEvent2);

            String getGameEvent_game_id = gameEventsDbInServer.getGameEvent(gameEvent.getEventId()).getGameId();

            Assert.assertEquals("game1", getGameEvent_game_id);

            Game getGame = gameDbInServer.getGame(getGameEvent_game_id);
            Set<String> judgesList = getGame.getJudgesOfTheGameList();
            Assert.assertEquals(3, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(majorJudge1.getEmailAddress()));

            Map<String, GameEvent> getMap_eventId_GameEvent_ByGameId = gameEventsDbInServer.getMap_eventId_GameEvent_ByGameId("game1");
            Assert.assertEquals(2, getMap_eventId_GameEvent_ByGameId.size());

            ArrayList<GameEvent> gameEvents = new ArrayList<>();
            for (String game_id: getMap_eventId_GameEvent_ByGameId.keySet())
            {
                gameEvents.add(getMap_eventId_GameEvent_ByGameId.get(game_id));
            }
            Assert.assertEquals(2, gameEvents.size());
            GameEvent ge1 = gameEvents.remove(0);
            GameEvent ge2 = gameEvents.remove(0);

            int ge1_em = ge1.getEventMinute();
            int ge2_em = ge2.getEventMinute();
            Assert.assertEquals(true, (55 == ge1_em || 55 == ge2_em));
            Assert.assertEquals(true, (26 == ge1_em || 26 == ge2_em));

            String ge1_d = ge1.getDescription();
            String ge2_d = ge2.getDescription();

            Assert.assertEquals(true, (ge1_d.equals("red card to number 7") || ge2_d.equals("red card to number 7")));
            Assert.assertEquals(true, (ge1_d.equals("goal of number 3") || ge2_d.equals("goal of number 3")));

            String ge1_get = ge1.getGameEventType().toString();
            String ge2_get = ge2.getGameEventType().toString();

            String red_card = GameEventType.RED_CARD.toString();
            String goal = GameEventType.GOAL.toString();

            Boolean b1 = (ge1_get.equals(red_card) || ge1_get.equals(red_card));
            Boolean b2 = (ge2_get.equals(goal) || ge2_get.equals(goal));

//            Assert.assertEquals(true, b1);
//            Assert.assertEquals(true, b2);
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

        Date dateStart = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        dateStart.setHours(10);
        dateStart.setMinutes(15);
        dateStart.setSeconds(00);

        Date dateEnd = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
        dateEnd.setHours(10);
        dateEnd.setMinutes(15);
        dateEnd.setSeconds(00);

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
            Assert.assertEquals(3, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(majorJudge1.getEmailAddress()));


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

        Date dateStart = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        dateStart.setHours(10);
        dateStart.setMinutes(15);
        dateStart.setSeconds(00);

        Date dateEnd = new GregorianCalendar(2020, Calendar.FEBRUARY, 12).getTime();
        dateEnd.setHours(10);
        dateEnd.setMinutes(15);
        dateEnd.setSeconds(00);

        Game game = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName(), judgesOfTheGameList, majorJudge1.getEmailAddress(), dateEnd);

        int event_minute = 55;
        String description = "red card to number 7";
        GameEventType game_event_type = GameEventType.RED_CARD;

        GameEvent gameEvent = new GameEvent(game.getGameID(), game.getGameDate(), game.getGameDate(), event_minute, game_event_type, description);

        int event_minute2 = 26;
        String description2 = "goal of number 3";
        GameEventType game_event_type2 = GameEventType.GOAL;

        GameEvent gameEvent2 = new GameEvent(game.getGameID(), game.getGameDate(), game.getGameDate(), event_minute2, game_event_type2, description2);

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
            gameEventsDbInServer.insertGameEvent(gameEvent2);

            String getGameEvent_game_id = gameEventsDbInServer.getGameEvent(gameEvent.getEventId()).getGameId();

            Assert.assertEquals("game1", getGameEvent_game_id);

            Game getGame = gameDbInServer.getGame(getGameEvent_game_id);
            Set<String> judgesList = getGame.getJudgesOfTheGameList();
            Assert.assertEquals(3, judgesList.size());
            Assert.assertEquals(true, judgesList.contains(judge1.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(judge2.getEmailAddress()));
            Assert.assertEquals(true, judgesList.contains(majorJudge1.getEmailAddress()));

            Map<String, GameEvent> getMap_eventId_GameEvent_ByGameId = gameEventsDbInServer.getMap_eventId_GameEvent_ByGameId("game1");
            Assert.assertEquals(2, getMap_eventId_GameEvent_ByGameId.size());

            ArrayList<GameEvent> gameEvents = new ArrayList<>();
            for (String game_id: getMap_eventId_GameEvent_ByGameId.keySet())
            {
                gameEvents.add(getMap_eventId_GameEvent_ByGameId.get(game_id));
            }
            Assert.assertEquals(2, gameEvents.size());
            GameEvent ge1 = gameEvents.remove(0);
            GameEvent ge2 = gameEvents.remove(0);

            int ge1_em = ge1.getEventMinute();
            int ge2_em = ge2.getEventMinute();
            Assert.assertEquals(true, (55 == ge1_em || 55 == ge2_em));
            Assert.assertEquals(true, (26 == ge1_em || 26 == ge2_em));

            String ge1_d = ge1.getDescription();
            String ge2_d = ge2.getDescription();

            Assert.assertEquals(true, (ge1_d.equals("red card to number 7") || ge2_d.equals("red card to number 7")));
            Assert.assertEquals(true, (ge1_d.equals("goal of number 3") || ge2_d.equals("goal of number 3")));

            String ge1_get = ge1.getGameEventType().toString();
            String ge2_get = ge2.getGameEventType().toString();

            String red_card = GameEventType.RED_CARD.toString();
            String goal = GameEventType.GOAL.toString();

            Boolean b1 = (ge1_get.equals(red_card) || ge1_get.equals(red_card));
            Boolean b2 = (ge2_get.equals(goal) || ge2_get.equals(goal));

//            Assert.assertEquals(true, b1);
//            Assert.assertEquals(true, b2);

            gameEventsDbInServer.deleteAll();
        }
        catch (Exception e)
        {
            Assert.assertEquals("GameEvent already exist in system", e.getMessage());
        }
    }
}
