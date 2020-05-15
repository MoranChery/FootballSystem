package Service;

import Controller.JudgeController;
import Model.Enums.GameEventType;
import Model.Game;
import Model.LoggerHandler;

import java.sql.Time;
import java.util.logging.Level;

public class JudgeService {

    private JudgeController judgeController;
    LoggerHandler loggerHandler;

    public JudgeService() {
        this.judgeController = new JudgeController();
        loggerHandler = new LoggerHandler(JudgeController.class.getName());
    }

    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception {
        judgeController.wantToEditQualification(judgeMail, newQualification);
    }

    public void addGameToTheJudge(String judgeMail, Game gameToAdd) throws Exception {
        judgeController.addGameToTheJudge(judgeMail, gameToAdd);
    }

    public void addEventToGame(String judgeMail, String gameId, Time eventTime, Integer eventMinute, GameEventType gameEventType, String description) throws Exception {
       try{
        judgeController.addEventToGame(judgeMail, gameId, eventTime, eventMinute, gameEventType, description);
        loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + judgeMail + " Description: Event was added to Game \"" + gameId + "\"");
    }catch(Exception e)

    {
        loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + judgeMail + " Description: Event wasn't updated to Game \"" + gameId + "\" because " + e.getMessage());
    }

}
    public void updateGameEventAfterEnd(String judgeMail, String gameId, String eventId, Time eventTime, Integer eventMinute, GameEventType gameEventType, String description) throws Exception {
        try {
            judgeController.updateGameEventAfterEnd(judgeMail, gameId, eventId, eventTime, eventMinute, gameEventType, description);
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + judgeMail + " Description: Event \"" + eventId + "\" was updated to Game \"" + gameId + "\"");

        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + judgeMail + " Description: Event \"" + eventId + "\" wasn't updated to Game \"" + gameId + "\" because " + e.getMessage());
        }
    }

    public void createReportForGame(String path, String judgeMail, String gameId) throws Exception {
        try {
            judgeController.createReportForGame(path, judgeMail, gameId);
            loggerHandler.getLoggerEvents().log(Level.INFO, "Created by: " + judgeMail + " Description: report was created to Game \"" + gameId + "\"");

        } catch (Exception e) {
            loggerHandler.getLoggerErrors().log(Level.WARNING, "Created by: " + judgeMail + " Description: report wasn't created to Game \"" + gameId + "\" because " + e.getMessage());
        }
    }
}
