package Model;

import Model.Enums.GameEventType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class GameEvent {
    private String gameId;
    private String eventId;
    private Date gameDate;
    private Date eventTime;
    private Integer eventMinute;
    private GameEventType gameEventType;
    private String description;

    public GameEvent(String gameId, Date gameDate, Date eventTime, Integer eventMinute,GameEventType gameEventType, String description) {
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

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
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

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(eventTime.getTime());
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = myFormat.format(gameDate);

        return "GameEvent{" +
                "gameId='" + gameId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", gameDate=" +  date +
                ", eventTime=" + time +
                ", eventMinute=" + eventMinute +
                ", gameEventType=" + gameEventType +
                ", description='" + description + '\'' +
                '}';
    }
}
