package Data;

import Model.UsersTypes.Player;

public interface PlayerDb {
    void createPlayer(Player player) throws Exception;
    Player getPlayer(Integer playerId) throws Exception;
}
