package Model.roi;

import Model.UsersTypes.Player;

public interface PlayerDb {
//    void createPlayer(Player player);
    Player getPlayer(Integer playerId) throws Exception;
}
