package Controller;

import Data.*;
import Model.Court;
import Model.Enums.*;
import Model.FinancialActivity;
import Model.Role;
import Model.Team;
import Model.UsersTypes.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class TeamOwnerControllerTest {
    private TeamOwnerController teamOwnerController = new TeamOwnerController();
    private TeamDb teamDb = TeamDbInMemory.getInstance();
    private PlayerDb playerDb = PlayerDbInMemory.getInstance();
    private TeamManagerDb teamManagerDb = TeamManagerDbInMemory.getInstance();
    private TeamOwnerDb teamOwnerDb =  TeamOwnerDbInMemory.getInstance();
    private CoachDb coachDb =  CoachDbInMemory.getInstance();

    @Before
    public void init() {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(CoachDbInMemory.getInstance());
        dbs.add(CourtDbInMemory.getInstance());
        dbs.add(FinancialActivityDbInMemory.getInstance());
        dbs.add(PlayerDbInMemory.getInstance());
        dbs.add(SubscriberDbInMemory.getInstance());
        dbs.add(TeamDbInMemory.getInstance());
        dbs.add(TeamManagerDbInMemory.getInstance());
        dbs.add(TeamOwnerDbInMemory.getInstance());
        dbs.add(RoleDbInMemory.getInstance());
        dbs.add(PageDbInMemory.getInstance());
        dbs.add(PermissionDbInMemory.getInstance());
        for (Db db : dbs) {
            db.deleteAll();
        }
    }

    @Test
    public void createTeamNull() throws Exception {
        try {
            teamDb.insertTeam(null);
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void createTeamLegal() throws Exception {
        teamDb.insertTeam("Team");
        Team team = TeamDbInMemory.getInstance().getTeam("Team");
        Assert.assertEquals("Team",team.getTeamName());
    }

    @Test
    public void getTeamNull() throws Exception {
        try {
            teamOwnerController.getTeam(null);
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void getTeamLegal() throws Exception {
        teamDb.insertTeam("Team");
        Team team = teamOwnerController.getTeam("Team");
        Assert.assertEquals("Team",team.getTeamName());
    }

    ///////////////////////////////// playerController///////////////////////////////////
    @Test
    public void testCreatePlayerNull(){
        try {
            playerDb.insertPlayer(null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void testCreatePlayerLegal() throws Exception {
            Player player = new Player("email@gmail.com", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
            playerDb.insertPlayer(player);
            Assert.assertEquals(player, PlayerDbInMemory.getInstance().getPlayer("email@gmail.com"));
    }

    ///////////////////////////////// playerController///////////////////////////////////
    @Test
    public void testCreateCoachNull(){
        try {
            coachDb.insertCoach(null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void testCreateCoachLegal() throws Exception {
        Coach coach = new Coach("email@gmail.com", 2, "firstTeamOwnerName", "lastTeamOwnerName", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        coachDb.insertCoach(coach);
        Assert.assertEquals(coach, CoachDbInMemory.getInstance().getCoach("email@gmail.com"));
    }

    ///////////////////////////////// TeamOwnerController///////////////////////////////////
    @Test
    public void testCreateTeamOwnerNull(){
        try {
            teamOwnerDb.insertTeamOwner(null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void testCreateTeamOwnerLegal() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        Assert.assertEquals(teamOwner, TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerEmail));
    }

    ///////////////////////////////// TeamManagerController///////////////////////////////////
    @Test
    public void testTeamManagerCoachNull(){
        try {
            teamManagerDb.insertTeamManager(null);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void testTeamManagerLegal() throws Exception {
        TeamManager teamManager = new TeamManager( "email@gmail.com","1111", 1, "firstTeamManager", "lastTeamManager", "ownerEmail");
        teamManagerDb.insertTeamManager(teamManager);
        Assert.assertEquals(teamManager, TeamManagerDbInMemory.getInstance().getTeamManager("email@gmail.com"));
    }

//////////////////////////////////// addPlayer /////////////////////////////////
    @Test
    public void testAddPlayerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.addPlayer(teamName,null, "email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.addPlayer(teamName, withoutPermissionsOwnerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testAddPlayerOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.ADD_PLAYER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());
        teamOwnerController.addPlayer(teamName, withPermissionsEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
        Assert.assertEquals(1, teamDb.getTeam(teamName).getPlayers().size());
        Assert.assertTrue(teamDb.getTeam(teamName).getPlayers().containsKey("email@gmail.com"));
    }

    @Test
    public void testAddPlayerOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.addPlayer(teamName, withoutPermissionsOwnerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerTeamNotFound() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.addPlayer("notExists", "owner@gmail.com","email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
        try {
        teamOwnerController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        Date birthDate = new Date();

        String playerToAdd = "email@gmail.com";
        teamOwnerController.addPlayer(teamName, ownerEmail,playerToAdd, 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);

    Team team = teamDb.getTeam(teamName);
    Map<String, Player> players = team.getPlayers();

        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey(playerToAdd));
        Player player = players.get(playerToAdd);
        Assert.assertEquals(1, player.getId().intValue());
        Assert.assertEquals(playerToAdd, player.getEmailAddress());
        Assert.assertEquals("firstPlayer", player.getFirstName());
        Assert.assertEquals("lastPlayer", player.getLastName());
        Assert.assertEquals(birthDate, player.getBirthDate());
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
        Assert.assertEquals(team, player.getTeam());
        Assert.assertNotNull(player.getPassword());
        Assert.assertNotNull(SubscriberDbInMemory.getInstance().getSubscriber(playerToAdd));
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();
        Assert.assertEquals(RoleType.PLAYER, roleDbInMemory.getRole(playerToAdd).getRoleType());
    }

    @Test
    public void testAddPlayerExistsPlayerAssociatedWithSameTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();

        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

//        Player player = new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
//        SubscriberDbInMemory.getInstance().createSubscriber(player);
        teamOwnerController.addPlayer(teamName, "owner@gmail.com","email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        try {
            teamOwnerController.addPlayer(teamName, "owner@gmail.com","email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Player associated this team", e.getMessage());
        }
    }
    @Test
    public void testAddPlayerExistsPlayerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);

        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);


        String ownerEmail2 = "owner2@gmail.com";
        TeamOwner teamOwner2 = new TeamOwner(ownerEmail2, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(teamOwner2);
        RoleDbInMemory.getInstance().insertRole(ownerEmail2,"Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner2);

        try {
            teamOwnerController.addPlayer("Other", ownerEmail2,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Player associated with a team", e.getMessage());
        }
    }



    @Test
    public void testAddPlayerExistsPlayerIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);  playerDb.insertPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        try {
            teamOwnerController.addPlayer(teamName,ownerEmail, "email@gmail.com", 1, "firstPlayerOther", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("One or more of the details incorrect", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerExistsPlayerAsDifferentSubscriber() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        Coach coach = new Coach("email@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        SubscriberDbInMemory.getInstance().insertSubscriber(coach);
        try {
            teamOwnerController.addPlayer(teamName,"owner@gmail.com", "email@gmail.com", 1, "first", "last", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The player to added already has other subscriber type", e.getMessage());
        }
    }


    @Test
    public void testAddPlayerExistSubscriberWithTeamOwnerAndSameTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String playerToAdd = "email@gmail.com";
        TeamOwner ownerToAddAsPlayer = new TeamOwner(playerToAdd, "1234",1, "first", "last", teamName);
        SubscriberDbInMemory.getInstance().insertSubscriber(ownerToAddAsPlayer);
        RoleDbInMemory.getInstance().insertRole(playerToAdd,teamName, RoleType.TEAM_OWNER);
        Assert.assertEquals(RoleType.TEAM_OWNER,roleDbInMemory.getRole(playerToAdd).getRoleType());
        Thread.sleep(100);
        teamOwnerController.addPlayer(teamName,ownerEmail, playerToAdd, 1, "first", "last", birthDate, PlayerRole.GOALKEEPER);

    Team team = teamDb.getTeam(teamName);Map<String, Player> players = team.getPlayers();

        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey(playerToAdd));
        Player player = players.get(playerToAdd);
        Assert.assertEquals(1, player.getId().intValue());
        Assert.assertEquals(playerToAdd, player.getEmailAddress());
        Assert.assertEquals("first", player.getFirstName());
        Assert.assertEquals("last", player.getLastName());
        Assert.assertEquals(birthDate, player.getBirthDate());
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
        Assert.assertEquals(team, player.getTeam());
        Assert.assertEquals(2,roleDbInMemory.getRoles(playerToAdd).size());
        Assert.assertEquals(RoleType.PLAYER,roleDbInMemory.getRole(playerToAdd).getRoleType());
    }

    @Test
    public void testAddPlayerExistSubscriberWithTeamOwnerAndDifferentTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String otherTeam = "other";
 teamDb.insertTeam(otherTeam);
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String playerToAdd = "email@gmail.com";
        TeamOwner ownerToAddAsPlayer = new TeamOwner(playerToAdd, "1234",1, "first", "last", otherTeam);
        SubscriberDbInMemory.getInstance().insertSubscriber(ownerToAddAsPlayer);
        RoleDbInMemory.getInstance().insertRole(playerToAdd,otherTeam, RoleType.TEAM_OWNER);
        Assert.assertEquals(RoleType.TEAM_OWNER,roleDbInMemory.getRole(playerToAdd).getRoleType());
        try {
            teamOwnerController.addPlayer(teamName, ownerEmail, playerToAdd, 1, "first", "last", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The player to added already has other team", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerExistSubscriberWithDifferentTypeAndSameTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String playerToAdd = "email@gmail.com";
        Coach ownerToAddAsPlayer = new Coach(playerToAdd, "1234",1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        SubscriberDbInMemory.getInstance().insertSubscriber(ownerToAddAsPlayer);
        RoleDbInMemory.getInstance().insertRole(playerToAdd,teamName, RoleType.COACH);
        Assert.assertEquals(RoleType.COACH,roleDbInMemory.getRole(playerToAdd).getRoleType());

        try {
            teamOwnerController.addPlayer(teamName, ownerEmail, playerToAdd, 1, "first", "last", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The player to added already has other subscriber type", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerExistsPlayer() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        Date birthDate = new Date();
        playerDb.insertPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamOwnerController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
    Team team = teamDb.getTeam(teamName);Map<String, Player> players = team.getPlayers();
        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey("email@gmail.com"));
        Player player = players.get("email@gmail.com");
        Assert.assertEquals("email@gmail.com", player.getEmailAddress());
        Assert.assertEquals(1, player.getId().intValue());
        Assert.assertEquals("firstPlayer", player.getFirstName());
        Assert.assertEquals("lastPlayer", player.getLastName());
        Assert.assertEquals(birthDate, player.getBirthDate());
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
        Assert.assertEquals(teamName, player.getTeam());
    }


    //////////////////////////////////// addTeamManager /////////////////////////////////////////////
    @Test
    public void testAddTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.addTeamManager(null, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),"email2@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void testAddTeamManagerOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.addTeamManager(teamName,"email@gmail.com", 1, "firstPlayer", "lastPlayer",new ArrayList<>(),withoutPermissionsOwnerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testAddTeamManagerOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.ADD_TEAM_MANAGER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());
        teamOwnerController.addTeamManager(teamName,"email@gmail.com", 1, "firstPlayer", "lastPlayer",new ArrayList<>(), withPermissionsEmail);
        Assert.assertEquals(2, teamDb.getTeam(teamName).getTeamManagers().size());
        Assert.assertTrue(teamDb.getTeam(teamName).getTeamManagers().containsKey("email@gmail.com"));
    }

    @Test
    public void testAddTeamManagerOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.addTeamManager(teamName,"email@gmail.com", 1, "firstPlayer", "lastPlayer", new ArrayList<>(),withoutPermissionsOwnerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testAddTeamManagerTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

            teamOwnerController.addTeamManager("notExists", "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),ownerEmail);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);try {
            teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamOwnerNotExists() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerNotExistsTeamManager() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String teamManagerToAdd = "email@gmail.com";
        teamOwnerController.addTeamManager(teamName, teamManagerToAdd, 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),ownerEmail);
    Team team = teamDb.getTeam(teamName);Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey(teamManagerToAdd));
        TeamManager teamManager = teamManagers.get(teamManagerToAdd);
        Assert.assertEquals(1, teamManager.getId().intValue());
        Assert.assertEquals(teamManagerToAdd, teamManager.getEmailAddress());
        Assert.assertEquals("firstTeamManager", teamManager.getFirstName());
        Assert.assertEquals("lastTeamManager", teamManager.getLastName());
        Assert.assertEquals(ownerEmail, teamManager.getOwnedByEmail());
        Assert.assertEquals(team, teamManager.getTeam());
        Assert.assertNotNull(teamManager.getPassword());
        Assert.assertNotNull(SubscriberDbInMemory.getInstance().getSubscriber(teamManagerToAdd));
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();
        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDbInMemory.getRole(teamManagerToAdd).getRoleType());    }


    @Test
    public void testAddTeamManagerExistsTeamManagerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),ownerEmail);

        String other = "Other";
        teamDb.insertTeam(other);
        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", other);
        teamOwnerDb.insertTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().insertRole(otherOwnerEmail,other, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(otherTeamOwner);

        try {
            teamOwnerController.addTeamManager(other, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager",new ArrayList<>(), otherOwnerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team Manager associated with a team", e.getMessage());
        }
    }
    @Test
    public void testAddTeamManagerExistsManagerAssociatedWithSameTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();

        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

//        Player player = new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
//        SubscriberDbInMemory.getInstance().createSubscriber(player);
        teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),ownerEmail);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        try {
            teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamManager associated with a this team", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistsTeamManagerIdDifferentDetails() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamManagerDb.insertTeamManager(new TeamManager("email@gmail.com", 1, "firstTeamManager", "lastTeamManager", null));
        try {
            teamOwnerController.addTeamManager(teamName, "email@gmail.com", 2, "firstTeamManagerOther", "lastTeamManager", new ArrayList<>(),ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("One or more of the details incorrect", e.getMessage());
        }
    }


    @Test
    public void testAddTeamManagerExistsTeamManagerIdAssociatedWithOwnedBy() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().createRoleInSystem(otherOwnerEmail, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(otherTeamOwner);

        teamManagerDb.insertTeamManager(new TeamManager("email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email3@gmail.com"));
        RoleDbInMemory.getInstance().createRoleInSystem("email@gmail.com", RoleType.TEAM_MANAGER);
        TeamManagerDbInMemory.getInstance().getTeamManager("email@gmail.com").setOwnedByEmail(otherOwnerEmail);
//        teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", otherOwnerEmail);

        try {
            teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team Manager owned by another teamOwner", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistSubscriberWithTeamOwnerAndSameTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String managerToAdd = "email@gmail.com";
        TeamOwner ownerToAddAsManager = new TeamOwner(managerToAdd, "1234",1, "first", "last", teamName);
        SubscriberDbInMemory.getInstance().insertSubscriber(ownerToAddAsManager);
        RoleDbInMemory.getInstance().insertRole(managerToAdd,teamName, RoleType.TEAM_OWNER);
        Assert.assertEquals(RoleType.TEAM_OWNER,roleDbInMemory.getRole(managerToAdd).getRoleType());
        Thread.sleep(100);
        teamOwnerController.addTeamManager(teamName, managerToAdd, 1, "first", "last", new ArrayList<>(),ownerEmail);

    Team team = teamDb.getTeam(teamName);Map<String, TeamManager> teamManagers = team.getTeamManagers();

        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey(managerToAdd));
        TeamManager manager = teamManagers.get(managerToAdd);
        Assert.assertEquals(1, manager.getId().intValue());
        Assert.assertEquals(managerToAdd, manager.getEmailAddress());
        Assert.assertEquals("first", manager.getFirstName());
        Assert.assertEquals("last", manager.getLastName());
        Assert.assertEquals(team, manager.getTeam());
        Assert.assertEquals(2,roleDbInMemory.getRoles(managerToAdd).size());
        Assert.assertEquals(RoleType.TEAM_MANAGER,roleDbInMemory.getRole(managerToAdd).getRoleType());
    }

    @Test
    public void testAddTeamManagerExistSubscriberWithTeamOwnerAndDifferentTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String otherTeam = "other";
 teamDb.insertTeam(otherTeam);
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String managerToAdd = "email@gmail.com";
        TeamOwner ownerToAddAsPlayer = new TeamOwner(managerToAdd, "1234",1, "first", "last", otherTeam);
        SubscriberDbInMemory.getInstance().insertSubscriber(ownerToAddAsPlayer);
        RoleDbInMemory.getInstance().insertRole(managerToAdd,otherTeam, RoleType.TEAM_OWNER);
        Assert.assertEquals(RoleType.TEAM_OWNER,roleDbInMemory.getRole(managerToAdd).getRoleType());
        try {
            teamOwnerController.addTeamManager(teamName, managerToAdd, 1, "first", "last",new ArrayList<>(),ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The teamManager to added already has other team", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistSubscriberWithDifferentTypeAndSameTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String playerToAdd = "email@gmail.com";
        Coach ownerToAddAsPlayer = new Coach(playerToAdd, "1234",1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        SubscriberDbInMemory.getInstance().insertSubscriber(ownerToAddAsPlayer);
        RoleDbInMemory.getInstance().insertRole(playerToAdd,teamName, RoleType.COACH);
        Assert.assertEquals(RoleType.COACH,roleDbInMemory.getRole(playerToAdd).getRoleType());

        try {
            teamOwnerController.addTeamManager(teamName, playerToAdd, 1, "first", "last",new ArrayList<>(),ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The teamManager to added already has other subscriber type", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistsTeamManager() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamManagerDb.insertTeamManager(new TeamManager("email@gmail.com", 1, "firstTeamManager", "lastTeamManager", null));

        teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager",new ArrayList<>(), ownerEmail);
    Team team = teamDb.getTeam(teamName);Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey("email@gmail.com"));
        TeamManager teamManager = teamManagers.get("email@gmail.com");
        Assert.assertEquals("email@gmail.com", teamManager.getEmailAddress());
        Assert.assertEquals(1, teamManager.getId().intValue());
        Assert.assertEquals("firstTeamManager", teamManager.getFirstName());
        Assert.assertEquals("lastTeamManager", teamManager.getLastName());
        Assert.assertEquals(ownerEmail, teamManager.getOwnedByEmail());
        Assert.assertEquals(team, teamManager.getTeam());
    }

    @Test
    public void testAddTeamManagerExistsTeamManagerAsDifferentSubscriber() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        Coach coach = new Coach("email@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        SubscriberDbInMemory.getInstance().insertSubscriber(coach);
        try {
            teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "first", "last", new ArrayList<>(),ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The teamManager to added already has other subscriber type", e.getMessage());
        }
    }

    ////////////////////////////////////addCoach/////////////////////////////////////////////
    @Test
    public void testAddCoachInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
             teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.addCoach(null, "owner@gmail.com","email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddCoachOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);
        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Thread.sleep(100);

        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.addCoach(teamName,withoutPermissionsOwnerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.GOALKEEPER, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testAddCoachOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.ADD_COACH);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());
        teamOwnerController.addCoach(teamName,withPermissionsEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.GOALKEEPER, QualificationCoach.UEFA_A);
        Assert.assertEquals(1, teamDb.getTeam(teamName).getCoaches().size());
        Assert.assertTrue(teamDb.getTeam(teamName).getCoaches().containsKey("email@gmail.com"));
    }


    @Test
    public void testAddCoachOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);

        try {
            teamOwnerController.addCoach(teamName,withoutPermissionsOwnerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testAddCoachTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
            teamOwnerController.addCoach("notExists",ownerEmail, "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddCoachTeamInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);try {
teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddCoachNotExistsCoach() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String coachToAdd = "email@gmail.com";
        teamOwnerController.addCoach(teamName, ownerEmail,coachToAdd, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
    Team team = teamDb.getTeam(teamName);Map<String, Coach> coaches = team.getCoaches();
        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey(coachToAdd));
        Coach coach = coaches.get(coachToAdd);
        Assert.assertEquals(coachToAdd, coach.getEmailAddress());
        Assert.assertEquals(1, coach.getId().intValue());
        Assert.assertEquals("firstCoach", coach.getFirstName());
        Assert.assertEquals("lastCoach", coach.getLastName());
        Assert.assertEquals(CoachRole.MAJOR, coach.getCoachRole());
        Assert.assertEquals(QualificationCoach.UEFA_A, coach.getQualificationCoach());
        Assert.assertEquals(team, coach.getTeam());
        Assert.assertNotNull(coach.getPassword());
        Assert.assertNotNull(SubscriberDbInMemory.getInstance().getSubscriber(coachToAdd));
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();
        Assert.assertEquals(RoleType.COACH, roleDbInMemory.getRole(coachToAdd).getRoleType());
}

    @Test
    public void testAddCoachExistsCoachAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);

        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);


        String ownerEmail2 = "owner2@gmail.com";
        TeamOwner teamOwner2 = new TeamOwner(ownerEmail2, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(teamOwner2);
        RoleDbInMemory.getInstance().insertRole(ownerEmail2,"Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner2);
        try {
            teamOwnerController.addCoach("Other", ownerEmail2,"email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Coach associated with a team", e.getMessage());
        }
    }


    @Test
    public void testAddCoachExistsCoachAssociatedWithSameTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();

        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

//        Player player = new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
//        SubscriberDbInMemory.getInstance().createSubscriber(player);
        teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoachOther", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);

        try {
            teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoachOther", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Coach associated this team", e.getMessage());
        }
    }

    @Test
    public void testAddCoachExistsCoachIdDifferentDetails() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        coachDb.insertCoach(new Coach("email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        try {
            teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoachOther", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("One or more of the details incorrect", e.getMessage());
        }
    }


    @Test
    public void testAddTCoachExistSubscriberWithTeamOwnerAndSameTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String managerToAdd = "email@gmail.com";
        TeamOwner ownerToAddAsManager = new TeamOwner(managerToAdd, "1234",1, "first", "last", teamName);
        SubscriberDbInMemory.getInstance().insertSubscriber(ownerToAddAsManager);
        RoleDbInMemory.getInstance().insertRole(managerToAdd,teamName, RoleType.TEAM_OWNER);
        Assert.assertEquals(RoleType.TEAM_OWNER,roleDbInMemory.getRole(managerToAdd).getRoleType());
        Thread.sleep(100);
        teamOwnerController.addCoach(teamName, ownerEmail,managerToAdd, 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);

    Team team = teamDb.getTeam(teamName);Map<String, Coach> coaches = team.getCoaches();

        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey(managerToAdd));
        Coach coach = coaches.get(managerToAdd);
        Assert.assertEquals(1, coach.getId().intValue());
        Assert.assertEquals(managerToAdd, coach.getEmailAddress());
        Assert.assertEquals("first", coach.getFirstName());
        Assert.assertEquals("last", coach.getLastName());
        Assert.assertEquals(team, coach.getTeam());
        Assert.assertEquals(2,roleDbInMemory.getRoles(managerToAdd).size());
        Assert.assertEquals(RoleType.COACH,roleDbInMemory.getRole(managerToAdd).getRoleType());
    }

    @Test
    public void testAddCoachExistSubscriberWithTeamOwnerAndDifferentTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String otherTeam = "other";
 teamDb.insertTeam(otherTeam);
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String coachToAdd = "email@gmail.com";
        TeamOwner ownerToAddAsCoach = new TeamOwner(coachToAdd, "1234",1, "first", "last", otherTeam);
        SubscriberDbInMemory.getInstance().insertSubscriber(ownerToAddAsCoach);
        RoleDbInMemory.getInstance().insertRole(coachToAdd,otherTeam, RoleType.TEAM_OWNER);
        Assert.assertEquals(RoleType.TEAM_OWNER,roleDbInMemory.getRole(coachToAdd).getRoleType());
        try {
            teamOwnerController.addCoach(teamName, ownerEmail,coachToAdd, 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The coach to added already has other team", e.getMessage());
        }
    }

    @Test
    public void testAddCoachExistSubscriberWithDifferentTypeAndSameTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String coachToAdd = "email@gmail.com";
        Coach ownerToAddAsCoach = new Coach(coachToAdd, "1234",1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        SubscriberDbInMemory.getInstance().insertSubscriber(ownerToAddAsCoach);
        RoleDbInMemory.getInstance().insertRole(coachToAdd,teamName, RoleType.COACH);
        Assert.assertEquals(RoleType.COACH,roleDbInMemory.getRole(coachToAdd).getRoleType());

        try {
            teamOwnerController.addCoach(teamName, ownerEmail,coachToAdd, 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The coach to added already has other subscriber type", e.getMessage());
        }
    }


    @Test
    public void testAddCoachExistsCoach() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        coachDb.insertCoach(new Coach("email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
    Team team = teamDb.getTeam(teamName);Map<String, Coach> coaches = team.getCoaches();
        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey("email@gmail.com"));
        Coach coach = coaches.get("email@gmail.com");
        Assert.assertEquals("email@gmail.com", coach.getEmailAddress());
        Assert.assertEquals(1, coach.getId().intValue());
        Assert.assertEquals("firstCoach", coach.getFirstName());
        Assert.assertEquals("lastCoach", coach.getLastName());
        Assert.assertEquals(CoachRole.MAJOR, coach.getCoachRole());
        Assert.assertEquals(QualificationCoach.UEFA_A, coach.getQualificationCoach());
        Assert.assertEquals(team, coach.getTeam());
    }

    @Test
    public void testAddCoachExistsCoachAsDifferentSubscriber() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        Player player = new Player("email@gmail.com", 1, "first", "last", birthDate, PlayerRole.GOALKEEPER);
        SubscriberDbInMemory.getInstance().insertSubscriber(player);
        try {
            teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The coach to added already has other subscriber type", e.getMessage());
        }
    }

    /////////////////////////// addCourt ///////////////////////////////
    @Test
    public void testAddCourtInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.addCourt(teamName, "owner@gmail.com",null, "courtCity");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void testAddCourtOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Thread.sleep(100);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.addCourt(teamName,withoutPermissionsOwnerEmail,"courtName","courtCity");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testAddCourtOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.ADD_COURT);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());
        teamOwnerController.addCourt(teamName,withPermissionsEmail,"courtName","courtCity");
        Assert.assertEquals("courtName", teamDb.getTeam(teamName).getCourt().getCourtName());
        Assert.assertEquals("courtCity", teamDb.getTeam(teamName).getCourt().getCourtCity());
    }

    @Test
    public void testAddCourtOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.addCourt(teamName, withoutPermissionsOwnerEmail,"courtName","courtCity");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testAddCourtTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
            teamOwnerController.addCourt("notExists", ownerEmail,"courtName", "courtCity");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddCourtTeamInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);try {
teamOwnerController.addCourt(teamName, ownerEmail,"courtName", "courtCity");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddCourtExistsCourtAssociatedWithSameTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.insertCourt(new Court("courtName", "courtCity"));
        teamOwnerController.addCourt(teamName, "owner@gmail.com","courtName", "courtCity");
        try {
            teamOwnerController.addCourt(teamName, "owner@gmail.com","courtName", "courtCity");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("team already associated with court", e.getMessage());
        }
    }

    @Test
    public void testAddCourtExistsCourtAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addCourt(teamName, ownerEmail,"courtName", "courtCity");

        String ownerEmail2 = "owner2@gmail.com";
        TeamOwner teamOwner2 = new TeamOwner(ownerEmail2, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(teamOwner2);
        RoleDbInMemory.getInstance().insertRole(ownerEmail2,"Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner2);
        teamOwnerController.addCourt("Other", ownerEmail2,"courtName", "courtCity");
        Assert.assertTrue(CourtDbInMemory.getInstance().getCourt("courtName").getTeams().containsKey(teamName));
        Assert.assertTrue(CourtDbInMemory.getInstance().getCourt("courtName").getTeams().containsKey("Other"));
    }

    @Test
    public void testAddCourtNotExistsCourt() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addCourt(teamName, ownerEmail,"courtName", "courtCity");
    Team team = teamDb.getTeam(teamName);Court court = team.getCourt();
        Assert.assertEquals("courtName", court.getCourtName());
        Assert.assertEquals("courtCity", court.getCourtCity());
        Assert.assertEquals(team, court.getTeam(teamName));
    }

    @Test
    public void testAddCourtIncorrectCityName() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.insertCourt(new Court("courtName", "courtCity"));
        try {
            teamOwnerController.addCourt(teamName, ownerEmail,"courtName", "courtCityOther");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The court name isn't match to the city", e.getMessage());
        }
    }

    @Test
    public void testAddCourtExistsCourt() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.insertCourt(new Court("courtName", "courtCity"));
        teamOwnerController.addCourt(teamName, ownerEmail,"courtName", "courtCity");
    Team team = teamDb.getTeam(teamName);Court court = team.getCourt();
        Assert.assertEquals("courtName", court.getCourtName());
        Assert.assertEquals("courtCity", court.getCourtCity());
        Assert.assertEquals(team, court.getTeam(teamName));
    }

////////////////////////////////// removePlayer /////////////////////////////////////

    @Test
    public void testRemovePlayerInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.removePlayer(teamName, null,"email@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.removePlayer(teamName, withoutPermissionsOwnerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testRemovePlayerOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.REMOVE_PLAYER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());
        teamOwnerController.removePlayer(teamName, withPermissionsEmail,"email@gmail.com");
        Assert.assertEquals(0, teamDb.getTeam(teamName).getPlayers().size());
        Assert.assertFalse(teamDb.getTeam(teamName).getPlayers().containsKey("email@gmail.com"));
    }

    @Test
    public void testRemovePlayerOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.removePlayer(teamName, withoutPermissionsOwnerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.removePlayer("notExists", "owner@gmail.com","email@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerTeamInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);try {
teamOwnerController.removePlayer(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.removePlayer(teamName, "owner@gmail.com","email@gmail.com");
        } catch (NotFoundException e) {
            Assert.assertEquals("Player not found", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerExistsPlayerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);
        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(otherOwner);
        RoleDbInMemory.getInstance().insertRole(otherOwnerEmail,"Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(otherOwner);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        Date birthDate = new Date();
        playerDb.insertPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamOwnerController.addPlayer("Other", otherOwnerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamOwnerController.removePlayer(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Player is not part with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner); playerDb.insertPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        try {
            teamOwnerController.removePlayer(teamName, "owner@gmail.com","email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Player is not part with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
    Team team = teamDb.getTeam(teamName);RoleDbInMemory teamRoleDbInMemory = RoleDbInMemory.getInstance();
        Map<String, Player> players = team.getPlayers();
        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.PLAYER, RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());

        teamOwnerController.removePlayer(teamName, ownerEmail,"email@gmail.com");

        Assert.assertEquals(0, players.size());//players in team
        Assert.assertFalse(players.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.PLAYER, RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());
        Assert.assertNull(RoleDbInMemory.getInstance().getRole("email@gmail.com").getTeamName());
    }


    ////////////////////////////////// removeTeamManager /////////////////////////////////////

    @Test
    public void testRemoveTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.removeTeamManager(teamName, null,"email@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.removeTeamManager(teamName,withoutPermissionsOwnerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testRemoveTeamManagerOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addTeamManager(teamName,"email@gmail.com", 1, "firstPlayer", "lastPlayer",new ArrayList<>(),ownerEmail);

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.REMOVE_TEAM_MANAGER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);

        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());

        teamOwnerController.removeTeamManager(teamName,withPermissionsEmail,"email@gmail.com");
        Assert.assertEquals(1, teamDb.getTeam(teamName).getTeamManagers().size());
        Assert.assertFalse(teamDb.getTeam(teamName).getTeamManagers().containsKey("email@gmail.com"));
    }


    @Test
    public void testRemoveTeamManagerOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.removeTeamManager(teamName, withoutPermissionsOwnerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }
    @Test
    public void testRemoveTeamManagerTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.removePlayer("notExists", "owner@gmail.com","email@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerTeamInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,"owner@gmail.com", TeamStatus.INACTIVE);
try {
            teamOwnerController.removeTeamManager(teamName, "owner@gmail.com","email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.removeTeamManager(teamName, "owner@gmail.com","email@gmail.com");
        } catch (NotFoundException e) {
            Assert.assertEquals("Team Manager not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerExistsTeamManagerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        Date birthDate = new Date();
        teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(otherOwner);
        RoleDbInMemory.getInstance().insertRole(otherOwnerEmail,"Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(otherOwner);

        teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", new ArrayList<>(),ownerEmail);

        try {
            teamOwnerController.removeTeamManager("Other", otherOwnerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamManager is not part of the team", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamManagerDb.insertTeamManager(new TeamManager("email@gmail.com", 1, "firstPlayer", "lastPlayer", null));
        try {
            teamOwnerController.removeTeamManager(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamManager is not part of the team", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", new ArrayList<>(),ownerEmail);
    Team team = teamDb.getTeam(teamName);RoleDbInMemory teamRoleDbInMemory = RoleDbInMemory.getInstance();
        List<Role> roles = teamRoleDbInMemory.getRoles("email@gmail.com");
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());
        teamOwnerController.removeTeamManager(teamName, ownerEmail,"email@gmail.com");
        Assert.assertEquals(0, teamManagers.size());
        Assert.assertFalse(team.getTeamManagers().containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());
        Assert.assertNull(RoleDbInMemory.getInstance().getRole("email@gmail.com").getTeamName());
    }

    ////////////////////////////////// removeCoach /////////////////////////////////////////
    @Test
    public void testRemoveCoachInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

            teamOwnerController.removeCoach(teamName,null, "email@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addCoach(teamName,ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.GOALKEEPER, QualificationCoach.UEFA_A);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);
        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.removeCoach(teamName,withoutPermissionsOwnerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testRemoveCoachOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addCoach(teamName,ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.GOALKEEPER, QualificationCoach.UEFA_A);

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.REMOVE_COACH);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());
        teamOwnerController.removeCoach(teamName,withPermissionsEmail,"email@gmail.com");
        Assert.assertEquals(0, teamDb.getTeam(teamName).getCoaches().size());
        Assert.assertFalse(teamDb.getTeam(teamName).getCoaches().containsKey("email@gmail.com"));
    }

    @Test
    public void testRemoveCoachOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.removeCoach(teamName, withoutPermissionsOwnerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }
    @Test
    public void testRemoveCoachTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerMail = "owner@gmail.com";
            teamOwnerDb.insertTeamOwner(new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.removeCoach("notExists",ownerMail, "email@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachTeamInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);try {
teamOwnerController.removeCoach(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachNotExistsCoach() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.removeCoach(teamName, "owner@gmail.com","email@gmail.com");
        } catch (NotFoundException e) {
            Assert.assertEquals("Coach not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        coachDb.insertCoach(new Coach("email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        try {
            teamOwnerController.removeCoach(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Coach is not part with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String coachToRemove = "email@gmail.com";
        coachDb.insertCoach(new Coach(coachToRemove, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamOwnerController.addCoach(teamName,ownerEmail, coachToRemove, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
    Team team = teamDb.getTeam(teamName);RoleDbInMemory teamRoleDbInMemory = RoleDbInMemory.getInstance();
        List<Role> roles = teamRoleDbInMemory.getRoles(coachToRemove);
        Map<String, Coach> coaches = team.getCoaches();

        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey(coachToRemove));
        Assert.assertEquals(RoleType.COACH, RoleDbInMemory.getInstance().getRole(coachToRemove).getRoleType());

        teamOwnerController.removeCoach(teamName, ownerEmail,coachToRemove);

        Assert.assertEquals(0, coaches.size());
        Assert.assertFalse(coaches.containsKey(coachToRemove));
        Assert.assertEquals(RoleType.COACH, RoleDbInMemory.getInstance().getRole(coachToRemove).getRoleType());
        Assert.assertNull(RoleDbInMemory.getInstance().getRole(coachToRemove).getTeamName());
    }

    ////////////////////////////////// removeCourt /////////////////////////////////////////
    @Test
    public void testRemoveCourtInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
             teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.removeCourt(teamName,null, "courtName");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testRemoveCourtOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.removeCourt(teamName,withoutPermissionsOwnerEmail,"courtName");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testRemoveCourtOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addCourt(teamName,ownerEmail,"courtName","cityName");

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.REMOVE_COURT);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());
        teamOwnerController.removeCourt(teamName,withPermissionsEmail,"courtName");
        Assert.assertNull(teamDb.getTeam(teamName).getCourt());
    }

    @Test
    public void testRemoveCourtOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.removeCourt(teamName, withoutPermissionsOwnerEmail,"courName");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

            teamOwnerController.removeCourt("notExists", ownerEmail, "courtName");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtTeamInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,"owner@gmail.com", TeamStatus.INACTIVE);
try {
            teamOwnerController.removeCourt(teamName, ownerEmail,"courtName");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtNotExistsCourt() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.removeCourt(teamName, ownerEmail,"courtName");
        } catch (NotFoundException e) {
            Assert.assertEquals("Court not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.insertCourt(new Court("courtName", "courtCity"));

        try {
            teamOwnerController.removeCourt(teamName, ownerEmail,"courtName");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Court is not part of the with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.insertCourt(new Court("courtName", "courtCity"));
        teamOwnerController.addCourt(teamName, ownerEmail,"courtName", "courtCity");
    Team team = teamDb.getTeam(teamName);Court court = team.getCourt();
        Assert.assertEquals(team, court.getTeam(teamName));
        HashMap<String, Team> teams = court.getTeams();
        Assert.assertEquals(1, teams.size());
        Assert.assertTrue(teams.containsKey(teamName));
        teamOwnerController.removeCourt(teamName, ownerEmail,"courtName");
        Assert.assertEquals(0, teams.size());
        Assert.assertFalse(teams.containsKey(court.getCourtName()));
        Assert.assertNull(teamDb.getTeam(teamName).getCourt());
    }


    ////////////////////////////////// addFinancialActivity //////////////////////////////////////////
    @Test
    public void testAddFinancialActivityInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.addFinancialActivity(null, "owner@gmail.com",1000.0, "Description", FinancialActivityType.OUTCOME);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void testAddFinanacialActivityOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
       teamDb.getTeam(teamName).setBudget(100.0);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.addFinancialActivity(teamName, withoutPermissionsOwnerEmail,100.0, "description", FinancialActivityType.INCOME);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testAddFinancialActivityOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
       teamDb.getTeam(teamName).setBudget(100.0);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.ADD_FINANCIAL);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());
        teamOwnerController.addFinancialActivity(teamName, withPermissionsEmail,100.0, "description", FinancialActivityType.INCOME);
        Assert.assertEquals(200.0, teamDb.getTeam(teamName).getBudget(),0);
    }


    @Test
    public void testAddFinancialActivityOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.addFinancialActivity(teamName, withoutPermissionsOwnerEmail,1000.0, "Description", FinancialActivityType.OUTCOME);

            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }
    @Test
    public void testAddFinancialActivityTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

            teamOwnerController.addFinancialActivity("NotExists", "owner@gmail.com",1000.0, "Description", FinancialActivityType.OUTCOME);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddFinancialActivityWithOutcomeExceedsBudget() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
    Team team = teamDb.getTeam(teamName);team.setBudget(800.0);
        try {
            teamOwnerController.addFinancialActivity(teamName, "owner@gmail.com",1000.0, "Description", FinancialActivityType.OUTCOME);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The financial outcome exceeds from the budget", e.getMessage());
        }
        Assert.assertEquals(800.0, team.getBudget(), 0);
    }

    @Test
    public void testAddFinancialActivityWithOutcomeUnderBudget() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
    Team team = teamDb.getTeam(teamName);team.setBudget(1001.0);
        teamOwnerController.addFinancialActivity(teamName, ownerEmail,1000.0, "Description", FinancialActivityType.OUTCOME);
        Assert.assertEquals(1.0, team.getBudget(), 0);
    }

    @Test
    public void testAddFinancialActivityWithOutcomeEqualBudget() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
    Team team = teamDb.getTeam(teamName);team.setBudget(1000.0);

        teamOwnerController.addFinancialActivity(teamName,ownerEmail, 1000.0, "Description", FinancialActivityType.OUTCOME);
        Assert.assertEquals(0.0, team.getBudget(), 0);
        Map<String, FinancialActivity> financialActivities = team.getFinancialActivities();
        Assert.assertEquals(1, financialActivities.size());
        Set<String> keySet = financialActivities.keySet();
        Assert.assertEquals(1000.0, financialActivities.get(keySet.iterator().next()).getFinancialActivityAmount(), 0);
        Assert.assertEquals(FinancialActivityType.OUTCOME, financialActivities.get(keySet.iterator().next()).getFinancialActivityType());
    }


    @Test
    public void testAddFinancialActivityWithIncomeEqualBudget() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
    Team team = teamDb.getTeam(teamName);team.setBudget(1000.0);
        teamOwnerController.addFinancialActivity(teamName, ownerEmail,1000.0, "Description", FinancialActivityType.INCOME);
        Map<String, FinancialActivity> financialActivities = team.getFinancialActivities();
        Assert.assertEquals(1, financialActivities.size());
        Set<String> keySet = financialActivities.keySet();
        Assert.assertEquals(1000.0, financialActivities.get(keySet.iterator().next()).getFinancialActivityAmount(), 0);
        Assert.assertEquals(FinancialActivityType.INCOME, financialActivities.get(keySet.iterator().next()).getFinancialActivityType());
        Assert.assertEquals(2000.0, team.getBudget(), 0);
    }

    @Test
    public void testAddFinancialActivityWithIncomeUnderBudget() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

    Team team = teamDb.getTeam(teamName);team.setBudget(800.0);
        teamOwnerController.addFinancialActivity(teamName, ownerEmail,1000.0, "Description", FinancialActivityType.INCOME);
        Assert.assertEquals(1800.0, team.getBudget(), 0);
    }

    //////////////////////////////////////////// changeStatus /////////////////////////////////////////////////////

    @Test
    public void testChangeStatusInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com" ,"1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.changeStatus(null,"owner@gmail.com", TeamStatus.ACTIVE);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testChangeStatusOwnerWithoutManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withoutPermissionsOwnerEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withoutPermissionsOwnerEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withoutPermissionsOwnerEmail).getRoleType());
        try {
            teamOwnerController.changeStatus(teamName,withoutPermissionsOwnerEmail, TeamStatus.INACTIVE);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }


    @Test
    public void testChangeStatusOwnerWithManagerPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withPermissionsEmail = "withPermissions@gmail.com";
        Player managerToAdd = new Player(withPermissionsEmail, 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(managerToAdd);
        RoleDbInMemory.getInstance().insertRole(withPermissionsEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);

        ArrayList<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.CHANGE_STATUS);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerEmail, withPermissionsEmail,permissionTypes);
        Assert.assertEquals(RoleType.TEAM_MANAGER, RoleDbInMemory.getInstance().getRole(withPermissionsEmail).getRoleType());
        Assert.assertEquals(TeamStatus.ACTIVE, teamDb.getTeam(teamName).getTeamStatus());
        teamOwnerController.changeStatus(teamName,withPermissionsEmail, TeamStatus.INACTIVE);
        Assert.assertEquals(TeamStatus.INACTIVE, teamDb.getTeam(teamName).getTeamStatus());
    }

    @Test
    public void testChangeStatusOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.changeStatus(teamName,withoutPermissionsOwnerEmail, TeamStatus.INACTIVE);

            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }
    @Test
    public void testChangeStatusTeamNotFound() {
        try {
            String teamName = "Exists";

             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

            teamOwnerController.changeStatus("NotExists", ownerEmail, TeamStatus.ACTIVE);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testChangeStatusFromActiveToInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
    Team team = teamDb.getTeam(teamName);team.setTeamStatus(TeamStatus.ACTIVE);
        teamOwnerController.changeStatus(teamName,"owner@gmail.com", TeamStatus.INACTIVE);
Assert.assertEquals(TeamStatus.INACTIVE, team.getTeamStatus());
    }

    @Test
    public void testChangeStatusFromInactiveToActive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(teamOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(teamOwnerEmail,teamName, RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
    Team team = teamDb.getTeam(teamName);team.setTeamStatus(TeamStatus.INACTIVE);
        teamOwnerController.changeStatus(teamName, teamOwnerEmail, TeamStatus.ACTIVE);
        Assert.assertEquals(TeamStatus.ACTIVE, team.getTeamStatus());
    }

    @Test
    public void testChangeStatusFromActiveToActive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        Team team = teamDb.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        try {
            teamOwnerController.changeStatus(teamName,"owner@gmail.com", TeamStatus.ACTIVE);
        }catch (Exception e){
            Assert.assertEquals("The team already ACTIVE",e.getMessage());
        }
    }
    @Test
    public void testChangeStatusFromInctiveToInctive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

    Team team = teamDb.getTeam(teamName);team.setTeamStatus(TeamStatus.INACTIVE);
        try {
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
        } catch (Exception e){
            Assert.assertEquals("The team already INACTIVE",e.getMessage());
        }
    }



    ////////////////////////////////////////////// updatePlayer ///////////////////////////////////////////

    @Test
    public void testUpdatePlayerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            Date birthDate = new Date();
            teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com" ,"1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.updatePlayerDetails(teamName,"owner@gmail.com", "email@gmail.com", "changePlayer", null, birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testUpdatePlayerExistsPlayerChangeDetails() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         Date birthDate = new Date();
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        playerDb.insertPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamOwnerController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        teamOwnerController.updatePlayerDetails(teamName,ownerEmail, "email@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
    Team team = teamDb.getTeam(teamName);Map<String, Player> players = team.getPlayers();
        Player player = players.get("email@gmail.com");
        Assert.assertEquals("changePlayer", player.getFirstName());
    }
    @Test
    public void testUpdatePlayerOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.updatePlayerDetails(teamName,withoutPermissionsOwnerEmail, "email@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);

            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testUpdatePlayerNotExistsChangeDetails() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);Date birthDate = new Date();
        playerDb.insertPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamOwnerController.updatePlayerDetails(teamName,ownerEmail, "emailNotExists@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Player not associated with teamOwner's team", e.getMessage());
        }
    }

    @Test
    public void testUpdatePlayerTeamOwnerNotExists() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);Date birthDate = new Date();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamOwnerController.updatePlayerDetails(teamName,ownerEmail, "emailNotExists@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        } catch (NotFoundException e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testUpdatePlayerTeamOwnerExistsWithDifferentTeam() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().insertRole(otherOwnerEmail,"Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(otherTeamOwner);

        Date birthDate = new Date();
        teamOwnerController.addPlayer("Other",otherOwnerEmail, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamOwnerController.updatePlayerDetails(teamName,ownerEmail, "email@gmail.com", "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Player not associated with teamOwner's team", e.getMessage());
        }
    }

    //////////////////////////////////// subscriptionTeamOwner ///////////////////////////////////////////////////
    @Test
    public void testsSubscriptionTeamOwnerInvalidInputs() {
        try {
            teamOwnerController.subscriptionTeamOwner("Team", null, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerOwnerWithOtherTeamNotPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.subscriptionTeamOwner(teamName, withoutPermissionsOwnerEmail, "emailToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerTeamNotFound() {
        try {
            teamOwnerController.subscriptionTeamOwner("notExists", "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
            ;
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerTeamInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);try {
teamOwnerController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerOwnerNotExists() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerSubscriberNotExist() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
        try {
            teamOwnerController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }


    @Test
    public void testSubscriptionTeamOwnerTeamRoleWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        Player player = new Player("teamOwnerToAdd@gmail.com", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(player);
        SubscriberDbInMemory.getInstance().insertSubscriber(player);
        RoleDbInMemory.getInstance().insertRole("teamOwnerToAdd@gmail.com", "Other", RoleType.PLAYER);
        try {
            teamOwnerController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");

            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("OwnerToAdd already associated with other team", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerTeamRoleTeamOwnerExists() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        TeamOwner teamOwnerToAdd = new TeamOwner("teamOwnerToAdd@gmail.com", "1234", 3, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwnerToAdd);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwnerToAdd);
        RoleDbInMemory.getInstance().insertRole("teamOwnerToAdd@gmail.com", teamName, RoleType.TEAM_OWNER);

        try {
            teamOwnerController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber already teamOwner", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerLegalSubscription() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String ownerToAdd = "teamOwnerToAdd@gmail.com";
        teamOwnerController.addPlayer(teamName,ownerEmail,ownerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);

        teamOwnerController.subscriptionTeamOwner(teamName,ownerEmail,ownerToAdd);

        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber(ownerToAdd);
        List<Role> roles = RoleDbInMemory.getInstance().getRoles(ownerToAdd);
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_OWNER, roles.get(1).getRoleType());
        TeamOwner teamOwnerAdded = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerToAdd);
        Assert.assertEquals(ownerEmail, teamOwnerAdded.getOwnedByEmailAddress());
        List<String> allTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerEmail);
        Assert.assertEquals(ownerToAdd, allTeamOwnersOwnedBy.get(0));
    }

    @Test
    public void testSubscriptionTeamOwnerWithoutAssociatedTeamSubscription() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        Player player = new Player("teamOwnerToAdd@gmail.com", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(player);
        RoleDbInMemory.getInstance().insertRole("teamOwnerToAdd@gmail.com", null, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(player);

        teamOwnerController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");

        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber("teamOwnerToAdd@gmail.com");
        List<Role> roles = RoleDbInMemory.getInstance().getRoles("teamOwnerToAdd@gmail.com");
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_OWNER, roles.get(1).getRoleType());
        TeamOwner teamOwnerAdded = TeamOwnerDbInMemory.getInstance().getTeamOwner("teamOwnerToAdd@gmail.com");
        Assert.assertEquals(ownerEmail, teamOwnerAdded.getOwnedByEmailAddress());
        List<String> allTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerEmail);
        Assert.assertEquals("teamOwnerToAdd@gmail.com", allTeamOwnersOwnedBy.get(0));
    }

    /////////////////////////////// removeSubscriptionTeamOwner //////////////////////////////////////////////////////////////
    @Test
    public void testRemoveSubscriptionTeamOwnerInvalidInputs() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            teamOwnerController.removeSubscriptionTeamOwner(null, "email@gmail.com", "emailToRemove@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerWithOtherTeamNotPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.removeSubscriptionTeamOwner(teamName, withoutPermissionsOwnerEmail, "emailToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }
    @Test
    public void testRemoveSubscriptionTeamOwnerTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            teamOwnerController.removeSubscriptionTeamOwner("notExists", "email@gmail.com","emailToRemove@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerTeamInactive() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);try {
teamOwnerController.removeSubscriptionTeamOwner(teamName, ownerEmail,"emailToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerNotExists() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.removeSubscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }
    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerWithOtherTeam() throws Exception {
        String teamName = "Exists";
    teamDb.insertTeam(teamName);teamDb.insertTeam("Other");

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.removeSubscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner's team does't match", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveNotExist() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.removeSubscriptionTeamOwner(teamName,  ownerEmail,"teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveAssociatedWithOtherTeamOwner() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String differentOwnerEmail = "differentTeamOwner@gmail.com";
        TeamOwner differentOwner = new TeamOwner(differentOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(differentOwner);
        RoleDbInMemory.getInstance().insertRole(differentOwnerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(differentOwner);


        Player teamOwnerToAdd = new Player("teamOwnerToAdd@gmail.com",  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(teamOwnerToAdd);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwnerToAdd);

        teamOwnerController.subscriptionTeamOwner(teamName,differentOwnerEmail,"teamOwnerToAdd@gmail.com");

        try {
            teamOwnerController.removeSubscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwnerToRemove owned by another teamOwner", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
    teamDb.insertTeam(teamName);teamDb.insertTeam("Other");


        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail, teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        TeamOwner teamOwnerDifferent = new TeamOwner("teamOwnerDifferent@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(teamOwnerDifferent);
        RoleDbInMemory.getInstance().insertRole("teamOwnerDifferent@gmail.com", "Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwnerDifferent);

        teamOwnerController.addPlayer("Other","teamOwnerDifferent@gmail.com","teamOwnerToAdd@gmail.com",  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        teamOwnerController.subscriptionTeamOwner("Other","teamOwnerDifferent@gmail.com","teamOwnerToAdd@gmail.com");
        try {
            teamOwnerController.removeSubscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwnerToRemove associated with other team", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveWithOthersTeamOwnerOwnedByTeamOwnerToRemoveTwoLevels() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerMail = "owner@gmail.com";
        String ownerToAdd = "teamOwnerToAdd@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(teamOwnerMail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addPlayer(teamName,teamOwnerMail,ownerToAdd,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        List<String> teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(teamOwnerMail);
        Assert.assertEquals(ownerToAdd, teamOwnerAllTeamOwnersOwnedBy.get(0));


        String ownerToAddUnder = "teamOwnerToAddUnder@gmail.com";
        teamOwnerController.addPlayer(teamName,ownerToAdd,ownerToAddUnder,  5, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamOwner(teamName, ownerToAdd, ownerToAddUnder);

        List<String> teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(ownerToAddUnder, teamOwnerToAddAllTeamOwnersOwnedBy.get(0));

        //delete ownerToAdd should to remove ownerToAddUnder//
        teamOwnerController.removeSubscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(teamOwnerMail);
        teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);

        Assert.assertEquals(RoleType.PLAYER, RoleDbInMemory.getInstance().getRole(ownerToAdd).getRoleType());
        Assert.assertEquals(RoleType.PLAYER, RoleDbInMemory.getInstance().getRole(ownerToAddUnder).getRoleType());
        Assert.assertEquals(0, teamOwnerAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(0, teamOwnerToAddAllTeamOwnersOwnedBy.size());
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveWithOthersTeamOwnerOwnedByTeamOwnerToRemoveThreeLevels() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerMail = "owner@gmail.com";
        String ownerToAdd = "teamOwnerToAdd@gmail.com";
        String ownerToAddUnder = "teamOwnerToAddUnder@gmail.com";
        String ownerToAddUnderSecond = "teamOwnerToAddUnderSecond@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(teamOwnerMail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addPlayer(teamName,teamOwnerMail,ownerToAdd,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        List<String> teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(teamOwnerMail);
        Assert.assertEquals(ownerToAdd, teamOwnerAllTeamOwnersOwnedBy.get(0));


        teamOwnerController.addPlayer(teamName,ownerToAdd,ownerToAddUnder,  5, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamOwner(teamName, ownerToAdd, ownerToAddUnder);

        List<String> teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(ownerToAddUnder, teamOwnerToAddAllTeamOwnersOwnedBy.get(0));

        teamOwnerController.addPlayer(teamName,ownerToAddUnder,ownerToAddUnderSecond,  6, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamOwner(teamName, ownerToAddUnder, ownerToAddUnderSecond);

        List<String> teamOwnerToAddUnderAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAddUnder);
        Assert.assertEquals(ownerToAddUnderSecond, teamOwnerToAddUnderAllTeamOwnersOwnedBy.get(0));

        //delete ownerToAdd should to remove ownerToAddUnder//
        teamOwnerController.removeSubscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(teamOwnerMail);
        teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        teamOwnerToAddUnderAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAddUnder);

        Assert.assertEquals(0, teamOwnerAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(0, teamOwnerToAddAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(0,teamOwnerToAddUnderAllTeamOwnersOwnedBy.size());
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();
        Assert.assertEquals(RoleType.PLAYER,roleDbInMemory.getRole(ownerToAdd).getRoleType());
        Assert.assertEquals(RoleType.PLAYER,roleDbInMemory.getRole(ownerToAddUnder).getRoleType());
        Assert.assertEquals(RoleType.PLAYER,roleDbInMemory.getRole(ownerToAddUnderSecond).getRoleType());
        Assert.assertEquals(1,roleDbInMemory.getRoles(ownerToAdd).size());
        Assert.assertEquals(1,roleDbInMemory.getRoles(ownerToAddUnder).size());
        Assert.assertEquals(1,roleDbInMemory.getRoles(ownerToAddUnderSecond).size());
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveWithOthersTeamOwnerOwnedByTeamOwnerToRemoveWithTeamManager() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String ownerToAdd = "teamOwnerToAdd@gmail.com";
        teamOwnerController.addPlayer(teamName,ownerEmail,ownerToAdd,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamOwner(teamName, ownerEmail, ownerToAdd);

        List<String> teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerEmail);
        Assert.assertEquals(ownerToAdd, teamOwnerAllTeamOwnersOwnedBy.get(0));

        String ownerToAddUnder = "teamOwnerToAddUnder@gmail.com";
        teamOwnerController.addPlayer(teamName,ownerToAdd,ownerToAddUnder,  5, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamOwner(teamName, ownerToAdd, ownerToAddUnder);

        List<String> teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(ownerToAddUnder, teamOwnerToAddAllTeamOwnersOwnedBy.get(0));

        String  teamManagerToAdd = "teamManagerToAddUnder@gmail.com";
        teamOwnerController.addPlayer(teamName,ownerToAdd,teamManagerToAdd,  5, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerController.subscriptionTeamManager(teamName,ownerToAdd,teamManagerToAdd,new ArrayList<>());

        String ownedByEmail = TeamManagerDbInMemory.getInstance().getTeamManager(teamManagerToAdd).getOwnedByEmail();
        Assert.assertEquals(ownerToAdd,ownedByEmail);

        //delete ownerToAdd should to remove ownerToAddUnder//
        teamOwnerController.removeSubscriptionTeamOwner(teamName, ownerEmail, ownerToAdd);

        teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerEmail);
        teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(RoleType.PLAYER, RoleDbInMemory.getInstance().getRole(teamManagerToAdd).getRoleType());
        Assert.assertEquals(RoleType.PLAYER, RoleDbInMemory.getInstance().getRole(ownerToAdd).getRoleType());
        Assert.assertEquals(RoleType.PLAYER, RoleDbInMemory.getInstance().getRole(ownerToAddUnder).getRoleType());
        Assert.assertEquals(0, teamOwnerAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(0, teamOwnerToAddAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(1, RoleDbInMemory.getInstance().getRoles(ownerEmail).size());
        Assert.assertEquals(1, RoleDbInMemory.getInstance().getRoles(ownerToAdd).size());
        Assert.assertEquals(1, RoleDbInMemory.getInstance().getRoles(teamManagerToAdd).size());
    }

    ////////////////////// removeSubscriptionTeamManager ////////////////////////////////
    @Test
    public void testRemoveSubscriptionTeamManagerInvalidInputs() {
        try {

            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            teamOwnerController.removeSubscriptionTeamManager(null, "email@gmail.com", "emailToRemove@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testRemoveSubscriptionTeamManagerOwnerWithOtherTeamNotPermissions() throws Exception {
        String teamName = "Exists";
         teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.removeSubscriptionTeamManager(teamName, withoutPermissionsOwnerEmail, "emailToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerTeamNotFound() {
        try {
            String teamName = "Exists";
             teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));

            teamOwnerController.removeSubscriptionTeamManager("notExists", "email@gmail.com","emailToRemove@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerTeamInactive() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);try {
        teamOwnerController.removeSubscriptionTeamManager(teamName, ownerEmail,"emailToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerOwnerNotExists() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.removeSubscriptionTeamManager(teamName, ownerEmail, "managerToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }
    @Test
    public void testRemoveSubscriptionTeamManagerOwnerWithOtherTeam() throws Exception {
        String teamName = "Exists";
                    teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
         teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner); try {
            teamOwnerController.removeSubscriptionTeamManager(teamName, "owner@gmail.com", "managerToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner's team doesn't match", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerManagerToRemoveNotExist() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        try {
            teamOwnerController.removeSubscriptionTeamManager(teamName, ownerEmail, "managerToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team Manager not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerManagerToRemoveAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String otherTeamName = "Other";
        teamDb.insertTeam(otherTeamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);


        String ownerEmailDifferent = "teamOwnerDifferent@gmail.com";
        TeamOwner teamOwnerEmailDifferent = new TeamOwner(ownerEmailDifferent, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", otherTeamName);
        teamOwnerDb.insertTeamOwner(teamOwnerEmailDifferent);
        RoleDbInMemory.getInstance().insertRole(ownerEmailDifferent,otherTeamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwnerEmailDifferent);

        String managerToRemoveEmail = "managerToRemove@gmail.com";
        teamOwnerController.addPlayer(otherTeamName, ownerEmailDifferent, managerToRemoveEmail,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        teamOwnerController.subscriptionTeamManager(otherTeamName,ownerEmailDifferent, managerToRemoveEmail,new ArrayList<>());

        try {
            teamOwnerController.removeSubscriptionTeamManager(teamName, ownerEmail, managerToRemoveEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamManagerToRemove associated with other team", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerManagerToRemoveAssociatedWithOtherTeamOwner() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerMail = "owner@gmail.com";
        String teamManagerToRemove = "teamManagerToRemove@gmail.com";


        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(teamOwnerMail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        TeamOwner differentTeamOwner = new TeamOwner("differentTeamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(differentTeamOwner);
        RoleDbInMemory.getInstance().insertRole("differentTeamOwner@gmail.com",teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(differentTeamOwner);

        teamOwnerController.addPlayer(teamName,"differentTeamOwner@gmail.com",teamManagerToRemove,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        teamOwnerController.subscriptionTeamManager(teamName,"differentTeamOwner@gmail.com", teamManagerToRemove,new ArrayList<>());
        try {
            teamOwnerController.removeSubscriptionTeamManager(teamName, teamOwnerMail, teamManagerToRemove);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamManagerToRemove owned by another teamOwner", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerLegalRemove() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerMail = "owner@gmail.com";
        String managerToRemove = "managerToRemove@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(teamOwnerMail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addPlayer(teamName,teamOwnerMail,managerToRemove,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(1000);
        teamOwnerController.subscriptionTeamManager(teamName, teamOwnerMail, managerToRemove,new ArrayList<>());

        TeamManager teamManager = TeamManagerDbInMemory.getInstance().getTeamManager(managerToRemove);
        Assert.assertEquals(teamOwnerMail,teamManager.getOwnedByEmail());
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();
        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDbInMemory.getRole(managerToRemove).getRoleType());
        Assert.assertEquals(2, roleDbInMemory.getRoles(managerToRemove).size());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDbInMemory.getRoles(managerToRemove).get(0).getRoleType());
        Assert.assertEquals(RoleType.PLAYER, roleDbInMemory.getRoles(managerToRemove).get(1).getRoleType());

        //delete managerToRemove should to remove ownerToAddUnder//
        teamOwnerController.removeSubscriptionTeamManager(teamName, teamOwnerMail, managerToRemove);

        Assert.assertEquals(RoleType.PLAYER, roleDbInMemory.getRole(managerToRemove).getRoleType());
        Assert.assertEquals(1, roleDbInMemory.getRoles(managerToRemove).size());
    }



    //////////////////////////////////// subscriptionTeamManager ///////////////////////////////////////////////////
    @Test
    public void testsSubscriptionTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            teamOwnerController.subscriptionTeamManager("Exists", "owner@gmail.com",null, new ArrayList<>());
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }



    @Test
    public void testSubscriptionTeamManagerOwnerWithOtherTeamNotPermissions() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.subscriptionTeamManager(teamName, withoutPermissionsOwnerEmail, "emailToRemove@gmail.com",new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }
    @Test
    public void testSubscriptionTeamManagerTeamNotFound() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            teamOwnerController.subscriptionTeamManager("notExists", "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com",new ArrayList<>());
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerTeamInactive() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);try {
        teamOwnerController.subscriptionTeamManager(teamName, ownerEmail, "teamManagerToAdd@gmail.com",new ArrayList<>());
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerOwnerNotExists() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        Player managerToAdd = new Player("teamManagerToAdd@gmail.com", 3, "firstName", "lastName",new Date(), PlayerRole.GOALKEEPER);
        SubscriberDbInMemory.getInstance().insertSubscriber(managerToAdd);
        try {
            teamOwnerController.subscriptionTeamManager(teamName,ownerEmail,"teamManagerToAdd@gmail.com" ,new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }


    @Test
    public void testSubscriptionTeamManagerSubscriberNotExist() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);teamOwnerDb.insertTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
        try {
            teamOwnerController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", "TeamManagerToAdd@gmail.com",new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }


    @Test
    public void testSubscriptionTeamManagerTeamRoleWithOtherTeam() throws Exception {
        String teamName = "Exists";
                    teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
                    teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);
                    String ownerMail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerMail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String otherOwnerMail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().insertRole(otherOwnerMail,"Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(otherTeamOwner);

        String teamManagerToAdd = "teamManagerToAdd@gmail.com";
        teamOwnerController.addPlayer("Other", otherOwnerMail,teamManagerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        try {
            teamOwnerController.subscriptionTeamManager(teamName, ownerMail, teamManagerToAdd,new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("ManagerToAdd already associated with other team", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerTeamRoleTeamOwnerExists() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "teamOwner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 3, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail, teamName, RoleType.TEAM_OWNER);

        String managerToAdd = "teamManagerToAdd@gmail.com";

        TeamOwner teamManager = new TeamOwner(managerToAdd, "1234", 3, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamManager);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamManager);
        RoleDbInMemory.getInstance().insertRole(managerToAdd, teamName, RoleType.TEAM_OWNER);

        try {
            teamOwnerController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", managerToAdd,new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber already teamOwner", e.getMessage());
        }
    }
    @Test
    public void testSubscriptionTeamManagerTeamRoleTeamManagerExists() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String managerToAdd = "teamManagerToAdd@gmail.com";
        teamOwnerController.addTeamManager(teamName,managerToAdd,3, "firstTeamOwnerName", "lastTeamOwnerName",new ArrayList<>(),ownerEmail);
        try {
            teamOwnerController.subscriptionTeamManager(teamName, ownerEmail, managerToAdd,new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber already teamManager", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerLegalSubscription() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String managerToAdd = "teamManagerToAdd@gmail.com";
        teamOwnerController.addPlayer(teamName, ownerEmail,managerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        teamOwnerController.subscriptionTeamManager(teamName, ownerEmail, managerToAdd, new ArrayList<>());
        List<Role> roles = RoleDbInMemory.getInstance().getRoles(managerToAdd);
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roles.get(1).getRoleType());
        Assert.assertEquals(ownerEmail, TeamManagerDbInMemory.getInstance().getTeamManager(managerToAdd).getOwnedByEmail());
    }

    @Test
    public void testSubscriptionTeamManagerWithoutAssociatedTeamSubscription() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String managerToAdd = "teamManagerToAdd@gmail.com";
        Player player = new Player(managerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(player);
        RoleDbInMemory.getInstance().insertRole(managerToAdd, null, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(player);

        teamOwnerController.subscriptionTeamManager(teamName, ownerEmail, managerToAdd, new ArrayList<>());

        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber(managerToAdd);
        List<Role> roles = RoleDbInMemory.getInstance().getRoles(managerToAdd);
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roles.get(1).getRoleType());
        TeamManager teamManager = TeamManagerDbInMemory.getInstance().getTeamManager(managerToAdd);
        Assert.assertEquals(ownerEmail, teamManager.getOwnedByEmail());
        Assert.assertEquals(teamName, teamManager.getTeam());
    }
/////////////////////////////////// createNewTeam ///////////////////////////////////////
@Test
public void testsCreateNewTeamInvalidInputs() {
    try {
        teamOwnerController.createNewTeam("Team", null, new ArrayList<Player>(), new ArrayList<Coach>(), new ArrayList<TeamManager>(), new Court("courtName", "courtCity"),1000.0);
        Assert.fail("Should throw NullPointerException");
    } catch (Exception e) {
        Assert.assertTrue(e instanceof NullPointerException);
        Assert.assertEquals("bad input", e.getMessage());
    }
}
    @Test
    public void testCreateNewTeamNotPermissions() throws Exception {
        String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);
        try {
            teamOwnerController.createNewTeam(teamName, withoutPermissionsOwnerEmail, new ArrayList<Player>(), new ArrayList<Coach>(), new ArrayList<TeamManager>(), new Court("courtName", "courtCity"),1000.0);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testsCreateNewTeamNotExistsTeamOwnerInDb() {
        try {
            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRoleInSystem(ownerEmail, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
            teamOwnerController.createNewTeam("Team", "notExist", new ArrayList<Player>(), new ArrayList<Coach>(), new ArrayList<TeamManager>(), new Court("courtName", "courtCity"),1000.0);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }

    @Test
    public void testsCreateNewTeamTeamAlreadyExists() {
        try {
            teamDb.insertTeam("Team");
            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRoleInSystem(ownerEmail, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

            teamOwnerController.createNewTeam("Team", "owner@gmail.com", new ArrayList<Player>(), new ArrayList<Coach>(),new ArrayList<TeamManager>(),new Court("courtName","courtCity"),1000.0);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertEquals("Team already exist in the system", e.getMessage());
        }
    }

    @Test
    public void testsCreateNewTeamTeamOwnerHasTeam() throws Exception {
        try{
            String teamName = "Team";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
            teamOwnerDb.insertTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);            teamOwnerController.createNewTeam("newTeam", "owner@gmail.com", new ArrayList<Player>(), new ArrayList<Coach>(),new ArrayList<TeamManager>(),new Court("courtName","courtCity"),1000.0);
        Assert.fail("Should throw Exception");
        } catch (Exception e) {
        Assert.assertEquals("This teamOwner has already team", e.getMessage());
        }
    }

    @Test
    public void testsCreateNewTeamTeamOwnerAssociatedWithTheTeam() throws Exception {
        String teamName = "Team";
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.createNewTeam(teamName, ownerEmail, new ArrayList<Player>(), new ArrayList<Coach>(),new ArrayList<TeamManager>(),new Court("courtName","courtCity"),1000.0);
        Assert.assertEquals(teamName, TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerEmail).getTeam());
        Assert.assertTrue(TeamDbInMemory.getInstance().getTeam(teamName).getTeamOwners().containsKey(ownerEmail));
    }

    @Test
    public void testsCreateNewTeamTeamOwnerAddPlayers() throws Exception {
        String teamName = "Team";
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("email1@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
        players.add(new Player("email2@gmail.com", 2, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
        teamOwnerController.createNewTeam(teamName, ownerEmail, players, new ArrayList<Coach>(),new ArrayList<TeamManager>(),new Court("courtName","courtCity"),1000.0);
        teamOwner = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerEmail);
        Assert.assertEquals(teamName, teamOwner.getTeam());
        Team team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerEmail));
        Assert.assertTrue(team.getPlayers().containsKey("email1@gmail.com"));
        Assert.assertTrue(team.getPlayers().containsKey("email2@gmail.com"));
    }

    @Test
    public void testsCreateNewTeamTeamOwnerAddCoaches() throws Exception {
        String teamName = "Team";
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);        ArrayList<Coach> coaches = new ArrayList<>();
        coaches.add(new Coach("email1@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        coaches.add(new Coach("email2@gmail.com", 2, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamOwnerController.createNewTeam(teamName, ownerEmail, new ArrayList<Player>(), coaches,new ArrayList<TeamManager>(),new Court("courtName","courtCity"),1000.0);
        teamOwner = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerEmail);
        Assert.assertEquals(teamName, teamOwner.getTeam());
        Team team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerEmail));
        Assert.assertTrue(team.getCoaches().containsKey("email1@gmail.com"));
        Assert.assertTrue(team.getCoaches().containsKey("email2@gmail.com"));
    }

    @Test
    public void testsCreateNewTeamTeamOwnerAddTeamManagers() throws Exception {
        String teamName = "Team";
        String ownerMail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerMail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        ArrayList<TeamManager> teamManagers = new ArrayList<>();
        teamManagers.add(new TeamManager("email1@gmail.com", 1, "first", "last","owner@gmail.com"));
        teamManagers.add(new TeamManager("email2@gmail.com", 2, "first", "last","owner@gmail.com"));

        teamOwnerController.createNewTeam(teamName, ownerMail,new ArrayList<Player>() , new ArrayList<Coach>(),teamManagers,new Court("courtName","courtCity"),1000.0);

        TeamOwner teamOwnerFromDb = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerMail);
        Assert.assertEquals(teamName, teamOwnerFromDb.getTeam());
        Team team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getTeamManagers().containsKey("email1@gmail.com"));
        Assert.assertTrue(team.getTeamManagers().containsKey("email2@gmail.com"));
        Assert.assertEquals(ownerMail, TeamManagerDbInMemory.getInstance().getTeamManager("email1@gmail.com").getOwnedByEmail());
        Assert.assertEquals(ownerMail, TeamManagerDbInMemory.getInstance().getTeamManager("email2@gmail.com").getOwnedByEmail());
    }

    @Test
    public void testsCreateNewTeamTeamOwnerAddCourt() throws Exception {
        String teamName = "Team";
        Court court = new Court("courtName", "courtCity");
        CourtDbInMemory.getInstance().insertCourt(court);

        String ownerMail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerMail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);


        teamOwnerController.createNewTeam(teamName, ownerMail,new ArrayList<Player>() , new ArrayList<Coach>(), new ArrayList<TeamManager>(),court,1000.0);
        TeamOwner teamOwnerFromDb = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerMail);
        Assert.assertEquals(teamName, teamOwnerFromDb.getTeam());
        Team team = court.getTeam(teamName);
        Assert.assertEquals("courtName",team.getCourt().getCourtName());
        Assert.assertEquals("courtCity",team.getCourt().getCourtCity());
    }

    @Test
    public void testsCreateNewTeamTeamOwnerAddAllAssets() throws Exception {
        String teamName = "Team";
        String ownerMail = "owner@gmail.com";
        Court court = new Court("courtName", "courtCity");
        CourtDbInMemory.getInstance().insertCourt(court);

        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerMail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);


        ArrayList<TeamManager> teamManagers = new ArrayList<>();
        teamManagers.add(new TeamManager("email1@gmail.com", 2, "first", "last","owner@gmail.com"));
        teamManagers.add(new TeamManager("email2@gmail.com", 3, "first", "last","owner@gmail.com"));
        ArrayList<Coach> coaches = new ArrayList<>();
        coaches.add(new Coach("email3@gmail.com", 4, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        coaches.add(new Coach("email4@gmail.com", 5, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("email5@gmail.com", 6, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
        players.add(new Player("email6@gmail.com", 7, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));


        teamOwnerController.createNewTeam(teamName, ownerMail,players ,coaches, teamManagers,court,1000.0);
        teamOwner = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerMail);

        Assert.assertEquals(teamName, teamOwner.getTeam());
        Team team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getPlayers().containsKey("email5@gmail.com"));
        Assert.assertTrue(team.getPlayers().containsKey("email6@gmail.com"));
        Assert.assertEquals(teamName, teamOwner.getTeam());
        team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getCoaches().containsKey("email3@gmail.com"));
        Assert.assertTrue(team.getCoaches().containsKey("email4@gmail.com"));
        Assert.assertEquals(teamName, teamOwner.getTeam());
        team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getTeamManagers().containsKey("email1@gmail.com"));
        Assert.assertTrue(team.getTeamManagers().containsKey("email2@gmail.com"));
        Assert.assertEquals(ownerMail, TeamManagerDbInMemory.getInstance().getTeamManager("email1@gmail.com").getOwnedByEmail());
        Assert.assertEquals(ownerMail, TeamManagerDbInMemory.getInstance().getTeamManager("email2@gmail.com").getOwnedByEmail());

        Assert.assertEquals(teamName, teamOwner.getTeam());
        team = court.getTeam(teamName);
        Assert.assertEquals("courtName",team.getCourt().getCourtName());
        Assert.assertEquals("courtCity",team.getCourt().getCourtCity());

        Assert.assertEquals(teamName,team.getTeamPage().getPageID());
    }

//////////////////////////////// updateCoach ////////////////////////////////////////

    @Test
    public void testUpdateCoachInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            Date birthDate = new Date();
            teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com" ,"1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.updateCoachDetails(teamName,"owner@gmail.com", "email@gmail.com", null, "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testUpdateCoachExistsCoachChangeDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);Date birthDate = new Date();
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        teamOwnerController.updateCoachDetails(teamName,"owner@gmail.com", "email@gmail.com", "changePlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
    Team team = teamDb.getTeam(teamName);Map<String, Coach> coaches = team.getCoaches();
        Coach coach = coaches.get("email@gmail.com");
        Assert.assertEquals("changePlayer", coach.getFirstName());
    }
    @Test
    public void testUpdateCoachOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);

        try {
            teamOwnerController.updateCoachDetails(teamName,withoutPermissionsOwnerEmail, "email@gmail.com", "changePlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);

            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testUpdateCoachNotExistsChangeDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        try {
            teamOwnerController.updateCoachDetails(teamName,ownerEmail, "NotExists@gmail.com", "changePlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Coach not associated with teamOwner's team", e.getMessage());
        }
    }

    @Test
    public void testUpdateCoachTeamOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);Date birthDate = new Date();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        try {
            teamOwnerController.updateCoachDetails(teamName, "emailNotExists@gmail.com","email@gmail.com", "changePlayer", "lastPlayer",  CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw NotFoundException");
        } catch (NotFoundException e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }

    @Test
    public void testUpdateCoachTeamOwnerExistsWithDifferentTeam() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().insertRole(otherOwnerEmail,"Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(otherTeamOwner);

        teamOwnerController.addCoach("Other", otherOwnerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        try {
            teamOwnerController.updateCoachDetails(teamName,ownerEmail, "email@gmail.com", "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Coach not associated with teamOwner's team", e.getMessage());
        }
    }

    ////////////////////////////////// updateCourt //////////////////////////////////////

    @Test
    public void testUpdateCourtInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            Date birthDate = new Date();
            teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com" ,"1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.updateCourtDetails(teamName,"owner@gmail.com", null, "courtCity");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testUpdateCourtExistsCourtChangeDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);Date birthDate = new Date();
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addCourt(teamName, ownerEmail, "courtName","cityName");
        teamOwnerController.updateCourtDetails(teamName,"owner@gmail.com", "courtName", "courtCity");
    Team team = teamDb.getTeam(teamName);Court court = team.getCourt();
        Assert.assertEquals("courtCity", court.getCourtCity());
    }
    @Test
    public void testUpdateCourtOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);

        try {
            teamOwnerController.updateCourtDetails(teamName,withoutPermissionsOwnerEmail, "courtName", "courtCity");

            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testUpdateCourtNotExistsChangeDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        try {
            teamOwnerController.updateCourtDetails(teamName,ownerEmail, "NotExists", "courtCity");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Court not associated with teamOwner's team", e.getMessage());
        }
    }

    @Test
    public void testUpdateCourtTeamOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);Date birthDate = new Date();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addCourt(teamName, ownerEmail, "courtName","cityName");

        try {
            teamOwnerController.updateCourtDetails(teamName,"emailNotExists@gmail.com", "courtName", "courtCity");
            Assert.fail("Should throw NotFoundException");
        } catch (NotFoundException e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }
//////////////////////////////// updateTeamManager ////////////////////////////////////////

    @Test
    public void testUpdateTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
            Date birthDate = new Date();
            teamOwnerDb.insertTeamOwner(new TeamOwner("owner@gmail.com" ,"1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com", RoleType.TEAM_OWNER);
            teamOwnerController.updateTeamManagerDetails(teamName,"owner@gmail.com", "email@gmail.com", null, "lastPlayer",new ArrayList<>());
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void testUpdateTeamManagerExistsTeamManagerChangeDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail, teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        List<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.ADD_FINANCIAL);
        teamOwnerController.addTeamManager(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", new ArrayList<>(),ownerEmail);
        teamManagerDb.getTeamManager("email@gmail.com").setPermissionTypes(permissionTypes);


        List<PermissionType> permissionTypesToChange = new ArrayList<>();
        permissionTypesToChange.add(PermissionType.ADD_FINANCIAL);
        permissionTypesToChange.add(PermissionType.REMOVE_COURT);
        teamOwnerController.updateTeamManagerDetails(teamName, "owner@gmail.com", "email@gmail.com", "changePlayer", "lastPlayer", permissionTypesToChange);
    Team team = teamDb.getTeam(teamName);Map<String, TeamManager> teamManagers = team.getTeamManagers();
        TeamManager teamManager = teamManagers.get("email@gmail.com");
        Assert.assertEquals("changePlayer", teamManager.getFirstName());
        Assert.assertTrue(teamManager.getPermissionTypes().contains(PermissionType.ADD_FINANCIAL));
        Assert.assertTrue(teamManager.getPermissionTypes().contains(PermissionType.REMOVE_COURT));
    }

    @Test
    public void testUpdateTeamManagerOwnerWithoutPermissions() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String withoutPermissionsOwnerEmail = "withoutPermissions@gmail.com";
        Player withoutPermissionsOwner = new Player(withoutPermissionsOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(withoutPermissionsOwner);
        RoleDbInMemory.getInstance().insertRole(withoutPermissionsOwnerEmail,teamName, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(withoutPermissionsOwner);

        try {
            teamOwnerController.updateTeamManagerDetails(teamName, withoutPermissionsOwnerEmail, "email@gmail.com", "firstName", "lastName", new ArrayList<>());

            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This user hasn't Permissions for this operation", e.getMessage());
        }
    }

    @Test
    public void testUpdateTeamManagerNotExistsChangeDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerController.addTeamManager(teamName,"email@gmail.com", 1, "firstPlayer", "lastPlayer",new ArrayList<>(),ownerEmail);
        try {
            teamOwnerController.updateTeamManagerDetails(teamName, ownerEmail, "NotExists@gmail.com", "firstName", "lastName", new ArrayList<>());

            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("TeamManager not associated with teamOwner's team", e.getMessage());
        }
    }

    @Test
    public void testUpdatTeamManagerTeamOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);Date birthDate = new Date();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        teamOwnerController.addTeamManager(teamName,"email@gmail.com", 1, "firstPlayer", "lastPlayer",new ArrayList<>(),ownerEmail);
        try {
            teamOwnerController.updateTeamManagerDetails(teamName, "emailNotExists@gmail.com", "email@gmail.com", "firstName", "lastName", new ArrayList<>());
        } catch (NotFoundException e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("subscriber not found", e.getMessage());
        }
    }

    @Test
    public void testUpdateTeamManagerTeamOwnerExistsWithDifferentTeam() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        teamDb.insertTeam("Other",0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", "Other");
        teamOwnerDb.insertTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().insertRole(otherOwnerEmail,"Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(otherTeamOwner);

        teamOwnerController.addTeamManager("Other","email@gmail.com", 1, "firstPlayer", "lastPlayer",new ArrayList<>(),otherOwnerEmail);
        try {
            teamOwnerController.updateTeamManagerDetails(teamName, ownerEmail, "email@gmail.com", "firstName", "lastName", new ArrayList<>());

            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("TeamManager not associated with teamOwner's team", e.getMessage());
        }
    }

}

