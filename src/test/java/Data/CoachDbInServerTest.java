package Data;

import Controller.BaseEmbeddedSQL;
import Controller.TeamOwnerController;
import Model.Enums.CoachRole;
import Model.Enums.PlayerRole;
import Model.Enums.QualificationCoach;
import Model.Enums.RoleType;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoachDbInServerTest extends BaseEmbeddedSQL {

    private TeamOwnerController teamOwnerController = new TeamOwnerController();
    private TeamDb teamDb = TeamDbInServer.getInstance();
    private CoachDb coachDb = CoachDbInServer.getInstance();
    private TeamManagerDb teamManagerDb = TeamManagerDbInServer.getInstance();
    private TeamOwnerDb teamOwnerDb =  TeamOwnerDbInServer.getInstance();
    private PlayerDb playerDb =  PlayerDbInServer.getInstance();
    private RoleDb roleDb = RoleDbInServer.getInstance();
    private SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();
    private CourtDb courtDb = CourtDbInServer.getInstance();
    private PermissionDb permissionDb = PermissionDbInServer.getInstance();
    private PageDb pageDb = PageDbInServer.getInstance();
    private FinancialActivityDb financialActivityDb = FinancialActivityDbInServer.getInstance();

    @Before
    public void init() throws SQLException {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(coachDb);
        dbs.add(courtDb);
        dbs.add(financialActivityDb);
        dbs.add(teamOwnerDb);
        dbs.add(playerDb);
        dbs.add(subscriberDb);
        dbs.add(teamManagerDb);
        dbs.add(teamManagerDb);
        dbs.add(roleDb);
        dbs.add(pageDb);
        dbs.add(permissionDb);
        dbs.add(teamDb);
        for (Db db : dbs) {
            db.deleteAll();
        }
    }


    @Test
    public void testInsertCoach_null() throws Exception
    {
        Coach coach = null;
        try
        {
            coachDb.insertCoach(coach);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad_input", e.getMessage());
        }
    }


    @Test
    public void testInsertCoach_legal() throws Exception
    {
        String emailAddress = "email@gmail.com";
        Coach coach = new Coach(emailAddress, "Pass1234",222222222, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        subscriberDb.insertSubscriber(coach);
        coachDb.insertCoach(coach);
        roleDb.insertRole(emailAddress,null, RoleType.COACH);
        Assert.assertEquals(RoleType.COACH,roleDb.getRole(emailAddress).getRoleType());
        coach = coachDb.getCoach(emailAddress);
        Assert.assertEquals(emailAddress, coach.getEmailAddress());
        Assert.assertEquals(222222222, coach.getId().intValue());
        Assert.assertEquals("first", coach.getFirstName());
        Assert.assertEquals("last", coach.getLastName());
        Assert.assertEquals(CoachRole.MAJOR, coach.getCoachRole());
        Assert.assertEquals(QualificationCoach.UEFA_A, coach.getQualificationCoach());
        Assert.assertNull(coach.getTeam());
        Assert.assertEquals("Pass1234",coach.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(emailAddress));
    }

    @Test
    public void testInsertCoach_PlayerExists() throws Exception
    {

        String emailAddress = "email@gmail.com";
        Coach coach = new Coach(emailAddress, "Pass1234",222222222, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        try
        {
            subscriberDb.insertSubscriber(coach);
            coachDb.insertCoach(coach);
            coachDb.insertCoach(coach);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Duplicate entry \'" + emailAddress+ "\' for key \'PRIMARY\'", e.getMessage());
        }
    }
    @Test
    public void testGetCoach_null() throws Exception {

        try
        {
            coachDb.getCoach(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testGetCoach_legal() throws Exception{
        String emailAddress = "email@gmail.com";
        Coach coach = new Coach(emailAddress, "Pass1234",222222222, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        subscriberDb.insertSubscriber(coach);
        coachDb.insertCoach(coach);
        coach = coachDb.getCoach(emailAddress);
        Assert.assertEquals(emailAddress, coach.getEmailAddress());
        Assert.assertEquals(222222222, coach.getId().intValue());
        Assert.assertEquals("first", coach.getFirstName());
        Assert.assertEquals("last", coach.getLastName());
        Assert.assertEquals(CoachRole.MAJOR, coach.getCoachRole());
        Assert.assertEquals(QualificationCoach.UEFA_A, coach.getQualificationCoach());
        Assert.assertNull(coach.getTeam());
        Assert.assertEquals("Pass1234",coach.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(emailAddress));
    }


    @Test
    public void testgetCoach_notExists() throws Exception{
        String emailAddress = "email@gmail.com";
        Coach coach = new Coach(emailAddress, "Pass1234",222222222, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        try
        {
            subscriberDb.insertSubscriber(coach);
            coachDb.getCoach(emailAddress);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Coach not found", e.getMessage());
        }
    }

    @Test
    public void TestUpdateCoachDetails_legal() throws Exception {
        String emailAddress = "email@gmail.com";
        Date birthDate = new Date();
        Coach coach = new Coach(emailAddress, "Pass1234",222222222, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        subscriberDb.insertSubscriber(coach);
        coachDb.insertCoach(coach);
        Assert.assertEquals("first",coach.getFirstName());
        coachDb.updateCoachDetails(emailAddress,"firstChange","last",CoachRole.GOALKEEPER, QualificationCoach.UEFA_A);
        coach = coachDb.getCoach(emailAddress);
        Assert.assertEquals(emailAddress, coach.getEmailAddress());
        Assert.assertEquals(222222222, coach.getId().intValue());
        Assert.assertEquals("firstChange", coach.getFirstName());
        Assert.assertEquals("last", coach.getLastName());
        Assert.assertEquals(CoachRole.GOALKEEPER, coach.getCoachRole());
        Assert.assertEquals(QualificationCoach.UEFA_A, coach.getQualificationCoach());
        Assert.assertNull(coach.getTeam());
        Assert.assertEquals("Pass1234",coach.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(emailAddress));
    }

    @Test
    public void testUpdatePlayerDetails_notExists() throws Exception {
        String emailAddress = "email@gmail.com";
        Date birthDate = new Date();
        try {
            coachDb.updateCoachDetails(emailAddress, "firstChange", "last",CoachRole.GOALKEEPER, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("Coach not found", e.getMessage());

        }
    }
}
