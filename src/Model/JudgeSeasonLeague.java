package Model;

public class JudgeSeasonLeague
{
    static int judgeSeasonLeagueIdCounter = 0;

    private Integer judgeSeasonLeagueId;
    private Integer seasonLeagueId;
    private String judgeEmailAddress;

    public JudgeSeasonLeague(Integer seasonLeagueId, String judgeEmailAddress)
    {
        this.seasonLeagueId = seasonLeagueId;
        this.judgeEmailAddress = judgeEmailAddress;
        this.judgeSeasonLeagueId = judgeSeasonLeagueIdCounter;

        judgeSeasonLeagueIdCounter++;
    }

    //region Getters
    public Integer getJudgeSeasonLeagueId() { return judgeSeasonLeagueId; }

    public Integer getSeasonLeagueId() { return seasonLeagueId; }

    public String getJudgeEmailAddress() { return judgeEmailAddress; }
    //endregion
}
