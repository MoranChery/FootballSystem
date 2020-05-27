package Data;

import Controller.BaseEmbeddedSQL;
import Controller.TeamOwnerController;
import Model.Enums.PlayerRole;
import Model.UsersTypes.Player;
import Model.UsersTypes.Player;
import Model.UsersTypes.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerDbInServerTest extends BaseEmbeddedSQL {

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
    public void testInsertPlayer_null() throws Exception
    {
        Player player = null;

        try
        {
            playerDb.insertPlayer(player);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testInsertPlayer_legal() throws Exception
    {
        String emailAddress = "email@gmail.com";
        Date birthDate = new Date();
        Player player = new Player(emailAddress, "Pass1234",222222222, "first", "last", birthDate, PlayerRole.GOALKEEPER);
        subscriberDb.insertSubscriber(player);
        playerDb.insertPlayer(player);
        player = playerDb.getPlayer(emailAddress);
        Assert.assertEquals(emailAddress, player.getEmailAddress());
        Assert.assertEquals(222222222, player.getId().intValue());
        Assert.assertEquals("first", player.getFirstName());
        Assert.assertEquals("last", player.getLastName());
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String playerToAddDate = df.format(birthDate);
        String playerInDbDate = df.format(player.getBirthDate());

        Assert.assertEquals(playerToAddDate, playerInDbDate);
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
        Assert.assertNull(player.getTeam());
        Assert.assertNotNull(player.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(emailAddress));
    }

    @Test
    public void testInsertPlayer_PlayerExists() throws Exception
    {

        String emailAddress = "email@gmail.com";
        Player player = new Player(emailAddress, "Pass1234",222222222, "first", "last", new Date(), PlayerRole.GOALKEEPER);
        try
        {
            subscriberDb.insertSubscriber(player);
            playerDb.insertPlayer(player);
            playerDb.insertPlayer(player);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Duplicate entry \'" + emailAddress+ "\' for key \'PRIMARY\'", e.getMessage());
        }
    }
    @Test
    public void testGetPlayer_null() throws Exception {
        try
        {
            playerDb.getPlayer(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testGetPlayer_legal() throws Exception{
        String emailAddress = "email@gmail.com";
        Date birthDate = new Date();
        Player player = new Player(emailAddress, "Pass1234",222222222, "first", "last", birthDate, PlayerRole.GOALKEEPER);
        subscriberDb.insertSubscriber(player);
        playerDb.insertPlayer(player);
        player = playerDb.getPlayer(emailAddress);
        Assert.assertEquals(emailAddress, player.getEmailAddress());
        Assert.assertEquals(222222222, player.getId().intValue());
        Assert.assertEquals("first", player.getFirstName());
        Assert.assertEquals("last", player.getLastName());
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String playerToAddDate = df.format(birthDate);
        String playerInDbDate = df.format(player.getBirthDate());

        Assert.assertEquals(playerToAddDate, playerInDbDate);
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
        Assert.assertNull(player.getTeam());
        Assert.assertNotNull(player.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(emailAddress));
    }


    @Test
    public void testGetPlayer_notExists() throws Exception{
        String emailAddress = "email@gmail.com";
        Player player = new Player(emailAddress, "Pass1234",222222222, "first", "last", new Date(), PlayerRole.GOALKEEPER);
        try
        {
            subscriberDb.insertSubscriber(player);
            playerDb.getPlayer(emailAddress);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Player not found", e.getMessage());
        }
    }

    @Test
    public void TestUpdatePlayerDetails_legal() throws Exception {
        String emailAddress = "email@gmail.com";
        Date birthDate = new Date();
        Player player = new Player(emailAddress, "Pass1234",222222222, "first", "last", birthDate, PlayerRole.GOALKEEPER);
        subscriberDb.insertSubscriber(player);
        playerDb.insertPlayer(player);
        player = playerDb.getPlayer(emailAddress);
        Assert.assertEquals("first",player.getFirstName());
        playerDb.updatePlayerDetails(emailAddress,"firstChange","last",birthDate, PlayerRole.GOALKEEPER);
        player = playerDb.getPlayer(emailAddress);
        Assert.assertEquals("firstChange",player.getFirstName());
        Assert.assertEquals(222222222, player.getId().intValue());
        Assert.assertEquals("last", player.getLastName());
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String playerToAddDate = df.format(birthDate);
        String playerInDbDate = df.format(player.getBirthDate());
        Assert.assertEquals(playerToAddDate, playerInDbDate);
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
    }

    @Test
    public void testUpdatePlayerDetails_notExists() throws Exception {
        String emailAddress = "email@gmail.com";
        Date birthDate = new Date();
        try {
            playerDb.updatePlayerDetails(emailAddress, "firstChange", "last", birthDate, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("Player not found", e.getMessage());

        }
    }
}
