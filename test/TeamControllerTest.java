import Controller.PlayerController;
import Controller.TeamManagerController;
import Data.NotFoundException;
import Data.TeamRole;
import Model.Enums.PlayerRole;
import Model.Enums.TeamStatus;
import Model.Team;
import Model.UsersTypes.Player;
import Model.UsersTypes.TeamManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Date;
import java.util.Map;

public class TeamControllerTest {
    private Controller.TeamController teamController;
    private PlayerController playerController;
    private TeamManagerController teamManagerController;

    @Before
    public void init() {
        teamController = new Controller.TeamController();
        playerController = new PlayerController();
        teamManagerController = new TeamManagerController();
    }

    @Test
    public void testAddPlayerInvalidInputs() {
        try{
            teamController.addPlayer(null,1,"Noy","Harari",new Date(), PlayerRole.GOALKEEPER);
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testAddPlayerTeamNotFound(){
        try{
            teamController.addPlayer("notExists",1,"Noy","Harari",new Date(), PlayerRole.GOALKEEPER);
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
            teamController.addPlayer(teamName,1,"Noy","Harari",new Date(), PlayerRole.GOALKEEPER);
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
        teamController.addPlayer(teamName,1,"Noy","Harari", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        Map<Integer, Player> players = team.getPlayers();
        Assert.assertEquals(1,players.size());
        Assert.assertTrue(players.containsKey(1));
        Player player = players.get(1);
        Assert.assertEquals(1,player.getId().intValue());
        Assert.assertEquals("Noy",player.getFirstName());
        Assert.assertEquals("Harari",player.getLastName());
        Assert.assertEquals(birthDate,player.getBirthDate());
        Assert.assertEquals(PlayerRole.GOALKEEPER,player.getPlayerRole());
        Assert.assertEquals(team,player.getTeam());
    }
    @Test
    public void testAddPlayerExistsPlayerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        Date birthDate = new Date();
        teamController.createTeam(teamName);
        teamController.addPlayer(teamName,1,"Noy","Harari", birthDate, PlayerRole.GOALKEEPER);
        try{
            teamController.addPlayer(teamName,1,"Noy","Harari", birthDate, PlayerRole.GOALKEEPER);
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
        playerController.createPlayer(new Player(1,"Noy","Harari",birthDate,PlayerRole.GOALKEEPER));
        try{
            teamController.addPlayer(teamName,1,"Hila","Harari", birthDate, PlayerRole.GOALKEEPER);
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
        playerController.createPlayer(new Player(1,"Noy","Harari", birthDate, PlayerRole.GOALKEEPER));
        teamController.addPlayer(teamName,1,"Noy","Harari", birthDate, PlayerRole.GOALKEEPER);
        Team team = teamController.getTeam(teamName);
        Map<Integer, Player> players = team.getPlayers();
        Assert.assertEquals(1,players.size());
        Assert.assertTrue(players.containsKey(1));
        Player player = players.get(1);
        Assert.assertEquals(1,player.getId().intValue());
        Assert.assertEquals("Noy",player.getFirstName());
        Assert.assertEquals("Harari",player.getLastName());
        Assert.assertEquals(birthDate,player.getBirthDate());
        Assert.assertEquals(PlayerRole.GOALKEEPER,player.getPlayerRole());
        Assert.assertEquals(team,player.getTeam());
    }


    //////////////////////////////////// addTeamOwner/////////////////////////////////////////////
    @Test
    public void testAddTeamManagerInvalidInputs() {
        try{
            teamController.addTeamManager(null,1,"Noy","Harari");
            Assert.fail("Should throw NullPointerException");
        }catch (Exception e){
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("bad input",e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerTeamNotFound(){
        try{
            teamController.addTeamManager("notExists",1,"Noy","Harari");
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
            teamController.addTeamManager(teamName,1,"Noy","Harari");
            Assert.fail("Should throw Exception");
        }catch (Exception e){
            Assert.assertEquals("This Team's status - Inactive",e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerNotExistsPlayer() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.addTeamManager(teamName,1,"Noy","Harari");
        Team team = teamController.getTeam(teamName);
        Map<Integer, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1,teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey(1));
        TeamManager teamManager = teamManagers.get(1);
        Assert.assertEquals(1,teamManager.getId().intValue());
        Assert.assertEquals("Noy",teamManager.getFirstName());
        Assert.assertEquals("Harari",teamManager.getLastName());
        Assert.assertEquals(team,teamManager.getTeam());
    }

    @Test
    public void testAddTeamManagerExistsTeamManagerAssociatedWithOtherTeam() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamController.addTeamManager(teamName,1,"Noy","Harari");
        try{
            teamController.addTeamManager(teamName,1,"Noy","Harari");
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("Team Manager associated with a team",e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistsTeamManagerIdDifferentDetails() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamManagerController.createTeamManager(new TeamManager(1,"Noy","Harari"));
        try{
            teamController.addTeamManager(teamName,1,"Hila","Harari");
            Assert.fail("Should throw Exception");
        } catch (Exception e){
            Assert.assertEquals("One or more of the details incorrect",e.getMessage());
        }
    }

    @Test
    public void testAddTeamManagerExistsTeamManager() throws Exception {
        String teamName = "Exists";
        teamController.createTeam(teamName);
        teamManagerController.createTeamManager(new TeamManager(1,"Noy","Harari"));
        teamController.addTeamManager(teamName,1,"Noy","Harari");
        Team team = teamController.getTeam(teamName);
        Map<Integer, TeamManager> teamManagers = team.getTeamManagers();
        Assert.assertEquals(1,teamManagers.size());
        Assert.assertTrue(teamManagers.containsKey(1));
        TeamManager teamManager = teamManagers.get(1);
        Assert.assertEquals(1,teamManager.getId().intValue());
        Assert.assertEquals("Noy",teamManager.getFirstName());
        Assert.assertEquals("Harari",teamManager.getLastName());
        Assert.assertEquals(null,teamManager.getOwnedById());
        Assert.assertEquals(team,teamManager.getTeam());
    }



}
