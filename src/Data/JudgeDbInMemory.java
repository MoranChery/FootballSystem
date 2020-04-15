package Data;

import Model.Enums.QualificationJudge;
import Model.JudgeSeasonLeague;
import Model.UsersTypes.Judge;

import java.util.HashMap;
import java.util.Map;

public class JudgeDbInMemory implements JudgeDb
{
    /*structure like the DB of Judges*/
    private Map<String, Judge> judgeMap;

    private static JudgeDbInMemory ourInstance = new JudgeDbInMemory();

    public static JudgeDbInMemory getInstance() { return ourInstance; }

    public JudgeDbInMemory() { judgeMap = new HashMap<>(); }

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
        if(judgeMap.containsKey(judge.getEmailAddress()))
        {
            throw new Exception("Judge already exist in the system");
        }
        judgeMap.put(judge.getEmailAddress(), judge);
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
        if (!judgeMap.containsKey(judgeEmailAddress))
        {
            throw new Exception("Judge not found");
        }
        return judgeMap.get(judgeEmailAddress);
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
        if(!judgeMap.containsKey(judgeEmailAddress))
        {
            throw new Exception("Judge not found");
        }
        judgeMap.remove(judgeEmailAddress);
    }

    /**
     * Will receive from the Controller the JudgeSeasonLeague,
     * add to seasonLeagueId_JudgeSeasonLeagueId Map the seasonLeagueId and the judgeSeasonLeagueId of the specific Judge.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        if (!judgeMap.containsKey(judgeSeasonLeague.getJudgeEmailAddress()))
        {
            throw new Exception("Judge not found");
        }
        judgeMap.get(judgeSeasonLeague.getJudgeEmailAddress()).getSeasonLeagueId_JudgeSeasonLeagueId().put(judgeSeasonLeague.getSeasonLeagueId(), judgeSeasonLeague.getJudgeSeasonLeagueId());
    }
}
