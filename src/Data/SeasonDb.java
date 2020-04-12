package Data;

import Model.Season;

public interface SeasonDb
{
    /**
     * Will receive from the Controller the season's name, want to create Season.
     * @param seasonName-name of the new Season.
     * @throws Exception-if details are incorrect.
     */
    void createSeason(String seasonName) throws Exception;

    /**
     * Will receive from the Controller the season's id, return the Season.
     * @param seasonId-id of the Season.
     * @return the Season.
     * @throws Exception-if details are incorrect.
     */
    Season getSeason(Integer seasonId) throws Exception;
}
