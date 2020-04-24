package Controller;

import Data.*;
import Model.Enums.*;
import Model.Team;
import Model.UsersTypes.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestControllerTest {
    private GuestController guestController = new GuestController();
    //DBs
    private SubscriberDb subscriberDb = SubscriberDbInMemory.getInstance();
    private CoachDb coachDb = CoachDbInMemory.getInstance();
    private JudgeDb judgeDb = JudgeDbInMemory.getInstance();
    private PlayerDb playerDb = PlayerDbInMemory.getInstance();
    private TeamManagerDb teamManagerDb = TeamManagerDbInMemory.getInstance();
    private TeamOwnerDb teamOwnerDb = TeamOwnerDbInMemory.getInstance();
    private FanDb fanDb = FanDbInMemory.getInstance();
    private RoleDb roleDb = RoleDbInMemory.getInstance();
    private SystemAdministratorDb systemAdministratorDb = SystemAdministratorDbInMemory.getInstance();
    private RepresentativeAssociationDb representativeAssociationDb = RepresentativeAssociationDbInMemory.getInstance();
    private PageDb pageDb = PageDbInMemory.getInstance();
    private TeamDb teamDb=TeamDbInMemory.getInstance();;

    @Before
    public void init() {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(subscriberDb);
        dbs.add(coachDb);
        dbs.add(judgeDb);
        dbs.add(playerDb);
        dbs.add(teamManagerDb);
        dbs.add(teamOwnerDb);
        dbs.add(fanDb);
        dbs.add(roleDb);
        dbs.add(systemAdministratorDb);
        dbs.add(representativeAssociationDb);
        dbs.add(pageDb);
        dbs.add(teamDb);
        for (Db db : dbs) {
            db.deleteAll();
        }
    }

    @Test
    public void login() throws Exception {
        Fan fan = new Fan("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "Harary");
        subscriberDb.createSubscriber(fan);
        //good login
        Assert.assertEquals(guestController.login("noY12@gmail.com", "L1o8oy"), true);
        //wrong email
        Assert.assertEquals(guestController.login("noY1211", "L1o8oy"), false);
        //wrong password
        Assert.assertEquals(guestController.login("noY12@gmail.com", "L1o8oy4652"), false);
        //wrong password and email
        try {
            guestController.login("no@gmail.com", "L1o8oy646");
        }catch (Exception e){
            Assert.assertEquals(e.getMessage(), "subscriber not found");
        }
        //null email
        Assert.assertEquals(guestController.login(null, "L1o8oy646"), false);
        //null password
        Assert.assertEquals(guestController.login("no@gmail.com", null), false);
    }

    @Test
    public void coachRegistering() {
        Coach coach = new Coach("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        try {
            subscriberDb.createSubscriber(coach);
            coachDb.createCoach(coach);
            //register coach with exist email in subscriberDb
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        } catch (Exception e) {
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }

        //null email
        try{
            guestController.registerCoach(null, "L1o8oy", 207785070, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid email
        try {
            guestController.registerCoach("bla", "L1o8oy", 207785070, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //wrong password
        try {
            guestController.registerCoach("noY12@gmail.com", "//nsco12", 207785070, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null password
        try {
            guestController.registerCoach("noY12@gmail.com", null, 207785070, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //empty password
        try {
            guestController.registerCoach("noY12@gmail.com", "", 207785070, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid id
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", -12, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 1111111111, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", null, "Noy", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid first name
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207785070, "", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207785070, null, "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207785070, "noy132", "Harary", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid last name
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207785070, "Noy", null, CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123", CoachRole.MAJOR, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null coach role
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "Harary", null, QualificationCoach.UEFA_A);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //null QualificationCoach
        try {
            guestController.registerCoach("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123", CoachRole.MAJOR, null);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
    }


    @Test
    public void fanRegistering() {
        Fan fan = new Fan("noa@gmail.com", "L1o8oy", 207785070, "Noy", "Harary");
        try {
            subscriberDb.createSubscriber(fan);
            fanDb.createFan(fan);
            //register fan with exist email in subscriberDb
            guestController.registerFan("noa@gmail.com", "L1o8oy", 207785070, "Noy", "Harary");
        } catch (Exception e) {
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }

        //null email
        try{
            guestController.registerFan(null, "L1o8oy", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid email
        try {
            guestController.registerFan("bla", "L1o8oy", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //wrong password
        try {
            guestController.registerFan("noa@gmail.com", "//nsco12", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null password
        try {
            guestController.registerFan("noY12@gmail.com", null, 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //empty password
        try {
            guestController.registerFan("noY12@gmail.com", "", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid id
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", 207, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", -12, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", 1111111111, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", null, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid first name
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", 207785070, "", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", 207785070, null, "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", 207785070, "noy132", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid last name
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", 207785070, "Noy", null);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerFan("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

    }

    @Test
    public void judgeRegistering() {
        Judge judge = new Judge("moran@gmail.com", "L1o8oy", 207785070, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        try {
            subscriberDb.createSubscriber(judge);
            judgeDb.createJudge(judge);
            //register fan with exist email in subscriberDb
            guestController.registerJudge("moran@gmail.com", "L1o8oy", 207785070, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        } catch (Exception e) {
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }

        //null email
        try{
            guestController.registerJudge(null, "L1o8oy", 207785070, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid email
        try {
            guestController.registerJudge("bla", "L1o8oy", 207785070, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //wrong password
        try {
            guestController.registerJudge("noa@gmail.com", "//nsco12", 207785070, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null password
        try {
            guestController.registerJudge("noY12@gmail.com", null, 207785070, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //empty password
        try {
            guestController.registerJudge("noY12@gmail.com", "", 207785070, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid id
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 207, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", -12, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 1111111111, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", null, "Noy", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid first name
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 207785070, "", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 207785070, null, "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 207785070, "noy132", "Harary", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid last name
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 207785070, "Noy", null, QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123", QualificationJudge.NATIONAL, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null QualificationJudge
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123", null, JudgeType.MAJOR_JUDGE);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null JudgeType
        try {
            guestController.registerJudge("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123", QualificationJudge.NATIONAL, null);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

    }

    @Test
    public void playerRegistering() {
        Date date=new Date(10/4/1958);
        Player player = new Player("avi@gmail.com", "L1o8oy", 207785070, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        try {
            subscriberDb.createSubscriber(player);
            playerDb.createPlayer(player);
            //register fan with exist email in subscriberDb
            guestController.registerPlayer("avi@gmail.com", "L1o8oy", 207785070, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        } catch (Exception e) {
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }

        //null email
        try{
            guestController.registerPlayer(null, "L1o8oy", 207785070, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid email
        try {
            guestController.registerPlayer("bla", "L1o8oy", 207785070, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //wrong password
        try {
            guestController.registerPlayer("noa@gmail.com", "//nsco12", 207785070, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null password
        try {
            guestController.registerPlayer("noY12@gmail.com", null, 207785070, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //empty password
        try {
            guestController.registerPlayer("noY12@gmail.com", "", 207785070, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid id
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", 207, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", -12, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", 1111111111, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", null, "Noy", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid first name
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", 207785070, "", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", 207785070, null, "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", 207785070, "noy132", "Harary", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid last name
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", 207785070, "Noy", null, date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerPlayer("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123", date, PlayerRole.GOALKEEPER);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

    }
    @Test
    public void representativeAssociationRegistering() {
        RepresentativeAssociation representativeAssociation = new RepresentativeAssociation("avior@gmail.com", "L1o8oy", 207785070, "Noy", "Harary");
        try {
            subscriberDb.createSubscriber(representativeAssociation);
            representativeAssociationDb.createRepresentativeAssociation(representativeAssociation);
            //register fan with exist email in subscriberDb
            guestController.registerRepresentativeAssociation("avior@gmail.com", "L1o8oy", 207785070, "Noy", "Harary");
        } catch (Exception e) {
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }

        //null email
        try{
            guestController.registerRepresentativeAssociation(null, "L1o8oy", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid email
        try {
            guestController.registerRepresentativeAssociation("bla", "L1o8oy", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //wrong password
        try {
            guestController.registerRepresentativeAssociation("noa@gmail.com", "//nsco12", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null password
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", null, 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //empty password
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid id
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", 207, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", -12, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", 1111111111, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", null, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid first name
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", 207785070, "", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", 207785070, null, "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", 207785070, "noy132", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid last name
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", 207785070, "Noy", null);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerRepresentativeAssociation("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

    }
    @Test
    public void systemAdministratorRegistering() {
        SystemAdministrator systemAdministrator = new SystemAdministrator("hila@gmail.com", "L1o8oy", 207785070, "Noy", "Harary");
        try {
            subscriberDb.createSubscriber(systemAdministrator);
            systemAdministratorDb.createSystemAdministrator(systemAdministrator);
            //register fan with exist email in subscriberDb
            guestController.registerSystemAdministrator("hila@gmail.com", "L1o8oy", 207785070, "Noy", "Harary");
        } catch (Exception e) {
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }

        //null email
        try{
            guestController.registerSystemAdministrator(null, "L1o8oy", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid email
        try {
            guestController.registerSystemAdministrator("bla", "L1o8oy", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //wrong password
        try {
            guestController.registerSystemAdministrator("noa@gmail.com", "//nsco12", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null password
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", null, 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //empty password
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "", 207785070, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid id
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", 207, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", -12, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", 1111111111, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", null, "Noy", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid first name
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", 207785070, "", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", 207785070, null, "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", 207785070, "noy132", "Harary");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid last name
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", 207785070, "Noy", null);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerSystemAdministrator("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

    }
    @Test
    public void teamManagerRegistering() {
        //create for the test
        Team team=new Team();
        TeamOwner teamOwner=new TeamOwner("owner@gmail.com", "L1o8oy",207785070, "Noy", "Harary",team);
        try {
            teamOwnerDb.createTeamOwner(teamOwner);
            subscriberDb.createSubscriber(teamOwner);
        } catch (Exception e) {
            //not should get into the catch
            Assert.assertEquals("1","0");
        }

        //start testing
        TeamManager teamManager = new TeamManager("manager@gmail.com", "L1o8oy",207785070, "Noy", "Harary","owner@gmail.com");
        try {
            subscriberDb.createSubscriber(teamManager);
            teamManagerDb.createTeamManager(teamManager);
            //register TeamManager with exist email in subscriberDb
            guestController.registerTeamManager("manager@gmail.com", "L1o8oy", 207785070, "Noy", "Harary","owner@gmail.com");
        } catch (Exception e) {
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }

        //null email
        try{
            guestController.registerTeamManager(null, "L1o8oy", 207785070, "Noy", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid email
        try {
            guestController.registerTeamManager("bla", "L1o8oy", 207785070, "Noy", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //wrong password
        try {
            guestController.registerTeamManager("noa@gmail.com", "//nsco12", 207785070, "Noy", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null password
        try {
            guestController.registerTeamManager("noY12@gmail.com", null, 207785070, "Noy", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //empty password
        try {
            guestController.registerTeamManager("noY12@gmail.com", "", 207785070, "Noy", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid id
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207, "Noy", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", -12, "Noy", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 1111111111, "Noy", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", null, "Noy", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid first name
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207785070, "", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207785070, null, "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207785070, "noy132", "Harary","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid last name
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207785070, "Noy", null,"owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123","owner@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //owned by email is not exist
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123","la@gmail.com");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //owned by email is not valid
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123",null);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //owned by email is not valid
        try {
            guestController.registerTeamManager("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123","");
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
    }

    @Test
    public void teamOwnerRegistering() {
        Team team=null;
        try {
            teamDb.createTeam("team");
            team =teamDb.getTeam("team");
        } catch (Exception e) {
            //should't get here
            Assert.assertEquals(1,0);
        }
        TeamOwner teamOwner= new TeamOwner("teamO@gmail.com", "L1o8oy", 207785070, "Noy", "Harary", team);
        try {
            subscriberDb.createSubscriber(teamOwner);
            teamOwnerDb.createTeamOwner(teamOwner);

            //register teamOwner with exist email in subscriberDb
            guestController.registerTeamOwner("teamO@gmail.com", "L1o8oy", 207785070, "Noy", "Harary",team);
        } catch (Exception e) {
            Assert.assertEquals("subscriber already exists", e.getMessage());
        }

        //null email
        try{
            guestController.registerTeamOwner(null, "L1o8oy", 207785070, "Noy", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid email
        try {
            guestController.registerTeamOwner("bla", "L1o8oy", 207785070, "Noy", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //wrong password
        try {
            guestController.registerTeamOwner("noa@gmail.com", "//nsco12", 207785070, "Noy", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null password
        try {
            guestController.registerTeamOwner("noY12@gmail.com", null, 207785070, "Noy", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //empty password
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "", 207785070, "Noy", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid id
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 207, "Noy", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", -12, "Noy", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 1111111111, "Noy", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid id
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", null, "Noy", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid first name
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 207785070, "", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 207785070, null, "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid first name
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 207785070, "noy132", "Harary",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //not valid last name
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 207785070, "Noy", null,team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //not valid last name
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy123",team);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }

        //null team
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy",null);
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }
        //unexist team
        try {
            guestController.registerTeamOwner("noY12@gmail.com", "L1o8oy", 207785070, "Noy", "noy",new Team());
        }catch (Exception e){
            Assert.assertEquals("try to enter details again!", e.getMessage());
        }


    }

}
