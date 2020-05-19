package Data;

import Model.Enums.PlayerRole;
import Model.UsersTypes.Player;

import java.util.Date;
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
    public void insertPlayer(Player player) throws Exception {
        if(player == null){
            throw new NullPointerException("bad input");
        }
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

    public void updatePlayerDetails(String playerEmailAddress, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws NotFoundException {
        if(!players.containsKey(playerEmailAddress)){
            throw new NotFoundException("Player not found");
        }
        Player player = players.get(playerEmailAddress);
        player.setFirstName(firstName);
        player.setLastName(lastName);
        player.setBirthDate(birthDate);
        player.setPlayerRole(playerRole);
    }

    @Override
    public void removePlayerFromDb(Player player) {
        players.remove(player.getEmailAddress());
    }

    @Override
    public void deleteAll() {
        players.clear();
    }
}
