import Controller.PlayerController;
import Data.NotFoundException;
import Data.TeamRole;
import Model.Enums.PlayerRole;
import Model.Enums.TeamStatus;
import Model.Team;
import Model.UsersTypes.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.Date;
import java.util.Map;

public class TeamControllerTest {
    private Controller.TeamController teamController;
    private PlayerController playerController;

    @Before
    public void init() {
        teamController = new Controller.TeamController();
        playerController = new PlayerController();
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
//    @Test
//    public void testAddPlayerToNonExistingTeam() throws Exception {
//        Integer playerId = 1;
//        Player player = new Player();
//        player.setId(playerId);
//        playerController.createPlayer(player);
//        try {
//            teamController.addPlayer("nonExistingTeam", playerId);
//            Assert.fail("Team shouldn't exist");
//        } catch (Exception e) {
//            Assert.assertEquals("Team not found", e.getMessage());
//        }
//    }
//
//
//    @Test
//    public void testAddPlayer() throws Exception {
//        Integer playerId = 1;
//        Player player = new Player();
//        player.setId(playerId);
//        playerController.createPlayer(player);
//
//        String teamName = "team";
//        teamController.createTeam(teamName);
//        teamController.addPlayer(teamName, playerId);
//
//        Team team = teamController.getTeam(teamName);
//        Map<Integer, Player> players = team.getPlayers();
//        Assert.assertTrue(players.containsKey(playerId));
//    }
}
