package Model;

public class CalculateLeaguePoints extends Policy
{
    static int calculateLeaguePointsIdCounter = 0;

    private Integer calculateLeaguePointsId;
//    private String calculateLeaguePointsName;
//    private Integer seasonLeagueId;

    public CalculateLeaguePoints(String calculateLeaguePointsName, Integer seasonLeagueId)
    {
        super(calculateLeaguePointsIdCounter, calculateLeaguePointsName, seasonLeagueId);
//        this.inlayGamesName = inlayGamesName;
//        this.seasonLeagueId = seasonLeagueId;
//        this.calculateLeaguePointsId = calculateLeaguePointsIdCounter;

        calculateLeaguePointsIdCounter++;
    }

    //region Getters
    public Integer getCalculateLeaguePointsId() { return calculateLeaguePointsId; }

//    public String getCalculateLeaguePointsName() { return calculateLeaguePointsName; }

//    public Integer getSeasonLeagueId() { return seasonLeagueId; }
    //endregion

    //region Setters
    public void setCalculateLeaguePointsId(Integer calculateLeaguePointsId) { this.calculateLeaguePointsId = calculateLeaguePointsId; }

//    public void setCalculateLeaguePointsName(String calculateLeaguePointsName) { this.calculateLeaguePointsName = calculateLeaguePointsName; }

//    public void setSeasonLeagueId(Integer seasonLeagueId) { this.seasonLeagueId = seasonLeagueId; }
    //endregion
}
