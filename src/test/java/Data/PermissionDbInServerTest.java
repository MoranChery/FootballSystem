package Data;

import Controller.BaseEmbeddedSQL;
import Model.Enums.PermissionType;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.Permission;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionDbInServerTest extends BaseEmbeddedSQL {

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
    public void testInsertPermission_null() throws Exception
    {
        try
        {
            permissionDb.insertPermission(null,null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testInsertPermissionLegal() throws Exception
    {
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";

        TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
        subscriberDb.insertSubscriber(teamManager);
        teamManagerDb.insertTeamManager(teamManager);

        permissionDb.insertPermission(emailAddress,PermissionType.ADD_COURT);

        teamManager = teamManagerDb.getTeamManager(emailAddress);

        Assert.assertEquals(emailAddress, teamManager.getEmailAddress());
        Assert.assertEquals(222222222, teamManager.getId().intValue());
        Assert.assertEquals("first", teamManager.getFirstName());
        Assert.assertEquals("last", teamManager.getLastName());
        Assert.assertEquals(1,teamManager.getPermissionTypes().size());
        Assert.assertTrue(teamManager.getPermissionTypes().contains(PermissionType.ADD_COURT));

    }


    @Test
    public void testGetPermission_null() throws Exception {
        try
        {
            permissionDb.getPermissions(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testGetPermission_legal() throws Exception{
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";

        TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
        subscriberDb.insertSubscriber(teamManager);
        teamManagerDb.insertTeamManager(teamManager);

        permissionDb.insertPermission(emailAddress,PermissionType.ADD_COURT);

        teamManager = teamManagerDb.getTeamManager(emailAddress);

        Assert.assertEquals(emailAddress, teamManager.getEmailAddress());
        Assert.assertEquals(222222222, teamManager.getId().intValue());
        Assert.assertEquals("first", teamManager.getFirstName());
        Assert.assertEquals("last", teamManager.getLastName());
        Assert.assertEquals(1,teamManager.getPermissionTypes().size());
        Assert.assertTrue(teamManager.getPermissionTypes().contains(PermissionType.ADD_COURT));
        List<PermissionType> permissions = permissionDb.getPermissions(emailAddress);
        Assert.assertEquals(1,permissions.size());
        Assert.assertTrue(permissions.contains(PermissionType.ADD_COURT));


    }


    @Test
    public void testGetPermission_notExists() throws Exception{
        String emailAddress = "email@gmail.com";
        String ownedBy = "ownedBy@gmail.com";

        TeamManager teamManager = new TeamManager(emailAddress, "Pass1234",222222222, "first", "last",ownedBy);
        subscriberDb.insertSubscriber(teamManager);
        teamManagerDb.insertTeamManager(teamManager);

        try
        {
            permissionDb.getPermissions(emailAddress);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Permission not found", e.getMessage());
        }
    }

}
