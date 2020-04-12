package Data;

import Model.Enums.QualificationJudge;
import Model.UsersTypes.Judge;

public interface JudgeDb
{
    /**
     * Will receive from the Controller the judge's name, want to create Judge.
     * @param judgeName-name of the new Judge.
     * @param qualificationJudge-qualification of the new Judge.
     * @throws Exception-if details are incorrect.
     */
    void createJudge(String judgeName, QualificationJudge qualificationJudge) throws Exception;

    /**
     * Will receive from the Controller the judge's id, return the Judge.
     * @param judgeId-id of the Judge.
     * @return the Judge.
     * @throws Exception-if details are incorrect.
     */
    Judge getJudge(Integer judgeId) throws Exception;

    /**
     * Will receive from the Controller the judge's id, want to remove Judge.
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    void removeJudge(Integer judgeId) throws Exception;

    /**
     * Will receive from the Controller the seasonLeague's id and the judge's id,
     * want to inlay Judge to SeasonLeague.
     * @param seasonLeagueId-id of the SeasonLeague.
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    void inlayJudgeToSeasonLeague(Integer seasonLeagueId, Integer judgeId) throws Exception;
}
