package Data;

import Model.Season;
import Model.SeasonLeague;
import Model.UsersTypes.Judge;

import java.util.HashMap;
import java.util.Map;

public class JudgeDbInMemory implements JudgeDb
{
    /*structure like the DB of Judges*/
    private Map<Integer, Judge> judgeMap;

    private static JudgeDbInMemory ourInstance = new JudgeDbInMemory();

    public static JudgeDbInMemory getInstance() { return ourInstance; }

    public JudgeDbInMemory() { judgeMap = new HashMap<Integer, Judge>(); }

    /**
     * Will receive from the Controller the judge's name, want to create Judge.
     *
     * for the tests - create Judge in DB
     *
     * @param judgeName-name of the new Judge.
     * @param judgeQualification-qualification of the new Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudge(String judgeName, String judgeQualification) throws Exception
    {
        for (Judge judge : judgeMap.values())
        {
            if(judgeName.equals(judge.getJudgeName()))
            {
                throw new Exception("Judge already exist in the system");
            }
        }
        Judge judge = new Judge(judgeName, judgeQualification);
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
        if (judgeMap.containsKey(judgeId))
        {
            judgeMap.remove(judgeId);
        }
    }

    /**
     * combine seasonLeague to the judge
     * @param seasonLeagueId
     * @param judgeId
     * @throws Exception
     */
    public void inlayJudgeToSeasonLeague(Integer seasonLeagueId, Integer judgeId) throws Exception
    {
        Judge judge = judgeMap.get(judgeId);
        judge.getInlaySeasonLeagueIdList().add(seasonLeagueId);
    }
}
