package Model;

import java.util.HashMap;
import java.util.Map;

public class Season
{
    private String seasonName;
    private Map<String, String> leagueName_SeasonLeagueName;

    public Season(String seasonName)
    {
        this.seasonName = seasonName;
        this.leagueName_SeasonLeagueName = new HashMap<>();
    }

    //region Getters
    public String getSeasonName() { return seasonName; }

    public Map<String, String> getLeagueName_SeasonLeagueId() { return leagueName_SeasonLeagueName; }
    //endregion

    //region Setters
    public void setLeagueName_SeasonLeagueName(Map<String, String> leagueName_SeasonLeagueName)
    {
        this.leagueName_SeasonLeagueName = leagueName_SeasonLeagueName;
    }
    //endregion
}
