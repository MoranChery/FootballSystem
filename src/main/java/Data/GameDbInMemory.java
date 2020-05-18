package Data;

import Model.Court;
import Model.Game;
import Model.UsersTypes.Judge;

import javax.print.attribute.standard.NumberUp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GameDbInMemory implements GameDb {

    private Map<String, Game> allGamesMap;
    private static GameDbInMemory ourInstance = new GameDbInMemory();

    public GameDbInMemory() {
        this.allGamesMap = new HashMap<>();
    }

    public static GameDbInMemory getInstance() { return ourInstance; }


    @Override
    public void createGame(Game game) throws Exception {
        if(game == null){
            throw new NullPointerException("The game object is null");
        }
        if(allGamesMap.containsKey(game.getGameID())){
            throw new Exception("game already exist in system");
        }
        allGamesMap.put(game.getGameID(),game);
    }

    @Override
    public void addJudgeToGame(Integer gameID, Judge judgeToAdd) throws Exception {
        if(gameID == null || judgeToAdd == null){
            throw new NullPointerException("One or more of the inputs is null");
        }
        Game theGame = allGamesMap.get(gameID);
        if(theGame == null){
            throw new NotFoundException("Game not found");
        }
        Set<String> theJudgesOfThisGameList = theGame.getJudgesOfTheGameList();
        if(theJudgesOfThisGameList.contains(judgeToAdd.getEmailAddress())){
            throw new Exception("This judge already belong to this game");
        }
        theJudgesOfThisGameList.add(judgeToAdd.getEmailAddress());
    }

    @Override
    public Game getGame(String gameID) throws Exception {
        if(gameID == null){
            throw new NullPointerException("bad input");
        }
        //TODO : make sure that we get all of the details
        Game theGame = allGamesMap.get(gameID);
        if(theGame == null){
            throw new NotFoundException("Game not found");
        }
        return theGame;
    }

    @Override
    public void changeGameLocation(String newLocation, String gameID) throws Exception {
        if(!allGamesMap.containsKey(gameID)){
            throw new Exception("The game is not in the DB");
        }
        Game theGame = allGamesMap.get(gameID);
        Court court = theGame.getCourt();
        court.setCourtCity(newLocation);
    }

    @Override
    public void changeGameDate(String repMail, Date newDate, String gameID) throws Exception {
        if(!allGamesMap.containsKey(gameID)){
            throw new Exception("The game is not in the DB");
        }
        Game theGame = allGamesMap.get(gameID);
        theGame.setGameDate(newDate);
    }

    @Override
    public void deleteAll() {
        allGamesMap.clear();
    }
}