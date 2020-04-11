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

    public JudgeDbInMemory() { judgeMap = new HashMap<Integer, Judge>(); }

    /**
     * for the tests - create Judge in DB
     * @param judgeName
     * @param judgeQualification
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
     * "pull" Judge from DB
     * @param judgeId
     * @return
     * @throws Exception
     */
    public Judge getJudge(Integer judgeId) throws Exception
    {
        if (!judgeMap.containsKey(judgeId))
        {
            throw new Exception("Judge not found");
        }
        return judgeMap.get(judgeId);
    }
}
