package Controller;

import Data.*;
import Model.Enums.*;
import Model.*;
import Model.UsersTypes.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.rmi.server.InactiveGroupException;

import java.sql.SQLException;
import java.util.*;

public class SystemAdministratorControllerTest extends BaseEmbeddedSQL{
//public class SystemAdministratorControllerTest {
    private SystemAdministratorController systemAdministratorController = new SystemAdministratorController();
    private GuestController guestController = new GuestController();
    private TeamDb teamDb = TeamDbInServer.getInstance();
    private SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();


    @Before
    public void init() throws SQLException {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(subscriberDb);
        dbs.add(teamDb);
        for (Db db : dbs) {
            db.deleteAll();
        }
        try {
            guestController.registerSystemAdministrator("noy@gmail.com", "ae646", 123456789, "noy", "harary");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void closeTeamForEverEmptyString() throws Exception {
        try {
            systemAdministratorController.closeTeamForEver("");
        }
        catch (Exception e){

            Assert.assertEquals("Not valid name", e.getMessage());
        }
    }

    @Test
    public void closeTeamForEverTeamNotFound() throws Exception{
        try {
            systemAdministratorController.closeTeamForEver("not exist");
        }
        catch (Exception e){

            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void closeTeamForEverLegal() throws Exception {
        Team newTeam = new Team();
        newTeam.setTeamName("team");
        newTeam.setTeamManagers(null);
        teamDb.insertTeam("team", 0.0, TeamStatus.ACTIVE);
        Map<String, TeamOwner> teamOwnerMap = new HashMap<>();
        Map<String, TeamManager> teamManagerMap = new HashMap<>();
        String emailAddress = "email@gmail.com";
        String password = "psw@123";
        Integer id = 1;
        String firstName = "first";
        String lastName = "last";
        String team = "team";
        TeamOwner teamOwner = new TeamOwner(emailAddress, password, id, firstName, lastName, team);
        TeamManager teamManager = new TeamManager("manager@gmail.com", "password", 2, "firstName", "lastName", emailAddress);
        newTeam.setTeamOwners(teamOwnerMap);
        newTeam.setTeamManagers(teamManagerMap);
        systemAdministratorController.closeTeamForEver("team");

        Team teamFromDb = teamDb.getTeam("team");
        Assert.assertNotNull(teamFromDb.getTeamClose());
        Assert.assertEquals(TeamStatus.INACTIVE,teamFromDb.getTeamStatus());
    }
    public void closeTeamForEverListNull() throws Exception{
        try {

            Team newTeam = new Team();
            newTeam.setTeamName("team");
            teamDb.insertTeam("team", 0.0, TeamStatus.ACTIVE);
            newTeam.setTeamOwners(null);
            systemAdministratorController.closeTeamForEver("team");
        }
        catch (Exception e){
            Assert.assertEquals("one or more of the lists is null", e.getMessage());
        }
    }


    @Test
    public void removeNotExistSubscriber() {
        try {
            systemAdministratorController.removeSubscriber("loy@gmail.com", "noy@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "subscriber not found");
        }
    }

    //team owner
    @Test
    public void removeNullSubscriberAndNullAdmin() {
        try {
            systemAdministratorController.removeSubscriber(null, null);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), null + " email");
        }
    }

    @Test
    public void removeSubscriberWithNullAdmin() {
        try {
            systemAdministratorController.removeSubscriber("hila", null);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), null + " email");
        }
    }

    @Test
    public void removeNullSubscriber() {
        try {
            systemAdministratorController.removeSubscriber(null, "hila");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), null + " email");
        }
    }

    @Test
    public void removeExistFanSubscriber() {
        try {
            //register fan to remove
            guestController.registerFan("fan@gmail.com", "L1o8oy", 111111111, "Noy", "Harary");
            //register player to connect it to page that the fan will follow it
            guestController.registerPlayer("player@gmail.com", "L1o8oy", 111111111, "Noy", "Harary", new Date(12 / 9 / 1222), PlayerRole.GOALKEEPER);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
        Player player = null;
        Fan fan = null;
        try {
            player = PlayerDbInMemory.getInstance().getPlayer("player@gmail.com");
            //PageDbInMemory.getInstance().createPersonalPage("player@gmail.com",player);
            fan = FanDbInMemory.getInstance().getFan("fan@gmail.com");
            PageDbInMemory.getInstance().getPage("player@gmail.com").addFanFollowingThisPage(fan);
            Set pageSet = new LinkedHashSet();
            pageSet.add(PageDbInMemory.getInstance().getPage("player@gmail.com"));
            fan.setMyPages(pageSet);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
        try {
            systemAdministratorController.removeSubscriber("fan@gmail.com", "noy@gmail.com");
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
        //check if the fan deleted properly
        try {
            SubscriberDbInMemory.getInstance().getSubscriber("fan@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "subscriber not found");
        }
        try {
            FanDbInMemory.getInstance().getFan("fan@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Fan not found");
        }
        try {
            Page page = PageDbInMemory.getInstance().getPage("player@gmail.com");
            for (String fan1 : page.getFansFollowingThisPage().keySet()) {
                if (fan.equals("fan@gmail.com"))
                    Assert.assertTrue(false);
            }
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void removeCoach() throws Exception {
        guestController.registerCoach("coach@gmail.com", "23bkh", 123456789, "noy", "harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        TeamDbInMemory.getInstance().insertTeam("team");
        Team team = TeamDbInMemory.getInstance().getTeam("team");
        team.setTeamStatus(TeamStatus.ACTIVE);
        Coach coach = CoachDbInMemory.getInstance().getCoach("coach@gmail.com");
        //connect coach to team
        team.getCoaches().put("coach@gmail.com", coach);
        coach.setTeam("team");
        //register fan to follow coach page
        guestController.registerFan("fan1@gmail.com", "L1o8oy", 111111111, "Noy", "Harary");
        Fan fan = FanDbInMemory.getInstance().getFan("fan1@gmail.com");
        PageDbInMemory.getInstance().getPage("coach@gmail.com").addFanFollowingThisPage(fan);
        Set pageSet = new LinkedHashSet();
        pageSet.add(PageDbInMemory.getInstance().getPage("coach@gmail.com"));
        fan.setMyPages(pageSet);
        systemAdministratorController.removeSubscriber("coach@gmail.com", "noy@gmail.com");
        //check if the fan deleted properly
        try {
            SubscriberDbInMemory.getInstance().getSubscriber("coach@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "subscriber not found");
        }
        try {
            CoachDbInMemory.getInstance().getCoach("coach@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "Coach not found");
        }
        //check if coach page removed
        try {
            PageDbInMemory.getInstance().getPage("coach@gmail.com");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        //check if the page removed from the fan
        if (fan.getMyPages().size() > 0)
            Assert.assertTrue(false);
        else Assert.assertTrue(true);


    }

    @Test
    public void removeJudge() throws Exception {
        //initializing
        guestController.registerJudge("judge@gmail.com", "23bkh", 123456789, "noy", "harary", QualificationJudge.NATIONAL);
        SeasonLeague seasonLeague = new SeasonLeague("season", "league", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeagueDbInMemory.getInstance().insertSeasonLeague(seasonLeague);
        JudgeSeasonLeague judgeSeasonLeague = new JudgeSeasonLeague(seasonLeague.getSeasonLeagueName(), "judge@gmail.com");
        SeasonLeagueDbInMemory.getInstance().createJudgeSeasonLeague(judgeSeasonLeague);
        Judge judge = JudgeDbInMemory.getInstance().getJudge("judge@gmail.com");
        Set judges = new LinkedHashSet();
        judges.add(judge);
        Game game = new Game("11", null, "season", null, null, null, judges,null,null);
        GameDbInMemory.getInstance().insertGame(game);
        judge.addGameToList(game);
        //remove judge
        systemAdministratorController.removeSubscriber("judge@gmail.com", "noy@gmail.com");
        //check if the fan deleted properly
        try {
            JudgeSeasonLeagueDbInMemory.getInstance().getJudgeSeasonLeague(judgeSeasonLeague.getJudgeSeasonLeagueName());
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "JudgeSeasonLeague not found");
        }
        for (String game1 : judge.getTheJudgeGameList()) {
            Game game2 = GameDbInMemory.getInstance().getGame(game1);
            if (game2.getJudgesOfTheGameList().contains(judge))
                Assert.assertTrue(false);
            else Assert.assertTrue(true);
        }
        try {
            JudgeDbInMemory.getInstance().getJudge("judge@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }
}
