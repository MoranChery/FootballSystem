package Controller;

import Data.PlayerDb;
import Data.PlayerDbInMemory;
import Model.UsersTypes.Player;

public class PlayerController {
    private PlayerDb playerDb;

    public PlayerController() {
        playerDb = PlayerDbInMemory.getInstance();
    }

    public void createPlayer(Player player) throws Exception {
        if(player == null) {
            throw new NullPointerException();
        }
        playerDb.createPlayer(player);
    }
}
