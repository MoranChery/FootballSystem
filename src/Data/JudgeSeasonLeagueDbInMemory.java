package Data;

import Model.JudgeSeasonLeague;

import java.util.HashMap;
import java.util.Map;

public class JudgeSeasonLeagueDbInMemory implements JudgeSeasonLeagueDb
{
    /*structure like the DB of JudgeSeasonLeague*/
    private Map<Integer, JudgeSeasonLeague> judgeSeasonLeagueMap;

    private static JudgeSeasonLeagueDbInMemory ourInstance = new JudgeSeasonLeagueDbInMemory();

    public static JudgeSeasonLeagueDbInMemory getInstance() { return ourInstance; }

    public JudgeSeasonLeagueDbInMemory() { judgeSeasonLeagueMap = new HashMap<>(); }

    /**
     * Will receive from the Controller the JudgeSeasonLeague, add JudgeSeasonLeague to Data.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        if(judgeSeasonLeagueMap.containsKey(judgeSeasonLeague.getJudgeSeasonLeagueId()))
        {
            throw new Exception("JudgeSeasonLeague already exist in the system");
        }
        judgeSeasonLeagueMap.put(judgeSeasonLeague.getSeasonLeagueId(), judgeSeasonLeague);
    }

    public JudgeSeasonLeague getJudgeSeasonLeague(Integer judgeSeasonLeagueId) throws Exception
    {
        if (!judgeSeasonLeagueMap.containsKey(judgeSeasonLeagueId))
        {
            throw new Exception("JudgeSeasonLeague not found");
        }
        return judgeSeasonLeagueMap.get(judgeSeasonLeagueId);
    }
}
