package Model;

import java.util.List;

public class SeasonLeague
{
    static int seasonLeagueIdCounter = 0;

    private Integer seasonLeagueId;
    private Integer seasonId;
    private Integer leagueId;
    private Integer calculateLeaguePointsId;
    private Integer inlayGamesId;
    private List<Integer> inlayJudgeIdList;

    public SeasonLeague(Integer seasonId, Integer leagueId, Integer calculateLeaguePointsId, Integer inlayGamesId)
    {
        this.seasonId = seasonId;
        this.leagueId = leagueId;
        this.calculateLeaguePointsId = calculateLeaguePointsId;
        this.inlayGamesId = inlayGamesId;
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

    public List<Integer> getInlayJudgeIdList() { return inlayJudgeIdList; }
    //endregion

    //region Setters
//    public void setSeasonLeagueId(Integer seasonLeagueId) { this.seasonLeagueId = seasonLeagueId; }

//    public void setSeasonId(Integer seasonId) { this.seasonId = seasonId; }

//    public void setLeagueId(Integer leagueId) { this.leagueId = leagueId; }

    public void setCalculateLeaguePointsId(Integer calculateLeaguePointsId) { this.calculateLeaguePointsId = calculateLeaguePointsId; }

//    public void setInlayGamesId(Integer inlayGamesId) { this.inlayGamesId = inlayGamesId; }

//    public void setInlayJudgeIdList(List<Integer> inlayJudgeIdList) { this.inlayJudgeIdList = inlayJudgeIdList; }
    //endregion


}
