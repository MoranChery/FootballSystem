package Controller;

import Data.*;
import Model.Enums.QualificationJudge;
import Model.Game;
import Model.Page;
import Model.TeamPage;
import Model.UsersTypes.Judge;

import java.util.Map;

public class JudgeController {
    private JudgeDb judgeDb;
    private GameDb gameDb;

    public JudgeController() {
        this.judgeDb = JudgeDbInMemory.getInstance();
        gameDb = GameDbInMemory.getInstance();
    }
    public void createJudge(Judge judge) throws Exception{
        if (judge == null){
            throw new NullPointerException("Can't create this judge");
        }
        judgeDb.createJudge(judge);
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
            throw new NullPointerException("bad input");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        if(judge == null){
            throw new NotFoundException("Judge not found to edit the qualification");
        }
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
            throw new NullPointerException("bad input");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        if(judge == null){
            throw new NotFoundException("Judge not found");
        }
        Map<Integer, Game> judgeGamesMap = judge.getTheJudgeGameList();
        Integer gameID = gameToAdd.getGameID();
        Game testGame = gameDb.getGame(gameID);
        if(!testGame.equals(gameToAdd)){
            throw new Exception("One or more of the details incorrect");
        }
        if(judgeGamesMap.containsKey(gameID)){
            throw new Exception("This game already in the system");
        }
        judgeDb.addGameToTheJudge(judgeMail,gameToAdd);
    }



}
