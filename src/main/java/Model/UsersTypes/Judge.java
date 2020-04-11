package Model.UsersTypes;

import java.util.ArrayList;
import java.util.List;

public class Judge extends Subscriber
{
    static int judgeIdCounter = 0;

    private Integer id;
    private String judgeName;
    private String judgeQualification;
    private List<Integer> inlaySeasonLeagueIdList;

    public Judge(String judgeName, String judgeQualification)
    {
        this.judgeName = judgeName;
        this.id = judgeIdCounter;
        this.judgeQualification = judgeQualification;
        this.inlaySeasonLeagueIdList = new ArrayList<Integer>();

        judgeIdCounter++;
    }

    //region Getters
    public Integer getId() {
        return id;
    }

    public String getJudgeName() { return judgeName; }

    public String getJudgeQualification() { return judgeQualification; }

    public List<Integer> getInlaySeasonLeagueIdList() { return inlaySeasonLeagueIdList; }
    //endregion

    //region Setters
//    public void setId(Integer id) { this.id = id; }

//    public void setJudgeName(String judgeName) { this.judgeName = judgeName; }

//    public void setJudgeQualification(String judgeQualification) { this.judgeQualification = judgeQualification; }

//    public void setInlaySeasonLeagueId(List<Integer> inlaySeasonLeagueId) { this.inlaySeasonLeagueId = inlaySeasonLeagueId; }
    //endregion
}
