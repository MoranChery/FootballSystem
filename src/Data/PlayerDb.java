package Data;

import Model.UsersTypes.Player;

public interface PlayerDb extends Db {
    void createPlayer(Player player) throws Exception;
    Player getPlayer(String playerEmailAddress) throws Exception;
     void updatePlayerDetails(Player player) throws NotFoundException;
    }
