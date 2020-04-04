package Model.roi;

import Model.UsersTypes.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerDbInMemory implements PlayerDb {
    private Map<Integer, Player> players;

    public PlayerDbInMemory() {
        players = new HashMap<>();
    }

    @Override
    public Player getPlayer(Integer playerId) throws Exception {
        if (!players.containsKey(playerId)) {
            throw new Exception("Player not found");
        }
        return players.get(playerId);
    }
}
