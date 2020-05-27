package Data;

import Controller.BaseEmbeddedSQL;
import Controller.TeamOwnerController;
import Model.Enums.PlayerRole;
import Model.Enums.RoleType;
import Model.Enums.TeamStatus;
import Model.Role;
import Model.UsersTypes.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import Controller.BaseEmbeddedSQL;
import Controller.TeamOwnerController;
import Model.Enums.PlayerRole;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeamManagerDbInServerTest extends BaseEmbeddedSQL {
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
        public void testInsertTeamManager_null() throws Exception
        {
            TeamManager teamManager = null;
            try
            {
                teamManagerDb.insertTeamManager(teamManager);
            }
            catch (Exception e)
            {
                Assert.assertTrue(e instanceof NullPointerException);
                Assert.assertEquals("bad input", e.getMessage());
            }
        }


        @Test
        public void testInsertTeamManager_legal() throws Exception
        {
            String emailAddress = "email@gmail.com";
            String ownedBy = "ownedBy@gmail.com";
            TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
            subscriberDb.insertSubscriber(teamManager);
            teamManagerDb.insertTeamManager(teamManager);
            teamManager = teamManagerDb.getTeamManager(emailAddress);
            Assert.assertEquals(emailAddress, teamManager.getEmailAddress());
            Assert.assertEquals(222222222, teamManager.getId().intValue());
            Assert.assertEquals("first", teamManager.getFirstName());
            Assert.assertEquals("last", teamManager.getLastName());
            Assert.assertNull(teamManager.getTeam());
            Assert.assertEquals("Pass1234",teamManager.getPassword());
            Assert.assertNotNull(subscriberDb.getSubscriber(emailAddress));
        }

        @Test
        public void testInsertTeamManager_TeamManagerExists() throws Exception
        {

            String emailAddress = "email@gmail.com";
            String ownedBy = "ownedBy@gmail.com";
            TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
            try
            {
                subscriberDb.insertSubscriber(teamManager);
                teamManagerDb.insertTeamManager(teamManager);
                teamManagerDb.insertTeamManager(teamManager);
            }
            catch (Exception e)
            {
                Assert.assertEquals("Duplicate entry \'" + emailAddress+ "\' for key \'PRIMARY\'", e.getMessage());
            }
        }
        @Test
        public void testGetTeamManager_null() throws Exception {
            try
            {
                teamManagerDb.getTeamManager(null);
            }
            catch (Exception e)
            {
                Assert.assertTrue(e instanceof NullPointerException);
                Assert.assertEquals("bad input", e.getMessage());
            }
        }

        @Test
        public void testGetTeamManager_legal() throws Exception{
            String emailAddress = "email@gmail.com";
            String ownedBy = "ownedBy@gmail.com";

            TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
            subscriberDb.insertSubscriber(teamManager);
            teamManagerDb.insertTeamManager(teamManager);
            teamManager = teamManagerDb.getTeamManager(emailAddress);
            Assert.assertEquals(emailAddress, teamManager.getEmailAddress());
            Assert.assertEquals(222222222, teamManager.getId().intValue());
            Assert.assertEquals("first", teamManager.getFirstName());
            Assert.assertEquals("last", teamManager.getLastName());
            Assert.assertNull(teamManager.getTeam());
            Assert.assertNotNull(teamManager.getPassword());
            Assert.assertNotNull(subscriberDb.getSubscriber(emailAddress));
        }


        @Test
        public void testGetTeamManager_notExists() throws Exception{
            String emailAddress = "email@gmail.com";
            String ownedBy = "ownedBy@gmail.com";
            TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
            try
            {
                subscriberDb.insertSubscriber(teamManager);
                teamManagerDb.getTeamManager(emailAddress);
            }
            catch (Exception e)
            {
                Assert.assertEquals("TeamManager not found", e.getMessage());
            }
        }

    @Test
    public void TestUpdateTeamManagerDetails_legal() throws Exception {
        String emailAddress = "email@gmail.com";
        TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",null);
        subscriberDb.insertSubscriber(teamManager);
        teamManagerDb.insertTeamManager(teamManager);
        teamManager = teamManagerDb.getTeamManager(emailAddress);

        Assert.assertEquals("first",teamManager.getFirstName());
        teamManagerDb.updateTeamManagerDetails(emailAddress,"firstChange","last",new ArrayList<>());
        teamManager = teamManagerDb.getTeamManager(emailAddress);
        Assert.assertEquals("firstChange",teamManager.getFirstName());
        Assert.assertEquals(222222222, teamManager.getId().intValue());
        Assert.assertEquals("last", teamManager.getLastName());
    }

    @Test
    public void testUpdateTeamManagerDetails_notExists() throws Exception {
        String emailAddress = "email@gmail.com";

        try {
            teamManagerDb.updateTeamManagerDetails(emailAddress,"firstChange","last",new ArrayList<>());
        }catch (Exception e){
            Assert.assertEquals("TeamManager not found", e.getMessage());

        }
    }

    @Test
    public void testGetAllTeamManagersOwnedBy() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0, TeamStatus.ACTIVE);


        String teamOwnerMail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(teamOwnerMail,teamName, RoleType.TEAM_OWNER);

        String managerToAdd1 = "teamOwnerToAdd1@gmail.com";
        teamOwnerController.addPlayer(teamName,teamOwnerMail,managerToAdd1,  444444444, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(500);
        teamOwnerController.subscriptionTeamManager(teamName, teamOwnerMail, managerToAdd1,new ArrayList<>());

        String managerToAdd2 = "teamOwnerToAdd2@gmail.com";
        teamOwnerController.addPlayer(teamName,teamOwnerMail,managerToAdd2,  333333333, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(500);
        teamOwnerController.subscriptionTeamManager(teamName, teamOwnerMail, managerToAdd2,new ArrayList<>());


        List<String> teamOwnerAllTeamOwnersOwnedBy = teamManagerDb.getAllTeamManagersOwnedBy(teamOwnerMail);
        Assert.assertTrue(teamOwnerAllTeamOwnersOwnedBy.contains(managerToAdd1));
        Assert.assertTrue(teamOwnerAllTeamOwnersOwnedBy.contains(managerToAdd2));
        Assert.assertEquals(2, teamOwnerAllTeamOwnersOwnedBy.size());

    }

    @Test
    public void testsSubscriptionTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            teamManagerDb.subscriptionTeamManager("Exists", "owner@gmail.com",null, new ArrayList<>());
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testSubscriptionTeamManagerLegalSubscription() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        String managerToAdd = "teamManagerToAdd@gmail.com";
        teamOwnerController.addPlayer(teamName, ownerEmail,managerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        Subscriber subscriber = subscriberDb.getSubscriber(managerToAdd);

        teamManagerDb.subscriptionTeamManager(teamName, ownerEmail, subscriber, new ArrayList<>());
        Assert.assertEquals(ownerEmail, teamManagerDb.getTeamManager(managerToAdd).getOwnedByEmail());
    }


    @Test
    public void testRemoveSubscriptionTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            TeamOwner teamOwner = new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            teamManagerDb.removeSubscriptionTeamManager(null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testRemoveSubscriptionTeamManagerLegalRemove() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerMail = "owner@gmail.com";
        String managerToRemove = "managerToRemove@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(teamOwnerMail,teamName, RoleType.TEAM_OWNER);

        teamOwnerController.addPlayer(teamName,teamOwnerMail,managerToRemove,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(2000);
        teamOwnerController.subscriptionTeamManager(teamName, teamOwnerMail, managerToRemove,new ArrayList<>());

        TeamManager teamManager = teamManagerDb.getTeamManager(managerToRemove);
       try {
           //delete managerToRemove should to remove ownerToAddUnder//
           teamManagerDb.removeSubscriptionTeamManager(managerToRemove);
       }catch (Exception e){
           Assert.assertEquals("TeamManager not Found", e.getMessage());

       }


    }

}




