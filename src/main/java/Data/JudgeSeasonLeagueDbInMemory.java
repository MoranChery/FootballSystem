package Data;

import Model.JudgeSeasonLeague;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JudgeSeasonLeagueDbInMemory implements JudgeSeasonLeagueDb
{
    /*structure like the DB of JudgeSeasonLeague*/
    private Map<String, JudgeSeasonLeague> judgeSeasonLeagueMap;

    private static JudgeSeasonLeagueDbInMemory ourInstance = new JudgeSeasonLeagueDbInMemory();

    public static JudgeSeasonLeagueDbInMemory getInstance() { return ourInstance; }

    public JudgeSeasonLeagueDbInMemory() { judgeSeasonLeagueMap = new HashMap<>(); }

    /**
     * Will receive from the Controller the JudgeSeasonLeague, add JudgeSeasonLeague to Data.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public void insertJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        if(judgeSeasonLeagueMap.containsKey(judgeSeasonLeague.getJudgeSeasonLeagueName()))
        {
            throw new Exception("JudgeSeasonLeague already exists in the system");
        }
        judgeSeasonLeagueMap.put(judgeSeasonLeague.getJudgeSeasonLeagueName(), judgeSeasonLeague);
    }

    /**
     * Will receive from the Controller the judgeSeasonLeague's name, return the JudgeSeasonLeague.
     * @param judgeSeasonLeagueName-name of the JudgeSeasonLeague.
     * @return the JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public JudgeSeasonLeague getJudgeSeasonLeague(String judgeSeasonLeagueName) throws Exception
    {
        if (!judgeSeasonLeagueMap.containsKey(judgeSeasonLeagueName))
        {
            throw new Exception("JudgeSeasonLeague not found");
        }
        return judgeSeasonLeagueMap.get(judgeSeasonLeagueName);
    }

    @Override
    public void removeJudgeSeasonLeague(String judgeSeasonLeagueName) throws Exception {
        if (!judgeSeasonLeagueMap.containsKey(judgeSeasonLeagueName))
        {
            throw new Exception("JudgeSeasonLeague not found");
        }
        judgeSeasonLeagueMap.remove(judgeSeasonLeagueName);
    }

    @Override
    public ArrayList<String> getAllJudgeSeasonLeagueNames() throws Exception
    {
        //todo
        throw new NotImplementedException();
//        return null;
    }

    /**
     * For the tests-Clear the JudgeSeasonLeague Map from the DB.
     */
    @Override
    public void deleteAll()
    {
        judgeSeasonLeagueMap.clear();
    }


}
