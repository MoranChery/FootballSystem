package Service;//package Service;

import Data.*;
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

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TeamOwnerServiceTest {
    private TeamOwnerService teamOwnerService = new TeamOwnerService();
    //    private TeamDb teamDb = teamDb;
//    private PlayerDb playerDb = PlayerDbInMemory.getInstance();
//    private TeamManagerDb teamManagerDb = teamManagerDb;
//    private TeamOwnerDb teamOwnerDb =  teamOwnerDb;
//    private CoachDb coachDb =  CoachDbInMemory.getInstance();
    private TeamDb teamDb = TeamDbInServer.getInstance();
    private PlayerDb playerDb = PlayerDbInServer.getInstance();
    private TeamManagerDb teamManagerDb = TeamManagerDbInServer.getInstance();
    private TeamOwnerDb teamOwnerDb =  TeamOwnerDbInServer.getInstance();
    private CoachDb coachDb =  CoachDbInServer.getInstance();
    private RoleDb roleDb = RoleDbInServer.getInstance();
    private SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();
    private CourtDb courtDb = CourtDbInServer.getInstance();
    private PermissionDb permissionDb = PermissionDbInServer.getInstance();
    private PageDb pageDb = PageInServer.getInstance();
    private FinancialActivityDb financialActivityDb = FinancialActivityDbInServer.getInstance();

    @Before
    public void init() throws SQLException {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(CoachDbInServer.getInstance());
        dbs.add(CourtDbInServer.getInstance());
        dbs.add(financialActivityDb);
        dbs.add(playerDb);
        dbs.add(subscriberDb);
        dbs.add(teamManagerDb);
        dbs.add(TeamOwnerDbInServer.getInstance());
        dbs.add(RoleDbInServer.getInstance());
        dbs.add(pageDb);
        dbs.add(permissionDb);
        dbs.add(TeamDbInServer.getInstance());
        for (Db db : dbs) {
            db.deleteAll();
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
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String birthDate = df.format(new Date());


        String playerToAdd = "email@gmail.com";
        teamOwnerService.addPlayer(teamName, ownerEmail,playerToAdd, "1", "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER.name());

        Team team = teamDb.getTeam(teamName);
        Map<String, Player> players = team.getPlayers();

        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey(playerToAdd));
        Player player = players.get(playerToAdd);
        Assert.assertEquals(1, player.getId().intValue());
        Assert.assertEquals(playerToAdd, player.getEmailAddress());
        Assert.assertEquals("firstPlayer", player.getFirstName());
        Assert.assertEquals("lastPlayer", player.getLastName());
        String playerToAddDate = df.format(birthDate);
        String playerInDbDate = df.format(player.getBirthDate());
        Assert.assertEquals(playerToAddDate, playerInDbDate);
        Assert.assertEquals(PlayerRole.GOALKEEPER, player.getPlayerRole());
        Assert.assertEquals(teamName, player.getTeam());
        Assert.assertNotNull(player.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(playerToAdd));
        Assert.assertEquals(RoleType.PLAYER, roleDb.getRole(playerToAdd).getRoleType());
    }

    @Test
    public void testAddTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);

        String teamManagerToAdd = "email@gmail.com";
        teamOwnerService.addTeamManager(teamName, teamManagerToAdd, "1", "firstTeamManager", "lastTeamManager","",ownerEmail);
        Team team = teamDb.getTeam(teamName);
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey(teamManagerToAdd));
        TeamManager teamManager = teamManagers.get(teamManagerToAdd);
        Assert.assertEquals(1, teamManager.getId().intValue());
        Assert.assertEquals(teamManagerToAdd, teamManager.getEmailAddress());
        Assert.assertEquals("firstTeamManager", teamManager.getFirstName());
        Assert.assertEquals("lastTeamManager", teamManager.getLastName());
        Assert.assertEquals(ownerEmail, teamManager.getOwnedByEmail());
        Assert.assertEquals(teamName, teamManager.getTeam());
        Assert.assertNotNull(teamManager.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(teamManagerToAdd));

        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDb.getRole(teamManagerToAdd).getRoleType());
    }

    @Test
    public void testAddCoach() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);

        String coachToAdd = "email@gmail.com";
        teamOwnerService.addCoach(teamName, ownerEmail,coachToAdd, "1", "firstCoach", "lastCoach", CoachRole.MAJOR.name(), QualificationCoach.UEFA_A.name());
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
        Assert.assertEquals(teamName, coach.getTeam());
        Assert.assertNotNull(coach.getPassword());
        Assert.assertNotNull(subscriberDb.getSubscriber(coachToAdd));
        Assert.assertEquals(RoleType.COACH, roleDb.getRole(coachToAdd).getRoleType());
    }

    @Test
    public void testAddCourt() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);

        teamOwnerService.addCourt(teamName, ownerEmail,"courtName", "courtCity");
        Team team =teamDb.getTeam(teamName);
        Court court = courtDb.getCourt("courtName");
        Assert.assertEquals("courtName", court.getCourtName());
        Assert.assertEquals("courtCity", court.getCourtCity());
        Assert.assertEquals(teamName, court.getTeam(teamName));
    }

    @Test
    public void testRemovePlayer() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);


        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String birthDate = df.format(new Date());

        teamOwnerService.addPlayer(teamName, ownerEmail,"email@gmail.com", "1", "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER.name());
        Team team =teamDb.getTeam(teamName);

        Map<String, Player> players = team.getPlayers();
        Assert.assertEquals(1, players.size());
        Assert.assertTrue(players.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.PLAYER,roleDb.getRole("email@gmail.com").getRoleType());

        teamOwnerService.removePlayer(teamName, ownerEmail,"email@gmail.com");
        team =teamDb.getTeam(teamName);
        players = team.getPlayers();
        Assert.assertEquals(0, players.size());//players in team
        Assert.assertFalse(players.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.PLAYER,roleDb.getRole("email@gmail.com").getRoleType());
        Assert.assertNull(roleDb.getRole("email@gmail.com").getTeamName());
    }

    @Test
    public void testRemoveTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);

        teamOwnerService.addTeamManager(teamName, "email@gmail.com", "1", "firstPlayer", "lastPlayer","",ownerEmail);
        Team team = teamDb.getTeam(teamName);
        List<Role> roles = roleDb.getRoles("email@gmail.com");
        Map<String, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1, teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.TEAM_MANAGER,roleDb.getRole("email@gmail.com").getRoleType());
        teamOwnerService.removeTeamManager(teamName, ownerEmail,"email@gmail.com");
        team = teamDb.getTeam(teamName);
        teamManagers = team.getTeamManagers();
        Assert.assertEquals(0, teamManagers.size());
        Assert.assertFalse(team.getTeamManagers().containsKey("email@gmail.com"));
        Assert.assertEquals(RoleType.TEAM_MANAGER,roleDb.getRole("email@gmail.com").getRoleType());
        Assert.assertNull(roleDb.getRole("email@gmail.com").getTeamName());
    }

    @Test
    public void testRemoveCoach() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        String coachToRemove = "email@gmail.com";
