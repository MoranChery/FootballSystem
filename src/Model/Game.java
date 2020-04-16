package Model;


import Model.UsersTypes.Judge;

import java.text.SimpleDateFormat;
import java.util.Set;


public class Game {
    private Integer gameID;
    private SimpleDateFormat gameDate;
    private SeasonLeague seasonLeague;
    private Team hostTeam;
    private Team guestTeam;
    private Court court;
    private Integer hostTeamScore;
    private Integer guestTeamScore;
    private Set<Judge> judgesOfTheGameList;
    private Set<GameEvent> eventsInTheGameList;

    public Game(Integer gameID, SimpleDateFormat gameDate, SeasonLeague seasonLeague, Team hostTeam, Team guestTeam, Court court, Integer hostTeamScore, Integer guestTeamScore, Set<Judge> judgesOfTheGameList, Set<GameEvent> eventsInTheGameList) {
        this.gameID = gameID;
        this.gameDate = gameDate;
        this.seasonLeague = seasonLeague;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.court = court;
        this.hostTeamScore = hostTeamScore;
        this.guestTeamScore = guestTeamScore;
        this.judgesOfTheGameList = judgesOfTheGameList;
        this.eventsInTheGameList = eventsInTheGameList;
    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public SimpleDateFormat getGameDate() {
        return gameDate;
    }

    public void setGameDate(SimpleDateFormat gameDate) {
        this.gameDate = gameDate;
    }

    public SeasonLeague getSeasonLeague() {
        return seasonLeague;
    }

    public void setSeasonLeague(SeasonLeague seasonLeague) {
        this.seasonLeague = seasonLeague;
    }

    public Team getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(Team hostTeam) {
        this.hostTeam = hostTeam;
    }

    public Team getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(Team guestTeam) {
        this.guestTeam = guestTeam;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Integer getHostTeamScore() {
        return hostTeamScore;
    }

    public void setHostTeamScore(Integer hostTeamScore) {
        this.hostTeamScore = hostTeamScore;
    }

    public Integer getGuestTeamScore() {
        return guestTeamScore;
    }

    public void setGuestTeamScore(Integer guestTeamScore) {
        this.guestTeamScore = guestTeamScore;
    }

    public Set<Judge> getJudgesOfTheGameList() {
        return judgesOfTheGameList;
    }

    public void setJudgesOfTheGameList(Set<Judge> judgesOfTheGameList) {
        this.judgesOfTheGameList = judgesOfTheGameList;
    }

    public Set<GameEvent> getEventsInTheGameList() {
        return eventsInTheGameList;
    }

    public void setEventsInTheGameList(Set<GameEvent> eventsInTheGameList) {
        this.eventsInTheGameList = eventsInTheGameList;
    }
}