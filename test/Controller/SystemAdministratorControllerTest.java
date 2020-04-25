package Controller;

import Data.*;
import Model.Enums.*;
import Model.Page;
import Model.PersonalPage;
import Model.Team;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class SystemAdministratorControllerTest {
    SystemAdministratorController systemAdministratorController = new SystemAdministratorController();
    GuestController guestController = new GuestController();

    @Before
    public void init() {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(SubscriberDbInMemory.getInstance());
        dbs.add(FanDbInMemory.getInstance());
        dbs.add(PageDbInMemory.getInstance());
        dbs.add(TeamDbInMemory.getInstance());
        dbs.add(CoachDbInMemory.getInstance());
        dbs.add(CourtDbInMemory.getInstance());
        dbs.add(JudgeDbInMemory.getInstance());
        dbs.add(PlayerDbInMemory.getInstance());
        dbs.add(TeamManagerDbInMemory.getInstance());
        dbs.add(TeamOwnerDbInMemory.getInstance());
        dbs.add(SeasonLeagueDbInMemory.getInstance());
        dbs.add(JudgeSeasonLeagueDbInMemory.getInstance());
        dbs.add(RoleDbInMemory.getInstance());
        dbs.add(SystemAdministratorDbInMemory.getInstance());
        dbs.add(RepresentativeAssociationDbInMemory.getInstance());
        dbs.add(TeamDbInMemory.getInstance());
        dbs.add(RoleDbInMemory.getInstance());
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
    public void closeExistTeam() {
        try {
            TeamDbInMemory.getInstance().createTeam("barca");
            systemAdministratorController.closeTeamForEver("barca");
        } catch (Exception e) {
            //not should enter here
            Assert.assertEquals(e.getMessage(), null);
        }
    }

    @Test
    public void closeNullTeam() {
        try {
            systemAdministratorController.closeTeamForEver(null);
        } catch (Exception e) {
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void closeNotExistTeam() {
        try {
            systemAdministratorController.closeTeamForEver("blaTeam");
        } catch (Exception e) {
            Assert.assertEquals("Team not found", e.getMessage());
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
            //create player page
            //PageDbInMemory.getInstance().createPersonalPage("player@gmail.com",player);
            fan = FanDbInMemory.getInstance().getFan("fan@gmail.com");
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
        TeamDbInMemory.getInstance().createTeam("team");
        Team team=TeamDbInMemory.getInstance().getTeam("team");
        team.setTeamStatus(TeamStatus.ACTIVE);
        Coach coach=CoachDbInMemory.getInstance().getCoach("coach@gmail.com");
        //connect coach to team
        team.getCoaches().put("coach@gmail.com",coach);
        coach.setTeam(team);
        //register fan to follow coach page
        guestController.registerFan("fan1@gmail.com", "L1o8oy", 111111111, "Noy", "Harary");
        Fan fan=FanDbInMemory.getInstance().getFan("fan1@gmail.com");
        Set pageSet = new LinkedHashSet();
        pageSet.add(PageDbInMemory.getInstance().getPage("coach@gmail.com"));
        fan.setMyPages(pageSet);
        systemAdministratorController.removeSubscriber("coach@gmail.com","noy@gmail.com");
        //check if the fan deleted properly
        try {
            SubscriberDbInMemory.getInstance().getSubscriber("coach@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "subscriber not found");
        }
        try {
            CoachDbInMemory.getInstance().getCoach("coach@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "coach not found");
        }
        //check if coach page removed
        try {
            PageDbInMemory.getInstance().getPage("coach@gmail.com");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
        //check if the page removed from the fan
       if(fan.getMyPages().size()>0)
           Assert.assertTrue(false);
       else Assert.assertTrue(true);


    }


}
