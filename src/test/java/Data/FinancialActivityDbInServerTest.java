package Data;

import Controller.BaseEmbeddedSQL;
import Model.Enums.FinancialActivityType;
import Model.Enums.RoleType;
import Model.Enums.TeamStatus;
import Model.FinancialActivity;
import Model.Team;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FinancialActivityDbInServerTest extends BaseEmbeddedSQL {


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
    public void testInsertFinancialActivity_null() throws Exception
    {
        try
        {
            financialActivityDb.insertFinancialActivity(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testInsertFinancialActivity_legal() throws Exception
    {
        String teamName = "TeamName";
        teamDb.insertTeam(teamName,1000.0, TeamStatus.ACTIVE);
        String emailAddress = "email@gmail.com";
        TeamOwner teamOwner = new TeamOwner(emailAddress, "Pass1234",222222222, "first", "last",null);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(emailAddress,teamName, RoleType.TEAM_OWNER);

        FinancialActivity financialActivity = new FinancialActivity("id",1000.0,"description", FinancialActivityType.INCOME,teamName);
        financialActivityDb.insertFinancialActivity(financialActivity);
        Map<String, FinancialActivity> financialActivities = teamDb.getTeam(teamName).getFinancialActivities();
        Assert.assertTrue(financialActivities.containsKey("id"));
    }


}
