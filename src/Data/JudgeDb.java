package Data;

import Model.Enums.QualificationJudge;
import Model.JudgeSeasonLeague;
import Model.UsersTypes.Judge;
import jdk.nashorn.internal.runtime.ECMAException;

public interface JudgeDb
{
    /**
     * Will receive from the Controller the Judge, add Judge to Data.
     * @param judge-the new Judge.
     * @throws Exception-if details are incorrect.
     */
    void createJudge(Judge judge) throws Exception;

    /**
     * Will receive from the Controller the judge's emailAddress, return the Judge.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @return the Judge.
     * @throws Exception-if details are incorrect.
     */
    Judge getJudge(String judgeEmailAddress) throws Exception;

    /**
     * Will receive from the Controller the judge's emailAddress, remove Judge from Data.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    void removeJudge(String judgeEmailAddress) throws Exception;

    /**
     * Will receive from the Controller the JudgeSeasonLeague,
     * add to seasonLeagueId_JudgeSeasonLeagueId Map the seasonLeagueId and the judgeSeasonLeagueId of the specific Judge.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception;
}
