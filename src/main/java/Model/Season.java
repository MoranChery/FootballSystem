package Model;

public class Season
{
    static int seasonIdCounter = 0;

    private Integer seasonId;
    private String seasonName;

    public Season(String leagueName)
    {
        this.seasonName = leagueName;
        this.seasonId = seasonIdCounter;

        seasonIdCounter++;
    }

    //region Getters
    public Integer getSeasonId() { return seasonId; }

    public String getSeasonName() { return seasonName; }
    //endregion

    //region Setters
//    public void setSeasonId(Integer seasonId) { this.seasonId = seasonId; }

//    public void setSeasonName(String leagueName) { this.seasonName = leagueName; }
    //endregion
}
