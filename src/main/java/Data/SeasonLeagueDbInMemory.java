package Data;

import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.JudgeSeasonLeague;
import Model.SeasonLeague;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeasonLeagueDbInMemory implements SeasonLeagueDb
{
    /*structure like the DB of SeasonLeagues*/
    private Map<String, SeasonLeague> seasonLeagueMap;

    private static SeasonLeagueDbInMemory ourInstance = new SeasonLeagueDbInMemory();

    public static SeasonLeagueDbInMemory getInstance() { return ourInstance; }

    public SeasonLeagueDbInMemory() { seasonLeagueMap = new HashMap<>();
        SeasonLeague seasonLeague11 = new SeasonLeague("seasonName1", "leagueName1", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague12 = new SeasonLeague("seasonName1", "leagueName2", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague13 = new SeasonLeague("seasonName1", "leagueName3", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague21 = new SeasonLeague("seasonName2", "leagueName1", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague22 = new SeasonLeague("seasonName2", "leagueName2", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague23 = new SeasonLeague("seasonName2", "leagueName3", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        seasonLeagueMap.put(seasonLeague11.getSeasonLeagueName(),seasonLeague11);
        seasonLeagueMap.put(seasonLeague12.getSeasonLeagueName(),seasonLeague12);
        seasonLeagueMap.put(seasonLeague13.getSeasonLeagueName(),seasonLeague13);
        seasonLeagueMap.put(seasonLeague21.getSeasonLeagueName(),seasonLeague21);
        seasonLeagueMap.put(seasonLeague22.getSeasonLeagueName(),seasonLeague22);
        seasonLeagueMap.put(seasonLeague23.getSeasonLeagueName(),seasonLeague23);
    }


    /**
     * Will receive from the Controller the SeasonLeague, add SeasonLeague to Data.
     *
     * for the tests - create SeasonLeague in DB
     *
     * @param seasonLeague-the new SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public void insertSeasonLeague(SeasonLeague seasonLeague) throws Exception
    {
        if(seasonLeagueMap.containsKey(seasonLeague.getSeasonLeagueName()))
        {
            throw new Exception("SeasonLeague already exists in the system");
        }
        seasonLeagueMap.put(seasonLeague.getSeasonLeagueName(), seasonLeague);
    }

    /**
     * Will receive from the Controller the seasonLeague's name, return the SeasonLeague.
     *
     * "pull" SeasonLeague from DB.
     *
     * @param seasonLeagueName-name of the SeasonLeague.
     * @return the SeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public SeasonLeague getSeasonLeague(String seasonLeagueName) throws Exception
    {
        if (!seasonLeagueMap.containsKey(seasonLeagueName))
        {
            throw new Exception("SeasonLeague not found");
        }
        return seasonLeagueMap.get(seasonLeagueName);
    }

    /**
     * Will receive from the Controller the JudgeSeasonLeague,
     * add to judgeEmailAddress_JudgeSeasonLeagueName Map the judgeEmailAddress and the judgeSeasonLeagueName of the specific SeasonLeague.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception
    {
        if (!seasonLeagueMap.containsKey(judgeSeasonLeague.getSeasonLeagueName()))
        {
            throw new Exception("SeasonLeague not found");
        }
        seasonLeagueMap.get(judgeSeasonLeague.getSeasonLeagueName()).getJudgeEmailAddress_JudgeSeasonLeagueName().put(judgeSeasonLeague.getJudgeEmailAddress(), judgeSeasonLeague.getJudgeSeasonLeagueName());
    }

    /**
     * Will receive from the Controller the seasonLeague's name and the calculateLeaguePoints,
     * want to set Policy CalculateLeaguePoints of thr SeasonLeague.
     *
     * @param seasonLeagueName-name of the SeasonLeague.
     * @param calculateLeaguePoints-new Policy CalculateLeaguePoints.
     */
    public void updateCalculateLeaguePointsPolicy(String seasonLeagueName, CalculateLeaguePoints calculateLeaguePoints) throws Exception
    {
        if(!seasonLeagueMap.containsKey(seasonLeagueName))
        {
            throw new Exception("SeasonLeague not found");
        }
        seasonLeagueMap.get(seasonLeagueName).setCalculateLeaguePoints(calculateLeaguePoints);
    }

    @Override
    public ArrayList<String> getAllSeasonLeagueNames() throws Exception
    {
        //todo
        throw new NotImplementedException();
//        return null;
    }

    @Override
    public ArrayList<SeasonLeague> getAllSeasonLeagueObjects() throws Exception
    {
        //todo
        throw new NotImplementedException();
//        return null;
    }

    /**
     * For the tests-Clear the SeasonLeague Map from the DB.
     */
    @Override
    public void deleteAll()
    {
        seasonLeagueMap.clear();
    }
}
