package Controller;

import Data.*;
import Model.*;
import Model.Enums.*;
import Model.UsersTypes.Judge;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.*;

public class JudgeControllerTest extends BaseEmbeddedSQL{

//    private JudgeController judgeController = new JudgeController();
//    private JudgeDb judgeDb = JudgeDbInMemory.getInstance();
//    private GameDb gameDb = GameDbInMemory.getInstance();
//    private GameEventsDb gameEventsDb = GameEventsDbInMemory.getInstance();
//    private RoleDb roleDb = roleDb;
//    private String path = "C:\\Users\\noyha\\IdeaProjects\\footballtest";
private JudgeController judgeController = new JudgeController();
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
    private Gson prettyGson = new Gson();

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


        for (Db db : dbs) {
            db.deleteAll();
        }
    }

    @Test
    public void createJudgeNull() throws Exception {
        try {
            judgeController.createJudge(null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Can't create this judge", e.getMessage());
        }
    }
    @Test
    public void createJudgeExist() throws Exception{
        Judge judge = new Judge("email", "1234",1,"first","last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(judge);
        judgeDb.insertJudge(judge);
        try {
            Judge newJudge = new Judge("email", "1234",1,"first","last", QualificationJudge.JUNIOR);
            judgeDb.insertJudge(newJudge);
        }
        catch (Exception e){
            Assert.assertEquals("Judge already exists in the system", e.getMessage());
        }
    }
    @Test
    public void createJudgeLegal() throws Exception {
        Judge newJudge = new Judge("email", "1234",1,"first","last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.insertRole(newJudge.getEmailAddress(),null,RoleType.JUDGE);
        Judge testJudge = judgeDb.getJudge("email");
        Assert.assertEquals(newJudge.getEmailAddress(), testJudge.getEmailAddress());
    }
    @Test
    public void getJudgeNull() throws Exception {
        try {
            judgeController.getJudge(null);
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }
//    @Test
//    public void getJudgeLegal() throws Exception {
//        Judge newJudge = new Judge("email@gmail.com", "1234",1,"first","last", QualificationJudge.JUNIOR);
//        subscriberDb.insertSubscriber(newJudge);
//        judgeDb.insertJudge(newJudge);
//        roleDb.insertRole("email@gmail.com",null,RoleType.JUDGE);
//        Judge judge = judgeController.getJudge("email");
//        Assert.assertEquals(judge.getEmailAddress(), newJudge.getEmailAddress());
//    }

    /** Tests for the function wantToEditQualification **/

    @Test
    public void testWantToEditQualificationNullInput() throws Exception{
        try {
            judgeController.wantToEditQualification("id", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Something went wrong in editing judge's qualification", e.getMessage());
        }
    }
    @Test
    public void wantToEditQualificationNotFound() throws Exception{
        try {
            judgeController.wantToEditQualification("id", "qualification");
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }
    @Test
    public void wantToEditQualificationSameQ() throws Exception{
        try {
            Judge newJudge = new Judge("email", "1234",1,"first","last", QualificationJudge.JUNIOR);
            subscriberDb.insertSubscriber(newJudge);
            judgeDb.insertJudge(newJudge);
            roleDb.insertRole(newJudge.getEmailAddress(),null,RoleType.JUDGE);
            String theQualificationJudge = newJudge.getQualificationJudge().toString();
            judgeController.wantToEditQualification("email", theQualificationJudge);
        }
        catch (Exception e){
            Assert.assertEquals("This qualification equal the previous", e.getMessage());
        }
    }

    @Test
    public void wantToEditQualificationLegal() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        String theQualificationJudge = QualificationJudge.NATIONAL.toString();
        judgeController.wantToEditQualification("email", theQualificationJudge);
        newJudge = judgeDb.getJudge("email");
        Assert.assertEquals(newJudge.getQualificationJudge().toString(),theQualificationJudge);
    }

    /** Tests for the function addGameToTheJudge **/

    @Test
    public void addGameToTheJudgeNullInput() throws Exception{
        try {
            judgeController.addGameToTheJudge("judgeMail", null);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the inputs wrong", e.getMessage());
        }
    }
    @Test
    public void addGameToTheJudgeNotFound() throws Exception{
        try {
            Date date = new Date();
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
            Game game = new Game("gameId",date,"s1_l1","team1","team2","courtName",judgeGame,null,date);
            gameDb.insertGame(game);
            judgeController.addGameToTheJudge("judgeMail",game);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }
    @Test
    public void addGameToTheJudgeGameNotFound() throws Exception{
        try {
            Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
            subscriberDb.insertSubscriber(newJudge);
            judgeDb.insertJudge(newJudge);
            roleDb.insertRole(newJudge.getEmailAddress(),null,RoleType.JUDGE);

            Date date = new Date();
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
            Game game = new Game("gameId",date,"s1_l1","team1","team2","courtName",judgeGame,null,date);
            gameDb.insertGame(game);
            judgeController.addGameToTheJudge(newJudge.getEmailAddress(),game);
        }
        catch (Exception e){
            Assert.assertEquals("Game not found", e.getMessage());
        }
    }
    @Test
    public void addGameToTheJudgeGameAlreadyExist() throws Exception{
        try {
            Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
            subscriberDb.insertSubscriber(newJudge);
            judgeDb.insertJudge(newJudge);
            roleDb.insertRole(newJudge.getEmailAddress(),null,RoleType.JUDGE);

            Date date = new Date();
            teamDb.insertTeam("host");
            teamDb.insertTeam("away");
            seasonDb.insertSeason(new Season("A"));
            leagueDb.insertLeague(new League("B"));
            SeasonLeague seasonLeague = new SeasonLeague("A","B", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            seasonLeagueDb.insertSeasonLeague(seasonLeague);

            Set<String> judges = new HashSet<>();
            Court court = new Court("court", "Netanya");
            courtDb.insertCourt(court);
            Game game = new Game("1", date,"A_B","host", "away", "court", judges,newJudge.getEmailAddress(),date);
            gameDb.insertGame(game);
//
//            List<String> gameMap = newJudge.getTheJudgeGameList();
//            gameMap.add("1");
//            newJudge.setTheJudgeGameList(gameMap);

            judgeController.addGameToTheJudge(newJudge.getEmailAddress(),game);
        }
        catch (Exception e){
            Assert.assertEquals("This game already in the system", e.getMessage());
        }
    }

    @Test
    public void addGameToTheJudgeGameLegal() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.insertRole(newJudge.getEmailAddress(),null,RoleType.JUDGE);

        teamDb.insertTeam("host");
        teamDb.insertTeam("away");
        seasonDb.insertSeason(new Season("A"));
        leagueDb.insertLeague(new League("B"));
        SeasonLeague seasonLeague = new SeasonLeague("A", "B", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);

        Set<String> judges = new HashSet<>();
        Court court = new Court("court", "Netanya");
        courtDb.insertCourt(court);
        Game game = new Game("1", new Date(), "A_B", "host", "away", "court", judges,null,new Date());
        gameDb.insertGame(game);

        judgeController.addGameToTheJudge(newJudge.getEmailAddress(), game);
        newJudge = judgeDb.getJudge(newJudge.getEmailAddress());
        Assert.assertTrue(newJudge.getTheJudgeGameList().contains(game.getGameID()));
    }

    @Test
    public void addEventToGameNull() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.insertRole(newJudge.getEmailAddress(),null,RoleType.JUDGE);
        try {
            judgeController.addEventToGame(newJudge.getEmailAddress(), null, null, null, null, null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void addEventToGameNotPermissions() throws Exception {
//        Coach notPremited = new Coach("email", "1234", 1, "first", "last",CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(newJudge.getEmailAddress(), RoleType.COACH);
        try {
            judgeController.addEventToGame(newJudge.getEmailAddress(), "id", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber hasn't judge permissions", e.getMessage());
        }
    }

    @Test
    public void addEventToGameNotExistsGame() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem("email", RoleType.JUDGE);
        try {
            judgeController.addEventToGame(newJudge.getEmailAddress(), "id", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Game not found", e.getMessage());
        }
    }

    @Test
    public void addEventToGameNotFoundJudge() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
//        judgeDb.insertJudge(newJudge);
//        roleDb.createRoleInSystem("email", RoleType.JUDGE);
        try {
            judgeController.addEventToGame(newJudge.getEmailAddress(), "id", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }

    @Test
    public void addEventToGameJudgeNotAssociatedWithGame() throws Exception {
        teamDb.insertTeam("host");
        teamDb.insertTeam("away");
        seasonDb.insertSeason(new Season("A"));
        leagueDb.insertLeague(new League("B"));
        SeasonLeague seasonLeague = new SeasonLeague("A", "B", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);

        Set<String> judges = new HashSet<>();
        Court court = new Court("court", "Netanya");
        courtDb.insertCourt(court);
        Game game = new Game("id", new Date(), "A_B", "host", "away", "court", null,null,new Date());
        gameDb.insertGame(game);

        String judgeMail = "email";
        Judge newJudge = new Judge(judgeMail, "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(judgeMail, RoleType.JUDGE);
        Judge judge = judgeDb.getJudge(judgeMail);

        List<String> judgeGame = new ArrayList<>();
        judgeGame.add("notAssociated");
        judge.setTheJudgeGameList(judgeGame);
        try {
            judgeController.addEventToGame(newJudge.getEmailAddress(), "id", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This game doesnt associated with current judge", e.getMessage());
        }
    }

    @Test
    public void addEventToGameLegal() throws Exception {
        Date date = new Date();
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

        judgeController.addEventToGame(newJudge.getEmailAddress(), "id", new Date(), 40, GameEventType.GOAL, "foul");
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
    public void updateGameEventAfterEndNullInputs() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.insertRole(newJudge.getEmailAddress(),null,RoleType.JUDGE);
        try {
            judgeController.updateGameEventAfterEnd(newJudge.getEmailAddress(), null, null, null, null, null,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void updateGameEventAfterEndeNotPermissions() throws Exception {
//        Coach notPremited = new Coach("email", "1234", 1, "first", "last",CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(newJudge.getEmailAddress(), RoleType.COACH);
        try {
            Thread.sleep(100);
            judgeController.updateGameEventAfterEnd(newJudge.getEmailAddress(), "gameId","eventId", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber hasn't judge permissions", e.getMessage());
        }
    }
    @Test
    public void updateGameEventAfterEndNotExistsGame() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem("email", RoleType.JUDGE);
        try {
            judgeController.updateGameEventAfterEnd(newJudge.getEmailAddress(), "gameId","eventId", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Game not found", e.getMessage());
        }
    }

    @Test
    public void updateGameEventAfterEndNotFoundJudge() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
//        judgeDb.insertJudge(newJudge);
//        roleDb.createRoleInSystem("email", RoleType.JUDGE);
        try {
            judgeController.updateGameEventAfterEnd(newJudge.getEmailAddress(), "gameId","eventId", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }

    @Test
    public void  updateGameEventAfterEndJudgeNotAssociatedWithGame() throws Exception {
        Date date = new Date();
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
        Game game = new Game("gameId",date,"s1_l1","team1","team2","courtName",judgeGame,null,date);
        gameDb.insertGame(game);

        String judgeMail = "email";
        Judge newJudge = new Judge(judgeMail, "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(judgeMail, RoleType.JUDGE);

        Judge judge = judgeDb.getJudge(judgeMail);

//        List<String> judgeGame = new ArrayList<>();
//        judgeGame.add("notAssociated");
//        judge.setTheJudgeGameList(judgeGame);
        try {
            judgeController.updateGameEventAfterEnd(newJudge.getEmailAddress(), "gameId","eventId", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This game doesnt associated with current judge", e.getMessage());
        }
    }


    @Test
    public void updateGameEventAfterEndNotMajorJudge() throws Exception {
        Game game = new Game("gameId",new Date(),null,null,null,null,null,"notExists",null);
        gameDb.insertGame(game);
        String judgeMail = "email";
        Judge newJudge = new Judge(judgeMail, "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(judgeMail, RoleType.JUDGE);
        Judge judge = judgeDb.getJudge(judgeMail);

        List<String> judgeGame = new ArrayList<>();
        judgeGame.add("gameId");
        judge.setTheJudgeGameList(judgeGame);
        try {
            judgeController.updateGameEventAfterEnd(newJudge.getEmailAddress(), "gameId","eventId", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This judge is not a major judge in this game", e.getMessage());
        }
    }

    @Test
    public void updateGameEventAfterEndMajorJudgeAfter5Hours() throws Exception {
        String judgeMail = "email";

        Judge newJudge = new Judge(judgeMail, "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(judgeMail, RoleType.JUDGE);

        Date endDate = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        Date date = new Date();
        teamDb.insertTeam("host");
        teamDb.insertTeam("away");
        seasonDb.insertSeason(new Season("A"));
        leagueDb.insertLeague(new League("B"));
        SeasonLeague seasonLeague = new SeasonLeague("A","B", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);

        Set<String> judges = new HashSet<>();

        Court court = new Court("court", "Netanya");
        courtDb.insertCourt(court);

        Game game = new Game("gameId",new Date(),"A_B","host","away","court",judges,judgeMail,endDate);
        gameDb.insertGame(game);

        Judge judge = judgeDb.getJudge(judgeMail);
//
//        List<String> judgeGame = new ArrayList<>();
//        judgeGame.add("gameId");
//        judge.setTheJudgeGameList(judgeGame);
        try {
            judgeController.updateGameEventAfterEnd(newJudge.getEmailAddress(), "gameId","eventId", new Date(), 40, GameEventType.GOAL, "foul");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("5 hours from the end of the game passed", e.getMessage());
        }
    }

    @Test
    public void updateGameEventAfterEndLegal() throws Exception {
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


        judgeController.updateGameEventAfterEnd(judgeMail, "gameId","eventId", date, 45, GameEventType.GOAL, "foul");
        eventTest = gameEventsDb.getGameEvent("eventId");

        Assert.assertEquals(eventTest.getEventMinute(),45,0);
        Assert.assertEquals(eventTest.getGameEventType(), GameEventType.GOAL);
    }

    @Test
    public void createReportForGameNullInputs() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        try {
            judgeController.createReportForGame(path,null,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void createReportForGameNotPermissions() throws Exception {
//        Coach notPremited = new Coach("email", "1234", 1, "first", "last",CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(newJudge.getEmailAddress(), RoleType.COACH);
        try {
            Thread.sleep(100);
            judgeController.createReportForGame(path, "email","gameId");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber hasn't judge permissions", e.getMessage());
        }
    }

    @Test
    public void createReportForGameNotExistsGame() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem("email", RoleType.JUDGE);
        try {
            judgeController.createReportForGame(path, "email","gameId");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Game not found", e.getMessage());
        }
    }


    @Test
    public void  createReportForGameJudgeNotAssociatedWithGame() throws Exception {
        Game game = new Game("id",new Date(),null,null,null,null,null,null,null);
        gameDb.insertGame(game);
        String judgeMail = "email";
        Judge newJudge = new Judge(judgeMail, "1234", 1, "first", "last", QualificationJudge.JUNIOR);
        subscriberDb.insertSubscriber(newJudge);
        judgeDb.insertJudge(newJudge);
        roleDb.createRoleInSystem(judgeMail, RoleType.JUDGE);
        Judge judge = judgeDb.getJudge(judgeMail);

        List<String> judgeGame = new ArrayList<>();
        judgeGame.add("notAssociated");
        judge.setTheJudgeGameList(judgeGame);
        try {
            judgeController.createReportForGame(path,newJudge.getEmailAddress(),"id");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This game doesnt associated with current judge", e.getMessage());
        }
    }

    @Test
    public void createReportForGameLegal() throws Exception {
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

        judgeController.addEventToGame(newJudge.getEmailAddress(), "id", new Date(), 40, GameEventType.GOAL, "foul");
        Map<String, GameEvent> gameEvents = gameEventsDb.getMap_eventId_GameEvent_ByGameId("id");
        Assert.assertEquals(gameEvents.size(),1);
        String key = (String) gameEvents.keySet().toArray()[0];
        GameEvent event = gameEventsDb.getGameEvent(key);
        Assert.assertEquals(event.getGameId(),"id");
        Assert.assertEquals(event.getEventMinute(),40,0);
        Assert.assertEquals(event.getGameEventType(), GameEventType.GOAL);
        Assert.assertEquals(event.getDescription(),"foul");

        judgeController.createReportForGame(path,newJudge.getEmailAddress(),"id");
        BufferedReader reader = new BufferedReader(new FileReader(path + "id"));
        Map<String, GameEvent> gameEventMap = prettyGson.fromJson(reader, new TypeToken<HashMap<String, GameEvent>>() {}.getType());
        GameEvent ge = gameEventMap.get(key);
        Assert.assertEquals(ge.getGameId(),"id");
        Assert.assertEquals(ge.getEventMinute(),40,0);
        Assert.assertEquals(ge.getGameEventType(), GameEventType.GOAL);
        Assert.assertEquals(ge.getDescription(),"foul");
    }
}
