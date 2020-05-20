package Service;

import Data.*;
import Model.Court;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.QualificationJudge;
import Model.Game;
import Model.SeasonLeague;
import Model.Team;
import Model.UsersTypes.Judge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;

public class JudgeServiceTest {

    private JudgeService judgeService = new JudgeService();
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
        judgeDb.insertJudge(newJudge);
        String theQualificationJudge = QualificationJudge.NATIONAL.toString();
        judgeService.wantToEditQualification("email", theQualificationJudge);
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
        judgeDb.insertJudge(newJudge);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Team homeTeam = new Team();
        Team awayTeam = new Team();
        SeasonLeague seasonLeague = new SeasonLeague("A", "B", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        Set<String> judges = new HashSet<>();
        Court court = new Court("court", "Netanya");
        Game game = new Game("1", new Date(), null, new Team(), new Team(), null, judges,null,null);
        gameDb.createGame(game);
        judgeService.addGameToTheJudge(newJudge.getEmailAddress(), game);
        Assert.assertTrue(newJudge.getTheJudgeGameList().contains(game.getGameID()));
    }


}