//        coachDb.insertCoach(new Coach(coachToRemove, "1", "firstCoach", "lastCoach", CoachRole.MAJOR, QualificationCoach.UEFA_A));
        teamOwnerService.addCoach(teamName,ownerEmail, coachToRemove, "1", "firstCoach", "lastCoach", CoachRole.MAJOR.name(), QualificationCoach.UEFA_A.name());
        Team team =teamDb.getTeam(teamName);
        List<Role> roles = roleDb.getRoles(coachToRemove);
        Map<String, Coach> coaches = team.getCoaches();

        Assert.assertEquals(1, coaches.size());
        Assert.assertTrue(coaches.containsKey(coachToRemove));
        Assert.assertEquals(RoleType.COACH,roleDb.getRole(coachToRemove).getRoleType());

        teamOwnerService.removeCoach(teamName, ownerEmail,coachToRemove);
        team = teamDb.getTeam(teamName);
        coaches = team.getCoaches();
        Assert.assertEquals(0, coaches.size());
        Assert.assertFalse(coaches.containsKey(coachToRemove));
        Assert.assertEquals(RoleType.COACH,roleDb.getRole(coachToRemove).getRoleType());
        Assert.assertNull(roleDb.getRole(coachToRemove).getTeamName());
    }

    @Test
    public void testRemoveCourt() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
