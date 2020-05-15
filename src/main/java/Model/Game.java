package Model;


import Model.UsersTypes.Judge;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;


public class Game {
    private String gameID;
    private Date gameDate;
    private SeasonLeague seasonLeague;
    private Team hostTeam;
    private Team guestTeam;
    private Court court;
    private Integer hostTeamScore;
    private Integer guestTeamScore;
    private Set<Judge> judgesOfTheGameList;
    private GameEventsLog eventLog;
    private String majorJudge;
    private Date endGameTime;

    public Game(String gameID, Date gameDate, SeasonLeague seasonLeague, Team hostTeam, Team guestTeam, Court court, Set<Judge> judges, String majorJudge, Date endGameTime) {
        this.gameID = gameID;
        this.gameDate = gameDate;
        this.seasonLeague = seasonLeague;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.court = court;
        this.hostTeamScore = 0;
        this.guestTeamScore = 0;
        this.judgesOfTheGameList = judges;
        this.eventLog = new GameEventsLog();
        this.majorJudge = majorJudge;
        this.endGameTime = endGameTime;
    }

    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Date getEndGameTime() {
        return endGameTime;
    }

    public void setEndGameTime(Date endGameTime) {
        this.endGameTime = endGameTime;
    }

    public String getMajorJudge() {
        return majorJudge;
    }

    public void setMajorJudge(String majorJudge) {
        this.majorJudge = majorJudge;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
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
    public GameEventsLog getEventLog() {
        return eventLog;
    }

    public void setEventLog(GameEventsLog eventLog) {
        this.eventLog = eventLog;
    }

}
