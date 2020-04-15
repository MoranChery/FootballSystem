package Data;

import Model.Enums.QualificationJudge;
import Model.UsersTypes.Judge;

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

    void wantToEditPassword(String judgeMail, String newPassword) throws Exception;
    void wantToEditFirstName(String judgeMail, String newFirstName) throws Exception;
    void wantToEditLastName(String judgeMail, String newLastName) throws Exception;
    void wantToEditQualification(String judgeMail, String newQualification) throws Exception;
//
//    /**
//     * Will receive from the Controller the seasonLeague's id and the judge's id,
//     * want to inlay Judge to SeasonLeague.
//     * @param seasonLeagueId-id of the SeasonLeague.
//     * @param judgeEmailAddress-emailAddress of the Judge.
//     * @throws Exception-if details are incorrect.
//     */
//    void inlayJudgeToSeasonLeague(Integer seasonLeagueId, String judgeEmailAddress) throws Exception;
}
