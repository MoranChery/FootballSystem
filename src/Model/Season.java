package Model;

import java.util.HashSet;
import java.util.Set;

public class Season
{
    static int seasonIdCounter = 0;

    private Integer seasonId;
    private String seasonName;
    private Set<Integer> seasonLeagueIdList;

    public Season(String leagueName)
    {
        this.seasonName = leagueName;
        this.seasonLeagueIdList = new HashSet<>();
        this.seasonId = seasonIdCounter;

        seasonIdCounter++;
    }

    //region Getters
    public Integer getSeasonId() { return seasonId; }

    public String getSeasonName() { return seasonName; }

    public Set<Integer> getSeasonLeagueIdList() { return seasonLeagueIdList; }
    //endregion

    //region Setters
//    public void setSeasonId(Integer seasonId) { this.seasonId = seasonId; }

//    public void setSeasonName(String leagueName) { this.seasonName = leagueName; }

//    public void setSeasonLeagueIdList(Set<Integer> seasonLeagueIdList) { this.seasonLeagueIdList = seasonLeagueIdList; }
    //endregion
}