//        courtDb.insertCourt(new Court("courtName", "courtCity"));
        teamOwnerService.addCourt(teamName, ownerEmail,"courtName", "courtCity");
        Team team = teamDb.getTeam(teamName);
        Court court = courtDb.getCourt("courtName");
        Assert.assertEquals(teamName, court.getTeam(teamName));

        List<String> teams = court.getTeams();
        Assert.assertEquals(1, teams.size());
        Assert.assertTrue(teams.contains(teamName));
        teamOwnerService.removeCourt(teamName, ownerEmail,"courtName");
        court = courtDb.getCourt("courtName");
        teams = court.getTeams();
        Assert.assertEquals(0, teams.size());
        Assert.assertFalse(teams.contains(court.getCourtName()));
        Assert.assertNull(teamOwnerService.getTeam(teamName).getCourt());
    }

    @Test
    public void testAddFinancialActivity() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,1000.0,TeamStatus.ACTIVE);

        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        teamOwnerService.addFinancialActivity(teamName, ownerEmail,1000.0, "Description", FinancialActivityType.INCOME);
        Team team = teamDb.getTeam(teamName);
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
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
        Team team = teamDb.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        teamOwnerService.changeStatusToInActive(teamName,"owner@gmail.com");
        team = teamDb.getTeam(teamName);
        Assert.assertEquals(TeamStatus.INACTIVE, team.getTeamStatus());
    }

    @Test
    public void testChangeStatusToActive() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.INACTIVE);
        String teamOwnerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(teamOwnerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(teamOwnerEmail,teamName,RoleType.TEAM_OWNER);
        teamOwnerService.changeStatusToActive(teamName, teamOwnerEmail);
        Team team = teamDb.getTeam(teamName);
        Assert.assertEquals(TeamStatus.ACTIVE, team.getTeamStatus());
    }

    @Test
    public void testUpdatePlayerDetails() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);
        String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);
