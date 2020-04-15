package Model.UsersTypes;

import Model.Enums.QualificationJudge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Judge extends Subscriber
{
    private QualificationJudge qualificationJudge;
    private Map<Integer, Integer> seasonLeagueId_JudgeSeasonLeagueId;

    public Judge(String username, String password,Integer id, String firstName, String lastName, QualificationJudge qualificationJudge)
    {
        setRegisteringDetails(username,password,id,firstName,lastName);
        this.qualificationJudge = qualificationJudge;
        this.seasonLeagueId_JudgeSeasonLeagueId = new HashMap<>();
    }


    public QualificationJudge getQualificationJudge() { return qualificationJudge; }

    public void setQualificationJudge(QualificationJudge qualificationJudge) {
        this.qualificationJudge = qualificationJudge;
    }

    public Map<Integer, Integer> getSeasonLeagueId_JudgeSeasonLeagueId() { return seasonLeagueId_JudgeSeasonLeagueId; }
    //endregion


    //endregion

    //region Setters
//    public void setId(Integer id) { this.id = id; }

//    public void setJudgeName(String judgeName) { this.judgeName = judgeName; }

//    public void setQualification(Qualification qualificationJudge) { this.qualificationJudge = qualificationJudge; }

//    public void setInlaySeasonLeagueId(List<Integer> inlaySeasonLeagueId) { this.inlaySeasonLeagueId = inlaySeasonLeagueId; }
    //endregion
}
