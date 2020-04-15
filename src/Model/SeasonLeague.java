package Model;

import java.util.HashMap;
import java.util.Map;

public class SeasonLeague
{
    static int seasonLeagueIdCounter = 0;

    private Integer seasonLeagueId;
    private Integer seasonId;
    private Integer leagueId;
    private Integer calculateLeaguePointsId;
    private Integer inlayGamesId;
    private Map<String, Integer> judgeEmailAddress_JudgeSeasonLeagueId;


    public SeasonLeague(Integer seasonId, Integer leagueId, Integer calculateLeaguePointsId, Integer inlayGamesId)
    {
        this.seasonId = seasonId;
        this.leagueId = leagueId;
        this.calculateLeaguePointsId = calculateLeaguePointsId;
        this.inlayGamesId = inlayGamesId;
        this.judgeEmailAddress_JudgeSeasonLeagueId = new HashMap<>();
        this.seasonLeagueId = seasonLeagueIdCounter;

        seasonLeagueIdCounter++;
    }

    //region Getters
    public Integer getSeasonLeagueId() {
        return seasonLeagueId;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public Integer getCalculateLeaguePointsId() { return calculateLeaguePointsId; }

    public Integer getInlayGamesId() { return inlayGamesId; }

    public Map<String, Integer> getJudgeEmailAddress_JudgeSeasonLeagueId() { return judgeEmailAddress_JudgeSeasonLeagueId; }
    //endregion

    //region Setters
    public void setCalculateLeaguePointsId(Integer calculateLeaguePointsId) { this.calculateLeaguePointsId = calculateLeaguePointsId; }
    //endregion
}