//        Player player1 = new Player("email@gmail.com", "1", "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER);
//        subscriberDb.insertSubscriber(player1);
//        playerDb.insertPlayer(player1);
        Date date = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String birthDate = df.format(date);

        teamOwnerService.addPlayer(teamName, ownerEmail,"email@gmail.com", "1", "firstPlayer", "lastPlayer", birthDate, PlayerRole.GOALKEEPER.name());
        teamOwnerService.updatePlayerDetails(teamName,ownerEmail, "email@gmail.com", "changePlayer", "lastPlayer", date, PlayerRole.GOALKEEPER);
        Team team = teamDb.getTeam(teamName);
        Map<String, Player> players = team.getPlayers();
        Player player = players.get("email@gmail.com");
        Assert.assertEquals("changePlayer", player.getFirstName());
    }

    @Test
    public void testSubscriptionTeamOwner() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);

        Player player = new Player("teamOwnerToAdd@gmail.com", "1234", 1, "firstPlayerName", "lastPlayerName", new Date(), PlayerRole.GOALKEEPER);
        subscriberDb.insertSubscriber(player);
        playerDb.insertPlayer(player);
        roleDb.insertRole("teamOwnerToAdd@gmail.com", null, RoleType.PLAYER);

        teamOwnerService.subscriptionTeamOwner(teamName, ownerEmail, "teamOwnerToAdd@gmail.com");

        Subscriber subscriber = subscriberDb.getSubscriber("teamOwnerToAdd@gmail.com");
        List<Role> roles = roleDb.getRoles("teamOwnerToAdd@gmail.com");
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_OWNER, roles.get(1).getRoleType());
        TeamOwner teamOwnerAdded = teamOwnerDb.getTeamOwner("teamOwnerToAdd@gmail.com");
        Assert.assertEquals(ownerEmail, teamOwnerAdded.getOwnedByEmailAddress());
        List<String> allTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(ownerEmail);
        Assert.assertEquals("teamOwnerToAdd@gmail.com", allTeamOwnersOwnedBy.get(0));
    }

    @Test
    public void testRemoveSubscriptionTeamOwner() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerMail = "owner@gmail.com";
        String ownerToAdd = "teamOwnerToAdd@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(teamOwnerMail,teamName,RoleType.TEAM_OWNER);

        Date date = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String birthDate = df.format(date);

        teamOwnerService.addPlayer(teamName,teamOwnerMail,ownerToAdd,  "4", "firstPlayerName", "lastPlayerName",birthDate, PlayerRole.GOALKEEPER.name());
        Thread.sleep(100);
        teamOwnerService.subscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        List<String> teamOwnerAllTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(teamOwnerMail);
        Assert.assertEquals(ownerToAdd, teamOwnerAllTeamOwnersOwnedBy.get(0));


        String ownerToAddUnder = "teamOwnerToAddUnder@gmail.com";
        teamOwnerService.addPlayer(teamName,ownerToAdd,ownerToAddUnder,  "5", "firstPlayerName", "lastPlayerName",birthDate, PlayerRole.GOALKEEPER.name());
        Thread.sleep(100);
        teamOwnerService.subscriptionTeamOwner(teamName, ownerToAdd, ownerToAddUnder);

        List<String> teamOwnerToAddAllTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(ownerToAdd);
        Assert.assertEquals(ownerToAddUnder, teamOwnerToAddAllTeamOwnersOwnedBy.get(0));

        //delete ownerToAdd should to remove ownerToAddUnder//
        teamOwnerService.removeSubscriptionTeamOwner(teamName, teamOwnerMail, ownerToAdd);

        teamOwnerAllTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(teamOwnerMail);
        teamOwnerToAddAllTeamOwnersOwnedBy = teamOwnerDb.getAllTeamOwnersOwnedBy(ownerToAdd);

        Assert.assertEquals(RoleType.PLAYER,roleDb.getRole(ownerToAdd).getRoleType());
        Assert.assertEquals(RoleType.PLAYER,roleDb.getRole(ownerToAddUnder).getRoleType());
        Assert.assertEquals(0, teamOwnerAllTeamOwnersOwnedBy.size());
        Assert.assertEquals(0, teamOwnerToAddAllTeamOwnersOwnedBy.size());
    }

    @Test
    public void testRemoveSubscriptionTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String teamOwnerMail = "owner@gmail.com";
        String managerToRemove = "managerToRemove@gmail.com";

        TeamOwner teamOwner = new TeamOwner(teamOwnerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(teamOwnerMail,teamName,RoleType.TEAM_OWNER);

        Date date = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String birthDate = df.format(date);

        teamOwnerService.addPlayer(teamName,teamOwnerMail,managerToRemove,  "4", "firstPlayerName", "lastPlayerName",birthDate, PlayerRole.GOALKEEPER.name());
        Thread.sleep(1000);
        teamOwnerService.subscriptionTeamManager(teamName, teamOwnerMail, managerToRemove,new ArrayList<>());

        TeamManager teamManager = teamManagerDb.getTeamManager(managerToRemove);
        Assert.assertEquals(teamOwnerMail,teamManager.getOwnedByEmail());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDb.getRole(managerToRemove).getRoleType());
        Assert.assertEquals(2, roleDb.getRoles(managerToRemove).size());
