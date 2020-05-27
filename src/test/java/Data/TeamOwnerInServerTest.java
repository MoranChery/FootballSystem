package Data;

import Controller.BaseEmbeddedSQL;
import Controller.TeamOwnerController;
import Model.Enums.PlayerRole;
import Model.Enums.RoleType;
import Model.Enums.TeamStatus;
import Model.Role;
import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TeamOwnerInServerTest extends BaseEmbeddedSQL {
    private TeamOwnerController teamOwnerController = new TeamOwnerController();
    private TeamDb teamDb = TeamDbInServer.getInstance();
    private PlayerDb playerDb = PlayerDbInServer.getInstance();
    private TeamManagerDb teamManagerDb = TeamManagerDbInServer.getInstance();
    private TeamOwnerDb teamOwnerDb =  TeamOwnerDbInServer.getInstance();
    private CoachDb coachDb =  CoachDbInServer.getInstance();
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
    public void testInsertTeamOwner_null() throws Exception
    {
        TeamOwner teamOwner = null;
        try
        {
            teamOwnerDb.insertTeamOwner(teamOwner);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testInsertOwner_legal() throws Exception
    {
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";
        TeamOwner teamOwner = new TeamOwner(emailAddress, "Pass1234",222222222, "first", "last",null);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        teamOwner = teamOwnerDb.getTeamOwner(emailAddress);
        Assert.assertEquals(emailAddress, teamOwner.getEmailAddress());
        Assert.assertEquals(222222222, teamOwner.getId().intValue());
        Assert.assertEquals("first", teamOwner.getFirstName());
        Assert.assertEquals("last", teamOwner.getLastName());
        Assert.assertNull(teamOwner.getTeam());
        Assert.assertEquals("Pass1234",teamOwner.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(emailAddress));
    }

    @Test
    public void testInsertTeamOwner_TeamOwnerExists() throws Exception
    {

        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";
        TeamOwner teamOwner = new TeamOwner(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
        try
        {
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Duplicate entry \'" + emailAddress+ "\' for key \'PRIMARY\'", e.getMessage());
        }
    }
    @Test
    public void testGetTeamOwner_null() throws Exception {
        try
        {
            teamOwnerDb.getTeamOwner(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testGetTeamOwner_legal() throws Exception{
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";

        TeamOwner teamOwner = new TeamOwner(emailAddress, "Pass1234",222222222, "first", "last",null);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        teamOwner = teamOwnerDb.getTeamOwner(emailAddress);
        Assert.assertEquals(emailAddress, teamOwner.getEmailAddress());
        Assert.assertEquals(222222222, teamOwner.getId().intValue());
        Assert.assertEquals("first", teamOwner.getFirstName());
        Assert.assertEquals("last", teamOwner.getLastName());
        Assert.assertNull(teamOwner.getTeam());
        Assert.assertNotNull(teamOwner.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(emailAddress));
    }


    @Test
    public void testGetTeamOwner_notExists() throws Exception{
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";
        TeamOwner teamOwner = new TeamOwner(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
        try
        {
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.getTeamOwner(emailAddress);
        }
        catch (Exception e)
        {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testUpdateTeamOwnerTeamInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0, TeamStatus.ACTIVE);
            TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);

            teamOwnerDb.updateTeamOwnerTeam(teamName,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testUpdateTeamOwnerTeam() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName, 0.0, TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "Pass1234", 222222222, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail, null, RoleType.TEAM_OWNER);

        teamOwnerDb.updateTeamOwnerTeam(teamName,ownerEmail);
        Team team = teamDb.getTeam(teamName);
        Map<String, TeamOwner> teamOwners = team.getTeamOwners();
        Assert.assertEquals(1, teamOwners.size());
        Assert.assertTrue(teamOwners.containsKey(ownerEmail));
        teamOwner = teamOwners.get(ownerEmail);
        Assert.assertEquals(222222222, teamOwner.getId().intValue());
        Assert.assertEquals(ownerEmail, teamOwner.getEmailAddress());
        Assert.assertEquals("firstTeamOwnerName", teamOwner.getFirstName());
        Assert.assertEquals("lastTeamOwnerName", teamOwner.getLastName());
        Assert.assertEquals(null, teamOwner.getOwnedByEmailAddress());
        Assert.assertEquals(teamName, teamOwner.getTeam());
    }

    @Test
    public void testsSubscriptionTeamOwnerInvalidInputs() {
        try {
            teamOwnerDb.subscriptionTeamOwner("Team", null,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerLegalSubscription() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);

        String ownerToAdd = "teamOwnerToAdd@gmail.com";
        teamOwnerController.addPlayer(teamName,ownerEmail,ownerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);

        Subscriber subscriber = subscriberDb.getSubscriber(ownerToAdd);
        teamOwnerDb.subscriptionTeamOwner(teamName,ownerEmail,subscriber);
        subscriber = subscriberDb.getSubscriber(ownerToAdd);

        TeamOwner teamOwnerAdded = teamOwnerDb.getTeamOwner(ownerToAdd);
        Assert.assertEquals(ownerEmail, teamOwnerAdded.getOwnedByEmailAddress());
        List<String> allTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(ownerEmail);
        Assert.assertEquals(ownerToAdd, allTeamOwnersOwnedBy.get(0));
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            TeamOwner teamOwner = new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            teamOwnerDb.removeSubscriptionTeamOwner(null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwner() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String teamOwnerMail = "owner@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(teamOwnerMail,teamName, RoleType.TEAM_OWNER);

        String ownerToAdd = "teamOwnerToAdd@gmail.com";
        teamOwnerController.addPlayer(teamName,teamOwnerMail,ownerToAdd,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(500);
        Subscriber subscriber = subscriberDb.getSubscriber(ownerToAdd);
        teamOwnerDb.subscriptionTeamOwner(teamName, teamOwnerMail, subscriber);

        List<String> teamOwnerAllTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(teamOwnerMail);
        Assert.assertTrue(teamOwnerAllTeamOwnersOwnedBy.contains(ownerToAdd));
        Assert.assertEquals(1, teamOwnerAllTeamOwnersOwnedBy.size());

        teamOwnerDb.removeSubscriptionTeamOwner(ownerToAdd);
        teamOwnerAllTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(teamOwnerMail);
        Assert.assertFalse(teamOwnerAllTeamOwnersOwnedBy.contains(ownerToAdd));
        Assert.assertEquals(0, teamOwnerAllTeamOwnersOwnedBy.size());
    }

    @Test
    public void testGetAllTeamOwnersOwnedBy() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);


        String teamOwnerMail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(teamOwnerMail,teamName, RoleType.TEAM_OWNER);

        String ownerToAdd1 = "teamOwnerToAdd1@gmail.com";
        teamOwnerController.addPlayer(teamName,teamOwnerMail,ownerToAdd1,  444444444, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(500);
        Subscriber subscriber1 = subscriberDb.getSubscriber(ownerToAdd1);
        teamOwnerDb.subscriptionTeamOwner(teamName, teamOwnerMail, subscriber1);

        String ownerToAdd2 = "teamOwnerToAdd2@gmail.com";
        teamOwnerController.addPlayer(teamName,teamOwnerMail,ownerToAdd2,  333333333, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(500);
        Subscriber subscriber2 = subscriberDb.getSubscriber(ownerToAdd2);
        teamOwnerDb.subscriptionTeamOwner(teamName, teamOwnerMail, subscriber2);

        List<String> teamOwnerAllTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(teamOwnerMail);
        Assert.assertTrue(teamOwnerAllTeamOwnersOwnedBy.contains(ownerToAdd1));
        Assert.assertTrue(teamOwnerAllTeamOwnersOwnedBy.contains(ownerToAdd2));
        Assert.assertEquals(2, teamOwnerAllTeamOwnersOwnedBy.size());

    }

}
