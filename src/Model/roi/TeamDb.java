package Model.roi;

import Model.UsersTypes.Player;

public interface TeamDb {
    void createTeam(String teamName) throws Exception;
    void addPlayer(String teamName, Player player) throws Exception;
    void removePlayer(String teamName, Integer playerId) throws Exception;
}
