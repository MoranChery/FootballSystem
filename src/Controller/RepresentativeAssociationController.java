package Controller;

import Data.*;
import Model.Enums.JudgeType;
import Model.Enums.QualificationJudge;
import Model.JudgeSeasonLeague;
import Model.League;
import Model.Season;
import Model.SeasonLeague;
import Model.UsersTypes.Judge;

public class RepresentativeAssociationController
{
    private LeagueDb leagueDb;
    private SeasonDb seasonDb;
    private SeasonLeagueDb seasonLeagueDb;
    private JudgeDb judgeDb;
    private JudgeSeasonLeagueDb judgeSeasonLeagueDb;

    public RepresentativeAssociationController()
    {
        this.leagueDb = LeagueDbInMemory.getInstance();
        this.seasonDb = SeasonDbInMemory.getInstance();
        this.seasonLeagueDb = SeasonLeagueDbInMemory.getInstance();
        this.judgeDb = JudgeDbInMemory.getInstance();
        this.judgeSeasonLeagueDb = JudgeSeasonLeagueDbInMemory.getInstance();
    }

    /**
     * Will receive from the Service the league's name, create the League.
     * Will continue to Data.
     * @param leagueName-name of the new League.
     * @throws Exception-if details are incorrect.
     */
    public void createLeague(String leagueName) throws Exception
    {
        if(leagueName == null)
        {
            throw new NullPointerException("One or more of the League details incorrect");
        }
        League league = new League(leagueName);
        leagueDb.createLeague(league);
    }

    /**
     * Will receive from the Service the season's name, create the Season.
     * Will continue to Data.
     * @param seasonName-name of the new Season.
     * @throws Exception-if details are incorrect.
     */
    public void createSeason(String seasonName) throws Exception
    {
        if(seasonName == null)
        {
            throw new NullPointerException("One or more of the Season details incorrect");
        }
        Season season = new Season(seasonName);
        seasonDb.createSeason(season);
    }

    /**
     * Will receive from the UI the league's id the season's id the policy-calculateLeaguePoints's id and the policy-inlayGames's id, create SeasonLeague.
     * Will continue to Data.
     * @param leagueId-id of the League.
     * @param seasonId-id of the Season.
     * @param calculateLeaguePointsId-id of the Policy CalculateLeaguePoints.
     * @param inlayGamesId-name of the Policy InlayGamesId.
     * @throws Exception-if details are incorrect.
     */
    public void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception
    {
        if(leagueId == null || seasonId == null || calculateLeaguePointsId == null || inlayGamesId == null)
        {
            throw new NullPointerException("One or more of the SeasonLeague details incorrect");
        }
        SeasonLeague seasonLeague = new SeasonLeague(seasonId, leagueId, calculateLeaguePointsId, inlayGamesId);
        seasonLeagueDb.createSeasonLeague(seasonLeague);
        seasonDb.addSeasonLeague(seasonLeague);
        leagueDb.addSeasonLeague(seasonLeague);
    }

    /**
     * Will receive from the Service the judge's details, want to create Judge.
     * Will continue to Data.
     * @param username-username of the new Judge.
     * @param password-password of the new Judge.
     * @param id-id of the new Judge.
     * @param firstName-firstName of the new Judge.
     * @param lastName-lastName of the new Judge.
     * @param qualificationJudge-qualification of the new Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudge(String username, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge, JudgeType theJudgeType) throws Exception
    {
        if(username == null || password == null || id == null || firstName == null || lastName == null || qualificationJudge == null)
        {
            throw new NullPointerException("One or more of the Judge details incorrect");
        }
        Judge judge = new Judge(username, password, id, firstName, lastName, qualificationJudge,theJudgeType);
        judgeDb.createJudge(judge);
    }

    /**
     * Will receive from the Service the judge's emailAddress, want to remove Judge.
     * Will continue to Data.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void removeJudge(String judgeEmailAddress) throws Exception
    {
        if(judgeEmailAddress == null)
        {
            throw new NullPointerException("One or more of the Judge details incorrect");
        }
        judgeDb.removeJudge(judgeEmailAddress);
    }

    /**
     * Will receive from the Service the season's id, the league's id and the judge's id, create the JudgeSeasonLeague.
     * Will continue to Data.
     * @param seasonId-id of the Season.
     * @param leagueId-id of the League.
     * @param judgeEmailAddress-emailAddress of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudgeSeasonLeague(Integer seasonId, Integer leagueId, String judgeEmailAddress) throws Exception
    {
        if(seasonId == null || leagueId == null || judgeEmailAddress == null)
        {
            throw new NullPointerException("One or more of the JudgeSeasonLeague details incorrect");
        }
        Integer seasonLeagueId = seasonDb.getSeasonLeagueIdBySeasonAndByLeague(seasonId, leagueId);

        JudgeSeasonLeague judgeSeasonLeague = new JudgeSeasonLeague(seasonLeagueId, judgeEmailAddress);
        judgeSeasonLeagueDb.createJudgeSeasonLeague(judgeSeasonLeague);
        seasonLeagueDb.createJudgeSeasonLeague(judgeSeasonLeague);
        judgeDb.createJudgeSeasonLeague(judgeSeasonLeague);
    }

    /**
     * Will receive from the Service the season's id, the league's id and the calculateLeaguePoints's id,
     * want to set Policy CalculateLeaguePointsId of thr SeasonLeague.
     * Will continue to Data.
     * @param seasonId-id of the Season.
     * @param leagueId-id of the League.
     * @param calculateLeaguePointsId-id of the new Policy CalculateLeaguePoints.
     * @throws Exception-if details are incorrect.
     */
    public void changeCalculateLeaguePointsPolicy(Integer seasonId, Integer leagueId, Integer calculateLeaguePointsId) throws Exception
    {
        if(seasonId == null || leagueId == null || calculateLeaguePointsId == null)
        {
            throw new NullPointerException();
        }
        Integer seasonLeagueId = seasonDb.getSeasonLeagueIdBySeasonAndByLeague(seasonId, leagueId);
        seasonLeagueDb.changeCalculateLeaguePointsPolicy(seasonLeagueId, calculateLeaguePointsId);
    }
}
