package Data;

import Model.System_Controller;
import Model.UsersTypes.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerDbInMemory implements PlayerDb {

    private static PlayerDbInMemory ourInstance = new PlayerDbInMemory();

    public static PlayerDbInMemory getInstance() {
        return ourInstance;
    }
    /*structure like the DB of players*/
    private Map<String, Player> players;

    private PlayerDbInMemory() {
        players = new HashMap<>();
    }

    /**
     * for the tests - create player in DB
     * @param player
     * @throws Exception
     */
    @Override
    public void createPlayer(Player player) throws Exception {
        String playerEmailAddress = player.getEmailAddress();
        if(players.containsKey(playerEmailAddress)) {
            throw new Exception("Player already exists");
        }
        players.put(playerEmailAddress, player);
    }

    /**
     * "pull" player from DB
     * @param playerEmailAddress
     * @return
     * @throws Exception
     */
    @Override
    public Player getPlayer(String playerEmailAddress) throws Exception {
        if (!players.containsKey(playerEmailAddress)) {
            throw new NotFoundException("Player not found");
        }
        return players.get(playerEmailAddress);
    }

    public void updatePlayer(Player player) throws NotFoundException {
        String emailAddress = player.getEmailAddress();
        if(!players.containsKey(emailAddress)){
            throw new NotFoundException("Player not found");
        }
        Player playerFromDb = players.get(emailAddress);
        playerFromDb = player;
    }

    @Override
    public void deleteAll() {
        players.clear();
    }
}
