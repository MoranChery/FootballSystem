package Controller;

import Data.LeagueDb;
import Data.SeasonDb;
import Data.SeasonLeagueDb;
import Data.JudgeDb;

public class RepresentativeAssociationController
{
    private LeagueDb leagueDb;
    private SeasonDb seasonDb;
    private SeasonLeagueDb seasonLeagueDb;
    private JudgeDb judgeDb;

    public RepresentativeAssociationController(LeagueDb leagueDb, SeasonDb seasonDb, SeasonLeagueDb seasonLeagueDb, JudgeDb judgeDb)
    {
        this.leagueDb = leagueDb;
        this.seasonDb = seasonDb;
        this.seasonLeagueDb = seasonLeagueDb;
        this.judgeDb = judgeDb;
    }


    public void createLeague(String leagueName) throws Exception
    {
        if(leagueName == null)
        {
            throw new NullPointerException();
        }
        leagueDb.createLeague(leagueName);
    }

    public void createSeason(String seasonName) throws Exception
    {
        if(seasonName == null)
        {
            throw new NullPointerException();
        }
        seasonDb.createSeason(seasonName);
    }

    public void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception
    {
        if(leagueId == null || seasonId == null)
        {
            throw new NullPointerException();
        }
        seasonLeagueDb.createSeasonLeague(leagueId, seasonId, calculateLeaguePointsId, inlayGamesId);

    }

    public void createJudge(String judgeName, String judgeQualification) throws Exception
    {
        if(judgeName == null || judgeQualification == null)
        {
            throw new NullPointerException();
        }
        judgeDb.createJudge(judgeName, judgeQualification);
    }
}
