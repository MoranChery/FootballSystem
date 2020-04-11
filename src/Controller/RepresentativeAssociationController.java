package Controller;

import Data.*;

public class RepresentativeAssociationController
{
    private LeagueDb leagueDb;
    private SeasonDb seasonDb;
    private SeasonLeagueDb seasonLeagueDb;
    private JudgeDb judgeDb;

    public RepresentativeAssociationController(/*LeagueDb leagueDb, SeasonDb seasonDb, SeasonLeagueDb seasonLeagueDb, JudgeDb judgeDb*/)
    {
//        this.leagueDb = leagueDb;
//        this.seasonDb = seasonDb;
//        this.seasonLeagueDb = seasonLeagueDb;
//        this.judgeDb = judgeDb;
//
        this.leagueDb = LeagueDbInMemory.getInstance();
        this.seasonDb = SeasonDbInMemory.getInstance();
        this.seasonLeagueDb = SeasonLeagueDbInMemory.getInstance();
        this.judgeDb = JudgeDbInMemory.getInstance();
    }

    /**
     * Will receive from the Service the league's name, want to create League.
     * Will continue to Data.
     * @param leagueName-name of the new League.
     * @throws Exception-if details are incorrect.
     */
    public void createLeague(String leagueName) throws Exception
    {
        if(leagueName == null)
        {
            throw new NullPointerException();
        }
        leagueDb.createLeague(leagueName);
    }

    /**
     * Will receive from the Service the season's name, want to create Season.
     * Will continue to Data.
     * @param seasonName-name of the new Season.
     * @throws Exception-if details are incorrect.
     */
    public void createSeason(String seasonName) throws Exception
    {
        if(seasonName == null)
        {
            throw new NullPointerException();
        }
        seasonDb.createSeason(seasonName);
    }

    /**
     * Will receive from the Service the league's id and the season's id, want to create SeasonLeague-
     * combine exists League to exists Season, and define both kind of Policy to this specific SeasonLeague.
     * Will continue to Data.
     * @param leagueId-id of the League.
     * @param seasonId-id of the Season.
     * @param calculateLeaguePointsId-id of the Policy CalculateLeaguePoints.
     * @param inlayGamesId-name of the Policy InlayGamesId.
     * @throws Exception-if details are incorrect.
     */
    public void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception
    {
        if(leagueId == null || seasonId == null)
        {
            throw new NullPointerException();
        }
        seasonLeagueDb.createSeasonLeague(leagueId, seasonId, calculateLeaguePointsId, inlayGamesId);
    }

    /**
     * Will receive from the Service the judge's name, want to create Judge.
     * Will continue to Data.
     * @param judgeName-name of the new Judge.
     * @param judgeQualification-qualification of the new Judge.
     * @throws Exception-if details are incorrect.
     */
    public void createJudge(String judgeName, String judgeQualification) throws Exception
    {
        if(judgeName == null || judgeQualification == null)
        {
            throw new NullPointerException();
        }
        judgeDb.createJudge(judgeName, judgeQualification);
    }

    /**
     * Will receive from the Service the judge's id, want to remove Judge.
     * Will continue to Data.
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void removeJudge(Integer judgeId) throws Exception
    {
        if(judgeId == null)
        {
            throw new NullPointerException();
        }
        judgeDb.removeJudge(judgeId);
    }

    /**
     * Will receive from the Service the season's id, the league's id and the judge's id,
     * want to inlay Judge to SeasonLeague.
     * Will continue to Data.
     * @param seasonId-id of the Season.
     * @param leagueId-id of the League.
     * @param judgeId-id of the Judge.
     * @throws Exception-if details are incorrect.
     */
    public void inlayJudgeToSeasonLeague(Integer seasonId, Integer leagueId, Integer judgeId) throws Exception
    {
        if(seasonId == null || leagueId == null || judgeId == null)
        {
            throw new NullPointerException();
        }
        Integer seasonLeagueId = seasonLeagueDb.getSeasonLeagueIdBySeasonAndByLeague(seasonId,leagueId);
        seasonLeagueDb.inlayJudgeToSeasonLeague(seasonLeagueId, judgeId);
        judgeDb.inlayJudgeToSeasonLeague(seasonLeagueId, judgeId);
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
        Integer seasonLeagueId = seasonLeagueDb.getSeasonLeagueIdBySeasonAndByLeague(seasonId,leagueId);
        seasonLeagueDb.changeCalculateLeaguePointsPolicy(seasonLeagueId, calculateLeaguePointsId);
    }
}
