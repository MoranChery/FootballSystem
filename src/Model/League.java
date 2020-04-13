package Model;

import java.util.HashSet;
import java.util.Set;

public class League
{
    static int leagueIdCounter = 0;

    private Integer leagueId;
    private String leagueName;
    private Set<Integer> seasonLeagueIdList;

    public League(String leagueName)
    {
        this.leagueName = leagueName;
        this.seasonLeagueIdList = new HashSet<>();
        this.leagueId = leagueIdCounter;

        leagueIdCounter++;
    }

    //region Getters
    public Integer getLeagueId() {
        return leagueId;
    }

    public String getLeagueName() { return leagueName; }

    public Set<Integer> getSeasonLeagueIdList() { return seasonLeagueIdList; }
    //endregion

    //region Setters
//    public void setLeagueId(Integer leagueId) { this.leagueId = leagueId; }

//    public void setLeagueName(String leagueName) { this.leagueName = leagueName; }

//    public void setSeasonLeagueIdList(Set<Integer> seasonLeagueIdList) { this.seasonLeagueIdList = seasonLeagueIdList; }
    //endregion
}
