package Service;//package Service;

import Data.CoachDb;
import Data.CoachDbInMemory;
import Data.CourtDbInMemory;
import Data.Db;
import Data.FinancialActivityDbInMemory;
import Data.PageDbInMemory;
import Data.PermissionDbInMemory;
import Data.PlayerDb;
import Data.PlayerDbInMemory;
import Data.RoleDbInMemory;
import Data.SubscriberDbInMemory;
import Data.TeamDb;
import Data.TeamDbInMemory;
import Data.TeamManagerDb;
import Data.TeamManagerDbInMemory;
import Data.TeamOwnerDb;
import Data.TeamOwnerDbInMemory;
import Model.Court;
import Model.Enums.CoachRole;
import Model.Enums.FinancialActivityType;
import Model.Enums.PermissionType;
import Model.Enums.PlayerRole;
import Model.Enums.QualificationCoach;
import Model.Enums.RoleType;
import Model.Enums.TeamStatus;
import Model.FinancialActivity;
import Model.Role;
import Model.Team;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.Subscriber;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TeamOwnerServiceTest {
    private TeamOwnerService teamOwnerService = new TeamOwnerService();
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
//            db.deleteAll();
        }
    }

    @Test
    public void getTeam() throws Exception {
        teamDb.insertTeam("Team");
        Team team = teamOwnerService.getTeam("Team");
        Assert.assertEquals("Team",team.getTeamName());
    }

    @Test
    public void testAddPlayer() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName", teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        Date birthDate = new Date();

        String playerToAdd = "email@gmail.com";
        teamOwnerService.addPlayer(teamName, ownerEmail,playerToAdd, 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);

        Team team =teamDb.getTeam(teamName);
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
    public void testAddTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String teamManagerToAdd = "email@gmail.com";
        teamOwnerService.addTeamManager(teamName, teamManagerToAdd, 1, "firstTeamManager", "lastTeamManager", new ArrayList<>(),ownerEmail);
        Team team =teamDb.getTeam(teamName);
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
        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDbInMemory.getRole(teamManagerToAdd).getRoleType());
    }

    @Test
    public void testAddCoach() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String coachToAdd = "email@gmail.com";
        teamOwnerService.addCoach(teamName, ownerEmail,coachToAdd, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Team team =teamDb.getTeam(teamName);
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
    public void testAddCourt() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerService.addCourt(teamName, ownerEmail,"courtName", "courtCity");
        Team team =teamDb.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals("courtName", court.getCourtName());
        Assert.assertEquals("courtCity", court.getCourtCity());
        Assert.assertEquals(team, court.getTeam(teamName));
    }

    @Test
    public void testRemovePlayer() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerService.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team =teamDb.getTeam(teamName);
        RoleDbInMemory teamRoleDbInMemory = RoleDbInMemory.getInstance();
        Map<String, Player> players = team.getPlayers();
        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());

        teamOwnerService.removePlayer(teamName, ownerEmail,"email@gmail.com");

        Assert.assertEquals(0, players.size());//players in team
        Assert.assertFalse(players.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());
        Assert.assertNull(RoleDbInMemory.getInstance().getRole("email@gmail.com").getTeamName());
    }

    @Test
    public void testRemoveTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerService.addTeamManager(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", new ArrayList<>(),ownerEmail);
        Team team =teamDb.getTeam(teamName);
        RoleDbInMemory teamRoleDbInMemory = RoleDbInMemory.getInstance();
        List<Role> roles = teamRoleDbInMemory.getRoles("email@gmail.com");
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.TEAM_MANAGER,RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());
        teamOwnerService.removeTeamManager(teamName, ownerEmail,"email@gmail.com");
        Assert.assertEquals(0, teamManagers.size());
        Assert.assertFalse(team.getTeamManagers().containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.TEAM_MANAGER,RoleDbInMemory.getInstance().getRole("email@gmail.com").getRoleType());
        Assert.assertNull(RoleDbInMemory.getInstance().getRole("email@gmail.com").getTeamName());
    }

    @Test
    public void testRemoveCoach() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        String coachToRemove = "email@gmail.com";
        coachDb.insertCoach(new Coach(coachToRemove, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamOwnerService.addCoach(teamName,ownerEmail, coachToRemove, 1, "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Team team =teamDb.getTeam(teamName);
        RoleDbInMemory teamRoleDbInMemory = RoleDbInMemory.getInstance();
        List<Role> roles = teamRoleDbInMemory.getRoles(coachToRemove);
        Map<String, Coach> coaches = team.getCoaches();

        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey(coachToRemove));
        Assert.assertEquals(RoleType.COACH,RoleDbInMemory.getInstance().getRole(coachToRemove).getRoleType());

        teamOwnerService.removeCoach(teamName, ownerEmail,coachToRemove);

        Assert.assertEquals(0, coaches.size());
        Assert.assertFalse(coaches.containsKey(coachToRemove));
        Assert.assertEquals(RoleType.COACH,RoleDbInMemory.getInstance().getRole(coachToRemove).getRoleType());
        Assert.assertNull(RoleDbInMemory.getInstance().getRole(coachToRemove).getTeamName());
    }

    @Test
    public void testRemoveCourt() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.insertCourt(new Court("courtName", "courtCity"));
        teamOwnerService.addCourt(teamName, ownerEmail,"courtName", "courtCity");
        Team team =teamDb.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals(team, court.getTeam(teamName));
        List<String> teams = court.getTeams();
        Assert.assertEquals(1, teams.size());
        Assert.assertTrue(teams.contains(teamName));
        teamOwnerService.removeCourt(teamName, ownerEmail,"courtName");
        Assert.assertEquals(0, teams.size());
        Assert.assertFalse(teams.contains(court.getCourtName()));
        Assert.assertNull(teamOwnerService.getTeam(teamName).getCourt());
    }

    @Test
    public void testAddFinancialActivity() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        Team team =teamDb.getTeam(teamName);
        team.setBudget(1000.0);
        teamOwnerService.addFinancialActivity(teamName, ownerEmail,1000.0, "Description", FinancialActivityType.INCOME);
        Map<String, FinancialActivity> financialActivities = team.getFinancialActivities();
        Assert.assertEquals(1, financialActivities.size());
        Set<String> keySet = financialActivities.keySet();
        Assert.assertEquals(1000.0, financialActivities.get(keySet.iterator().next()).getFinancialActivityAmount(), 0);
        Assert.assertEquals(FinancialActivityType.INCOME, financialActivities.get(keySet.iterator().next()).getFinancialActivityType());
        Assert.assertEquals(2000.0, team.getBudget(), 0);
    }

    @Test
    public void testChangeStatusToInactive() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        Team team =teamDb.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        teamOwnerService.changeStatusToInActive(teamName,"owner@gmail.com");
        Assert.assertEquals(TeamStatus.INACTIVE, team.getTeamStatus());
    }

    @Test
    public void testChangeStatusToActive() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(teamOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(teamOwnerEmail,teamName,RoleType.TEAM_OWNER);

        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);
        Team team =teamDb.getTeam(teamName);
        team.setTeamStatus(TeamStatus.INACTIVE);
        teamOwnerService.changeStatusToActive(teamName, teamOwnerEmail);
        Assert.assertEquals(TeamStatus.ACTIVE, team.getTeamStatus());
    }

    @Test
    public void testUpdatePlayerDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        Date birthDate = new Date();
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        playerDb.insertPlayer(new Player("email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamOwnerService.addPlayer(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        teamOwnerService.updatePlayerDetails(teamName,ownerEmail, "email@gmail.com", "changePlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team =teamDb.getTeam(teamName);
        Map<String, Player> players = team.getPlayers();
        Player player = players.get("email@gmail.com");
        Assert.assertEquals("changePlayer", player.getFirstName());
    }

    @Test
    public void testSubscriptionTeamOwner() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        Player player = new Player("teamOwnerToAdd@gmail.com", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        playerDb.insertPlayer(player);
        RoleDbInMemory.getInstance().insertRole("teamOwnerToAdd@gmail.com", null, RoleType.PLAYER);
        SubscriberDbInMemory.getInstance().insertSubscriber(player);

        teamOwnerService.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");

        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber("teamOwnerToAdd@gmail.com");
        List<Role> roles = RoleDbInMemory.getInstance().getRoles("teamOwnerToAdd@gmail.com");
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_OWNER, roles.get(1).getRoleType());
        TeamOwner teamOwnerAdded = TeamOwnerDbInMemory.getInstance().getTeamOwner("teamOwnerToAdd@gmail.com");
        Assert.assertEquals(ownerEmail, teamOwnerAdded.getOwnedByEmailAddress());
        List<String> allTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerEmail);
        Assert.assertEquals("teamOwnerToAdd@gmail.com", allTeamOwnersOwnedBy.get(0));
    }

    @Test
    public void testRemoveSubscriptionTeamOwner() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerMail = "owner@gmail.com";
        String ownerToAdd = "teamOwnerToAdd@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(teamOwnerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerService.addPlayer(teamName,teamOwnerMail,ownerToAdd,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerService.subscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        List<String> teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(teamOwnerMail);
        Assert.assertEquals(ownerToAdd, teamOwnerAllTeamOwnersOwnedBy.get(0));


        String ownerToAddUnder = "teamOwnerToAddUnder@gmail.com";
        teamOwnerService.addPlayer(teamName,ownerToAdd,ownerToAddUnder,  5, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(100);
        teamOwnerService.subscriptionTeamOwner(teamName, ownerToAdd, ownerToAddUnder);

        List<String> teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(ownerToAddUnder, teamOwnerToAddAllTeamOwnersOwnedBy.get(0));

        //delete ownerToAdd should to remove ownerToAddUnder//
        teamOwnerService.removeSubscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        teamOwnerAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(teamOwnerMail);
        teamOwnerToAddAllTeamOwnersOwnedBy = TeamOwnerDbInMemory.getInstance().getAllTeamOwnersOwnedBy(ownerToAdd);

        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole(ownerToAdd).getRoleType());
        Assert.assertEquals(RoleType.PLAYER,RoleDbInMemory.getInstance().getRole(ownerToAddUnder).getRoleType());
        Assert.assertEquals(0, teamOwnerAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(0, teamOwnerToAddAllTeamOwnersOwnedBy.size());
    }

    @Test
    public void testRemoveSubscriptionTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerMail = "owner@gmail.com";
        String managerToRemove = "managerToRemove@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(teamOwnerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerService.addPlayer(teamName,teamOwnerMail,managerToRemove,  4, "firstPlayerName", "lastPlayerName",new Date(), PlayerRole.GOALKEEPER);
        Thread.sleep(1000);
        teamOwnerService.subscriptionTeamManager(teamName, teamOwnerMail, managerToRemove,new ArrayList<>());

        TeamManager teamManager = TeamManagerDbInMemory.getInstance().getTeamManager(managerToRemove);
        Assert.assertEquals(teamOwnerMail,teamManager.getOwnedByEmail());
        RoleDbInMemory roleDbInMemory = RoleDbInMemory.getInstance();
        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDbInMemory.getRole(managerToRemove).getRoleType());
        Assert.assertEquals(2, roleDbInMemory.getRoles(managerToRemove).size());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDbInMemory.getRoles(managerToRemove).get(0).getRoleType());
        Assert.assertEquals(RoleType.PLAYER, roleDbInMemory.getRoles(managerToRemove).get(1).getRoleType());

        //delete managerToRemove should to remove ownerToAddUnder//
        teamOwnerService.removeSubscriptionTeamManager(teamName, teamOwnerMail, managerToRemove);

        Assert.assertEquals(RoleType.PLAYER, roleDbInMemory.getRole(managerToRemove).getRoleType());
        Assert.assertEquals(1, roleDbInMemory.getRoles(managerToRemove).size());
    }

    @Test
    public void testSubscriptionTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        String managerToAdd = "teamManagerToAdd@gmail.com";
        teamOwnerService.addPlayer(teamName, ownerEmail,managerToAdd, 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        teamOwnerService.subscriptionTeamManager(teamName, ownerEmail, managerToAdd, new ArrayList<>());
        List<Role> roles = RoleDbInMemory.getInstance().getRoles(managerToAdd);
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roles.get(1).getRoleType());
        Assert.assertEquals(ownerEmail, TeamManagerDbInMemory.getInstance().getTeamManager(managerToAdd).getOwnedByEmail());
    }

    @Test
    public void testsCreateNewTeam() throws Exception {
        String teamName = "Team";
        String ownerMail = "owner@gmail.com";
        Court court = new Court("courtName", "courtCity");
        CourtDbInMemory.getInstance().insertCourt(court);

        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerMail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);


        ArrayList<TeamManager> teamManagers = new ArrayList<>();
        teamManagers.add(new TeamManager("email1@gmail.com", 2, "first", "last","owner@gmail.com"));
        teamManagers.add(new TeamManager("email2@gmail.com", 3, "first", "last","owner@gmail.com"));
        ArrayList<Coach> coaches = new ArrayList<>();
        coaches.add(new Coach("email3@gmail.com", 4, "first", "last",CoachRole.MAJOR,QualificationCoach.UEFA_A));
        coaches.add(new Coach("email4@gmail.com", 5, "first", "last", CoachRole.MAJOR,QualificationCoach.UEFA_A));
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("email5@gmail.com", 6, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));
        players.add(new Player("email6@gmail.com", 7, "firstPlayer", "lastPlayer", new Date(), PlayerRole.GOALKEEPER));


