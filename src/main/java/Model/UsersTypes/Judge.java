package Model.UsersTypes;

import Model.Enums.QualificationJudge;

import java.util.ArrayList;
import java.util.List;

public class Judge extends Subscriber
{
    static int judgeIdCounter = 0;

    private Integer id;
    private String judgeName;
    private QualificationJudge qualificationJudge;
    private List<Integer> inlaySeasonLeagueIdList;

    public Judge(String judgeName, QualificationJudge qualificationJudge)
    {
        this.judgeName = judgeName;
        this.id = judgeIdCounter;
        this.qualificationJudge = qualificationJudge;
        this.inlaySeasonLeagueIdList = new ArrayList<Integer>();

        judgeIdCounter++;
    }

    //region Getters
    public Integer getId() {
        return id;
    }

    public String getJudgeName() { return judgeName; }

    public QualificationJudge getQualificationJudge() { return qualificationJudge; }

    public List<Integer> getInlaySeasonLeagueIdList() { return inlaySeasonLeagueIdList; }
    //endregion

    //region Setters
//    public void setId(Integer id) { this.id = id; }

//    public void setJudgeName(String judgeName) { this.judgeName = judgeName; }

//    public void setQualification(Qualification qualificationJudge) { this.qualificationJudge = qualificationJudge; }

//    public void setInlaySeasonLeagueId(List<Integer> inlaySeasonLeagueId) { this.inlaySeasonLeagueId = inlaySeasonLeagueId; }
    //endregion
}
