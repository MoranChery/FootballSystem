package Service;

import Controller.BaseEmbeddedSQL;
import Controller.JudgeController;
import Data.*;
import Model.*;
import Model.Enums.*;
import Model.UsersTypes.Judge;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JudgeServiceTest extends BaseEmbeddedSQL
{
    JudgeService judgeService = new JudgeService();
    private SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();
    private JudgeDb judgeDb = JudgeDbInServer.getInstance();
    private GameDb gameDb = GameDbInServer.getInstance();
    private GameEventsDb gameEventsDb = GameEventsDbInServer.getInstance();
    private RoleDb roleDb = RoleDbInServer.getInstance();
    private GameJudgesListDb judgesListDb = GameJudgesListDbInServer.getInstance();
    private SeasonDb seasonDb = SeasonDbInServer.getInstance();
    private LeagueDb leagueDb = LeagueDbInServer.getInstance();
    private SeasonLeagueDb seasonLeagueDb = SeasonLeagueDbInServer.getInstance();
    private TeamDb teamDb = TeamDbInServer.getInstance();
    private CourtDb courtDb = CourtDbInServer.getInstance();
    private GameJudgesListDb gameJudgesListDb = GameJudgesListDbInServer.getInstance();
    private String path = "C:\\Users\\noyha\\IdeaProjects\\footballtest";
    //    private String path = "C:\\Users\\avira\\Desktop";
    private Gson prettyGson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm").create();

    @Before
    public void init() throws SQLException {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(courtDb);
        dbs.add(roleDb);
        dbs.add(judgeDb);
        dbs.add(subscriberDb);
        dbs.add(teamDb);
        dbs.add(gameDb);
        dbs.add(gameEventsDb);
        dbs.add(judgesListDb);
        dbs.add(seasonDb);
        dbs.add(leagueDb);
        dbs.add(seasonLeagueDb);
        dbs.add(gameJudgesListDb);
        dbs.add(TeamDbInServer.getInstance());
        for (Db db : dbs) {
            db.deleteAll();
        }
    }
    @Test
    public void testWantToEditQualificationNullInput() throws Exception{
        try {
            judgeService.wantToEditQualification("id", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Something went wrong in editing judge's qualification", e.getMessage());
        }
    }
    @Test
    public void wantToEditQualificationLegal() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        String theQualificationJudge = QualificationJudge.NATIONAL.toString();
        judgeService.wantToEditQualification("email", theQualificationJudge);
        newJudge = judgeDb.getJudge(newJudge.getEmailAddress());
        Assert.assertEquals(newJudge.getQualificationJudge().toString(),theQualificationJudge);
    }
    @Test
    public void addGameToTheJudgeNullInput() throws Exception{
        try {
            judgeService.addGameToTheJudge("judgeMail", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the inputs wrong", e.getMessage());
        }
    }
    @Test
    public void addGameToTheJudgeGameLegal() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        Season season = new Season("s1");
        seasonDb.insertSeason(season);
        League league = new League("l1");
        leagueDb.insertLeague(league);
        SeasonLeague seasonLeague = new SeasonLeague("s1", "l1", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);
        Court court = new Court("courtName","courtCity");
        courtDb.insertCourt(court);
        teamDb.insertTeam("team1");
        teamDb.insertTeam("team2");
        Set<String> judgeGame = new HashSet<>();
        Date date = new Date();
        Game game = new Game("gameId",date,"s1_l1","team1","team2","courtName",judgeGame,null,date);
        gameDb.insertGame(game);
        judgeService.addGameToTheJudge(newJudge.getEmailAddress(), game);
        newJudge = judgeDb.getJudge(newJudge.getEmailAddress());
        Assert.assertTrue(newJudge.getTheJudgeGameList().contains(game.getGameID()));
    }

    @Test
    public void testAddEventToGame() throws Exception {
        Season season = new Season("s1");
        seasonDb.insertSeason(season);
        League league = new League("l1");
        leagueDb.insertLeague(league);
        SeasonLeague seasonLeague = new SeasonLeague("s1", "l1", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);
        Court court = new Court("courtName","courtCity");
        courtDb.insertCourt(court);
        teamDb.insertTeam("team1");
        teamDb.insertTeam("team2");

        String judgeMail = "email";
        Judge newJudge = new Judge(judgeMail, "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(judgeMail, RoleType.JUDGE);

        Set<String> judgeGame = new HashSet<>();
        judgeGame.add(judgeMail);

        Game game = new Game("id",new Date(),"s1_l1","team1","team2","courtName",judgeGame,null,null);
        gameDb.insertGame(game);


        Judge judge = judgeDb.getJudge(judgeMail);

//        Set<String> judgeGame = new HashSet<>();
//        judgeGame.add("id");
//        judge.setTheJudgeGameList(judgeGame);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(date);
        judgeService.addEventToGame(newJudge.getEmailAddress(), "id",time , "40", GameEventType.GOAL.name(), "foul");
        Map<String, GameEvent> gameEvents = gameEventsDb.getMap_eventId_GameEvent_ByGameId("id");
        Assert.assertEquals(gameEvents.size(),1);
        String key = (String) gameEvents.keySet().toArray()[0];
        GameEvent event = gameEventsDb.getGameEvent(key);
        Assert.assertEquals(event.getGameId(),"id");
        Assert.assertEquals(event.getEventMinute(),40,0);
        Assert.assertEquals(event.getGameEventType(), GameEventType.GOAL);
        Assert.assertEquals(event.getDescription(),"foul");
    }

    @Test

        public void testUpdateGameEventAfterEnd() throws Exception {
        String judgeMail = "email@gmail.com";
//        Date endDate = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        Date date = new Date();

        Judge newJudge = new Judge(judgeMail, "1234", 1, "first", "last", QualificationJudge.JUNIOR);
//        Judge newJudge = new Judge("email@gmail.com", "1234",1,"first","last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.insertRole(judgeMail,null,RoleType.JUDGE);

        Season season = new Season("s1");
        seasonDb.insertSeason(season);
        League league = new League("l1");
        leagueDb.insertLeague(league);
        SeasonLeague seasonLeague = new SeasonLeague("s1", "l1", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);
        Court court = new Court("courtName","courtCity");
        courtDb.insertCourt(court);
        teamDb.insertTeam("team1");
        teamDb.insertTeam("team2");
        Set<String> judgeGame = new HashSet<>();
        judgeGame.add(judgeMail);
        Game game = new Game("gameId",date,"s1_l1","team1","team2","courtName",judgeGame,judgeMail,date);
        gameDb.insertGame(game);

//        judgesListDb.insertGameJudgeList("gameId",judgeGame);

        Judge judge = judgeDb.getJudge(judgeMail);


        GameEvent gameEvent = new GameEvent("gameId",date,date,40, GameEventType.FOUL,"foul");
        gameEvent.setEventId("eventId");
        gameEventsDb.addEvent(gameEvent);

        GameEvent eventTest = gameEventsDb.getGameEvent("eventId");
        Assert.assertEquals(eventTest.getEventMinute(),40,0);
        Assert.assertEquals(eventTest.getGameEventType(), GameEventType.FOUL);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(date);

        judgeService.updateGameEventAfterEnd(judgeMail, "gameId","eventId", time, "45", GameEventType.GOAL, "foul");
        eventTest = gameEventsDb.getGameEvent("eventId");

        Assert.assertEquals(eventTest.getEventMinute(),45,0);
        Assert.assertEquals(eventTest.getGameEventType(), GameEventType.GOAL);
    }

@Test
    public void testCreateReportForGame() throws Exception {
        String judgeMail = "email";
        Judge newJudge = new Judge(judgeMail, "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(judgeMail, RoleType.JUDGE);

        Set<String> judgeGame = new HashSet<>();
        judgeGame.add(judgeMail);

        teamDb.insertTeam("host");
        teamDb.insertTeam("away");
        seasonDb.insertSeason(new Season("A"));
        leagueDb.insertLeague(new League("B"));
        SeasonLeague seasonLeague = new SeasonLeague("A","B",CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0,InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);
        Court court = new Court("court","courtCity");
        courtDb.insertCourt(court);
        Game game = new Game("id", new Date(),"A_B","host", "away", "court", judgeGame,null,new Date());
        gameDb.insertGame(game);

        JudgeController judgeController = new JudgeController();
        judgeController.addEventToGame(newJudge.getEmailAddress(), "id", new Date(), 40, GameEventType.GOAL, "foul");
        Map<String, GameEvent> gameEvents = gameEventsDb.getMap_eventId_GameEvent_ByGameId("id");
        Assert.assertEquals(gameEvents.size(),1);
        String key = (String) gameEvents.keySet().toArray()[0];
        GameEvent event = gameEventsDb.getGameEvent(key);
        Assert.assertEquals(event.getGameId(),"id");
        Assert.assertEquals(event.getEventMinute(),40,0);
        Assert.assertEquals(event.getGameEventType(), GameEventType.GOAL);
        Assert.assertEquals(event.getDescription(),"foul");

//        judgeService.createReportForGame( path,newJudge.getEmailAddress(),"id");
//        BufferedReader reader = new BufferedReader(new FileReader(path + "id"));
//
//        Map<String, GameEvent> gameEventMap = prettyGson.fromJson(reader, new TypeToken<HashMap<String, GameEvent>>() {}.getType());
//
//        GameEvent ge = gameEventMap.get(key);
//        Assert.assertEquals(ge.getGameId(),"id");
//        Assert.assertEquals(ge.getEventMinute(),40,0);
//        Assert.assertEquals(ge.getGameEventType(), GameEventType.GOAL);
//        Assert.assertEquals(ge.getDescription(),"foul");
    }
//
//    public Map<String, GameEvent>  getEventsByGameId(@PathVariable String judgeMail, @PathVariable String gameId){


    }
