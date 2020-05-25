package Data;

import Model.Court;
import Model.Game;
import Model.UsersTypes.Judge;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.print.attribute.standard.NumberUp;
import java.util.*;

public class GameDbInMemory implements GameDb {

    private Map<String, Game> allGamesMap;
    private static GameDbInMemory ourInstance;

    static {
        try {
            ourInstance = new GameDbInMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameDbInMemory() throws Exception {
        this.allGamesMap = new HashMap<>();

        Game game1  = new Game("game1",new Date(),"sl1", "team1", "team2","courtName1");
        Game game2  = new Game("game2",new Date(),"sl1", "team1", "team2","courtName2");
        allGamesMap.put(game1.getGameID(),game1);
        allGamesMap.put(game2.getGameID(),game2);


    }

    public static GameDbInMemory getInstance() { return ourInstance; }


    @Override
    public void insertGame(Game game) throws Exception {
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
    public List<Game> getAllGames() throws Exception
    {
        throw new NotImplementedException();
//        return null;
    }

    @Override
    public void updateGameLocation(String newLocation, String gameID) throws Exception {
        if(!allGamesMap.containsKey(gameID)){
            throw new Exception("The game is not in the DB");
        }
        Game theGame = allGamesMap.get(gameID);
        String court = theGame.getCourt();
//        court.setCourtCity(newLocation);
    }

    @Override
    public void updateGameDate(String repMail, Date newDate, String gameID) throws Exception {
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
