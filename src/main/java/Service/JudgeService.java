package Service;

import Controller.JudgeController;
import Model.Enums.GameEventType;
import Model.Game;
import Model.LoggerHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.apache.logging.log4j.LogManager;

import java.sql.Time;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JudgeService {
    private Logger logger = Logger.getLogger(JudgeService.class.getName());
    private JudgeController judgeController;
//    LoggerHandler loggerHandler;

    public JudgeService() {
        this.judgeController = new JudgeController();
//        loggerHandler = new LoggerHandler(JudgeController.class.getName());
        logger.addHandler(LoggerHandler.loggerErrorFileHandler);
        logger.addHandler(LoggerHandler.loggerEventFileHandler);
    }

    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception {
        judgeController.wantToEditQualification(judgeMail, newQualification);
    }

    public void addGameToTheJudge(String judgeMail, Game gameToAdd) throws Exception {
        judgeController.addGameToTheJudge(judgeMail, gameToAdd);
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "addEventToGame/{judgeMail}/{gameId}/{eventTime}/{eventMinute}/{gameEventType}/{description}/")
    @ResponseStatus(HttpStatus.OK)
    public void addEventToGame(@PathVariable String judgeMail, @PathVariable String gameId, @PathVariable Time eventTime, @PathVariable Integer eventMinute, @PathVariable String gameEventType, @PathVariable String description) throws Exception {
        try {
            GameEventType eventType = GameEventType.getGameEventType(gameEventType);
            judgeController.addEventToGame(judgeMail, gameId, eventTime, eventMinute, eventType, description);
            logger.log(Level.INFO, "Created by: " + judgeMail + " Description: Event was added to Game \"" + gameId + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + judgeMail + " Description: Event wasn't updated to Game \"" + gameId + "\" because " + e.getMessage());
        }
    }

    public void addEventToGame(String judgeMail, String gameId, Time eventTime, Integer eventMinute, GameEventType gameEventType, String description) throws Exception {
       try{
        judgeController.addEventToGame(judgeMail, gameId, eventTime, eventMinute, gameEventType, description);
        logger.log(Level.INFO, "Created by: " + judgeMail + " Description: Event was added to Game \"" + gameId + "\"");
    }catch(Exception e)

    {
        logger.log(Level.WARNING, "Created by: " + judgeMail + " Description: Event wasn't updated to Game \"" + gameId + "\" because " + e.getMessage());
    }

    }

    public void updateGameEventAfterEnd(String judgeMail, String gameId, String eventId, Time eventTime, Integer eventMinute, GameEventType gameEventType, String description) throws Exception {
        try {
            judgeController.updateGameEventAfterEnd(judgeMail, gameId, eventId, eventTime, eventMinute, gameEventType, description);
            logger.log(Level.INFO, "Created by: " + judgeMail + " Description: Event \"" + eventId + "\" was updated to Game \"" + gameId + "\"");

        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + judgeMail + " Description: Event \"" + eventId + "\" wasn't updated to Game \"" + gameId + "\" because " + e.getMessage());
        }
    }

    public void createReportForGame(String path, String judgeMail, String gameId) throws Exception {
        try {
            judgeController.createReportForGame(path, judgeMail, gameId);
            logger.log(Level.INFO, "Created by: " + judgeMail + " Description: report was created to Game \"" + gameId + "\"");

        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + judgeMail + " Description: report wasn't created to Game \"" + gameId + "\" because " + e.getMessage());
        }
    }
}
