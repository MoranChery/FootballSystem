package Model;

import Model.Enums.GameEventType;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class GameEvent {
    private String gameId;
    private String eventId;
    private SimpleDateFormat gameDate;
    private Time eventTime;
    private Integer eventMinute;
    private GameEventType gameEventType;
    private String description;

    public GameEvent(String gameId, SimpleDateFormat gameDate, Time eventTime, Integer eventMinute, GameEventType gameEventType, String description) {
        this.eventId = UUID.randomUUID().toString();
        this.gameDate = gameDate;
        this.eventTime = eventTime;
        this.eventMinute = eventMinute;
        this.gameEventType = gameEventType;
        this.description = description;
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public SimpleDateFormat getGameDate() {
        return gameDate;
    }

    public void setGameDate(SimpleDateFormat gameDate) {
        this.gameDate = gameDate;
    }

    public Time getEventTime() {
        return eventTime;
    }

    public void setEventTime(Time eventTime) {
        this.eventTime = eventTime;
    }

    public Integer getEventMinute() {
        return eventMinute;
    }

    public void setEventMinute(Integer eventMinute) {
        this.eventMinute = eventMinute;
    }

    public GameEventType getGameEventType() {
        return gameEventType;
    }

    public void setGameEventType(GameEventType gameEventType) {
        this.gameEventType = gameEventType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
