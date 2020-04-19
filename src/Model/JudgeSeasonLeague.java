package Model;

public class JudgeSeasonLeague
{
    private String judgeSeasonLeagueName;
    private String seasonLeagueName;
    private String judgeEmailAddress;

    public JudgeSeasonLeague(String seasonLeagueName, String judgeEmailAddress)
    {
        this.judgeSeasonLeagueName = seasonLeagueName + "_" + judgeEmailAddress;
        this.seasonLeagueName = seasonLeagueName;
        this.judgeEmailAddress = judgeEmailAddress;
    }

    //region Getters
    public String getJudgeSeasonLeagueName() { return judgeSeasonLeagueName; }

    public String getSeasonLeagueName() { return seasonLeagueName; }

    public String getJudgeEmailAddress() { return judgeEmailAddress; }
    //endregion
}
