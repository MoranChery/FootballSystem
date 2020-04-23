package Controller;

import Data.*;
import Model.Court;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.JudgeType;
import Model.Enums.QualificationJudge;
import Model.Game;
import Model.SeasonLeague;
import Model.Team;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Judge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

public class JudgeControllerTest {

    private JudgeController judgeController = new JudgeController();
    private JudgeDb judgeDb = JudgeDbInMemory.getInstance();
    private GameDb gameDb = GameDbInMemory.getInstance();

    @Before
    public void init() {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(JudgeDbInMemory.getInstance());
        dbs.add(GameDbInMemory.getInstance());
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
        Judge judge = new Judge("email", "1234",1,"first","last", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
        judgeController.createJudge(judge);
        try {
            Judge newJudge = new Judge("email", "1234",1,"first","last", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            judgeController.createJudge(newJudge);
        }
        catch (Exception e){
            Assert.assertEquals("Judge already exists in the system", e.getMessage());
        }
    }
    @Test
    public void createJudgeLegal() throws Exception {
        Judge newJudge = new Judge("email", "1234",1,"first","last", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
        judgeController.createJudge(newJudge);
        Judge testJudge = JudgeDbInMemory.getInstance().getJudge("email");
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
    @Test
    public void getJudgeLegal() throws Exception {
        Judge newJudge = new Judge("email", "1234",1,"first","last", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
        judgeController.createJudge(newJudge);
        Judge judge = judgeController.getJudge("email");
        Assert.assertEquals(judge.getEmailAddress(), newJudge.getEmailAddress());
    }

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
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }
    @Test
    public void wantToEditQualificationSameQ() throws Exception{
        try {
            Judge newJudge = new Judge("email", "1234",1,"first","last", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            judgeDb.createJudge(newJudge);
            String theQualificationJudge = newJudge.getQualificationJudge().toString();
            judgeController.wantToEditQualification("email", theQualificationJudge);
        }
        catch (Exception e){
            Assert.assertEquals("This qualification equal the previous", e.getMessage());
        }
    }

    @Test
    public void wantToEditQualificationLegal() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
        judgeDb.createJudge(newJudge);
        String theQualificationJudge = QualificationJudge.BEACH_FOOTBALL.toString();
        judgeController.wantToEditQualification("email", theQualificationJudge);
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
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Team homeTeam = new Team();
            Team awayTeam = new Team();
            SeasonLeague seasonLeague = new SeasonLeague("A","B", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            Set<Judge> judges = new HashSet<>();
            Court court = new Court("court", "Netanya");
            Game game = new Game(1, simpleDateFormat,null,new Team(), new Team(), null, judges);
            judgeController.addGameToTheJudge("judgeMail",game);
        }
        catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }
    @Test
    public void addGameToTheJudgeGameNotFound() throws Exception{
        try {
            Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Team homeTeam = new Team();
            Team awayTeam = new Team();
            SeasonLeague seasonLeague = new SeasonLeague("A","B", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            Set<Judge> judges = new HashSet<>();
            Court court = new Court("court", "Netanya");
            Game game = new Game(1, simpleDateFormat,null,new Team(), new Team(), null, judges);
            judgeController.createJudge(newJudge);
            judgeController.addGameToTheJudge(newJudge.getEmailAddress(),game);
        }
        catch (Exception e){
            Assert.assertEquals("Game not found", e.getMessage());
        }
    }
    @Test
    public void addGameToTheJudgeGameAlreadyExist() throws Exception{
        try {
            Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            judgeController.createJudge(newJudge);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Team homeTeam = new Team();
            Team awayTeam = new Team();
            SeasonLeague seasonLeague = new SeasonLeague("A","B", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            Set<Judge> judges = new HashSet<>();
            Court court = new Court("court", "Netanya");
            Game game = new Game(1, simpleDateFormat,null,new Team(), new Team(), null, judges);
            gameDb.createGame(game);
            Map<Integer, Game> gameMap = newJudge.getTheJudgeGameList();
            gameMap.put(1,game);
            newJudge.setTheJudgeGameList(gameMap);
            judgeController.addGameToTheJudge(newJudge.getEmailAddress(),game);
        }
        catch (Exception e){
            Assert.assertEquals("This game already in the system", e.getMessage());
        }
    }

    @Test
    public void addGameToTheJudgeGameLegal() throws Exception {
        Judge newJudge = new Judge("email", "1234", 1, "first", "last", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
        judgeController.createJudge(newJudge);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Team homeTeam = new Team();
        Team awayTeam = new Team();
        SeasonLeague seasonLeague = new SeasonLeague("A", "B", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        Set<Judge> judges = new HashSet<>();
        Court court = new Court("court", "Netanya");
        Game game = new Game(1, simpleDateFormat, null, new Team(), new Team(), null, judges);
        gameDb.createGame(game);
        judgeController.addGameToTheJudge(newJudge.getEmailAddress(), game);
        Assert.assertTrue(newJudge.getTheJudgeGameList().containsKey(game.getGameID()));
    }






}
