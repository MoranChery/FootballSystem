package Model.UsersTypes;

import Model.Enums.QualificationJudge;

import java.util.ArrayList;
import java.util.List;

public class Judge extends Subscriber
{

    private QualificationJudge qualificationJudge;
    private List<Integer> inlaySeasonLeagueIdList;


    public Judge(String username, String password,Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) {
        setRegisteringDetails(username,password,id,firstName,lastName);
        this.qualificationJudge = qualificationJudge;
    }


    public QualificationJudge getQualificationJudge() { return qualificationJudge; }
    public void setQualificationJudge(QualificationJudge qualificationJudge) {
        this.qualificationJudge = qualificationJudge;
    }

    public List<Integer> getInlaySeasonLeagueIdList() { return inlaySeasonLeagueIdList; }
    //endregion

    //region Setters
//    public void setId(Integer id) { this.id = id; }

//    public void setJudgeName(String judgeName) { this.judgeName = judgeName; }

//    public void setQualification(Qualification qualificationJudge) { this.qualificationJudge = qualificationJudge; }

//    public void setInlaySeasonLeagueId(List<Integer> inlaySeasonLeagueId) { this.inlaySeasonLeagueId = inlaySeasonLeagueId; }
    //endregion
}
