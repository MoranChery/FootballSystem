package Data;

import Model.Enums.QualificationJudge;
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
     * Will receive from the Controller the judge's name, want to create Judge.
     *
     * for the tests - create Judge in DB
     *
     * @param judgeName-name of the new Judge.
     * @param qualificationJudge-qualification of the new Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudge(String judgeName, QualificationJudge qualificationJudge) throws Exception
    {
        for (Judge judge : judgeMap.values())
        {
            if(judgeName.equals(judge.getJudgeName()))
            {
                throw new Exception("Judge already exist in the system");
            }
        }
        Judge judge = new Judge(judgeName, qualificationJudge);
        judgeMap.put(judge.getId(), judge);
    }

    /**
     * Will receive from the Controller the judge's id, return the Judge.
     *
     * "pull" Judge from DB.
     *
     * @param judgeId-id of the Judge.
     * @return the Judge.
     * @throws Exception-if details are incorrect.
     */
    public Judge getJudge(Integer judgeId) throws Exception
    {
        if (!judgeMap.containsKey(judgeId))
        {
            throw new Exception("Judge not found");
        }
        return judgeMap.get(judgeId);
    }

    /**
     * Will receive from the Controller the judge's id, want to remove Judge.
     *
     * "delete" Judge from DB
     *
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void removeJudge(Integer judgeId) throws Exception
    {
        if(!judgeMap.containsKey(judgeId))
        {
            throw new Exception("Judge not found");
        }
        judgeMap.remove(judgeId);
    }

    /**
     * Will receive from the Controller the seasonLeague's id and the judge's id,
     * want to inlay Judge to SeasonLeague.
     * @param seasonLeagueId-id of the SeasonLeague.
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void inlayJudgeToSeasonLeague(Integer seasonLeagueId, Integer judgeId) throws Exception
    {
        if(!judgeMap.containsKey(judgeId))
        {
            throw new Exception("Judge not found");
        }
        Judge judge = judgeMap.get(judgeId);
        judge.getInlaySeasonLeagueIdList().add(seasonLeagueId);
    }
}
