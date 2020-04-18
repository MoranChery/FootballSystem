import Controller.*;
import Data.*;
import Model.Court;
import Model.Enums.*;
import Model.FinancialActivity;
import Model.Team;
import Model.UsersTypes.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class TeamControllerTest {
    private TeamController teamController = new TeamController();
    private PlayerController playerController = new PlayerController();
    private TeamManagerController teamManagerController = new TeamManagerController();
    private TeamOwnerController teamOwnerController = new TeamOwnerController();
    private CoachController coachController = new CoachController();

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
        for (Db db : dbs) {
            db.deleteAll();
        }
    }

    private void createTeamOwnerSubscriberAndRole(){

    }
    @Test
    public void createTeamNull() throws Exception {
        try {
            teamController.createTeam(null);
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void createTeamLegal() throws Exception {
        teamController.createTeam("Team");
        Team team = TeamDbInMemory.getInstance().getTeam("Team");
        Assert.assertEquals("Team",team.getTeamName());
    }

    @Test
    public void getTeamNull() throws Exception {
        try {
            teamController.getTeam(null);
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }
    @Test
    public void getTeamLegal() throws Exception {
        teamController.createTeam("Team");
        Team team = teamController.getTeam("Team");
        Assert.assertEquals("Team",team.getTeamName());
    }
//////////////////////////////////// addPlayer /////////////////////////////////
    @Test
    public void testAddPlayerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.addPlayer(teamName,null, "email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.addPlayer("notExists", "owner@gmail.com","email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
        try {
            teamController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Date birthDate = new Date();

        String playerToAdd = "email@gmail.com";
        teamController.addPlayer(teamName, ownerEmail,playerToAdd, 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
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
    public void testAddPlayerExistsPlayerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Player player = new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
//        SubscriberDbInMemory.getInstance().createSubscriber(player);
        playerController.createPlayer(player);
        teamController.addPlayer(teamName, "owner@gmail.com","email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.addPlayer(teamName, "owner@gmail.com","email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Player associated with a team", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerExistsPlayerIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);  playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        try {
            teamController.addPlayer(teamName,ownerEmail, "email@gmail.com", 1, "firstPlayerOther", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("One or more of the details incorrect", e.getMessage());
        }
    }


    @Test
    public void testAddPlayerExistsPlayerAsDifferentSubscriber() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner); Coach coach = new Coach("email@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        SubscriberDbInMemory.getInstance().createSubscriber(coach);
        try {
            teamController.addPlayer(teamName,"owner@gmail.com", "email@gmail.com", 1, "first", "last", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The player to added already has other subscriber type", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        Date birthDate = new Date();
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        Map<String, Player> players = team.getPlayers();
        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey("email@gmail.com"));
        Player player = players.get("email@gmail.com");
        Assert.assertEquals("email@gmail.com", player.getEmailAddress());
        Assert.assertEquals(1, player.getId().intValue());
        Assert.assertEquals("firstPlayer", player.getFirstName());
        Assert.assertEquals("lastPlayer", player.getLastName());
        Assert.assertEquals(birthDate, player.getBirthDate());
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
        Assert.assertEquals(team, player.getTeam());
    }


    //////////////////////////////////// addTeamManager /////////////////////////////////////////////
    @Test
    public void testAddTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.addTeamManager(null, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);

            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

            teamController.addTeamManager("notExists", "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", ownerEmail);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.changeStatus(teamName, ownerEmail,TeamStatus.INACTIVE);
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerNotExistsTeamManager() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String teamManagerToAdd = "email@gmail.com";
        teamController.addTeamManager(teamName, teamManagerToAdd, 1, "firstTeamManager", "lastTeamManager", ownerEmail);
        Team team = teamController.getTeam(teamName);
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
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
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", ownerEmail);

        String other = "Other";
        teamController.createTeam(other);
        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(other));
        teamOwnerController.createTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().createRole(otherOwnerEmail,other,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(otherTeamOwner);

        try {
            teamController.addTeamManager(other, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", otherOwnerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team Manager associated with a team", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistsTeamManagerIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamManagerController.createTeamManager(new TeamManager("email@gmail.com", 1, "firstTeamManager", "lastTeamManager", null));
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 2, "firstTeamManagerOther", "lastTeamManager", ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("One or more of the details incorrect", e.getMessage());
        }
    }


    @Test
    public void testAddTeamManagerExistsTeamManagerIdAssociatedWithOwnedBy() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().createRoleInSystem(otherOwnerEmail,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(otherTeamOwner);

        teamManagerController.createTeamManager(new TeamManager("email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email3@gmail.com"));
        RoleDbInMemory.getInstance().createRoleInSystem("email@gmail.com",RoleType.TEAM_MANAGER);
        TeamManagerDbInMemory.getInstance().getTeamManager("email@gmail.com").setOwnedByEmail(otherOwnerEmail);
//        teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", otherOwnerEmail);

        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", ownerEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team Manager owned by another teamOwner", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistsTeamManager() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamManagerController.createTeamManager(new TeamManager("email@gmail.com", 1, "firstTeamManager", "lastTeamManager", null));

        teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", ownerEmail);
        Team team = teamController.getTeam(teamName);
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
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
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        Coach coach = new Coach("email@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        SubscriberDbInMemory.getInstance().createSubscriber(coach);
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "first", "last", ownerEmail);
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
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.addCoach(null, "owner@gmail.com","email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddCoachTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
            teamController.addCoach("notExists",ownerEmail, "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddCoachTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.changeStatus(teamName, ownerEmail,TeamStatus.INACTIVE);
        try {
            teamController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddCoachNotExistsCoach() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String coachToAdd = "email@gmail.com";
        teamController.addCoach(teamName, ownerEmail,coachToAdd, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Team team = teamController.getTeam(teamName);
        Map<String, Coach> coaches = team.getCoaches();
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
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        coachController.createCoach(new Coach("email2@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        try {
            teamController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Coach associated with a team", e.getMessage());
        }
    }

    @Test
    public void testAddCoachExistsCoachIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        coachController.createCoach(new Coach("email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        try {
            teamController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoachOther", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("One or more of the details incorrect", e.getMessage());
        }
    }

    @Test
    public void testAddCoachExistsCoach() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        coachController.createCoach(new Coach("email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Team team = teamController.getTeam(teamName);
        Map<String, Coach> coaches = team.getCoaches();
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
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        Player player = new Player("email@gmail.com", 1, "first", "last", birthDate, PlayerRole.GOALKEEPER);
        SubscriberDbInMemory.getInstance().createSubscriber(player);
        try {
            teamController.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The coach to added already has other subscriber type - you can to appoint him to team manager", e.getMessage());
        }
    }

    /////////////////////////// addCourt ///////////////////////////////
    @Test
    public void testAddCourtInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.addCourt(teamName, "owner@gmail.com",null, "courtCity");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddCourtTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
            teamController.addCourt("notExists", ownerEmail,"courtName", "courtCity");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddCourtTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.changeStatus(teamName, ownerEmail,TeamStatus.INACTIVE);
        try {
            teamController.addCourt(teamName, ownerEmail,"courtName", "courtCity");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddCourtExistsCourtAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));
        teamController.addCourt(teamName, "owner@gmail.com","courtName", "courtCity");
        try {
            teamController.addCourt(teamName, "owner@gmail.com","courtName", "courtCity");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("team already associated with court", e.getMessage());
        }
    }

    @Test
    public void testAddCourtNotExistsCourt() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamController.addCourt(teamName, ownerEmail,"courtName", "courtCity");
        Team team = teamController.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals("courtName", court.getCourtName());
        Assert.assertEquals("courtCity", court.getCourtCity());
        Assert.assertEquals(team, court.getTeam(teamName));
    }

    @Test
    public void testAddCourtIncorrectCityName() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));
        try {
            teamController.addCourt(teamName, ownerEmail,"courtName", "courtCityOther");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The court name isn't match to the city", e.getMessage());
        }
    }

    @Test
    public void testAddCourtExistsCourt() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));
        teamController.addCourt(teamName, ownerEmail,"courtName", "courtCity");
        Team team = teamController.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals("courtName", court.getCourtName());
        Assert.assertEquals("courtCity", court.getCourtCity());
        Assert.assertEquals(team, court.getTeam(teamName));
    }

////////////////////////////////// removePlayer /////////////////////////////////////

    @Test
    public void testRemovePlayerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.removePlayer(teamName, null,"email@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.removePlayer("notExists", "owner@gmail.com","email@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.changeStatus(teamName, ownerEmail,TeamStatus.INACTIVE);
        try {
            teamController.removePlayer(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.removePlayer(teamName, "owner@gmail.com","email@gmail.com");
        } catch (NotFoundException e) {
            Assert.assertEquals("Player not found", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerExistsPlayerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.createTeam("Other");

        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other"));
        teamOwnerController.createTeamOwner(otherOwner);
        RoleDbInMemory.getInstance().createRole(otherOwnerEmail,"Other",RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(otherOwner);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        Date birthDate = new Date();
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer("Other", otherOwnerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.removePlayer(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Player is not part with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner); playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        try {
            teamController.removePlayer(teamName, "owner@gmail.com","email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Player is not part with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        RoleDbInMemory teamRoleDbInMemory = RoleDbInMemory.getInstance();
        Map<String, Player> players = team.getPlayers();
        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());

        teamController.removePlayer(teamName, ownerEmail,"email@gmail.com");

        Assert.assertEquals(0, players.size());//players in team
        Assert.assertFalse(players.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());
        Assert.assertNull(RoleDbInMemory.getInstance().getRole("email@gmail.com").getTeamName());
    }


    ////////////////////////////////// removeTeamManager /////////////////////////////////////

    @Test
    public void testRemoveTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.removeTeamManager(teamName, null,"email@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.removePlayer("notExists", "owner@gmail.com","email@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.changeStatus(teamName,"owner@gmail.com", TeamStatus.INACTIVE);
        try {
            teamController.removeTeamManager(teamName, "owner@gmail.com","email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.removeTeamManager(teamName, "owner@gmail.com","email@gmail.com");
        } catch (NotFoundException e) {
            Assert.assertEquals("Team Manager not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerExistsTeamManagerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        teamController.createTeam("Other");

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other"));
        teamOwnerController.createTeamOwner(otherOwner);
        RoleDbInMemory.getInstance().createRole(otherOwnerEmail,"Other",RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(otherOwner);

        teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", ownerEmail);

        try {
            teamController.removeTeamManager("Other", otherOwnerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamManager is not part of the team", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamManagerController.createTeamManager(new TeamManager("email@gmail.com", 1, "firstPlayer", "lastPlayer", null));
        try {
            teamController.removeTeamManager(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamManager is not part of the team", e.getMessage());
        }
    }

    @Test
    public void testRemoveTeamManagerExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", ownerEmail);
        Team team = teamController.getTeam(teamName);
        RoleDbInMemory teamRoleDbInMemory = RoleDbInMemory.getInstance();
        List<Role> roles = teamRoleDbInMemory.getRoles("email@gmail.com");
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.TEAM_MANAGER,RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());
        teamController.removeTeamManager(teamName, ownerEmail,"email@gmail.com");
        Assert.assertEquals(0, teamManagers.size());
        Assert.assertFalse(team.getTeamManagers().containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.TEAM_MANAGER,RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());
        Assert.assertNull(RoleDbInMemory.getInstance().getRole("email@gmail.com").getTeamName());
    }

    ////////////////////////////////// removeCoach /////////////////////////////////////////
    @Test
    public void testRemoveCoachInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

            teamController.removeCoach(teamName,null, "email@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            String ownerMail = "owner@gmail.com";
            teamOwnerController.createTeamOwner(new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.removeCoach("notExists",ownerMail, "email@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
        try {
            teamController.removeCoach(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachNotExistsCoach() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.removeCoach(teamName, "owner@gmail.com","email@gmail.com");
        } catch (NotFoundException e) {
            Assert.assertEquals("Coach not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        coachController.createCoach(new Coach("email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        try {
            teamController.removeCoach(teamName, ownerEmail,"email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Coach is not part with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        String coachToRemove = "email@gmail.com";
        coachController.createCoach(new Coach(coachToRemove, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamController.addCoach(teamName,ownerEmail, coachToRemove, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Team team = teamController.getTeam(teamName);
        RoleDbInMemory teamRoleDbInMemory = RoleDbInMemory.getInstance();
        List<Role> roles = teamRoleDbInMemory.getRoles(coachToRemove);
        Map<String, Coach> coaches = team.getCoaches();

        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey(coachToRemove));
        Assert.assertEquals(RoleType.COACH,RoleDbInMemory.getInstance().getRole(coachToRemove).getRoleType());

        teamController.removeCoach(teamName, ownerEmail,coachToRemove);

        Assert.assertEquals(0, coaches.size());
        Assert.assertFalse(coaches.containsKey(coachToRemove));
        Assert.assertEquals(RoleType.COACH,RoleDbInMemory.getInstance().getRole(coachToRemove).getRoleType());
        Assert.assertNull(RoleDbInMemory.getInstance().getRole(coachToRemove).getTeamName());
    }

    ////////////////////////////////// removeCourt /////////////////////////////////////////
    @Test
    public void testRemoveCourtInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.removeCourt(teamName,null, "courtName");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);

            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

            teamController.removeCourt("notExists", ownerEmail, "courtName");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);teamController.changeStatus(teamName,"owner@gmail.com", TeamStatus.INACTIVE);
        try {
            teamController.removeCourt(teamName, ownerEmail,"courtName");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtNotExistsCourt() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.removeCourt(teamName, ownerEmail,"courtName");
        } catch (NotFoundException e) {
            Assert.assertEquals("Court not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));

        try {
            teamController.removeCourt(teamName, ownerEmail,"courtName");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Court is not part of the with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));
        teamController.addCourt(teamName, ownerEmail,"courtName", "courtCity");
        Team team = teamController.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals(team, court.getTeam(teamName));
        HashMap<String, Team> teams = court.getTeams();
        Assert.assertEquals(1, teams.size());
        Assert.assertTrue(teams.containsKey(teamName));
        teamController.removeCourt(teamName, ownerEmail,"courtName");
        Assert.assertEquals(0, teams.size());
        Assert.assertFalse(teams.containsKey(court.getCourtName()));
        Assert.assertNull(teamController.getTeam(teamName).getCourt());
    }


    ////////////////////////////////// addFinancialActivity //////////////////////////////////////////
    @Test
    public void testAddFinancialActivityInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.addFinancialActivity(null, "owner@gmail.com",1000.0, "Description", FinancialActivityType.OUTCOME);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddFinancialActivityTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);

            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

            teamController.addFinancialActivity("NotExists", "owner@gmail.com",1000.0, "Description", FinancialActivityType.OUTCOME);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testAddFinancialActivityWithOutcomeExceedsBudget() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Team team = teamController.getTeam(teamName);
        team.setBudget(800.0);
        try {
            teamController.addFinancialActivity(teamName, "owner@gmail.com",1000.0, "Description", FinancialActivityType.OUTCOME);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The financial outcome exceeds from the budget", e.getMessage());
        }
        Assert.assertEquals(800.0, team.getBudget(), 0);
    }

    @Test
    public void testAddFinancialActivityWithOutcomeUnderBudget() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Team team = teamController.getTeam(teamName);
        team.setBudget(1001.0);
        teamController.addFinancialActivity(teamName, ownerEmail,1000.0, "Description", FinancialActivityType.OUTCOME);
        Assert.assertEquals(1.0, team.getBudget(), 0);
    }

    @Test
    public void testAddFinancialActivityWithOutcomeEqualBudget() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Team team = teamController.getTeam(teamName);
        team.setBudget(1000.0);

        teamController.addFinancialActivity(teamName,ownerEmail, 1000.0, "Description", FinancialActivityType.OUTCOME);
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
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Team team = teamController.getTeam(teamName);
        team.setBudget(1000.0);
        teamController.addFinancialActivity(teamName, ownerEmail,1000.0, "Description", FinancialActivityType.INCOME);
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
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        Team team = teamController.getTeam(teamName);
        team.setBudget(800.0);
        teamController.addFinancialActivity(teamName, ownerEmail,1000.0, "Description", FinancialActivityType.INCOME);
        Assert.assertEquals(1800.0, team.getBudget(), 0);
    }

    //////////////////////////////////////////// changeStatus /////////////////////////////////////////////////////

    @Test
    public void testChangeStatusInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com" ,"1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            RoleDbInMemory.getInstance().createRoleInSystem("owner@gmail.com",RoleType.TEAM_OWNER);
            teamController.changeStatus(null,"owner@gmail.com", TeamStatus.ACTIVE);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testChangeStatusTeamNotFound() {
        try {
            String teamName = "Exists";

            teamController.createTeam(teamName);
            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

            teamController.changeStatus("NotExists", ownerEmail,TeamStatus.ACTIVE);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testChangeStatusFromActiveToInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Team team = teamController.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        teamController.changeStatus(teamName,"owner@gmail.com", TeamStatus.INACTIVE);
        Assert.assertEquals(TeamStatus.INACTIVE, team.getTeamStatus());
    }

    @Test
    public void testChangeStatusFromInactiveToActive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String teamOwnerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(teamOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(teamOwnerEmail,teamName,RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Team team = teamController.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        teamController.changeStatus(teamName, teamOwnerEmail,TeamStatus.INACTIVE);
        Assert.assertEquals(TeamStatus.INACTIVE, team.getTeamStatus());
    }

    @Test
    public void testChangeStatusFromActiveToActive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Team team = teamController.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        try {
            teamController.changeStatus(teamName,"owner@gmail.com", TeamStatus.ACTIVE);
        }catch (Exception e){
            Assert.assertEquals("The team already ACTIVE",e.getMessage());
        }
    }
    @Test
    public void testChangeStatusFromInctiveToInctive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        Team team = teamController.getTeam(teamName);
        team.setTeamStatus(TeamStatus.INACTIVE);
        try {
            teamController.changeStatus(teamName, "owner@gmail.com",TeamStatus.INACTIVE);
        }catch (Exception e){
            Assert.assertEquals("The team already INACTIVE",e.getMessage());
        }
    }



    ////////////////////////////////////////////// updatePlayer ///////////////////////////////////////////
    @Test
    public void testUpdatePlayerExistsPlayerChangeDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Date birthDate = new Date();
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        teamController.updatePlayerDetails(teamName,ownerEmail, "email@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        Map<String, Player> players = team.getPlayers();
        Player player = players.get("email@gmail.com");
        Assert.assertEquals("changePlayer", player.getFirstName());
    }

    @Test
    public void testUpdatePlayerNotExistsChangeDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Date birthDate = new Date();
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.updatePlayerDetails(teamName,ownerEmail, "emailNotExists@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Player not associated with teamOwner's team", e.getMessage());
        }
    }

    @Test
    public void testUpdatePlayerTeamOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Date birthDate = new Date();

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.updatePlayerDetails(teamName,ownerEmail, "emailNotExists@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        } catch (NotFoundException e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testUpdatePlayerTeamOwnerExistsWithDifferentTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.createTeam("Other");

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String otherOwnerEmail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other"));
        teamOwnerController.createTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().createRole(otherOwnerEmail,"Other",RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(otherTeamOwner);

        Date birthDate = new Date();
        teamController.addPlayer("Other",otherOwnerEmail, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.updatePlayerDetails(teamName,ownerEmail, "email@gmail.com", "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
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
            teamController.subscriptionTeamOwner("Team", null, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerTeamNotFound() {
        try {
            teamController.subscriptionTeamOwner("notExists", "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
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
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.changeStatus(teamName, ownerEmail,TeamStatus.INACTIVE);
        try {
            teamController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerOwnerWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.createTeam("Other");

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other"));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,"Other",RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        try {
            teamController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team doesn't match", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerSubscriberNotExist() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        try {
            teamController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Subscriber not found", e.getMessage());
        }
    }


    @Test
    public void testSubscriptionTeamOwnerTeamRoleWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.createTeam("Other");
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Player player = new Player("teamOwnerToAdd@gmail.com", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(player);
        SubscriberDbInMemory.getInstance().createSubscriber(player);
        RoleDbInMemory.getInstance().createRole("teamOwnerToAdd@gmail.com", "Other", RoleType.PLAYER);
        try {
            teamController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");

            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("OwnerToAdd already associated with other team", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerTeamRoleTeamOwnerExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        TeamOwner teamOwnerToAdd = new TeamOwner("teamOwnerToAdd@gmail.com", "1234", 3, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwnerToAdd);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwnerToAdd);
        RoleDbInMemory.getInstance().createRole("teamOwnerToAdd@gmail.com", teamName, RoleType.TEAM_OWNER);

        try {
            teamController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber already teamOwner", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerLegalSubscription() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String ownerToAdd = "teamOwnerToAdd@gmail.com";
        teamController.addPlayer(teamName,ownerEmail,ownerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);

        teamController.subscriptionTeamOwner(teamName,ownerEmail,ownerToAdd);

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
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        Player player = new Player("teamOwnerToAdd@gmail.com", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(player);
        RoleDbInMemory.getInstance().createRole("teamOwnerToAdd@gmail.com", null, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().createSubscriber(player);

        teamController.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");

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
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            teamController.removeSubscriptionTeamOwner(null, "email@gmail.com", "emailToRemove@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            teamController.removeSubscriptionTeamOwner("notExists", "email@gmail.com","emailToRemove@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.changeStatus(teamName, ownerEmail,TeamStatus.INACTIVE);
        try {
            teamController.removeSubscriptionTeamOwner(teamName, ownerEmail,"emailToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.removeSubscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }
    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.createTeam("Other");
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other"));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.removeSubscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner's team does't match", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveNotExist() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.removeSubscriptionTeamOwner(teamName,  ownerEmail,"teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveAssociatedWithOtherTeamOwner() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String differentOwnerEmail = "differentTeamOwner@gmail.com";
        TeamOwner differentOwner = new TeamOwner(differentOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(differentOwner);
        RoleDbInMemory.getInstance().createRole(differentOwnerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(differentOwner);


        Player teamOwnerToAdd = new Player("teamOwnerToAdd@gmail.com",  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(teamOwnerToAdd);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwnerToAdd);

        teamController.subscriptionTeamOwner(teamName,differentOwnerEmail,"teamOwnerToAdd@gmail.com");

        try {
            teamController.removeSubscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwnerToRemove owned by another teamOwner", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.createTeam("Other");

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail, teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        TeamOwner teamOwnerDifferent = new TeamOwner("teamOwnerDifferent@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other"));
        teamOwnerController.createTeamOwner(teamOwnerDifferent);
        RoleDbInMemory.getInstance().createRole("teamOwnerDifferent@gmail.com", "Other", RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwnerDifferent);

        teamController.addPlayer("Other","teamOwnerDifferent@gmail.com","teamOwnerToAdd@gmail.com",  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        teamController.subscriptionTeamOwner("Other","teamOwnerDifferent@gmail.com","teamOwnerToAdd@gmail.com");
        try {
            teamController.removeSubscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwnerToRemove associated with other team", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveWithOthersTeamOwnerOwnedByTeamOwnerToRemoveTwoLevels() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String teamOwnerMail = "owner@gmail.com";
        String ownerToAdd = "teamOwnerToAdd@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(teamOwnerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamController.addPlayer(teamName,teamOwnerMail,ownerToAdd,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamController.subscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        List<String> teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(teamOwnerMail);
        Assert.assertEquals(ownerToAdd, teamOwnerAllTeamOwnersOwnedBy.get(0));


        String ownerToAddUnder = "teamOwnerToAddUnder@gmail.com";
        teamController.addPlayer(teamName,ownerToAdd,ownerToAddUnder,  5, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamController.subscriptionTeamOwner(teamName, ownerToAdd, ownerToAddUnder);

        List<String> teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(ownerToAddUnder, teamOwnerToAddAllTeamOwnersOwnedBy.get(0));

        //delete ownerToAdd should to remove ownerToAddUnder//
        teamController.removeSubscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(teamOwnerMail);
        teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);

        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole(ownerToAdd).getRoleType());
        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole(ownerToAddUnder).getRoleType());
        Assert.assertEquals(0, teamOwnerAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(0, teamOwnerToAddAllTeamOwnersOwnedBy.size());
    }

    @Test
    public void testRemoveSubscriptionTeamOwnerOwnerToRemoveWithOthersTeamOwnerOwnedByTeamOwnerToRemoveThreeLevels() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String teamOwnerMail = "owner@gmail.com";
        String ownerToAdd = "teamOwnerToAdd@gmail.com";
        String ownerToAddUnder = "teamOwnerToAddUnder@gmail.com";
        String ownerToAddUnderSecond = "teamOwnerToAddUnderSecond@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(teamOwnerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamController.addPlayer(teamName,teamOwnerMail,ownerToAdd,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamController.subscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        List<String> teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(teamOwnerMail);
        Assert.assertEquals(ownerToAdd, teamOwnerAllTeamOwnersOwnedBy.get(0));


        teamController.addPlayer(teamName,ownerToAdd,ownerToAddUnder,  5, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamController.subscriptionTeamOwner(teamName, ownerToAdd, ownerToAddUnder);

        List<String> teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(ownerToAddUnder, teamOwnerToAddAllTeamOwnersOwnedBy.get(0));

        teamController.addPlayer(teamName,ownerToAddUnder,ownerToAddUnderSecond,  6, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamController.subscriptionTeamOwner(teamName, ownerToAddUnder, ownerToAddUnderSecond);

        List<String> teamOwnerToAddUnderAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAddUnder);
        Assert.assertEquals(ownerToAddUnderSecond, teamOwnerToAddUnderAllTeamOwnersOwnedBy.get(0));

        //delete ownerToAdd should to remove ownerToAddUnder//
        teamController.removeSubscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

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
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String ownerToAdd = "teamOwnerToAdd@gmail.com";
        teamController.addPlayer(teamName,ownerEmail,ownerToAdd,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamController.subscriptionTeamOwner(teamName, ownerEmail, ownerToAdd);

        List<String> teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerEmail);
        Assert.assertEquals(ownerToAdd, teamOwnerAllTeamOwnersOwnedBy.get(0));

        String ownerToAddUnder = "teamOwnerToAddUnder@gmail.com";
        teamController.addPlayer(teamName,ownerToAdd,ownerToAddUnder,  5, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamController.subscriptionTeamOwner(teamName, ownerToAdd, ownerToAddUnder);

        List<String> teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(ownerToAddUnder, teamOwnerToAddAllTeamOwnersOwnedBy.get(0));

        String  teamManagerToAdd = "teamManagerToAddUnder@gmail.com";
        teamController.addPlayer(teamName,ownerToAdd,teamManagerToAdd,  5, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamController.subscriptionTeamManager(teamName,ownerToAdd,teamManagerToAdd,new ArrayList<>());

        String ownedByEmail = TeamManagerDbInMemory.getInstance().getTeamManager(teamManagerToAdd).getOwnedByEmail();
        Assert.assertEquals(ownerToAdd,ownedByEmail);

        //delete ownerToAdd should to remove ownerToAddUnder//
        teamController.removeSubscriptionTeamOwner(teamName, ownerEmail, ownerToAdd);

        teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerEmail);
        teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole(teamManagerToAdd).getRoleType());
        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole(ownerToAdd).getRoleType());
        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole(ownerToAddUnder).getRoleType());
        Assert.assertEquals(0, teamOwnerAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(0, teamOwnerToAddAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(1,RoleDbInMemory.getInstance().getRoles(ownerEmail).size());
        Assert.assertEquals(1,RoleDbInMemory.getInstance().getRoles(ownerToAdd).size());
        Assert.assertEquals(1,RoleDbInMemory.getInstance().getRoles(teamManagerToAdd).size());
    }

    ////////////////////// removeSubscriptionTeamManager ////////////////////////////////
    @Test
    public void testRemoveSubscriptionTeamManagerInvalidInputs() {
        try {

            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            teamController.removeSubscriptionTeamManager(null, "email@gmail.com", "emailToRemove@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));

            teamController.removeSubscriptionTeamManager("notExists", "email@gmail.com","emailToRemove@gmail.com");
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
        try {
            teamController.removeSubscriptionTeamManager(teamName, ownerEmail,"emailToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.removeSubscriptionTeamManager(teamName, ownerEmail, "managerToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }
    @Test
    public void testRemoveSubscriptionTeamManagerOwnerWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.createTeam("Other");
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other"));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner); try {
            teamController.removeSubscriptionTeamManager(teamName, "owner@gmail.com", "managerToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner's team doesn't match", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerManagerToRemoveNotExist() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.removeSubscriptionTeamManager(teamName, ownerEmail, "managerToRemove@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team Manager not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerManagerToRemoveAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String otherTeamName = "Other";
        teamController.createTeam(otherTeamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);


        String ownerEmailDifferent = "teamOwnerDifferent@gmail.com";
        TeamOwner teamOwnerEmailDifferent = new TeamOwner(ownerEmailDifferent, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(otherTeamName));
        teamOwnerController.createTeamOwner(teamOwnerEmailDifferent);
        RoleDbInMemory.getInstance().createRole(ownerEmailDifferent,otherTeamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwnerEmailDifferent);

        String managerToRemoveEmail = "managerToRemove@gmail.com";
        teamController.addPlayer(otherTeamName, ownerEmailDifferent, managerToRemoveEmail,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        teamController.subscriptionTeamManager(otherTeamName,ownerEmailDifferent, managerToRemoveEmail,new ArrayList<>());

        try {
            teamController.removeSubscriptionTeamManager(teamName, ownerEmail, managerToRemoveEmail);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamManagerToRemove associated with other team", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerManagerToRemoveAssociatedWithOtherTeamOwner() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String teamOwnerMail = "owner@gmail.com";
        String teamManagerToRemove = "teamManagerToRemove@gmail.com";


        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(teamOwnerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        TeamOwner differentTeamOwner = new TeamOwner("differentTeamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(differentTeamOwner);
        RoleDbInMemory.getInstance().createRole("differentTeamOwner@gmail.com",teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(differentTeamOwner);

        teamController.addPlayer(teamName,"differentTeamOwner@gmail.com",teamManagerToRemove,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        teamController.subscriptionTeamManager(teamName,"differentTeamOwner@gmail.com", teamManagerToRemove,new ArrayList<>());
        try {
            teamController.removeSubscriptionTeamManager(teamName, teamOwnerMail, teamManagerToRemove);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamManagerToRemove owned by another teamOwner", e.getMessage());
        }
    }

    @Test
    public void testRemoveSubscriptionTeamManagerLegalRemove() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String teamOwnerMail = "owner@gmail.com";
        String managerToRemove = "managerToRemove@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(teamOwnerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamController.addPlayer(teamName,teamOwnerMail,managerToRemove,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(1000);
        teamController.subscriptionTeamManager(teamName, teamOwnerMail, managerToRemove,new ArrayList<>());

        TeamManager teamManager = TeamManagerDbInMemory.getInstance().getTeamManager(managerToRemove);
        Assert.assertEquals(teamOwnerMail,teamManager.getOwnedByEmail());
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();
        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDbInMemory.getRole(managerToRemove).getRoleType());
        Assert.assertEquals(2, roleDbInMemory.getRoles(managerToRemove).size());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDbInMemory.getRoles(managerToRemove).get(0).getRoleType());
        Assert.assertEquals(RoleType.PLAYER, roleDbInMemory.getRoles(managerToRemove).get(1).getRoleType());

        //delete managerToRemove should to remove ownerToAddUnder//
        teamController.removeSubscriptionTeamManager(teamName, teamOwnerMail, managerToRemove);

        Assert.assertEquals(RoleType.PLAYER, roleDbInMemory.getRole(managerToRemove).getRoleType());
        Assert.assertEquals(1, roleDbInMemory.getRoles(managerToRemove).size());
    }



    //////////////////////////////////// subscriptionTeamManager ///////////////////////////////////////////////////
    @Test
    public void testsSubscriptionTeamManagerInvalidInputs() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("owner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            teamController.subscriptionTeamManager("Exists", "owner@gmail.com",null, new ArrayList<>());
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerTeamNotFound() {
        try {
            String teamName = "Exists";
            teamController.createTeam(teamName);
            teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
            teamController.subscriptionTeamManager("notExists", "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com",new ArrayList<>());
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        teamController.changeStatus(teamName,ownerEmail, TeamStatus.INACTIVE);
        try {
            teamController.subscriptionTeamManager(teamName, ownerEmail, "teamManagerToAdd@gmail.com",new ArrayList<>());
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        Player managerToAdd = new Player("teamManagerToAdd@gmail.com", 3, "firstName", "lastName",new Date(),PlayerRole.GOALKEEPER);
        SubscriberDbInMemory.getInstance().createSubscriber(managerToAdd);
        try {
            teamController.subscriptionTeamManager(teamName,ownerEmail,"teamManagerToAdd@gmail.com" ,new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerOwnerWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        teamController.createTeam("Other");
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other"));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,"Other",RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        try {
            teamController.subscriptionTeamManager(teamName, ownerEmail, "teamManagerToAdd@gmail.com",new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team doesn't match", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerSubscriberNotExist() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        try {
            teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", "TeamManagerToAdd@gmail.com",new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Subscriber not found", e.getMessage());
        }
    }


    @Test
    public void testSubscriptionTeamManagerTeamRoleWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.createTeam("Other");

        String ownerMail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String otherOwnerMail = "otherOwner@gmail.com";
        TeamOwner otherTeamOwner = new TeamOwner(otherOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other"));
        teamOwnerController.createTeamOwner(otherTeamOwner);
        RoleDbInMemory.getInstance().createRole(otherOwnerMail,"Other",RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(otherTeamOwner);

        String teamManagerToAdd = "teamManagerToAdd@gmail.com";
        teamController.addPlayer("Other", otherOwnerMail,teamManagerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        try {
            teamController.subscriptionTeamManager(teamName, ownerMail, teamManagerToAdd,new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("ManagerToAdd already associated with other team", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerTeamRoleTeamOwnerExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "teamOwner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 3, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail, teamName, RoleType.TEAM_OWNER);

        String managerToAdd = "teamManagerToAdd@gmail.com";

        TeamOwner teamManager = new TeamOwner(managerToAdd, "1234", 3, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamManager);
        SubscriberDbInMemory.getInstance().createSubscriber(teamManager);
        RoleDbInMemory.getInstance().createRole(managerToAdd, teamName, RoleType.TEAM_OWNER);

        try {
            teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", managerToAdd,new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber already teamOwner", e.getMessage());
        }
    }
    @Test
    public void testSubscriptionTeamManagerTeamRoleTeamManagerExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String managerToAdd = "teamManagerToAdd@gmail.com";
        teamController.addTeamManager(teamName,managerToAdd,3, "firstTeamOwnerName", "lastTeamOwnerName",ownerEmail);
        try {
            teamController.subscriptionTeamManager(teamName, ownerEmail, managerToAdd,new ArrayList<>());
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber already teamManager", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerLegalSubscription() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String managerToAdd = "teamManagerToAdd@gmail.com";
        teamController.addPlayer(teamName, ownerEmail,managerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        teamController.subscriptionTeamManager(teamName, ownerEmail, managerToAdd, new ArrayList<>());
        List<Role> roles = RoleDbInMemory.getInstance().getRoles(managerToAdd);
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roles.get(1).getRoleType());
        Assert.assertEquals(ownerEmail, TeamManagerDbInMemory.getInstance().getTeamManager(managerToAdd).getOwnedByEmail());
    }

    @Test
    public void testSubscriptionTeamManagerWithoutAssociatedTeamSubscription() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        String managerToAdd = "teamManagerToAdd@gmail.com";
        Player player = new Player(managerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(player);
        RoleDbInMemory.getInstance().createRole(managerToAdd, null, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().createSubscriber(player);

        teamController.subscriptionTeamManager(teamName, ownerEmail, managerToAdd, new ArrayList<>());

        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber(managerToAdd);
        List<Role> roles = RoleDbInMemory.getInstance().getRoles(managerToAdd);
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roles.get(1).getRoleType());
        TeamManager teamManager = TeamManagerDbInMemory.getInstance().getTeamManager(managerToAdd);
        Assert.assertEquals(ownerEmail, teamManager.getOwnedByEmail());
        Assert.assertEquals(teamName, teamManager.getTeam().getTeamName());
    }
/////////////////////// createNewTeam ///////////////
@Test
public void testsCreateNewTeamInvalidInputs() {
    try {
        teamController.createNewTeam("Team", null, new ArrayList<Player>(), new ArrayList<Coach>(), new ArrayList< TeamManager>(), new Court("courtName", "courtCity"));
        Assert.fail("Should throw NullPointerException");
    } catch (Exception e) {
        Assert.assertTrue(e instanceof NullPointerException);
        Assert.assertEquals("bad input", e.getMessage());
    }
}

    @Test
    public void testsCreateNewTeamNotExistsTeamOwnerInDb() {
        try {
            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRoleInSystem(ownerEmail,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
            teamController.createNewTeam("Team", "notExist", new ArrayList<Player>(), new ArrayList<Coach>(), new ArrayList< TeamManager>(), new Court("courtName", "courtCity"));
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Subscriber not found", e.getMessage());
        }
    }

    @Test
    public void testsCreateNewTeamTeamAlreadyExists() {
        try {
            teamController.createTeam("Team");
            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRoleInSystem(ownerEmail,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

            teamController.createNewTeam("Team", "owner@gmail.com", new ArrayList<Player>(), new ArrayList<Coach>(),new ArrayList<TeamManager>(),new Court("courtName","courtCity"));
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertEquals("Team already exist in the system", e.getMessage());
        }
    }

    @Test
    public void testsCreateNewTeamTeamOwnerHasTeam() throws Exception {
        try{
            String teamName = "Team";
            teamController.createTeam(teamName);
            String ownerEmail = "owner@gmail.com";
            TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
            teamOwnerController.createTeamOwner(teamOwner);
            RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
            SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);            teamController.createNewTeam("newTeam", "owner@gmail.com", new ArrayList<Player>(), new ArrayList<Coach>(),new ArrayList<TeamManager>(),new Court("courtName","courtCity"));
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
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        teamController.createNewTeam(teamName, ownerEmail, new ArrayList<Player>(), new ArrayList<Coach>(),new ArrayList<TeamManager>(),new Court("courtName","courtCity"));
        Assert.assertEquals(teamName,TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerEmail).getTeam().getTeamName());
        Assert.assertTrue(TeamDbInMemory.getInstance().getTeam(teamName).getTeamOwners().containsKey(ownerEmail));
    }

    @Test
    public void testsCreateNewTeamTeamOwnerAddPlayers() throws Exception {
        String teamName = "Team";
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("email1@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
        players.add(new Player("email2@gmail.com", 2, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
        teamController.createNewTeam(teamName, ownerEmail, players, new ArrayList<Coach>(),new ArrayList<TeamManager>(),new Court("courtName","courtCity"));
        teamOwner = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerEmail);
        Assert.assertEquals(teamName, teamOwner.getTeam().getTeamName());
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
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);        ArrayList<Coach> coaches = new ArrayList<>();
        coaches.add(new Coach("email1@gmail.com", 1, "first", "last",CoachRole.MAJOR,QualificationCoach.UEFA_A));
        coaches.add(new Coach("email2@gmail.com", 2, "first", "last", CoachRole.MAJOR,QualificationCoach.UEFA_A));
        teamController.createNewTeam(teamName, ownerEmail, new ArrayList<Player>(), coaches,new ArrayList<TeamManager>(),new Court("courtName","courtCity"));
        teamOwner = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerEmail);
        Assert.assertEquals(teamName, teamOwner.getTeam().getTeamName());
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
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);

        ArrayList<TeamManager> teamManagers = new ArrayList<>();
        teamManagers.add(new TeamManager("email1@gmail.com", 1, "first", "last","owner@gmail.com"));
        teamManagers.add(new TeamManager("email2@gmail.com", 2, "first", "last","owner@gmail.com"));

        teamController.createNewTeam(teamName, ownerMail,new ArrayList<Player>() , new ArrayList<Coach>(),teamManagers,new Court("courtName","courtCity"));

        TeamOwner teamOwnerFromDb = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerMail);
        Assert.assertEquals(teamName, teamOwnerFromDb.getTeam().getTeamName());
        Team team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getTeamManagers().containsKey("email1@gmail.com"));
        Assert.assertTrue(team.getTeamManagers().containsKey("email2@gmail.com"));
        Assert.assertEquals(ownerMail,TeamManagerDbInMemory.getInstance().getTeamManager("email1@gmail.com").getOwnedByEmail());
        Assert.assertEquals(ownerMail,TeamManagerDbInMemory.getInstance().getTeamManager("email2@gmail.com").getOwnedByEmail());
    }

    @Test
    public void testsCreateNewTeamTeamOwnerAddCourt() throws Exception {
        String teamName = "Team";
        Court court = new Court("courtName", "courtCity");
        CourtDbInMemory.getInstance().createCourt(court);

        String ownerMail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);


        teamController.createNewTeam(teamName, ownerMail,new ArrayList<Player>() , new ArrayList<Coach>(), new ArrayList<TeamManager>(),court);
        TeamOwner teamOwnerFromDb = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerMail);
        Assert.assertEquals(teamName, teamOwnerFromDb.getTeam().getTeamName());
        Team team = court.getTeam(teamName);
        Assert.assertEquals("courtName",team.getCourt().getCourtName());
        Assert.assertEquals("courtCity",team.getCourt().getCourtCity());
    }

    @Test
    public void testsCreateNewTeamTeamOwnerAddAllAssets() throws Exception {
        String teamName = "Team";
        String ownerMail = "owner@gmail.com";
        Court court = new Court("courtName", "courtCity");
        CourtDbInMemory.getInstance().createCourt(court);

        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerController.createTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().createRole(ownerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);


        ArrayList<TeamManager> teamManagers = new ArrayList<>();
        teamManagers.add(new TeamManager("email1@gmail.com", 2, "first", "last","owner@gmail.com"));
        teamManagers.add(new TeamManager("email2@gmail.com", 3, "first", "last","owner@gmail.com"));
        ArrayList<Coach> coaches = new ArrayList<>();
        coaches.add(new Coach("email3@gmail.com", 4, "first", "last",CoachRole.MAJOR,QualificationCoach.UEFA_A));
        coaches.add(new Coach("email4@gmail.com", 5, "first", "last", CoachRole.MAJOR,QualificationCoach.UEFA_A));
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("email5@gmail.com", 6, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
        players.add(new Player("email6@gmail.com", 7, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));


        teamController.createNewTeam(teamName, ownerMail,players ,coaches, teamManagers,court);
        teamOwner = TeamOwnerDbInMemory.getInstance().getTeamOwner(ownerMail);

        Assert.assertEquals(teamName, teamOwner.getTeam().getTeamName());
        Team team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getPlayers().containsKey("email5@gmail.com"));
        Assert.assertTrue(team.getPlayers().containsKey("email6@gmail.com"));
        Assert.assertEquals(teamName, teamOwner.getTeam().getTeamName());
        team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getCoaches().containsKey("email3@gmail.com"));
        Assert.assertTrue(team.getCoaches().containsKey("email4@gmail.com"));
        Assert.assertEquals(teamName, teamOwner.getTeam().getTeamName());
        team = TeamDbInMemory.getInstance().getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getTeamManagers().containsKey("email1@gmail.com"));
        Assert.assertTrue(team.getTeamManagers().containsKey("email2@gmail.com"));
        Assert.assertEquals(ownerMail,TeamManagerDbInMemory.getInstance().getTeamManager("email1@gmail.com").getOwnedByEmail());
        Assert.assertEquals(ownerMail,TeamManagerDbInMemory.getInstance().getTeamManager("email2@gmail.com").getOwnedByEmail());

        Assert.assertEquals(teamName, teamOwner.getTeam().getTeamName());
        team = court.getTeam(teamName);
        Assert.assertEquals("courtName",team.getCourt().getCourtName());
        Assert.assertEquals("courtCity",team.getCourt().getCourtCity());

        Assert.assertEquals(teamName,team.getTeamPage().getPageID());
    }



}

