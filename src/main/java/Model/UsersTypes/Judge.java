package Model.UsersTypes;

import Model.Enums.CoachRole;
import Model.Enums.QualificationCoach;
import Model.Enums.QualificationJudge;
import Model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Judge extends Subscriber {
    private QualificationJudge qualificationJudge;
    private Map<String, String> seasonLeagueName_JudgeSeasonLeagueName;
    private List<String> theJudgeGameList; // gameId_game

    public Judge(String username, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) {
        setRegisteringDetails(username, password, id, firstName, lastName);
        this.qualificationJudge = qualificationJudge;
//        this.seasonLeagueId_JudgeSeasonLeagueId = new HashMap<>();
        this.seasonLeagueName_JudgeSeasonLeagueName = new HashMap<>();
        theJudgeGameList = new ArrayList<>();
    }

    public Judge(String emailAddress, Integer coachId, String firstName, String lastName, QualificationJudge qualificationJudge) {
        setRegisteringDetails(emailAddress,coachId,firstName,lastName);
        this.qualificationJudge = qualificationJudge;
        this.seasonLeagueName_JudgeSeasonLeagueName = new HashMap<>();
        theJudgeGameList = new ArrayList<>();
    }


    public QualificationJudge getQualificationJudge() {
        return qualificationJudge;
    }

    public void setQualificationJudge(QualificationJudge qualificationJudge) {
        this.qualificationJudge = qualificationJudge;
    }

    //    public Map<Integer, Integer> getSeasonLeagueId_JudgeSeasonLeagueId() { return seasonLeagueId_JudgeSeasonLeagueId; }
    public Map<String, String> getSeasonLeagueName_JudgeSeasonLeagueName() {
        return seasonLeagueName_JudgeSeasonLeagueName;
    }
    //endregion

    public void setTheJudgeGameList(List<String> theJudgeGameList) {
        this.theJudgeGameList = theJudgeGameList;
    }

    public List<String> getTheJudgeGameList() {
        return theJudgeGameList;
    }

    public void addGameToList(Game game) {
        theJudgeGameList.add(game.getGameID());
    }
    //endregion

    //region Setters
//    public void setId(Integer id) { this.id = id; }

//    public void setJudgeName(String judgeName) { this.judgeName = judgeName; }

//    public void setQualification(Qualification qualificationJudge) { this.qualificationJudge = qualificationJudge; }

//    public void setInlaySeasonLeagueId(List<Integer> inlaySeasonLeagueId) { this.inlaySeasonLeagueId = inlaySeasonLeagueId; }

    public void setSeasonLeagueName_JudgeSeasonLeagueName(Map<String, String> seasonLeagueName_JudgeSeasonLeagueName)
    {
        this.seasonLeagueName_JudgeSeasonLeagueName = seasonLeagueName_JudgeSeasonLeagueName;
    }

    //endregion
}
