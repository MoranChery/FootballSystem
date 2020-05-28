package Model;


import Model.UsersTypes.Judge;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Game {
    private String gameID;
    private Date gameDate;
    private String seasonLeague;
    private String hostTeam;
    private String guestTeam;
    private String court;
    private Integer hostTeamScore;
    private Integer guestTeamScore;
    private Set<String> judgesOfTheGameList;
    private GameEventsLog eventLog;
    private String majorJudge;
    private Date endGameTime;

    public Game(String gameID, Date gameDate,String seasonLeague, String hostTeam, String guestTeam, String court){
        this.gameID = gameID;
        this.gameDate = gameDate;
        this.seasonLeague = seasonLeague;
        this.hostTeam = hostTeam;
        this.guestTeam = guestTeam;
        this.court = court;

        this.hostTeamScore = 0;
        this.guestTeamScore = 0;
        this.judgesOfTheGameList = null;
        this.eventLog = new GameEventsLog();
        this.majorJudge = null;
//        this.endGameTime = null;
        int minute = gameDate.getMinutes();
        int hour = gameDate.getHours();
        if(minute > 30)
        {
            minute = minute - 30;
            hour = hour + 2;
        }
        else
        {
            minute = minute + 30;
            hour = hour + 1;
        }

        endGameTime = new Date();

        this.endGameTime.setHours(hour);
        this.endGameTime.setMinutes(minute);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(endGameTime);
        LocalTime timePart = LocalTime.parse(time);
        String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(gameDate);
        LocalDate datePart = LocalDate.parse(startingDate);
        LocalDateTime dt = LocalDateTime.of(datePart, timePart);
        this.endGameTime = convertToDateViaInstant(dt);
    }

    public Game(String gameID, Date gameDate, String seasonLeague, String hostTeam, String guestTeam, String court, Set<String> judges,String majorJudge,Date endGameTime) {
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
//        int minute = gameDate.getMinutes();
//        int hour = gameDate.getHours();
//        if(minute > 30)
//        {
//            minute = minute - 30;
//            hour = hour + 2;
//        }
//        else
//        {
//            minute = minute + 30;
//            hour = hour + 1;
//        }
//
//        endGameTime = new Date();
//
//        this.endGameTime.setHours(hour);
//        this.endGameTime.setMinutes(minute);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//        String time = sdf.format(endGameTime);
//        LocalTime timePart = LocalTime.parse(time);
//        String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(gameDate);
//        LocalDate datePart = LocalDate.parse(startingDate);
//        LocalDateTime dt = LocalDateTime.of(datePart, timePart);
//        this.endGameTime = convertToDateViaInstant(dt);
    }

    public Game(String gameID, Date gameDate, String seasonLeague, String hostTeam, String guestTeam, String court, Set<String> judges,String majorJudge)
    {
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

        int minute = gameDate.getMinutes();
        int hour = gameDate.getHours();
        if(minute > 30)
        {
            minute = minute - 30;
            hour = hour + 2;
        }
        else
        {
            minute = minute + 30;
            hour = hour + 1;
        }

        endGameTime = new Date();

        this.endGameTime.setHours(hour);
        this.endGameTime.setMinutes(minute);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(endGameTime);
        LocalTime timePart = LocalTime.parse(time);
        String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(gameDate);
        LocalDate datePart = LocalDate.parse(startingDate);
        LocalDateTime dt = LocalDateTime.of(datePart, timePart);
        this.endGameTime = convertToDateViaInstant(dt);
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert)
    {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
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

    public String getSeasonLeague() {
        return seasonLeague;
    }

    public void setSeasonLeague(String seasonLeague) {
        this.seasonLeague = seasonLeague;
    }

    public String getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(String guestTeam) {
        this.guestTeam = guestTeam;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
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

    public Set<String> getJudgesOfTheGameList() {
        return judgesOfTheGameList;
    }

    public void setJudgesOfTheGameList(Set<String> judgesOfTheGameList) {
        this.judgesOfTheGameList = judgesOfTheGameList;
    }
    public GameEventsLog getEventLog() {
        return eventLog;
    }

    public void setEventLog(GameEventsLog eventLog) {
        this.eventLog = eventLog;
    }

}
