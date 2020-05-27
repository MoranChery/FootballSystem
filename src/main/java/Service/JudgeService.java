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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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
    public void addEventToGame(@PathVariable String judgeMail, @PathVariable String gameId, @PathVariable String eventTime, @PathVariable String eventMinute, @PathVariable String gameEventType, @PathVariable String description) throws Exception {
        try {
            GameEventType eventType = GameEventType.getGameEventType(gameEventType);

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date time = sdf.parse(eventTime);



            Integer eventMinute1=Integer.parseInt(eventMinute);
            judgeController.addEventToGame(judgeMail, gameId, time, eventMinute1, eventType, description);
            logger.log(Level.INFO, "Created by: " + judgeMail + " Description: Event was added to Game \"" + gameId + "\"");
        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + judgeMail + " Description: Event wasn't updated to Game \"" + gameId + "\" because " + e.getMessage());
        }
    }

//    public void addEventToGame(String judgeMail, String gameId, Date eventTime, Integer eventMinute, GameEventType gameEventType, String description) throws Exception {
//       try{
//        judgeController.addEventToGame(judgeMail, gameId, eventTime, eventMinute, gameEventType, description);
//        logger.log(Level.INFO, "Created by: " + judgeMail + " Description: Event was added to Game \"" + gameId + "\"");
//    }catch(Exception e)
//
//    {
//        logger.log(Level.WARNING, "Created by: " + judgeMail + " Description: Event wasn't updated to Game \"" + gameId + "\" because " + e.getMessage());
//    }
//
//    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "updateGameEventAfterEnd/{judgeMail}/{gameId}/{eventId}/{eventTime}/{eventMinute}/{gameEventType}/{description}/")
    @ResponseStatus(HttpStatus.OK)
    public void updateGameEventAfterEnd(@PathVariable String judgeMail,@PathVariable String gameId,@PathVariable String eventId,@PathVariable String eventTime,@PathVariable Integer eventMinute,@PathVariable GameEventType gameEventType,@PathVariable String description) throws Exception {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date time = sdf.parse(eventTime);
            judgeController.updateGameEventAfterEnd(judgeMail, gameId, eventId, time, eventMinute, gameEventType, description);
            logger.log(Level.INFO, "Created by: " + judgeMail + " Description: Event \"" + eventId + "\" was updated to Game \"" + gameId + "\"");

        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + judgeMail + " Description: Event \"" + eventId + "\" wasn't updated to Game \"" + gameId + "\" because " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:63342")
    @GetMapping(value = "createReportForGame/{path}/{judgeMail}/{gameId}/")
    @ResponseStatus(HttpStatus.OK)
    public void createReportForGame(@PathVariable String path,@PathVariable String judgeMail,@PathVariable String gameId) throws Exception {
        try {
            judgeController.createReportForGame(path, judgeMail, gameId);
            logger.log(Level.INFO, "Created by: " + judgeMail + " Description: report was created to Game \"" + gameId + "\"");

        } catch (Exception e) {
            logger.log(Level.WARNING, "Created by: " + judgeMail + " Description: report wasn't created to Game \"" + gameId + "\" because " + e.getMessage());
        }
    }
}
