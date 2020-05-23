package Data;

import Model.Game;
import Model.JudgeSeasonLeague;
import Model.UsersTypes.Judge;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface JudgeDb extends Db
{
    /**
     * Will receive from the Controller the Judge, add Judge to Data.
     * @param judge-the new Judge.
     * @throws Exception-if details are incorrect.
     */
    void insertJudge(Judge judge) throws Exception;

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
    void wantToEditQualification(String judgeMail, String newQualification) throws Exception;

    /**
     * Will receive from the Controller the JudgeSeasonLeague,
     * add to seasonLeagueId_JudgeSeasonLeagueId Map the seasonLeagueId and the judgeSeasonLeagueId of the specific Judge.
     * @param judgeSeasonLeague-the new JudgeSeasonLeague.
     * @throws Exception-if details are incorrect.
     */
    void createJudgeSeasonLeague(JudgeSeasonLeague judgeSeasonLeague) throws Exception;
    void addGameToTheJudge(String judgeMail, Game gameToAdd) throws Exception;

    List<String> getJudgeGames(String judgeId) throws SQLException;

    ArrayList<String> getAllJudgeEmailAddress() throws Exception;
}
