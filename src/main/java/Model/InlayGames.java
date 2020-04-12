package Model;

public class InlayGames extends Policy
{
    static int inlayGamesIdCounter = 0;

    private Integer inlayGamesId;
//    private String inlayGamesName;
//    private Integer seasonLeagueId;

    public InlayGames(String inlayGamesName, Integer seasonLeagueId)
    {
        super(inlayGamesName, seasonLeagueId);
//        this.inlayGamesName = inlayGamesName;
//        this.seasonLeagueId = seasonLeagueId;
        this.inlayGamesId = inlayGamesIdCounter;

        inlayGamesIdCounter++;
    }

    //region Getters
    public Integer getInlayGamesId() { return inlayGamesId; }

//    public String getInlayGamesName() { return inlayGamesName; }

//    public Integer getSeasonLeagueId() { return seasonLeagueId; }
    //endregion

    //region Setters
//    public void setInlayGamesId(Integer inlayGamesId) { this.inlayGamesId = inlayGamesId; }

//    public void setInlayGamesName(String inlayGamesName) { this.inlayGamesName = inlayGamesName; }

//    public void setSeasonLeagueId(Integer seasonLeagueId) { this.seasonLeagueId = seasonLeagueId; }
    //endregion
}
