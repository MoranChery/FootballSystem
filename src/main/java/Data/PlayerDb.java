package Data;

import Model.Enums.PlayerRole;
import Model.UsersTypes.Player;

import java.sql.SQLException;
import java.util.Date;

public interface PlayerDb extends Db {
    void insertPlayer(Player player) throws Exception;
    Player getPlayer(String playerEmailAddress) throws Exception;
    void updatePlayerDetails(String playerEmailAddress, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws NotFoundException, SQLException;
    void removePlayerFromDb(Player player) throws  Exception;
}
