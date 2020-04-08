import Controller.PlayerController;
import Model.UsersTypes.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class TeamControllerTest {
    private Controller.TeamController teamController;
    private PlayerController playerController;

    @Before
    public void init() {
        teamController = new Controller.TeamController();
        playerController = new PlayerController();
    }

//    @Test
//    public void testAddNonExistingPlayer() {
//        Integer playerId = 1;
//        try {
//            teamController.addPlayer("nonExistingTeam", playerId);
//            Assert.fail("Player shouldn't exist");
//        } catch (Exception e) {
//            Assert.assertEquals("Player not found", e.getMessage());
//        }
//    }
//
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
