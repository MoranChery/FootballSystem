package Service;

import Controller.RepresentativeAssociationController;

public class RepresentativeAssociationService
{
    RepresentativeAssociationController representativeAssociationController;

    /**
     * Will receive from the UI the league's name, want to create League.
     * Will continue to controller.
     * @param leagueName
     * @throws Exception
     */
    public void createLeague(String leagueName/*, Integer playerId*/) throws Exception
    {
        representativeAssociationController.createLeague(leagueName/*,playerId*/);
    }

    /**
     * Will receive from the UI the season's name, want to create Season.
     * Will continue to controller.
     * @param seasonName
     * @throws Exception
     */
    public void createSeason(String seasonName/*, Integer playerId*/) throws Exception
    {
        representativeAssociationController.createSeason(seasonName/*,playerId*/);
    }

    /**
     * Will receive from the UI the league's id and the season's id, want to create SeasonLeague-
     * combine exists League to exists Season, and define both kind of Policy to this specific SeasonLeague.
     * Will continue to controller.
     *
     * @param leagueId
     * @param seasonId
     * @param calculateLeaguePointsId
     * @param inlayGamesId
     */
    public void createSeasonLeague(Integer leagueId, Integer seasonId, Integer calculateLeaguePointsId, Integer inlayGamesId) throws Exception
    {
        representativeAssociationController.createSeasonLeague(leagueId, seasonId, calculateLeaguePointsId, inlayGamesId);
//        representativeAssociationController.createSeasonLeague(leagueId, seasonId);
    }

    /**
     * Will receive from the UI the season's name, want to create Season.
     * Will continue to controller.
     * @param judgeName
     * @param judgeQualification
     * @throws Exception
     */
    public void createJudge(String judgeName, String judgeQualification/*, Integer playerId*/) throws Exception
    {
        representativeAssociationController.createJudge(judgeName, judgeQualification/*,playerId*/);
    }
}

