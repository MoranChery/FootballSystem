package Model.UsersTypes;

import Model.Enums.QualificationJudge;

import java.util.HashMap;
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

    //region Getters
    public QualificationJudge getQualificationJudge() { return qualificationJudge; }

    public Map<Integer, Integer> getSeasonLeagueId_JudgeSeasonLeagueId() { return seasonLeagueId_JudgeSeasonLeagueId; }
    //endregion
}
