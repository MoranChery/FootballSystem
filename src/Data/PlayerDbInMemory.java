package Data;

import Model.UsersTypes.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerDbInMemory implements PlayerDb {
    /*structure like the DB of players*/
    private Map<Integer, Player> players;

    public PlayerDbInMemory() {
        players = new HashMap<>();
    }

    /**
     * for the tests - create player in DB
     * @param player
     * @throws Exception
     */
    @Override
    public void createPlayer(Player player) throws Exception {
        Integer id = player.getId();
        if(players.containsKey(id)) {
            throw new Exception("Player already exists");
        }
        players.put(id, player);
    }

    /**
     * "pull" player from DB
     * @param playerId
     * @return
     * @throws Exception
     */
    @Override
    public Player getPlayer(Integer playerId) throws Exception {
        if (!players.containsKey(playerId)) {
            throw new Exception("Player not found");
        }
        return players.get(playerId);
    }
}
