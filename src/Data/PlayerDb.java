package Data;

import Model.UsersTypes.Player;

public interface PlayerDb extends Db {
    void createPlayer(Player player) throws Exception;
    Player getPlayer(Integer playerId) throws Exception;
}
