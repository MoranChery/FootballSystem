package Service;

import Controller.BaseEmbeddedSQL;
import Controller.GuestController;
import Data.*;
import Model.Enums.RoleType;
import Model.UsersTypes.Fan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SystemAdministratorServiceTest extends BaseEmbeddedSQL
{
    private SystemAdministratorService systemAdministratorService=new SystemAdministratorService();
    GuestController guestController=new GuestController();

    @Before
    public void init()
    {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(SubscriberDbInMemory.getInstance());
        dbs.add(FanDbInMemory.getInstance());
        dbs.add(PageDbInMemory.getInstance());
        dbs.add( TeamDbInMemory.getInstance());
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
        for (Db db : dbs)
        {
//            db.deleteAll();
        }
        try {
            guestController.registerSystemAdministrator("noy@gmail.com","ae646",123456789,"noy","harary");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void closeNullTeam(){
        try {
            systemAdministratorService.closeTeamForEver(null);
        } catch (Exception e) {
            Assert.assertEquals("null team name exception!",e.getMessage());
        }
    }

    @Test
    public void closeNotExistTeam(){
        try {
            systemAdministratorService.closeTeamForEver("blaTeam");
        } catch (Exception e) {
            Assert.assertEquals("the team " + "blaTeam" + " doesn't exist in the system",e.getMessage());
        }
    }

    @Test
    public void closeExistTeam(){
        try {
            TeamDbInMemory.getInstance().insertTeam("barca");
            systemAdministratorService.closeTeamForEver("barca");
        } catch (Exception e) {
            //not should enter here
            Assert.assertEquals(e.getMessage(),null);
        }
    }

    @Test
    public void removeNullSubscriberAndNullAdmin(){
        try {
            systemAdministratorService.removeSubscriber(null,null);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
    @Test
    public void removeSubscriberWithNullAdmin(){
        try {
            systemAdministratorService.removeSubscriber("hila",null);
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
    @Test
    public void removeNullSubscriber(){
        try {
            systemAdministratorService.removeSubscriber(null,"hila");
        } catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeExistSubscriber(){
        Fan fan=new Fan("noa@gmail.com", "L1o8oy", 111111111, "Noy", "Harary");
        try {
            SubscriberDbInMemory.getInstance().insertSubscriber(fan);
            FanDbInMemory.getInstance().createFan(fan);
            RoleDbInMemory.getInstance().createRoleInSystem(fan.getEmailAddress(), RoleType.FAN);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(),null);
        }
        try {
            systemAdministratorService.removeSubscriber(fan.getEmailAddress(),"noy@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"the subscriber with the Email " + fan.getEmailAddress() + " doesn't in the system!");
        }
        //check if the fan deleted properly
        try {
            SubscriberDbInMemory.getInstance().getSubscriber("noa@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"subscriber not found");
        }
        try {
            FanDbInMemory.getInstance().getFan("noa@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(),"Fan not found");
        }
    }



}
