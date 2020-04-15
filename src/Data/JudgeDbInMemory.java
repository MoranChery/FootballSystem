package Data;

import Model.Enums.QualificationJudge;
import Model.UsersTypes.Fan;
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
    public void createJudge(Judge judge) throws Exception
    {
        if(allJudgesMap.containsKey(judge.getEmailAddress()))
        {
            throw new Exception("Judge already exist in the system");
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

//    /**
//     * Will receive from the Controller the seasonLeague's id and the judge's id,
//     * want to inlay Judge to SeasonLeague.
//     * @param seasonLeagueId-id of the SeasonLeague.
//     * @param judgeId-id of the Judge.
//     * @throws Exception-if details are incorrect.
//     */
//    public void inlayJudgeToSeasonLeague(Integer seasonLeagueId, String judgeEmailAddress) throws Exception
//    {
//        if(!judgeMap.containsKey(judgeId))
//        {
//            throw new Exception("Judge not found");
//        }
//        Judge judge = judgeMap.get(judgeId);
//        judge.getInlaySeasonLeagueIdList().add(seasonLeagueId);
//    }
}