//        Assert.assertEquals(RoleType.TEAM_MANAGER, roleDb.getRoles(managerToRemove).get(0).getRoleType());
//        Assert.assertEquals(RoleType.PLAYER, roleDb.getRoles(managerToRemove).get(1).getRoleType());

        //delete managerToRemove should to remove ownerToAddUnder//
        teamOwnerService.removeSubscriptionTeamManager(teamName, teamOwnerMail, managerToRemove);

        Assert.assertEquals(RoleType.PLAYER, roleDb.getRole(managerToRemove).getRoleType());
        Assert.assertEquals(1, roleDb.getRoles(managerToRemove).size());
    }

    @Test
    public void testSubscriptionTeamManager() throws Exception {
        String teamName = "Exists";
        teamDb.insertTeam(teamName,0.0,TeamStatus.ACTIVE);String ownerEmail = "owner@gmail.com";
        TeamOwner teamOwner = new TeamOwner(ownerEmail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName",teamName);
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);

        Date date = new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String birthDate = df.format(date);

        String managerToAdd = "teamManagerToAdd@gmail.com";
        teamOwnerService.addPlayer(teamName, ownerEmail,managerToAdd, "1", "firstPlayerName", "lastPlayerName", birthDate ,PlayerRole.GOALKEEPER.name());
        teamOwnerService.subscriptionTeamManager(teamName, ownerEmail, managerToAdd, new ArrayList<>());
        List<Role> roles = roleDb.getRoles(managerToAdd);
        Assert.assertEquals(RoleType.PLAYER, roles.get(0).getRoleType());
        Assert.assertEquals(RoleType.TEAM_MANAGER, roles.get(1).getRoleType());
        Assert.assertEquals(ownerEmail, teamManagerDb.getTeamManager(managerToAdd).getOwnedByEmail());
    }

    @Test
    public void testsCreateNewTeam() throws Exception {
        String teamName = "Team";
        String ownerMail = "owner@gmail.com";
        Court court = new Court("courtName", "courtCity");
        courtDb.insertCourt(court);

        TeamOwner teamOwner = new TeamOwner(ownerMail, "1234", 2, "firstTeamOwnerName", "lastTeamOwnerName");
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerMail,null,RoleType.TEAM_OWNER);


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
        teamOwner = teamOwnerDb.getTeamOwner(ownerMail);

        Assert.assertEquals(teamName, teamOwner.getTeam());
        Team team = teamDb.getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getPlayers().containsKey("email5@gmail.com"));
        Assert.assertTrue(team.getPlayers().containsKey("email6@gmail.com"));
        Assert.assertEquals(teamName, teamOwner.getTeam());
        team = teamDb.getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getCoaches().containsKey("email3@gmail.com"));
        Assert.assertTrue(team.getCoaches().containsKey("email4@gmail.com"));
        Assert.assertEquals(teamName, teamOwner.getTeam());
        team = teamDb.getTeam(teamName);
        Assert.assertTrue(team.getTeamOwners().containsKey(ownerMail));
        Assert.assertTrue(team.getTeamManagers().containsKey("email1@gmail.com"));
        Assert.assertTrue(team.getTeamManagers().containsKey("email2@gmail.com"));
        Assert.assertEquals(ownerMail,teamManagerDb.getTeamManager("email1@gmail.com").getOwnedByEmail());
        Assert.assertEquals(ownerMail,teamManagerDb.getTeamManager("email2@gmail.com").getOwnedByEmail());

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
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);



        teamOwnerService.addCoach(teamName, ownerEmail,"email@gmail.com", "1", "firstPlayer", "lastPlayer", CoachRole.MAJOR.name(), QualificationCoach.UEFA_A.name());
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
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail,teamName,RoleType.TEAM_OWNER);

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
        subscriberDb.insertSubscriber(teamOwner);
        teamOwnerDb.insertTeamOwner(teamOwner);
        roleDb.insertRole(ownerEmail, teamName, RoleType.TEAM_OWNER);

        List<PermissionType> permissionTypes = new ArrayList<>();
        permissionTypes.add(PermissionType.ADD_FINANCIAL);
        teamOwnerService.addTeamManager(teamName, "email@gmail.com", "1", "firstPlayer", "lastPlayer","",ownerEmail);
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