//        teamOwnerService.createNewTeam(teamName, ownerMail,players ,coaches, teamManagers,court,1000.0);
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
        Assert.assertEquals(ownerMail,TeamManagerDbInMemory.getInstance().getTeamManager("email1@gmail.com").getOwnedByEmail());
        Assert.assertEquals(ownerMail,TeamManagerDbInMemory.getInstance().getTeamManager("email2@gmail.com").getOwnedByEmail());

        Assert.assertEquals(teamName, teamOwner.getTeam());

        Assert.assertEquals("courtName",team.getCourt().getCourtName());
        Assert.assertEquals("courtCity",team.getCourt().getCourtCity());

        Assert.assertEquals(teamName,team.getTeamPage().getPageID());
    }

    @Test
    public void testUpdateCoachDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);Date birthDate = new Date();
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerService.addCoach(teamName, ownerEmail,"email@gmail.com", 1, "firstPlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        teamOwnerService.updateCoachDetails(teamName,"owner@gmail.com", "email@gmail.com", "changePlayer", "lastPlayer", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        Team team =teamDb.getTeam(teamName);
        Map<String, Coach> coaches = team.getCoaches();
        Coach coach = coaches.get("email@gmail.com");
        Assert.assertEquals("changePlayer", coach.getFirstName());
    }

    @Test
    public void testUpdateCourtDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);Date birthDate = new Date();
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        teamOwnerService.addCourt(teamName, ownerEmail, "courtName","cityName");
        teamOwnerService.updateCourtDetails(teamName,"owner@gmail.com", "courtName", "courtCity");
        Team team =teamDb.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals("courtCity", court.getCourtCity());
    }

    @Test
    public void testUpdateTeamManagerDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        teamOwnerDb.insertTeamOwner(teamOwner);
        RoleDbInMemory.getInstance().insertRole(ownerEmail, teamName, RoleType.TEAM_OWNER);
        SubscriberDbInMemory.getInstance().insertSubscriber(teamOwner);

        List<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.ADD_FINANCIAL);
        teamOwnerService.addTeamManager(teamName, "email@gmail.com", 1, "firstPlayer", "lastPlayer", new ArrayList<>(),ownerEmail);
        teamManagerDb.getTeamManager("email@gmail.com").setPermissionTypes(permissionTypes);


        List<PermissionType> permissionTypesToChange = new ArrayList<>();
        permissionTypesToChange.add(PermissionType.ADD_FINANCIAL);
        permissionTypesToChange.add(PermissionType.REMOVE_COURT);
        teamOwnerService.updateTeamManagerDetails(teamName, "owner@gmail.com", "email@gmail.com", "changePlayer", "lastPlayer", permissionTypesToChange);
        Team team =teamDb.getTeam(teamName);
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        TeamManager teamManager = teamManagers.get("email@gmail.com");
        Assert.assertEquals("changePlayer", teamManager.getFirstName());
        Assert.assertTrue(teamManager.getPermissionTypes().contains(PermissionType.ADD_FINANCIAL));
        Assert.assertTrue(teamManager.getPermissionTypes().contains(PermissionType.REMOVE_COURT));
    }
}

