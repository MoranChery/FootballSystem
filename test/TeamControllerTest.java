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
        dbs.add(TeamRoleDbInMemory.getInstance());
        for (Db db : dbs) {
            db.deleteAll();
        }
    }

    @Test
    public void testAddPlayerInvalidInputs() {
        try {
            teamController.addPlayer(null, "email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerTeamNotFound() {
        try {
            teamController.addPlayer("notExists", "email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
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
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try {
            teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Date birthDate = new Date();
        teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        Map<String, Player> players = team.getPlayers();
        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey("email@gmail.com"));
        Player player = players.get("email@gmail.com");
        Assert.assertEquals(1, player.getId().intValue());
        Assert.assertEquals("email@gmail.com", player.getEmailAddress());
        Assert.assertEquals("firstPlayer", player.getFirstName());
        Assert.assertEquals("lastPlayer", player.getLastName());
        Assert.assertEquals(birthDate, player.getBirthDate());
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
        Assert.assertEquals(team, player.getTeam());
    }

    @Test
    public void testAddPlayerExistsPlayerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        Player player = new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
//        SubscriberDbInMemory.getInstance().createSubscriber(player);
        playerController.createPlayer(player);
        teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
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
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        try {
            teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayerOther", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
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
        Coach coach = new Coach("email@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        SubscriberDbInMemory.getInstance().createSubscriber(coach);
        try {
            teamController.addPlayer(teamName, "email@gmail.com", 1, "first", "last", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The player to added already has other subscriber type", e.getMessage());
        }
    }

    @Test
    public void testAddPlayerExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Date birthDate = new Date();
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
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
            teamController.addTeamManager("notExists", "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com");
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
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerNotExistsTeamManager() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("email2@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com");
        Team team = teamController.getTeam(teamName);
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey("email@gmail.com"));
        TeamManager teamManager = teamManagers.get("email@gmail.com");
        Assert.assertEquals(1, teamManager.getId().intValue());
        Assert.assertEquals("email@gmail.com", teamManager.getEmailAddress());
        Assert.assertEquals("firstTeamManager", teamManager.getFirstName());
        Assert.assertEquals("lastTeamManager", teamManager.getLastName());
        Assert.assertEquals("email2@gmail.com", teamManager.getOwnedByEmail());
        Assert.assertEquals(team, teamManager.getTeam());
    }


    @Test
    public void testAddTeamManagerExistsTeamManagerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);

        teamOwnerController.createTeamOwner(new TeamOwner("email2@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com");
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team Manager associated with a team", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistsTeamManagerIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("email2@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        teamManagerController.createTeamManager(new TeamManager("email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com"));
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManagerOther", "lastTeamManager", "email2@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("One or more of the details incorrect", e.getMessage());
        }
    }


    @Test
    public void testAddTeamManagerExistsTeamManagerIdAssociatedWithOwnedBy() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("email2@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        teamManagerController.createTeamManager(new TeamManager("email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email3@gmail.com"));
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Team Manager owned by another teamOwner", e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistsTeamManager() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("email2@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        teamManagerController.createTeamManager(new TeamManager("email@gmail.com", 1, "firstTeamManager", "lastTeamManager", null));
        teamController.addTeamManager(teamName, "email@gmail.com", 1, "firstTeamManager", "lastTeamManager", "email2@gmail.com");
        Team team = teamController.getTeam(teamName);
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey("email@gmail.com"));
        TeamManager teamManager = teamManagers.get("email@gmail.com");
        Assert.assertEquals("email@gmail.com", teamManager.getEmailAddress());
        Assert.assertEquals(1, teamManager.getId().intValue());
        Assert.assertEquals("firstTeamManager", teamManager.getFirstName());
        Assert.assertEquals("lastTeamManager", teamManager.getLastName());
        Assert.assertEquals("email2@gmail.com", teamManager.getOwnedByEmail());
        Assert.assertEquals(team, teamManager.getTeam());
    }

    @Test
    public void testAddTeamManagerExistsTeamManagerAsDifferentSubscriber() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        Coach coach = new Coach("email@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        SubscriberDbInMemory.getInstance().createSubscriber(coach);
        teamOwnerController.createTeamOwner(new TeamOwner("email2@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        try {
            teamController.addTeamManager(teamName, "email@gmail.com", 1, "first", "last", "email2@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The teamManager to added already has other subscriber type", e.getMessage());
        }
    }

    ////////////////////////////////////addCoach/////////////////////////////////////////////
    @Test
    public void testAddCoachInvalidInputs() {
        try {
            teamController.addCoach(null, "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddCoachTeamNotFound() {
        try {
            teamController.addCoach("notExists", "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
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
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try {
            teamController.addCoach(teamName, "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddCoachNotExistsCoach() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.addCoach(teamName, "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
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
    public void testAddCoachExistsCoachAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach("email2@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamController.addCoach(teamName, "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        try {
            teamController.addCoach(teamName, "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Coach associated with a team", e.getMessage());
        }
    }

    @Test
    public void testAddCoachExistsCoachIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach("email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        try {
            teamController.addCoach(teamName, "email@gmail.com", 1, "firstCoachOther", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("One or more of the details incorrect", e.getMessage());
        }
    }

    @Test
    public void testAddCoachExistsCoach() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach("email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamController.addCoach(teamName, "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
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
        Player player = new Player("email@gmail.com", 1, "first", "last", birthDate, PlayerRole.GOALKEEPER);
        SubscriberDbInMemory.getInstance().createSubscriber(player);
        teamOwnerController.createTeamOwner(new TeamOwner("email2@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        try {
            teamController.addCoach(teamName, "email@gmail.com", 1, "first", "last", CoachRole.MAJOR, QualificationCoach.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The teamManager to added already has other subscriber type - you can to appoint him to team manager", e.getMessage());
        }
    }

    /////////////////////////// addCourt ///////////////////////////////
    @Test
    public void testAddCourtInvalidInputs() {
        try {
            teamController.addCourt(null, "courtName", "courtCity");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddCourtTeamNotFound() {
        try {
            teamController.addCourt("notExists", "courtName", "courtCity");
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
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try {
            teamController.addCourt(teamName, "courtName", "courtCity");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testAddCourtExistsCourtAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));
        teamController.addCourt(teamName, "courtName", "courtCity");
        try {
            teamController.addCourt(teamName, "courtName", "courtCity");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("team already associated with court", e.getMessage());
        }
    }

    @Test
    public void testAddCourtNotExistsCourt() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.addCourt(teamName, "courtName", "courtCity");
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
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));
        try {
            teamController.addCourt(teamName, "courtName", "courtCityOther");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("The court name isn't match to the city", e.getMessage());
        }
    }

    @Test
    public void testAddCourtExistsCourt() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));
        teamController.addCourt(teamName, "courtName", "courtCity");
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
            teamController.removePlayer(null, "email@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerTeamNotFound() {
        try {
            teamController.removePlayer("notExists", "email@gmail.com");
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
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try {
            teamController.removePlayer(teamName, "email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try {
            teamController.removePlayer(teamName, "email@gmail.com");
        } catch (NotFoundException e) {
            Assert.assertEquals("Player not found", e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerExistsPlayerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        teamController.createTeam("Other");
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.removePlayer("Other", "email@gmail.com");
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
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        try {
            teamController.removePlayer(teamName, "email@gmail.com");
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
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        TeamRoleDbInMemory teamRoleDbInMemory = TeamRoleDbInMemory.getInstance();
        List<Role> roles = teamRoleDbInMemory.getRoles("email@gmail.com");
        Map<String, Player> players = team.getPlayers();
        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey("email@gmail.com"));
        boolean playerTeamRole = false;
        for (Role tr : roles) {
            if (RoleType.PLAYER.equals(tr.getRoleType())) {
                playerTeamRole = true;
            }
        }
        Assert.assertTrue(playerTeamRole);
        teamController.removePlayer(teamName, "email@gmail.com");
        Assert.assertEquals(0, players.size());
        Assert.assertFalse(players.containsKey("email@gmail.com"));
        for (Role tr : roles) {
            if (RoleType.PLAYER.equals(tr.getRoleType()) && tr.getTeamName() != null) {
                playerTeamRole = false;
            }
        }
        Assert.assertTrue(playerTeamRole);
    }


    ////////////////////////////////// removeTeamManager /////////////////////////////////////
    ////////////////////////////////// removeCoach /////////////////////////////////////////
    @Test
    public void testRemoveCoachInvalidInputs() {
        try {
            teamController.removeCoach(null, "email@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachTeamNotFound() {
        try {
            teamController.removeCoach("notExists", "email@gmail.com");
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
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try {
            teamController.removeCoach(teamName, "email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachNotExistsCoach() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try {
            teamController.removeCoach(teamName, "email@gmail.com");
        } catch (NotFoundException e) {
            Assert.assertEquals("Coach not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach("email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        try {
            teamController.removeCoach(teamName, "email@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Coach is not part with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach("email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamController.addCoach(teamName, "email@gmail.com", 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Team team = teamController.getTeam(teamName);
        TeamRoleDbInMemory teamRoleDbInMemory = TeamRoleDbInMemory.getInstance();
        List<Role> roles = teamRoleDbInMemory.getRoles("email@gmail.com");
        Map<String, Coach> coaches = team.getCoaches();
        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey("email@gmail.com"));
        boolean coachTeamRole = false;
        for (Role tr : roles) {
            if (RoleType.COACH.equals(tr.getRoleType())) {
                coachTeamRole = true;
            }
        }
        Assert.assertTrue(coachTeamRole);
        teamController.removeCoach(teamName, "email@gmail.com");
        Assert.assertEquals(0, coaches.size());
        Assert.assertFalse(coaches.containsKey("email@gmail.com"));
        for (Role tr : roles) {
            if (RoleType.COACH.equals(tr.getRoleType()) && tr.getTeamName() != null) {
                coachTeamRole = false;
            }
        }
        Assert.assertTrue(coachTeamRole);
    }

    ////////////////////////////////// removeCourt /////////////////////////////////////////
    @Test
    public void testRemoveCourtInvalidInputs() {
        try {
            teamController.removeCourt(null, "courtName");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtTeamNotFound() {
        try {
            teamController.removeCourt("notExists", "courtName");
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
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try {
            teamController.removeCourt(teamName, "courtName");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtNotExistsCourt() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try {
            teamController.removeCourt(teamName, "courtName");
        } catch (NotFoundException e) {
            Assert.assertEquals("Court not found", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));

        try {
            teamController.removeCourt(teamName, "courtName");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Court is not part of the with associated team", e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName", "courtCity"));
        teamController.addCourt(teamName, "courtName", "courtCity");
        Team team = teamController.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals(team, court.getTeam(teamName));
        HashMap<String, Team> teams = court.getTeams();
        Assert.assertEquals(1, teams.size());
        Assert.assertTrue(teams.containsKey(teamName));
        teamController.removeCourt(teamName, "courtName");
        Assert.assertEquals(0, teams.size());
        Assert.assertFalse(teams.containsKey(court.getCourtName()));
    }


    ////////////////////////////////// addFinancialActivity //////////////////////////////////////////
    @Test
    public void testAddFinancialActivityInvalidInputs() {
        try {
            teamController.addFinancialActivity(null, 1000.0, "Description", FinancialActivityType.OUTCOME);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testAddFinancialActivityTeamNotFound() {
        try {
            teamController.addFinancialActivity("NotExists", 1000.0, "Description", FinancialActivityType.OUTCOME);
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
        Team team = teamController.getTeam(teamName);
        team.setBudget(800.0);
        try {
            teamController.addFinancialActivity(teamName, 1000.0, "Description", FinancialActivityType.OUTCOME);
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
        Team team = teamController.getTeam(teamName);
        team.setBudget(1001.0);
        teamController.addFinancialActivity(teamName, 1000.0, "Description", FinancialActivityType.OUTCOME);
        Assert.assertEquals(1.0, team.getBudget(), 0);
    }

    @Test
    public void testAddFinancialActivityWithOutcomeEqualBudget() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Team team = teamController.getTeam(teamName);
        team.setBudget(1000.0);
        teamController.addFinancialActivity(teamName, 1000.0, "Description", FinancialActivityType.OUTCOME);
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
        Team team = teamController.getTeam(teamName);
        team.setBudget(1000.0);
        teamController.addFinancialActivity(teamName, 1000.0, "Description", FinancialActivityType.INCOME);
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
        Team team = teamController.getTeam(teamName);
        team.setBudget(800.0);
        teamController.addFinancialActivity(teamName, 1000.0, "Description", FinancialActivityType.INCOME);
        Assert.assertEquals(1800.0, team.getBudget(), 0);
    }

    //////////////////////////////////////////// changeStatus /////////////////////////////////////////////////////

    @Test
    public void testChangeStatusInvalidInputs() {
        try {
            teamController.changeStatus(null, TeamStatus.ACTIVE);
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testChangeStatusTeamNotFound() {
        try {
            teamController.changeStatus("NotExists", TeamStatus.ACTIVE);
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
        Team team = teamController.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        Assert.assertEquals(TeamStatus.INACTIVE, team.getTeamStatus());
    }

    @Test
    public void testChangeStatusFromInactiveToActive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Team team = teamController.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        Assert.assertEquals(TeamStatus.INACTIVE, team.getTeamStatus());
    }

    ////////////////////////////////////////////// updatePlayer ///////////////////////////////////////////
    @Test
    public void testUpdatePlayerExistsPlayerChangeDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Date birthDate = new Date();
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwnerMail@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        teamController.updatePlayerDetails("teamOwnerMail@gmail.com", "email@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
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
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwnerMail@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));

        teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.updatePlayerDetails("teamOwnerMail@gmail.com", "emailNotExists@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
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
        teamController.createTeam("Other");
        Date birthDate = new Date();
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwnerMail@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other")));
        teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.updatePlayerDetails("teamOwnerMail@gmail.com", "emailNotExists@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Player not associated with teamOwner's team", e.getMessage());
        }
    }

    @Test
    public void testUpdatePlayerTeamOwnerExistsWithDifferentTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Date birthDate = new Date();
        playerController.createPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try {
            teamController.updatePlayerDetails("teamOwnerMail@gmail.com", "emailNotExists@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        } catch (Exception e) {
//                Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("TeamOwner not found", e.getMessage());
        }
    }

    //////////////////////////////////// subscriptionTeamOwner ///////////////////////////////////////////////////
    @Test
    public void testsSubscriptionTeamOwnerInvalidInputs() {
        try {
            teamController.subscriptionTeamOwner(null, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
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
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try {
            teamController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try {
            teamController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
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
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other")));
        try {
            teamController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Teamowner's team does't match", e.getMessage());
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
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        Player player = new Player("teamOwnerToAdd@gmail.com", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(player);
        SubscriberDbInMemory.getInstance().createSubscriber(player);
        TeamRoleDbInMemory.getInstance().createRole("teamOwnerToAdd@gmail.com", "Other", RoleType.PLAYER);
        try {
            teamController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("OwnerToAdd already associated with other team", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerTeamRoleTeamOwnerExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        TeamOwner teamOwner = new TeamOwner("teamOwnerToAdd@gmail.com", "1234", 3, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        TeamRoleDbInMemory.getInstance().createRole("teamOwnerToAdd@gmail.com", teamName, RoleType.TEAM_OWNER);
        try {
            teamController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber already teamOwner", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamOwnerLegalSubscription() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        Player player = new Player("teamOwnerToAdd@gmail.com", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(player);
        SubscriberDbInMemory.getInstance().createSubscriber(player);
        TeamRoleDbInMemory.getInstance().createRole("teamOwnerToAdd@gmail.com", teamName, RoleType.PLAYER);
        teamController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber("teamOwnerToAdd@gmail.com");
        List<Role> roles = TeamRoleDbInMemory.getInstance().getRoles("teamOwnerToAdd@gmail.com");
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_OWNER, roles.get(1).getRoleType());
        TeamOwner teamOwnerAdded = TeamOwnerDbInMemory.getInstance().getTeamOwner("teamOwnerToAdd@gmail.com");
        Assert.assertEquals("teamOwner@gmail.com", teamOwnerAdded.getOwnedByEmailAddress());
        List<String> allTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy("teamOwner@gmail.com");
        Assert.assertEquals("teamOwnerToAdd@gmail.com", allTeamOwnersOwnedBy.get(0));
    }

    @Test
    public void testSubscriptionTeamOwnerWitoutAssociatedTeamSubscription() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        Player player = new Player("teamOwnerToAdd@gmail.com", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(player);
        TeamRoleDbInMemory.getInstance().createRole("teamOwnerToAdd@gmail.com", null, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().createSubscriber(player);
        teamController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", "teamOwnerToAdd@gmail.com");
        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber("teamOwnerToAdd@gmail.com");
        List<Role> roles = TeamRoleDbInMemory.getInstance().getRoles("teamOwnerToAdd@gmail.com");
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_OWNER, roles.get(1).getRoleType());
        TeamOwner teamOwnerAdded = TeamOwnerDbInMemory.getInstance().getTeamOwner("teamOwnerToAdd@gmail.com");
        Assert.assertEquals("teamOwner@gmail.com", teamOwnerAdded.getOwnedByEmailAddress());
        List<String> allTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy("teamOwner@gmail.com");
        Assert.assertEquals("teamOwnerToAdd@gmail.com", allTeamOwnersOwnedBy.get(0));
    }

///////////////////////////////////////// subscriptionTeamManager ///////////////////////////////

    @Test
    public void testsSubscriptionTeamManagerInvalidInputs() {
        try {
            teamController.subscriptionTeamManager(null, "teamOwner@gmail.com", "teamManagerToAdd@gmail.com");
            Assert.fail("Should throw NullPointerException");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerTeamNotFound() {
        try {
            teamController.subscriptionTeamOwner("notExists", "teamOwner@gmail.com", "teamManagerToAdd@gmail.com");
            ;
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
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try {
            teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", "teamManagerToAdd@gmail.com");
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This Team's status - Inactive", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try {
            teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", "teamManagerToAdd@gmail.com");
            ;
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
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam("Other")));
        try {
            teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", "teamManagerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Teamowner's team does't match", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerSubscriberNotExist() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        try {
            teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", "teamManagerToAdd@gmail.com");
            ;
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
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        Player player = new Player("teamManagerToAdd@gmail.com", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(player);
        SubscriberDbInMemory.getInstance().createSubscriber(player);
        TeamRoleDbInMemory.getInstance().createRole("teamManagerToAdd@gmail.com", "Other", RoleType.PLAYER);
        try {
            teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", "teamManagerToAdd@gmail.com");
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("Manager to Add already associated with other team", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerTeamRoleTeamOwnerExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        String teamManagerToAdd = "teamManagerToAdd@gmail.com";
        TeamOwner teamOwner = new TeamOwner(teamManagerToAdd, "1234", 3, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName));
        teamOwnerController.createTeamOwner(teamOwner);
        SubscriberDbInMemory.getInstance().createSubscriber(teamOwner);
        TeamRoleDbInMemory.getInstance().createRole(teamManagerToAdd, teamName, RoleType.TEAM_OWNER);
        try {
            teamController.subscriptionTeamOwner(teamName, "teamOwner@gmail.com", teamManagerToAdd);
            ;
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber already teamOwner", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerTeamRoleTeamManagerExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        String teamManagerToAdd = "teamManagerToAdd@gmail.com";
        TeamManager teamManager = new TeamManager(teamManagerToAdd, "1234", 3, "firstTeamOwnerName", "lastTeamOwnerName", null);
        teamManagerController.createTeamManager(teamManager);
        SubscriberDbInMemory.getInstance().createSubscriber(teamManager);
        TeamRoleDbInMemory.getInstance().createRole(teamManagerToAdd, teamName, RoleType.TEAM_MANAGER);
        try {
            teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", teamManagerToAdd);
            Assert.fail("Should throw Exception");
        } catch (Exception e) {
            Assert.assertEquals("This subscriber already teamManager", e.getMessage());
        }
    }

    @Test
    public void testSubscriptionTeamManagerLegalSubscription() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        String teamManagerToAdd = "teamManagerToAdd@gmail.com";
        /*create player in db and subscriber*/
        Player player = new Player(teamManagerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(player);
        SubscriberDbInMemory.getInstance().createSubscriber(player);
        TeamRoleDbInMemory.getInstance().createRole(teamManagerToAdd, teamName, RoleType.PLAYER);
        /*subscriptionTeamManager*/
        teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", teamManagerToAdd);
        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber(teamManagerToAdd);
        List<Role> roles = TeamRoleDbInMemory.getInstance().getRoles(teamManagerToAdd);
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roles.get(1).getRoleType());
        TeamManager teamManagerAdded = TeamManagerDbInMemory.getInstance().getTeamManager(teamManagerToAdd);
        Assert.assertEquals("teamOwner@gmail.com", teamManagerAdded.getOwnedByEmail());

    }

    @Test
    public void testSubscriptionTeamManagerWitoutAssociatedTeamSubscription() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner("teamOwner@gmail.com", "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamController.getTeam(teamName)));
        String teamManagerToAdd = "teamManagerToAdd@gmail.com";
        Player player = new Player(teamManagerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerController.createPlayer(player);
        TeamRoleDbInMemory.getInstance().createRole(teamManagerToAdd, null, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().createSubscriber(player);
        teamController.subscriptionTeamManager(teamName, "teamOwner@gmail.com", teamManagerToAdd);
        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber(teamManagerToAdd);
        List<Role> roles = TeamRoleDbInMemory.getInstance().getRoles(teamManagerToAdd);
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roles.get(1).getRoleType());
        TeamManager teamManagerAdded = TeamManagerDbInMemory.getInstance().getTeamManager(teamManagerToAdd);
        Assert.assertEquals("teamOwner@gmail.com", teamManagerAdded.getOwnedByEmail());
    }
}

