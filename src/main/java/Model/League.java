package Model;

import java.util.HashMap;
import java.util.Map;

public class League
{
    private String leagueName;
    private Map<String, String> seasonName_SeasonLeagueName;

    public League(String leagueName)
    {
        this.leagueName = leagueName;
        this.seasonName_SeasonLeagueName = new HashMap<>();
    }

    //region Getters
    public String getLeagueName() { return leagueName; }

    public Map<String, String> getSeasonName_SeasonLeagueId() { return seasonName_SeasonLeagueName; }
    //endregion

    //region Setters
    public void setSeasonName_SeasonLeagueName(Map<String, String> seasonName_SeasonLeagueName)
    {
        this.seasonName_SeasonLeagueName = seasonName_SeasonLeagueName;
    }
    //endregion
}
