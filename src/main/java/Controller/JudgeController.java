package Controller;

import Data.*;
import Model.Enums.GameEventType;
import Model.Enums.QualificationJudge;
import Model.Enums.RoleType;
import Model.Game;
import Model.GameEvent;
import Model.Role;
import Model.UsersTypes.Judge;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JudgeController {
    private JudgeDb judgeDb;
    private GameDb gameDb;
    private GameEventsDb gameEventsDb;
    private RoleDb roleDb;
    private Gson gson = new Gson();
    private Gson prettyGson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm").create();
    private RepresentativeAssociationController repController;

    public JudgeController() {
//        judgeDb = JudgeDbInMemory.getInstance();
//        gameDb = GameDbInMemory.getInstance();
//        roleDb = RoleDbInMemory.getInstance();
//        gameEventsDb = GameEventsDbInMemory.getInstance();
        judgeDb = JudgeDbInServer.getInstance();
        gameDb = GameDbInServer.getInstance();
        roleDb = RoleDbInServer.getInstance();
        gameEventsDb = GameEventsDbInServer.getInstance();
    }

    public Map<String, GameEvent> getGameEventsByGameId(String gameId) throws Exception {
        try {
            return gameEventsDb.getMap_eventId_GameEvent_ByGameId(gameId);
        } catch (Exception e) {
            throw e;
        }
    }

    public void setRepController(RepresentativeAssociationController controller) {
        repController = controller;
    }

    /**
     * This function creates new judge in the DB
     * @param judge Judge the judge you want to add to the DB
     * @throws Exception NullPointerException if input is null
     */
    public void createJudge(Judge judge) throws Exception {
        if (judge == null) {
            throw new NullPointerException("Can't create this judge");
        }
        judgeDb.insertJudge(judge);
    }

    /**
     * This function get string that represent judge id - his email address and returns Judge class instance
     *
     * @param judgeEmailAddress String - the id of the judge - his email address
     * @return Judge - the instance of the judge in the db
     * @throws Exception NullPointerException if the mail is null - the judge is not found in the db
     */
    public Judge getJudge(String judgeEmailAddress) throws Exception {
        if (judgeEmailAddress == null) {
            throw new NullPointerException("Judge not found");
        }
        return judgeDb.getJudge(judgeEmailAddress);
    }

    /**
     * this function enable the judge to edit qualification
     *
     * @param judgeMail        String the judge id- email address
     * @param newQualification String the new qualification the judge want to change to
     * @throws Exception NullPointerException - if one or more of the inputs is null
     *                   NotFoundException - if the judge is not in the db
     *                   Exception - if the new qualification is equal to the current qualification
     */
    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception {
        if (judgeMail == null || newQualification == null) {
            throw new NullPointerException("Something went wrong in editing judge's qualification");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        QualificationJudge theJudgeNewQualification = QualificationJudge.valueOf(newQualification);
        if (judge.getQualificationJudge().equals(theJudgeNewQualification)) {
            throw new Exception("This qualification equal the previous");
        }
        judgeDb.wantToEditQualification(judgeMail, newQualification);
    }

    /**
     * this function add game to the table of games for this judge
     *
     * @param judgeMail String the judge id- email address
     * @param gameToAdd Game the game you want to add to the table
     * @throws Exception NullPointerException - if one or more of the inputs is null
     *                   NotFoundException - if the judge is not in the db
     *                   Exception - if the judge not exist in the db or if the game exist in the db
     */
    public void addGameToTheJudge(String judgeMail, Game gameToAdd) throws Exception {
        if (judgeMail == null || judgeMail.isEmpty() || gameToAdd == null) {
            throw new NullPointerException("One or more of the inputs wrong");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        List<String> theJudgeGameList = judgeDb.getJudgeGames(judgeMail);
        String gameID = gameToAdd.getGameID();
        Game testGame = gameDb.getGame(gameID);
        /** check if the game exist in the db **/
        if (theJudgeGameList.contains(gameID)) {
            throw new Exception("This game already in the system");
        }
        judgeDb.addGameToTheJudge(judgeMail, gameToAdd);
    }


    public void addEventToGame(String judgeMail, String gameId, Date eventTime, Integer eventMinute, GameEventType gameEventType, String description) throws Exception {
        if (judgeMail == null || gameId == null || eventTime == null || eventMinute == null || gameEventType == null || description == null) {
            throw new NullPointerException("bad input");
        }

            checkPermissionsJudge(judgeMail);
            //check if the judge and game located in db
            Game game = gameDb.getGame(gameId);
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//            String time = sdf.format(eventTime);
//            LocalTime timePart = LocalTime.parse(time);
//            String startingDate = new SimpleDateFormat("yyyy-MM-dd").format(game.getGameDate());
//            LocalDate datePart = LocalDate.parse(startingDate);
//            LocalDateTime dt = LocalDateTime.of(datePart, timePart);
//            Date event = convertToDateViaInstant(dt);


        List<String> theJudgeGameList = judgeDb.getJudgeGames(judgeMail);
            if (!theJudgeGameList.contains(gameId)) {
                throw new Exception("This game doesnt associated with current judge");
            }
            GameEvent gameEvent = new GameEvent(gameId,game.getGameDate(),eventTime,eventMinute,gameEventType,description);
            gameEventsDb.addEvent(gameEvent);
    }

   private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
    public void updateGameEventAfterEnd(String judgeMail, String gameId, String eventId, Date eventTime, Integer eventMinute, GameEventType gameEventType, String description) throws Exception {
        if (judgeMail == null || gameId == null || eventTime == null || eventMinute == null || gameEventType == null || description == null) {
            throw new NullPointerException("bad input");
        }
        checkPermissionsJudge(judgeMail);
        //check if the judge and game located in db
        Game game = gameDb.getGame(gameId);
        List<String> theJudgeGameList = judgeDb.getJudgeGames(judgeMail);
        if (!theJudgeGameList.contains(gameId)) {
            throw new Exception("This game doesnt associated with current judge");
        }
        if (!judgeMail.equals(game.getMajorJudge())) {
            throw new Exception("This judge is not a major judge in this game");
        }
        long now = System.currentTimeMillis();
        long timeEndGame = game.getEndGameTime().getTime();
        long passedTime = TimeUnit.MILLISECONDS.toHours(now - timeEndGame);
        if (passedTime > 5) {
            throw new Exception("5 hours from the end of the game passed");
        }
        GameEvent gameEvent = new GameEvent(gameId, game.getGameDate(), eventTime, eventMinute, gameEventType, description);
        gameEvent.setEventId(eventId);
        gameEventsDb.updateGameEventDetails(gameEvent);
    }

    public void createReportForGame(String path, String judgeMail, String gameId) throws Exception {
        if (path == null || judgeMail == null || gameId == null) {
            throw new NullPointerException("bad input");
        }
        //check if it's judge
        checkPermissionsJudge(judgeMail);
        //check if the judge and game located in db
        Game game = gameDb.getGame(gameId);
        List<String> theJudgeGameList = judgeDb.getJudgeGames(judgeMail);
        if (!theJudgeGameList.contains(gameId)) {
            throw new Exception("This game doesnt associated with current judge");
        }
        Map<String, GameEvent> eventsLog = gameEventsDb.getMap_eventId_GameEvent_ByGameId(gameId);
        BufferedWriter report = new BufferedWriter(new FileWriter(path + gameId));
        report.write(prettyGson.toJson(eventsLog));
        report.flush();
        report.close();
    }


    private void checkPermissionsJudge(String judgeMail) throws Exception {
        List<Role> subscriberRoleList = roleDb.getRoles(judgeMail);
        Judge judge = judgeDb.getJudge(judgeMail);
        boolean isJudge = false;
        if (subscriberRoleList.size() > 0) {
            for (Role role : subscriberRoleList) {
                if (role.getRoleType().equals(RoleType.JUDGE)) {
                    isJudge = true;
                }
            }
        }
        if (!isJudge) {
            throw new Exception("This subscriber hasn't judge permissions");
        }
    }
}
