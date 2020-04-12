package Model;

public class League
{
    static int leagueIdCounter = 0;

    private Integer leagueId;
    private String leagueName;

    public League(String leagueName)
    {
        this.leagueName = leagueName;
        this.leagueId = leagueIdCounter;

        leagueIdCounter++;
    }

    //region Getters
    public Integer getLeagueId() {
        return leagueId;
    }

    public String getLeagueName() { return leagueName; }
    //endregion

    //region Setters
//    public void setLeagueId(Integer leagueId) { this.leagueId = leagueId; }

//    public void setLeagueName(String leagueName) { this.leagueName = leagueName; }
    //endregion
}
