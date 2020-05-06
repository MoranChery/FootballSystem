package Controller;

import Data.*;
import Model.*;
import Model.Enums.GameEventType;
import Model.Enums.QualificationJudge;
import Model.Enums.RoleType;
import Model.UsersTypes.Fan;
import Model.UsersTypes.Judge;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JudgeController {
    private JudgeDb judgeDb;
    private GameDb gameDb;
    private GameEventsLogDb gameEventsLogDb;
    private RoleDb roleDb;

    public JudgeController() {
        this.judgeDb = JudgeDbInMemory.getInstance();
        gameDb = GameDbInMemory.getInstance();
        roleDb = RoleDbInMemory.getInstance();
        gameEventsLogDb = GameEventsLogDbInMemory.getInstance();
    }

    /**
     * This function creates new judge in the DB
     * @param judge Judge the judge you want to add to the DB
     * @throws Exception NullPointerException if input is null
     */
    public void createJudge(Judge judge) throws Exception{
        if (judge == null){
            throw new NullPointerException("Can't create this judge");
        }
        judgeDb.createJudge(judge);
    }
    /**
     * This function get string that represent judge id - his email address and returns Judge class instance
     * @param judgeEmailAddress String - the id of the judge - his email address
     * @return Judge - the instance of the judge in the db
     * @throws Exception NullPointerException if the mail is null - the judge is not found in the db
     */
    public Judge getJudge(String judgeEmailAddress) throws Exception{
        if(judgeEmailAddress == null){
            throw new NullPointerException("Judge not found");
        }
        return judgeDb.getJudge(judgeEmailAddress);
    }

    /**
     * this function enable the judge to edit qualification
     * @param judgeMail String the judge id- email address
     * @param newQualification String the new qualification the judge want to change to
     * @throws Exception NullPointerException - if one or more of the inputs is null
     * NotFoundException - if the judge is not in the db
     * Exception - if the new qualification is equal to the current qualification
     */
    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception {
        if(judgeMail == null || newQualification == null){
            throw new NullPointerException("Something went wrong in editing judge's qualification");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        QualificationJudge theJudgeNewQualification = QualificationJudge.valueOf(newQualification);
        if(judge.getQualificationJudge().equals(theJudgeNewQualification)){
            throw new Exception("This qualification equal the previous");
        }
        judgeDb.wantToEditQualification(judgeMail, newQualification);
    }

    /**
     * this function add game to the table of games for this judge
     * @param judgeMail String the judge id- email address
     * @param gameToAdd Game the game you want to add to the table
     * @throws Exception NullPointerException - if one or more of the inputs is null
     * NotFoundException - if the judge is not in the db
     * Exception - if the judge not exist in the db or if the game exist in the db
     */
    public void addGameToTheJudge(String judgeMail, Game gameToAdd) throws Exception {
        if(judgeMail == null || judgeMail.isEmpty() || gameToAdd == null){
            throw new NullPointerException("One or more of the inputs wrong");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        List<String> theJudgeGameList = judgeDb.getJudgeGames(judgeMail);
        String gameID = gameToAdd.getGameID();
        Game testGame = gameDb.getGame(gameID);
        /** check if the game exist in the db **/
        if(theJudgeGameList.contains(gameID)){
            throw new Exception("This game already in the system");
        }
        judgeDb.addGameToTheJudge(judgeMail,gameToAdd);
    }


    public void addEventToGame(String judgeMail, String gameId, Time eventTime, Integer eventMinute, GameEventType gameEventType, String description) throws Exception {
        if(judgeMail == null || gameId == null || eventTime == null || eventMinute == null ||gameEventType == null || description == null ){
            throw new NullPointerException("bad input");
        }

            checkPermissionsJudge(judgeMail);
            //check if the judge and game located in db
            Game game = gameDb.getGame(gameId);

            List<String> theJudgeGameList = judgeDb.getJudgeGames(judgeMail);
            if (!theJudgeGameList.contains(gameId)) {
                throw new Exception("This game doesnt associated with current judge");
            }
            GameEvent gameEvent = new GameEvent(gameId,game.getGameDate(),eventTime,eventMinute,gameEventType,description);
            gameEventsLogDb.addEvent(gameEvent);
    }


    public void updateGameEventAfterGame(String judgeMail, String gameId,String eventId, Time eventTime, Integer eventMinute, GameEventType gameEventType, String description) throws Exception {
        if(judgeMail == null || gameId == null){
            throw new Exception("bad input");
        }
        checkPermissionsJudge(judgeMail);
        //check if the judge and game located in db
        Game game = gameDb.getGame(gameId);
        List<String> theJudgeGameList = judgeDb.getJudgeGames(judgeMail);
        if (!theJudgeGameList.contains(gameId)) {
            throw new Exception("This game doesnt associated with current judge");
        }
        if(!judgeMail.equals(game.getMajorJudge())){
            throw new Exception("This judge is not a major judge in this game");
        }

        //todo if less than 5 hour from the end of the game
        GameEvent gameEvent = new GameEvent(gameId,game.getGameDate(),eventTime,eventMinute,gameEventType,description);
        gameEventsLogDb.setUpdatedDetails(gameEvent);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(dtf.format(now));
//        if(5 >= (now - game.getEndGameTime())){
//            throw new Exception("5 hours from the end of the game passed");
//        }
    }

    private void checkPermissionsJudge(String judgeMail) throws Exception
    {
        if(judgeMail == null){
            throw new NullPointerException("bad input");
        }
            List<Role> subscriberRoleList = roleDb.getRoles(judgeMail);
            Judge judge = judgeDb.getJudge(judgeMail);
            boolean isJudge = false;
        if(subscriberRoleList.size() > 0)
            {
                for (Role role : subscriberRoleList)
                {
                    if (role.getRoleType().equals(RoleType.JUDGE))
                    {
                        isJudge = true;
                    }
                }
            }
        if(!isJudge){
            throw new Exception("This subscriber hasn't judge permissions");
        }
    }

}
