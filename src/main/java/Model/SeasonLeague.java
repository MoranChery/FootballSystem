package Model;

import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;

import java.util.HashMap;
import java.util.Map;

public class SeasonLeague
{
    private String seasonLeagueName;
    private String seasonName;
    private String leagueName;
    private CalculateLeaguePoints calculateLeaguePoints;
    private InlayGames inlayGames;
    private Map<String, String> judgeEmailAddress_JudgeSeasonLeagueName;


    public SeasonLeague(String seasonName, String leagueName, CalculateLeaguePoints calculateLeaguePoints, InlayGames inlayGames)
    {
        this.seasonLeagueName = seasonName + "_" + leagueName;
        this.seasonName = seasonName;
        this.leagueName = leagueName;
        this.calculateLeaguePoints = calculateLeaguePoints;
        this.inlayGames = inlayGames;
        this.judgeEmailAddress_JudgeSeasonLeagueName = new HashMap<>();
    }

    //region Getters
    public String getSeasonLeagueName() { return seasonLeagueName; }

    public String getSeasonName() {
        return seasonName;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public CalculateLeaguePoints getCalculateLeaguePoints() { return calculateLeaguePoints; }

    public InlayGames getInlayGames() { return inlayGames; }

    public Map<String, String> getJudgeEmailAddress_JudgeSeasonLeagueName() { return judgeEmailAddress_JudgeSeasonLeagueName; }
    //endregion

    //region Setters
    public void setCalculateLeaguePoints(CalculateLeaguePoints calculateLeaguePoints) { this.calculateLeaguePoints = calculateLeaguePoints; }

    public void setJudgeEmailAddress_JudgeSeasonLeagueName(Map<String, String> judgeEmailAddress_JudgeSeasonLeagueName)
    {
        this.judgeEmailAddress_JudgeSeasonLeagueName = judgeEmailAddress_JudgeSeasonLeagueName;
    }
    //endregion
}
