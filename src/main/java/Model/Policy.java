package Model;

public abstract class Policy
{
//    static int policyIdCounter = 0;

    private Integer policyId;
    private String policyName;
    private Integer seasonLeagueId;

//    private Integer calculateLeaguePointsId;
//    private Integer inlayGamesId;

    public Policy(Integer policyId, String policyName, Integer seasonLeagueId)
    {
        this.policyId = policyId;
        this.policyName = policyName;
        this.seasonLeagueId = seasonLeagueId;
    }

    //region Getters
    public Integer getPolicyId() { return policyId; }

    public Integer getSeasonLeagueId() { return seasonLeagueId; }

    public String getPolicyName() { return policyName; }

    //    public Integer getCalculateLeaguePointsId() { return calculateLeaguePointsId; }

//    public Integer getInlayGamesId() { return inlayGamesId; }
    //endregion

    //region Setters
//    public void setPolicyId(Integer policyId) { this.policyId = policyId; }

//    public void setSeasonLeagueId(Integer seasonLeagueId) { this.seasonLeagueId = seasonLeagueId; }

//    public void setPolicyName(String policyName) { this.policyName = policyName; }

//    public void setCalculateLeaguePointsId(Integer calculateLeaguePointsId) { this.calculateLeaguePointsId = calculateLeaguePointsId; }

//    public void setInlayGamesId(Integer inlayGamesId) { this.inlayGamesId = inlayGamesId; }
    //endregion
}
