import Controller.*;
import Data.*;
import Model.Court;
import Model.Enums.*;
import Model.FinancialActivity;
import Model.Team;
import Model.UsersTypes.Coach;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;
import Model.UsersTypes.TeamOwner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.lang.reflect.Field;
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
        for(Db db : dbs) {
            db.deleteAll();
        }
    }

    @Test
    public void testAddPlayerInvalidInputs() {
        try{
            teamController.addPlayer(null,1,"firstPlayer","lastPlayer",new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testAddPlayerTeamNotFound(){
        try{
            teamController.addPlayer("notExists",1,"firstPlayer","lastPlayer",new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NotFoundException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

    @Test
    public void testAddPlayerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try{
            teamController.addPlayer(teamName,1,"firstPlayer","lastPlayer",new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("This Team's status - Inactive",e.getMessage());
        }
    }

    @Test
    public void testAddPlayerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Date birthDate = new Date();
        teamController.addPlayer(teamName,1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        Map<Integer, Player> players = team.getPlayers();
        Assert.assertEquals(1,players.size());
        Assert.assertTrue(players.containsKey(1));
        Player player = players.get(1);
        Assert.assertEquals(1,player.getId().intValue());
        Assert.assertEquals("firstPlayer",player.getFirstName());
        Assert.assertEquals("lastPlayer",player.getLastName());
        Assert.assertEquals(birthDate,player.getBirthDate());
        Assert.assertEquals(PlayerRole.GOALKEEPER,player.getPlayerRole());
        Assert.assertEquals(team,player.getTeam());
    }

    @Test
    public void testAddPlayerExistsPlayerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        teamController.addPlayer(teamName,1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try{
            teamController.addPlayer(teamName,1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("Player associated with a team",e.getMessage());
        }
    }

    @Test
    public void testAddPlayerExistsPlayerIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        playerController.createPlayer(new Player(1,"firstPlayer","lastPlayer",birthDate,PlayerRole.GOALKEEPER));
        try{
            teamController.addPlayer(teamName,1,"firstPlayerOther","lastPlayer", birthDate, PlayerRole.GOALKEEPER);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("One or more of the details incorrect",e.getMessage());
        }
    }

    @Test
    public void testAddPlayerExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Date birthDate = new Date();
        playerController.createPlayer(new Player(1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName,1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        Map<Integer, Player> players = team.getPlayers();
        Assert.assertEquals(1,players.size());
        Assert.assertTrue(players.containsKey(1));
        Player player = players.get(1);
        Assert.assertEquals(1,player.getId().intValue());
        Assert.assertEquals("firstPlayer",player.getFirstName());
        Assert.assertEquals("lastPlayer",player.getLastName());
        Assert.assertEquals(birthDate,player.getBirthDate());
        Assert.assertEquals(PlayerRole.GOALKEEPER,player.getPlayerRole());
        Assert.assertEquals(team,player.getTeam());
    }


    //////////////////////////////////// addTeamManager/////////////////////////////////////////////
    @Test
    public void testAddTeamManagerInvalidInputs() {
        try{
            teamController.addTeamManager(null,1,"firstTeamManager","lastTeamManager",2);
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamNotFound(){
        try{
            teamController.addTeamManager("notExists",1,"firstTeamManager","lastTeamManager",2);
            Assert.fail("Should throw NotFoundException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try{
            teamController.addTeamManager(teamName,1,"firstTeamManager","lastTeamManager",2);
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("This Team's status - Inactive",e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamOwnerNotExists() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try{
            teamController.addTeamManager(teamName,1,"firstTeamManager","lastTeamManager",2);
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("TeamOwner not found",e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerNotExistsTeamManager() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner(2,teamController.getTeam(teamName),"firstTeamOwnerName","lastTeamOwnerName"));
        teamController.addTeamManager(teamName,1,"firstTeamManager","lastTeamManager",2);
        Team team = teamController.getTeam(teamName);
        Map<Integer, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1,teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey(1));
        TeamManager teamManager = teamManagers.get(1);
        Assert.assertEquals(1,teamManager.getId().intValue());
        Assert.assertEquals("firstTeamManager",teamManager.getFirstName());
        Assert.assertEquals("lastTeamManager",teamManager.getLastName());
        Assert.assertEquals(team,teamManager.getTeam());
    }



    @Test
    public void testAddTeamManagerExistsTeamManagerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner(2,teamController.getTeam(teamName),"firstTeamOwnerName","lastTeamOwnerName"));
        teamController.addTeamManager(teamName,1,"firstTeamManager","lastTeamManager",2);
        try{
            teamController.addTeamManager(teamName,1,"firstTeamManager","lastTeamManager",2);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("Team Manager associated with a team",e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistsTeamManagerIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner(2,teamController.getTeam(teamName),"firstTeamOwnerName","lastTeamOwnerName"));
        teamManagerController.createTeamManager(new TeamManager(1,"firstTeamManager","lastTeamManager",2));
        try{
            teamController.addTeamManager(teamName,1,"firstTeamManagerOther","lastTeamManager",2);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("One or more of the details incorrect",e.getMessage());
        }
    }


    @Test
    public void testAddTeamManagerExistsTeamManagerIdAssociatedWithOwnedBy() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner(2,teamController.getTeam(teamName),"firstTeamOwnerName","lastTeamOwnerName4"));
        teamManagerController.createTeamManager(new TeamManager(1,"firstTeamManager","lastTeamManager",3));
        try{
            teamController.addTeamManager(teamName,1,"firstTeamManager","lastTeamManager",2);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("Team Manager owned by another teamOwner",e.getMessage());
        }
    }
    @Test
    public void testAddTeamManagerExistsTeamManager() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamOwnerController.createTeamOwner(new TeamOwner(2,teamController.getTeam(teamName),"firstTeamOwnerName","firstTeamOwnerName"));
        teamManagerController.createTeamManager(new TeamManager(1,"firstTeamManager","lastTeamManager",null));
        teamController.addTeamManager(teamName,1,"firstTeamManager","lastTeamManager",2);
        Team team = teamController.getTeam(teamName);
        Map<Integer, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1,teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey(1));
        TeamManager teamManager = teamManagers.get(1);
        Assert.assertEquals(1,teamManager.getId().intValue());
        Assert.assertEquals("firstTeamManager",teamManager.getFirstName());
        Assert.assertEquals("lastTeamManager",teamManager.getLastName());
        Assert.assertEquals(2,teamManager.getOwnedById().intValue());
        Assert.assertEquals(team,teamManager.getTeam());
    }

    ////////////////////////////////////addCoach/////////////////////////////////////////////
    @Test
    public void testAddCoachInvalidInputs() {
        try{
            teamController.addCoach(null,1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A);
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testAddCoachTeamNotFound(){
        try{
            teamController.addCoach("notExists",1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A);
            Assert.fail("Should throw NotFoundException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

    @Test
    public void testAddCoachTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try{
            teamController.addCoach(teamName,1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A);
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("This Team's status - Inactive",e.getMessage());
        }
    }

    @Test
    public void testAddCoachNotExistsCoach() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.addCoach(teamName,1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A);
        Team team = teamController.getTeam(teamName);
        Map<Integer, Coach> coaches = team.getCoaches();
        Assert.assertEquals(1,coaches.size());
        Assert.assertTrue(coaches.containsKey(1));
        Coach coach = coaches.get(1);
        Assert.assertEquals(1,coach.getId().intValue());
        Assert.assertEquals("firstCoach",coach.getFirstName());
        Assert.assertEquals("lastCoach",coach.getLastName());
        Assert.assertEquals(CoachRole.MAJOR,coach.getCoachRole());
        Assert.assertEquals(Qualification.UEFA_A,coach.getQualification());
        Assert.assertEquals(team,coach.getTeam());
    }

    @Test
    public void testAddCoachExistsCoachAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach(1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A));
        teamController.addCoach(teamName,1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A);
        try{
            teamController.addCoach(teamName,1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("Coach associated with a team",e.getMessage());
        }
    }

    @Test
    public void testAddCoachExistsCoachIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach(1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A));
        try{
            teamController.addCoach(teamName,1,"firstCoachOther","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("One or more of the details incorrect",e.getMessage());
        }
    }

    @Test
    public void testAddCoachExistsCoach() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach(1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A));
        teamController.addCoach(teamName,1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A);
        Team team = teamController.getTeam(teamName);
        Map<Integer, Coach> coaches = team.getCoaches();
        Assert.assertEquals(1,coaches.size());
        Assert.assertTrue(coaches.containsKey(1));
        Coach coach = coaches.get(1);
        Assert.assertEquals(1,coach.getId().intValue());
        Assert.assertEquals("firstCoach",coach.getFirstName());
        Assert.assertEquals("lastCoach",coach.getLastName());
        Assert.assertEquals(CoachRole.MAJOR,coach.getCoachRole());
        Assert.assertEquals(Qualification.UEFA_A,coach.getQualification());
        Assert.assertEquals(team,coach.getTeam());
    }

    ///////////////////////////addCourt///////////////////////////////
    @Test
    public void testAddCourtInvalidInputs() {
        try{
            teamController.addCourt(null,"courtName","courtCity");
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testAddCourtTeamNotFound(){
        try{
            teamController.addCourt("notExists","courtName","courtCity");
            Assert.fail("Should throw NotFoundException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

    @Test
    public void testAddCourtTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try{
            teamController.addCourt(teamName,"courtName","courtCity");
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("This Team's status - Inactive",e.getMessage());
        }
    }

    @Test
    public void testAddCourtExistsCourtAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName","courtCity"));
        teamController.addCourt(teamName,"courtName","courtCity");
        try{
            teamController.addCourt(teamName,"courtName","courtCity");
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("team already associated with court",e.getMessage());
        }
    }

    @Test
    public void testAddCourtNotExistsCourt() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.addCourt(teamName,"courtName","courtCity");
        Team team = teamController.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals("courtName",court.getCourtName());
        Assert.assertEquals("courtCity",court.getCourtCity());
        Assert.assertEquals(team,court.getTeam(teamName));
    }

    @Test
    public void testAddCourtIncorrectCityName() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName","courtCity"));
        try{
            teamController.addCourt(teamName,"courtName","courtCityOther");
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("The court name isn't match to the city",e.getMessage());
        }
    }

    @Test
    public void testAddCourtExistsCourt() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName","courtCity"));
        teamController.addCourt(teamName,"courtName","courtCity");
        Team team = teamController.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals("courtName",court.getCourtName());
        Assert.assertEquals("courtCity",court.getCourtCity());
        Assert.assertEquals(team,court.getTeam(teamName));
    }

////////////////////////////////// removePlayer /////////////////////////////////////

    @Test
    public void testRemovePlayerInvalidInputs() {
        try{
            teamController.removePlayer(null,1);
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerTeamNotFound(){
        try{
            teamController.removePlayer("notExists",1);
            Assert.fail("Should throw NotFoundException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try{
            teamController.removePlayer(teamName,1);
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("This Team's status - Inactive",e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try{
            teamController.removePlayer(teamName,1);
        }catch (NotFoundException e){
            Assert.assertEquals("Player not found",e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerExistsPlayerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        teamController.createTeam("Other");
        playerController.createPlayer(new Player(1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName,1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        try{
            teamController.removePlayer("Other",1);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("Player is not part with associated team",e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        playerController.createPlayer(new Player(1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        try{
            teamController.removePlayer(teamName,1);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("Player is not part with associated team",e.getMessage());
        }
    }

    @Test
    public void testRemovePlayerExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        playerController.createPlayer(new Player(1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName,1,"firstPlayer","lastPlayer", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        TeamRoleDbInMemory teamRoleDbInMemory = TeamRoleDbInMemory.getInstance();
        List<TeamRole> teamRoles = teamRoleDbInMemory.getTeamRoles(1);
        Map<Integer, Player> players = team.getPlayers();
        Assert.assertEquals(1,players.size());
        Assert.assertTrue(players.containsKey(1));
        boolean playerTeamRole = false;
        for (TeamRole tr: teamRoles) {
            if(TeamRoleType.PLAYER.equals(tr.getTeamRoleType())){
                playerTeamRole = true;
            }
        }
        Assert.assertTrue(playerTeamRole);
        teamController.removePlayer(teamName,1);
        Assert.assertEquals(0,players.size());
        Assert.assertFalse(players.containsKey(1));
        playerTeamRole = false;
        for (TeamRole tr: teamRoles) {
            if(TeamRoleType.PLAYER.equals(tr.getTeamRoleType())){
                playerTeamRole = true;
            }
        }
        Assert.assertFalse(playerTeamRole);
    }


    ////////////////////////////////// removeTeamOwner /////////////////////////////////////
    ////////////////////////////////// removeCoach /////////////////////////////////////////
    @Test
    public void testRemoveCoachInvalidInputs() {
        try{
            teamController.removeCoach(null,1);
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachTeamNotFound(){
        try{
            teamController.removeCoach("notExists",1);
            Assert.fail("Should throw NotFoundException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try{
            teamController.removeCoach(teamName,1);
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("This Team's status - Inactive",e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachNotExistsCoach() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try{
            teamController.removeCoach(teamName,1);
        }catch (NotFoundException e){
            Assert.assertEquals("Coach not found",e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach(1,"firstPlayer","lastPlayer",CoachRole.MAJOR,Qualification.UEFA_A));
        try{
            teamController.removeCoach(teamName,1);
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("Coach is not part with associated team",e.getMessage());
        }
    }

    @Test
    public void testRemoveCoachExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        coachController.createCoach(new Coach(1,"firstCoach","lastCoach",CoachRole.MAJOR,Qualification.UEFA_A));
        teamController.addCoach(teamName,1,"firstCoach","lastCoach", CoachRole.MAJOR, Qualification.UEFA_A);
        Team team = teamController.getTeam(teamName);
        TeamRoleDbInMemory teamRoleDbInMemory = TeamRoleDbInMemory.getInstance();
        List<TeamRole> teamRoles = teamRoleDbInMemory.getTeamRoles(1);
        Map<Integer, Coach> coaches = team.getCoaches();
        Assert.assertEquals(1,coaches.size());
        Assert.assertTrue(coaches.containsKey(1));
        boolean coachTeamRole = false;
        for (TeamRole tr: teamRoles) {
            if(TeamRoleType.COACH.equals(tr.getTeamRoleType())){
                coachTeamRole = true;
            }
        }
        Assert.assertTrue(coachTeamRole);
        teamController.removeCoach(teamName,1);
        Assert.assertEquals(0,coaches.size());
        Assert.assertFalse(coaches.containsKey(1));
        coachTeamRole = false;
        for (TeamRole tr: teamRoles) {
            if(TeamRoleType.COACH.equals(tr.getTeamRoleType())){
                coachTeamRole = true;
            }
        }
        Assert.assertFalse(coachTeamRole);
    }

    ////////////////////////////////// removeCourt /////////////////////////////////////////
    @Test
    public void testRemoveCourtInvalidInputs() {
        try{
            teamController.removeCourt(null,"courtName");
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtTeamNotFound(){
        try{
            teamController.removeCourt("notExists","courtName");
            Assert.fail("Should throw NotFoundException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtTeamInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.changeStatus(teamName, TeamStatus.INACTIVE);
        try{
            teamController.removeCourt(teamName,"courtName");
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("This Team's status - Inactive",e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtNotExistsCourt() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        try{
            teamController.removeCourt(teamName,"courtName");
        }catch (NotFoundException e){
            Assert.assertEquals("Court not found",e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtNotAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName","courtCity"));

        try{
            teamController.removeCourt(teamName,"courtName");
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("Court is not part of the with associated team",e.getMessage());
        }
    }

    @Test
    public void testRemoveCourtExistsAndAssociatedWithTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        CourtDbInMemory courtDbInMemory = CourtDbInMemory.getInstance();
        courtDbInMemory.createCourt(new Court("courtName","courtCity"));
        teamController.addCourt(teamName,"courtName","courtCity");
        Team team = teamController.getTeam(teamName);
        Court court = team.getCourt();
        Assert.assertEquals(team,court.getTeam(teamName));
        HashMap<String, Team> teams = court.getTeams();
        Assert.assertEquals(1,teams.size());
        Assert.assertTrue(teams.containsKey(teamName));
        teamController.removeCourt(teamName,"courtName");
        Assert.assertEquals(0,teams.size());
        Assert.assertFalse(teams.containsKey(court.getCourtName()));
    }

////////////////////////////////// addFinancialActivity //////////////////////////////////////////
        @Test
        public void testAddFinancialActivityInvalidInputs() {
            try{
                teamController.addFinancialActivity(null,1000.0,"Description",FinancialActivityType.OUTCOME);
                Assert.fail("Should throw NullPointerException");
            }catch (Exception e){
                Assert.assertTrue(e instanceof NullPointerException);
                Assert.assertEquals("bad input",e.getMessage());
            }
        }

    @Test
    public void testAddFinancialActivityTeamNotFound(){
        try{
            teamController.addFinancialActivity("NotExists",1000.0,"Description",FinancialActivityType.OUTCOME);
            Assert.fail("Should throw NotFoundException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

    @Test
    public void testAddFinancialActivityWithOutcomeExceedsBudget() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Team team = teamController.getTeam(teamName);
        team.setBudget(800.0);
        try{
            teamController.addFinancialActivity(teamName,1000.0,"Description",FinancialActivityType.OUTCOME);
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("The financial outcome exceeds from the budget",e.getMessage());
        }
        Assert.assertEquals(800.0, team.getBudget(),0);
    }

    @Test
    public void testAddFinancialActivityWithOutcomeUnderBudget() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Team team = teamController.getTeam(teamName);
        team.setBudget(1001.0);
        teamController.addFinancialActivity(teamName,1000.0,"Description",FinancialActivityType.OUTCOME);
        Assert.assertEquals(1.0, team.getBudget(),0);
    }

    @Test
    public void testAddFinancialActivityWithOutcomeEqualBudget() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Team team = teamController.getTeam(teamName);
        team.setBudget(1000.0);
        teamController.addFinancialActivity(teamName,1000.0,"Description",FinancialActivityType.OUTCOME);
        Assert.assertEquals(0.0, team.getBudget(),0);
        Map<String, FinancialActivity> financialActivities = team.getFinancialActivities();
        Assert.assertEquals(1,financialActivities.size());
        Set<String> keySet = financialActivities.keySet();
        Assert.assertEquals(1000.0,financialActivities.get(keySet.iterator().next()).getFinancialActivityAmount(),0);
        Assert.assertEquals(FinancialActivityType.OUTCOME,financialActivities.get(keySet.iterator().next()).getFinancialActivityType());
    }


    @Test
    public void testAddFinancialActivityWithIncomeEqualBudget() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Team team = teamController.getTeam(teamName);
        team.setBudget(1000.0);
        teamController.addFinancialActivity(teamName,1000.0,"Description",FinancialActivityType.INCOME);
        Map<String, FinancialActivity> financialActivities = team.getFinancialActivities();
        Assert.assertEquals(1,financialActivities.size());
        Set<String> keySet = financialActivities.keySet();
        Assert.assertEquals(1000.0,financialActivities.get(keySet.iterator().next()).getFinancialActivityAmount(),0);
        Assert.assertEquals(FinancialActivityType.INCOME,financialActivities.get(keySet.iterator().next()).getFinancialActivityType());
        Assert.assertEquals(2000.0, team.getBudget(),0);
    }

    @Test
    public void testAddFinancialActivityWithIncomeUnderBudget() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Team team = teamController.getTeam(teamName);
        team.setBudget(800.0);
        teamController.addFinancialActivity(teamName,1000.0,"Description",FinancialActivityType.INCOME);
        Assert.assertEquals(1800.0, team.getBudget(),0);
    }

    ////////////////////////////////////////////changeStatus/////////////////////////////////////////////////////

    @Test
    public void testChangeStatusInvalidInputs() {
        try{
            teamController.changeStatus(null,TeamStatus.ACTIVE);
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testChangeStatusTeamNotFound(){
        try{
            teamController.changeStatus("NotExists",TeamStatus.ACTIVE);
            Assert.fail("Should throw NotFoundException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NotFoundException);
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

    @Test
    public void testChangeStatusFromActiveToInactive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Team team = teamController.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        teamController.changeStatus(teamName,TeamStatus.INACTIVE);
        Assert.assertEquals(TeamStatus.INACTIVE,team.getTeamStatus());
    }

    @Test
    public void testChangeStatusFromInactiveToActive() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        Team team = teamController.getTeam(teamName);
        team.setTeamStatus(TeamStatus.ACTIVE);
        teamController.changeStatus(teamName,TeamStatus.INACTIVE);
        Assert.assertEquals(TeamStatus.INACTIVE,team.getTeamStatus());
    }

}
