package Model;

import java.util.HashMap;
import java.util.Map;

public class Season
{
    static int seasonIdCounter = 0;

    private Integer seasonId;
    private String seasonName;
    private Map<Integer, Integer> leagueId_SeasonLeagueId;

    public Season(String leagueName)
    {
        this.seasonName = leagueName;
        this.leagueId_SeasonLeagueId = new HashMap<>();
        this.seasonId = seasonIdCounter;

        seasonIdCounter++;
    }

    //region Getters
    public Integer getSeasonId() { return seasonId; }

    public String getSeasonName() { return seasonName; }

    public Map<Integer, Integer> getLeagueId_SeasonLeagueId() { return leagueId_SeasonLeagueId; }
    //endregion
}
