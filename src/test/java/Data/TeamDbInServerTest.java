package Data;

import Controller.BaseEmbeddedSQL;
import Controller.TeamOwnerController;
import Model.*;
import Model.Enums.*;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TeamDbInServerTest extends BaseEmbeddedSQL {
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
    public void testInsertTeam_null() throws Exception
    {
        try
        {
            teamDb.insertTeam(null,1000.0, TeamStatus.ACTIVE);
        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testInsertTeam_legal() throws Exception
    {
        String teamName = "teamName";
        teamDb.insertTeam(teamName,1000.0,TeamStatus.ACTIVE);
        Team team = teamDb.getTeam(teamName);
        Assert.assertEquals(teamName, team.getTeamName());
        Assert.assertEquals(1000.0, team.getBudget(),0);
        Assert.assertEquals(TeamStatus.ACTIVE, team.getTeamStatus());
    }

    @Test
    public void testInsertTeam_TeamExists() throws Exception
    {
        String teamName = "teamName";

        try
        {
            teamDb.insertTeam(teamName,1000.0,TeamStatus.ACTIVE);
            teamDb.insertTeam(teamName,1000.0,TeamStatus.ACTIVE);

        }
        catch (Exception e)
        {
            Assert.assertEquals("Team already exist in the system", e.getMessage());
        }
    }


    @Test
    public void testAddPlayerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);

            String playerMail = "email@gmail.com";
//            Player player = new Player(playerMail, "Pass1234",222222222, null, "last", new Date(), PlayerRole.GOALKEEPER);

            teamDb.addPlayer(teamName,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddPlayer_legal() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        Date birthDate = new Date();
        String playerMail = "email@gmail.com";
        Player player = new Player(playerMail, "Pass1234",222222222, "first", "last", new Date(), PlayerRole.GOALKEEPER);
        subscriberDb.insertSubscriber(player);
        playerDb.insertPlayer(player);
        teamDb.addPlayer(teamName,player);

        Team team = teamDb.getTeam(teamName);
        Map<String, Player> players = team.getPlayers();

        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey(playerMail));
        player = players.get(playerMail);
        Assert.assertEquals(222222222, player.getId().intValue());
        Assert.assertEquals(playerMail, player.getEmailAddress());
        Assert.assertEquals("first", player.getFirstName());
        Assert.assertEquals("last", player.getLastName());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String playerToAddDate = df.format(birthDate);
        String playerInDbDate = df.format(player.getBirthDate());

        Assert.assertEquals(playerToAddDate, playerInDbDate);
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
        Assert.assertEquals(teamName, player.getTeam());
        Assert.assertNotNull(player.getPassword());
    }


    @Test
    public void testAddTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamDb.addTeamManager(teamName,null,new ArrayList<>(),teamOwner.getOwnedByEmailAddress());
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName, 0.0, TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "Pass1234", 222222222, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail, teamName, RoleType.TEAM_OWNER);

        String teamManagerToAdd = "email@gmail.com";
        TeamManager teamManager = new TeamManager(teamManagerToAdd, "Pass1234", 111111111, "firstTeamManager", "lastTeamManager", ownerEmail);
        subscriberDb.insertSubscriber(teamManager);
        teamManagerDb.insertTeamManager(teamManager);
        teamDb.addTeamManager(teamName, teamManager, new ArrayList<>(), ownerEmail);
        Team team = teamDb.getTeam(teamName);
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey(teamManagerToAdd));
        teamManager = teamManagers.get(teamManagerToAdd);
        Assert.assertEquals(111111111, teamManager.getId().intValue());
        Assert.assertEquals(teamManagerToAdd, teamManager.getEmailAddress());
        Assert.assertEquals("firstTeamManager", teamManager.getFirstName());
        Assert.assertEquals("lastTeamManager", teamManager.getLastName());
        Assert.assertEquals(ownerEmail, teamManager.getOwnedByEmail());
        Assert.assertEquals(teamName, teamManager.getTeam());
        Assert.assertNotNull(teamManager.getPassword());
    }

    @Test
    public void testAddCoach() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 222222222, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        Coach currCoach = new Coach("email@gmail.com", "Pass1234",111111111, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        subscriberDb.insertSubscriber(currCoach);
        coachDb.insertCoach(currCoach);

        teamDb.addCoach(teamName, currCoach);
        Team team = teamDb.getTeam(teamName);Map<String, Coach> coaches = team.getCoaches();
        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey("email@gmail.com"));
        Coach coach = coaches.get("email@gmail.com");
        Assert.assertEquals("email@gmail.com", coach.getEmailAddress());
        Assert.assertEquals(111111111, coach.getId().intValue());
        Assert.assertEquals("firstCoach", coach.getFirstName());
        Assert.assertEquals("lastCoach", coach.getLastName());
        Assert.assertEquals(CoachRole.MAJOR, coach.getCoachRole());
        Assert.assertEquals(QualificationCoach.UEFA_A, coach.getQualificationCoach());
        Assert.assertEquals(teamName, coach.getTeam());
    }

    @Test
    public void testAddCourtInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamDb.addCourt(teamName, null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddCourt() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        Court court = new Court("courtName", "courtCity");
        courtDb.insertCourt(court);
        teamDb.addCourt(teamName,court);
        Team team = teamDb.getTeam(teamName);
         court = team.getCourt();
        Assert.assertEquals("courtName", court.getCourtName());
        Assert.assertEquals("courtCity", court.getCourtCity());
    }

    @Test
    public void testRemovePlayerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

            TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);

            teamDb.removePlayer(teamName,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        String playerMail = "email@gmail.com";
        Player player = new Player(playerMail, "Pass1234",222222222, "first", "last", new Date(), PlayerRole.GOALKEEPER);
        subscriberDb.insertSubscriber(player);
        playerDb.insertPlayer(player);
        teamDb.addPlayer(teamName, player);

        Team team = teamDb.getTeam(teamName);
        Map<String, Player> players = team.getPlayers();
        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey("email@gmail.com"));

        teamOwnerController.removePlayer(teamName, ownerEmail,"email@gmail.com");
        team = teamDb.getTeam(teamName);
        players = team.getPlayers();
        Assert.assertEquals(0, players.size());//players in team
        Assert.assertFalse(players.containsKey("email@gmail.com"));
    }


    @Test
    public void testRemoveTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

            TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);

            teamDb.removeTeamManager(teamName, null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testRemoveTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer",new ArrayList<>(),ownerEmail);

        Team team = teamDb.getTeam(teamName);

        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey("email@gmail.com"));

        teamDb.removeTeamManager(teamName,"email@gmail.com");
        team = teamDb.getTeam(teamName);
        teamManagers = team.getTeamManagers();
        Assert.assertEquals(0, teamManagers.size());
        Assert.assertFalse(team.getTeamManagers().containsKey("email@gmail.com"));
    }

    @Test
    public void testAddCoachInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamDb.addCoach(teamName,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void testRemoveCoachInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

            teamDb.removeCoach(teamName,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoach() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        String coachToRemove = "email@gmail.com";
        Coach coach = new Coach(coachToRemove, "1234", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        subscriberDb.insertSubscriber(coach);
        coachDb.insertCoach(coach);
        pageDb.insertPage(coach.getEmailAddress(), PageType.COACH);
        teamOwnerController.addCoach(teamName,ownerEmail, coachToRemove, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Map<String, Coach> coaches = teamDb.getTeam(teamName).getCoaches();

        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey(coachToRemove));

        teamDb.removeCoach(teamName,coachToRemove);

        coaches = teamDb.getTeam(teamName).getCoaches();
        Assert.assertEquals(0, coaches.size());
        Assert.assertFalse(coaches.containsKey(coachToRemove));
    }

    @Test
    public void testRemoveCourtInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.insertRole("owner@gmail.com", null,RoleType.TEAM_OWNER);

            teamDb.removeCourtFromTeam(teamName,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }



    @Test
    public void testRemoveCourt() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

//        courtDb.insertCourt(new Court("courtName", "courtCity"));

        teamOwnerController.addCourt(teamName, ownerEmail,"courtName", "courtCity");

        Team team = teamDb.getTeam(teamName);
        Assert.assertEquals("courtName", team.getCourt().getCourtName());
        Court court = team.getCourt();
        List<String> teams = courtDb.getTeams("courtName");
        Assert.assertEquals(1, teams.size());
        Assert.assertTrue(teams.contains(teamName));

        teamDb.removeCourtFromTeam(teamName,"courtName");
        team = teamDb.getTeam(teamName);
        teams = courtDb.getTeams("courtName");
        Assert.assertEquals(0, teams.size());
        Assert.assertFalse(teams.contains(court.getCourtName()));
        Assert.assertNull(teamDb.getTeam(teamName).getCourt());
    }

    @Test
    public void testAddFinancialActivityInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);TeamOwner teamOwner = new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);

            teamDb.addFinancialActivity(teamName, null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testFinancialActivity() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,800.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        FinancialActivity financialActivity = new FinancialActivity("id",1000.0,"Description", FinancialActivityType.INCOME,teamName);
        teamDb.addFinancialActivity(teamName, financialActivity);
        Team team = teamDb.getTeam(teamName);
        Assert.assertEquals(1800.0, team.getBudget(), 0);
    }

    @Test
    public void testChangeStatusInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            TeamOwner teamOwner = new TeamOwner("owner@gmail.com" ,"1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            subscriberDb.insertSubscriber(teamOwner);
            teamOwnerDb.insertTeamOwner(teamOwner);
            roleDb.createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);

            teamDb.changeStatus(teamName,null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testChangeStatusFromActiveToInactive() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);


        teamDb.changeStatus(teamName,TeamStatus.INACTIVE);
        Team team = teamDb.getTeam(teamName);
        Assert.assertEquals(TeamStatus.INACTIVE, team.getTeamStatus());
    }

    @Test
    public void testCloseTeamForever() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        Team team = teamDb.getTeam(teamName);
        Assert.assertEquals(TeamStatus.ACTIVE, team.getTeamStatus());
        Assert.assertEquals(null, team.getTeamClose());

        teamDb.closeTeamForever(teamName);
        team = teamDb.getTeam(teamName);
        Assert.assertEquals(TeamStatus.INACTIVE, team.getTeamStatus());
        Assert.assertEquals("close", team.getTeamClose());
    }

}
