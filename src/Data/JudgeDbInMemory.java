package Data;

import Model.Enums.QualificationJudge;
import Model.Game;
import Model.JudgeSeasonLeague;
import Model.UsersTypes.Judge;

import java.util.HashMap;
import java.util.Map;

public class JudgeDbInMemory implements JudgeDb
{
    /*structure like the DB of Judges*/
    private Map<String, Judge> allJudgesMap;

    private static JudgeDbInMemory ourInstance = new JudgeDbInMemory();

    public static JudgeDbInMemory getInstance() { return ourInstance; }

    public JudgeDbInMemory() { allJudgesMap = new HashMap<>(); }

    /**
     * Will receive from the Controller the Judge, add Judge to Data.
     *
     * for the tests - create Judge in DB
     *
     * @param judge-the new Judge.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public void createJudge(Judge judge) throws Exception
    {
        if(allJudgesMap.containsKey(judge.getEmailAddress()))
        {
            throw new Exception("Judge already exists in the system");
        }
        allJudgesMap.put(judge.getEmailAddress(), judge);
    }

    /**
     * Will receive from the Controller the judge's emailAddress, return the Judge.
     *
     * "pull" Judge from DB.
     *
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @return the Judge.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public Judge getJudge(String judgeEmailAddress) throws Exception
    {
        if (!allJudgesMap.containsKey(judgeEmailAddress))
        {
            throw new Exception("Judge not found");
        }
        return allJudgesMap.get(judgeEmailAddress);
    }

    /**
     * Will receive from the Controller the judge's id, want to remove Judge.
     *
     * "delete" Judge from DB
     *
     * @param judgeEmailAddress-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public void removeJudge(String judgeEmailAddress) throws Exception
    {
        if(!allJudgesMap.containsKey(judgeEmailAddress))
        {
            throw new Exception("Judge not found");
        }
        allJudgesMap.remove(judgeEmailAddress);
    }

    @Override
    public void wantToEditPassword(String judgeMail, String newPassword) throws Exception {
        if(judgeMail == null || newPassword == null){
            throw new Exception("Something went wrong in editing judge the password");
        }
        Judge theJudge = allJudgesMap.get(judgeMail);
        if(theJudge == null){
            throw new NotFoundException("Couldn't get this judge");
        }
        if(theJudge.getPassword().equals(newPassword)){
            throw new Exception("You are already using this password");
        }
        theJudge.setPassword(newPassword);
    }

    @Override
    public void wantToEditFirstName(String judgeMail, String newFirstName) throws Exception {
        if(judgeMail == null || newFirstName == null){
            throw new Exception("Something went wrong in editing judge's first name");
        }
        Judge theJudge = allJudgesMap.get(judgeMail);
        if(theJudge == null){
            throw new NotFoundException("Couldn't get this judge");
        }
        if(theJudge.getFirstName().equals(newFirstName)){
            throw new Exception("You are already using this name as first name");
        }
        theJudge.setFirstName(newFirstName);
    }

    @Override
    public void wantToEditLastName(String judgeMail, String newLastName) throws Exception {
        if(judgeMail == null || newLastName == null){
            throw new Exception("Something went wrong in editing judge's last name");
        }
        Judge theJudge = allJudgesMap.get(judgeMail);
        if(theJudge == null){
            throw new NotFoundException("Couldn't get this judge");
        }
        if(theJudge.getLastName().equals(newLastName)){
            throw new Exception("You are already using this name as last name");
        }
        theJudge.setLastName(newLastName);
    }

    @Override
    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception {
        if(judgeMail == null || newQualification == null){
            throw new Exception("Something went wrong in editing judge's qualification");
        }
        Judge theJudge = allJudgesMap.get(judgeMail);
        if(theJudge == null){
            throw new NotFoundException("Couldn't get this judge");
        }
        QualificationJudge newQualificationEnum = QualificationJudge.valueOf(newQualification);
        if(theJudge.getQualificationJudge().equals(newQualificationEnum)){
            throw new Exception("You are already have this qualification");
        }
        theJudge.setQualificationJudge(newQualificationEnum);
    }

    /**
     * Will receive from the Controller the JudgeSeasonLeague,
     * add to seasonLeagueName_JudgeSeasonLeagueName Map the seasonLeagueName and the judgeSeasonLeagueName of the specific Judge.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        if (!allJudgesMap.containsKey(judgeSeasonLeague.getJudgeEmailAddress()))
        {
            throw new Exception("Judge not found");
        }
        allJudgesMap.get(judgeSeasonLeague.getJudgeEmailAddress()).getSeasonLeagueName_JudgeSeasonLeagueName().put(judgeSeasonLeague.getSeasonLeagueName(), judgeSeasonLeague.getJudgeSeasonLeagueName());
    }

    /**
     * For the tests-Clear the Judge Map from the DB.
     */
    @Override
    public void deleteAll()
    {
        allJudgesMap.clear();
    }

    @Override
    public void addGameToTheJudge(String judgeMail, Game gameToAdd) throws Exception {
        if(judgeMail.isEmpty() || judgeMail == null || gameToAdd == null){
            throw new Exception("One or more of the inputs wrong");
        }
        Judge theJudge = allJudgesMap.get(judgeMail);
        if(theJudge == null){
            throw new NotFoundException("Judge not found");
        }
        Map<Integer, Game> theGamesOfThisJudge = theJudge.getTheJudgeGameList();
        Integer gameID = gameToAdd.getGameID();
        if(theGamesOfThisJudge.containsKey(gameID)){
            throw new Exception("Game already belongs to this judge");
        }
        theGamesOfThisJudge.put(gameID, gameToAdd);
    }
}
