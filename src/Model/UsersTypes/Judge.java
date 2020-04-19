package Model.UsersTypes;

import Model.Enums.JudgeType;
import Model.Enums.QualificationJudge;
import Model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Judge extends Subscriber
{
    private QualificationJudge qualificationJudge;
//    private Map<Integer, Integer> seasonLeagueId_JudgeSeasonLeagueId;
    private Map<String, String> seasonLeagueName_JudgeSeasonLeagueName;
    private JudgeType judgeType;
    private Map<Integer, Game> theJudgeGameList; // gameId_game

    public Judge(String username, String password,Integer id, String firstName, String lastName, QualificationJudge qualificationJudge, JudgeType theJudgeType)
    {
        setRegisteringDetails(username,password,id,firstName,lastName);
        this.qualificationJudge = qualificationJudge;
//        this.seasonLeagueId_JudgeSeasonLeagueId = new HashMap<>();
        this.seasonLeagueName_JudgeSeasonLeagueName = new HashMap<>();
        judgeType = theJudgeType;
    }


    public QualificationJudge getQualificationJudge() { return qualificationJudge; }

    public void setQualificationJudge(QualificationJudge qualificationJudge) {
        this.qualificationJudge = qualificationJudge;
    }

//    public Map<Integer, Integer> getSeasonLeagueId_JudgeSeasonLeagueId() { return seasonLeagueId_JudgeSeasonLeagueId; }
    public Map<String, String> getSeasonLeagueName_JudgeSeasonLeagueName() { return seasonLeagueName_JudgeSeasonLeagueName; }
    //endregion

    public JudgeType getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(JudgeType judgeType) {
        this.judgeType = judgeType;
    }
    public Map<Integer, Game> getTheJudgeGameList() {
        return theJudgeGameList;
    }

    public void setTheJudgeGameList(Map<Integer, Game> theJudgeGameList) {
        this.theJudgeGameList = theJudgeGameList;
    }


    //endregion

    //region Setters
//    public void setId(Integer id) { this.id = id; }

//    public void setJudgeName(String judgeName) { this.judgeName = judgeName; }

//    public void setQualification(Qualification qualificationJudge) { this.qualificationJudge = qualificationJudge; }

//    public void setInlaySeasonLeagueId(List<Integer> inlaySeasonLeagueId) { this.inlaySeasonLeagueId = inlaySeasonLeagueId; }
    //endregion
}