package Data;

import Model.Season;

public interface SeasonDb
{
    void createSeason(String seasonName) throws Exception;
    Season getSeason(Integer seasonId) throws Exception;
}
