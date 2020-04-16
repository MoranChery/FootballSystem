package Model;

import java.util.HashMap;
import java.util.Map;

public class League
{
    static int leagueIdCounter = 0;

    private Integer leagueId;
    private String leagueName;
    private Map<Integer, Integer> seasonId_SeasonLeagueId;

    public League(String leagueName)
    {
        this.leagueName = leagueName;
        this.seasonId_SeasonLeagueId = new HashMap<>();
        this.leagueId = leagueIdCounter;

        leagueIdCounter++;
    }

    //region Getters
    public Integer getLeagueId() {
        return leagueId;
    }

    public String getLeagueName() { return leagueName; }

    public Map<Integer, Integer> getSeasonId_SeasonLeagueId() { return seasonId_SeasonLeagueId; }
    //endregion
}
