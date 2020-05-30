package Data;

import Controller.SubscriberController;
import Controller.SystemAdministratorController;
import Model.*;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.QualificationJudge;
import Model.Enums.RoleType;
import Model.UsersTypes.Judge;
import Model.UsersTypes.RepresentativeAssociation;
import Model.UsersTypes.SystemAdministrator;
import Model.UsersTypes.TeamOwner;

import java.sql.SQLException;
import java.util.*;

public class InitDbRecords
{
//    private static AlertDbInServer alertDbInServer = AlertDbInServer.getInstance();
    private static CoachDbInServer coachDbInServer = CoachDbInServer.getInstance();
    private static CourtDbInServer courtDbInServer = CourtDbInServer.getInstance();
//    private static FanDbInServer fanDbInServer = FanDbInServer.getInstance();

    //todo-no getInstance()
//    private static FanPageDbInServer fanPageDbInServer = FanPageDbInServer.getInstance();
    private static FinancialActivityDbInServer financialActivityDbInServer = FinancialActivityDbInServer.getInstance();
    private static GameDbInServer gameDbInServer = GameDbInServer.getInstance();
    private static GameEventsDbInServer gameEventsDbInServer = GameEventsDbInServer.getInstance();
    private static GameJudgesListDbInServer gameJudgesListDbInServer = GameJudgesListDbInServer.getInstance();
    private static JudgeDbInServer judgeDbInServer = JudgeDbInServer.getInstance();
    private static JudgeSeasonLeagueDbInServer judgeSeasonLeagueDbInServer = JudgeSeasonLeagueDbInServer.getInstance();
    private static LeagueDbInServer leagueDbInServer = LeagueDbInServer.getInstance();
    private static PageDbInServer pageDbInServer = PageDbInServer.getInstance();
    private static PermissionDbInServer permissionDbInServer = PermissionDbInServer.getInstance();
    private static PlayerDbInServer playerDbInServer = PlayerDbInServer.getInstance();
    private static RepresentativeAssociationDbInServer representativeAssociationDbInServer = RepresentativeAssociationDbInServer.getInstance();
    private static RoleDbInServer roleDbInServer = RoleDbInServer.getInstance();
    private static SeasonDbInServer seasonDbInServer = SeasonDbInServer.getInstance();
    private static SeasonLeagueDbInServer seasonLeagueDbInServer = SeasonLeagueDbInServer.getInstance();
    private static SubscriberDbInServer subscriberDbInServer = SubscriberDbInServer.getInstance();
    private static SystemAdministratorDbInServer systemAdministratorDbInServer = SystemAdministratorDbInServer.getInstance();
    private static TeamDbInServer teamDbInServer = TeamDbInServer.getInstance();
    private static TeamManagerDbInServer teamManagerDbInServer = TeamManagerDbInServer.getInstance();
    private static TeamOwnerDbInServer teamOwnerDbInServer = TeamOwnerDbInServer.getInstance();

    public static void main(String[] args) throws SQLException
    {
        deleteAllDbsRecords();

        initAllDbsRecords();
    }

    private static void initAllDbsRecords()
    {
        SystemAdministrator systemAdministrator1 = new SystemAdministrator("systemAdministrator1@gmail.com", "systemAdministrator1", 111111111, "systemAdministrator1_firstName", "systemAdministrator1_lastName");

        Season season1 = new Season("season1");
        Season season2 = new Season("season2");

        League league1 = new League("league1");
        League league2 = new League("league2");

        SeasonLeague seasonLeague11 = new SeasonLeague(season1.getSeasonName(), league1.getLeagueName(), CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        SeasonLeague seasonLeague22 = new SeasonLeague(season2.getSeasonName(), league2.getLeagueName(), CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_TWO_TIMES_EACH_AT_HOME_COURT);

        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("repAss1@gmail.com", "repAss1", 211111111, "repAss1_firstName", "repAss1_lastName");
        RepresentativeAssociation representativeAssociation2 = new RepresentativeAssociation("repAss2@gmail.com", "repAss2", 222222222, "repAss2_firstName", "repAss2_lastName");

        TeamOwner teamOwner1 = new TeamOwner("teamOwner1@gmail.com", "teamOwner1", 311111111, "teamOwner1_firstName", "teamOwner1_lastName");
        TeamOwner teamOwner2 = new TeamOwner("teamOwner2@gmail.com", "teamOwner2", 322222222, "teamOwner2_firstName", "teamOwner2_lastName");

        Team team1 = new Team();
        team1.setTeamName("team1");
        Team team2 = new Team();
        team2.setTeamName("team2");
        Team team3 = new Team();
        team3.setTeamName("team3");
        Team team4 = new Team();
        team4.setTeamName("team4");

        Court court1 = new Court();
        court1.setCourtName("court1");
        court1.setCourtCity("courtCity1");
        Court court2 = new Court();
        court2.setCourtName("court2");
        court2.setCourtCity("courtCity2");
        Court court3 = new Court();
        court3.setCourtName("court3");
        court3.setCourtCity("courtCity3");
        Court court4 = new Court();
        court4.setCourtName("court4");
        court4.setCourtCity("courtCity4");

        Judge judge1 = new Judge("judge1@gmail.com", "judge1", 411111111, "judge1_firstName", "judge1_lastName", QualificationJudge.NATIONAL);
        Judge judge2 = new Judge("judge2@gmail.com", "judge2", 422222222, "judge2_firstName", "judge2_lastName", QualificationJudge.NATIONAL);
        Judge judge3 = new Judge("judge3@gmail.com", "judge3", 433333333, "judge3_firstName", "judge3_lastName", QualificationJudge.NATIONAL);
        Judge judge4 = new Judge("judge4@gmail.com", "judge4", 444444444, "judge4_firstName", "judge4_lastName", QualificationJudge.NATIONAL);
        Judge judge5 = new Judge("judge5@gmail.com", "judge5", 455555555, "judge5_firstName", "judge5_lastName", QualificationJudge.NATIONAL);
        Judge judge6 = new Judge("judge6@gmail.com", "judge6", 466666666, "judge6_firstName", "judge6_lastName", QualificationJudge.NATIONAL);

        Set<String> judgesOfTheGameList12_3 = new HashSet<>();
        judgesOfTheGameList12_3.add(judge1.getEmailAddress());
        judgesOfTheGameList12_3.add(judge2.getEmailAddress());

        Set<String> judgesOfTheGameList45_6 = new HashSet<>();
        judgesOfTheGameList45_6.add(judge4.getEmailAddress());
        judgesOfTheGameList45_6.add(judge5.getEmailAddress());

        Date dateStart1 = new GregorianCalendar(2020, Calendar.MAY, 30).getTime();
        dateStart1.setHours(18);
        dateStart1.setMinutes(28);
        dateStart1.setSeconds(00);

        Date dateStart2 = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        dateStart2.setHours(10);
        dateStart2.setMinutes(15);
        dateStart2.setSeconds(00);

        Date dateStart3 = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        dateStart3.setHours(10);
        dateStart3.setMinutes(15);
        dateStart3.setSeconds(00);

//        Date dateStart2 = new Date(2020, 10, 8, 10, 15, 30);
//        Date dateEnd2 = new Date(2020, 10, 8, 11, 45, 30);
//        Date dateStart3 = new Date(2020, 11, 8, 10, 15, 30);
//        Date dateEnd3 = new Date(2020, 11, 8, 11, 45, 30);

        Game game1 = new Game("game1", dateStart1, seasonLeague11.getSeasonLeagueName(), team1.getTeamName(), team2.getTeamName(), court1.getCourtName(), judgesOfTheGameList12_3, judge3.getEmailAddress());
        Game game2 = new Game("game2", dateStart2, seasonLeague22.getSeasonLeagueName(), team3.getTeamName(), team4.getTeamName(), court3.getCourtName(), judgesOfTheGameList45_6, judge6.getEmailAddress());
        Game game3 = new Game("game3", dateStart3, seasonLeague22.getSeasonLeagueName(), team4.getTeamName(), team3.getTeamName(), court4.getCourtName(), judgesOfTheGameList45_6, judge6.getEmailAddress());

        try
        {
//            SubscriberController subscriberController = new SubscriberController();
//            subscriberController.createSubscriber(systemAdministrator1);
            subscriberDbInServer.insertSubscriber(systemAdministrator1);
            systemAdministratorDbInServer.insertSystemAdministrator(systemAdministrator1);
            roleDbInServer.insertRole(systemAdministrator1.getEmailAddress(), null, RoleType.SYSTEM_ADMINISTRATOR);


            seasonDbInServer.insertSeason(season1);
            seasonDbInServer.insertSeason(season2);

            leagueDbInServer.insertLeague(league1);
            leagueDbInServer.insertLeague(league2);

            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague11);
            seasonLeagueDbInServer.insertSeasonLeague(seasonLeague22);

            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            subscriberDbInServer.insertSubscriber(representativeAssociation2);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation2);
            roleDbInServer.insertRole(representativeAssociation1.getEmailAddress(), null, RoleType.REPRESENTATIVE_ASSOCIATION);
            roleDbInServer.insertRole(representativeAssociation2.getEmailAddress(), null, RoleType.REPRESENTATIVE_ASSOCIATION);


            subscriberDbInServer.insertSubscriber(teamOwner1);
            subscriberDbInServer.insertSubscriber(teamOwner2);
            teamOwnerDbInServer.insertTeamOwner(teamOwner1);
            teamOwnerDbInServer.insertTeamOwner(teamOwner2);
            roleDbInServer.insertRole(teamOwner1.getEmailAddress(), null, RoleType.TEAM_OWNER);
            roleDbInServer.insertRole(teamOwner2.getEmailAddress(), null, RoleType.TEAM_OWNER);


            teamDbInServer.insertTeam(team1.getTeamName());
            teamDbInServer.insertTeam(team2.getTeamName());
            teamDbInServer.insertTeam(team3.getTeamName());
            teamDbInServer.insertTeam(team4.getTeamName());

            courtDbInServer.insertCourt(court1);
            courtDbInServer.insertCourt(court2);
            courtDbInServer.insertCourt(court3);
            courtDbInServer.insertCourt(court4);

            subscriberDbInServer.insertSubscriber(judge1);
            subscriberDbInServer.insertSubscriber(judge2);
            subscriberDbInServer.insertSubscriber(judge3);
            subscriberDbInServer.insertSubscriber(judge4);
            subscriberDbInServer.insertSubscriber(judge5);
            subscriberDbInServer.insertSubscriber(judge6);
            judgeDbInServer.insertJudge(judge1);
            judgeDbInServer.insertJudge(judge2);
            judgeDbInServer.insertJudge(judge3);
            judgeDbInServer.insertJudge(judge4);
            judgeDbInServer.insertJudge(judge5);
            judgeDbInServer.insertJudge(judge6);
            roleDbInServer.insertRole(judge1.getEmailAddress(), null, RoleType.JUDGE);
            roleDbInServer.insertRole(judge2.getEmailAddress(), null, RoleType.JUDGE);
            roleDbInServer.insertRole(judge3.getEmailAddress(), null, RoleType.JUDGE);
            roleDbInServer.insertRole(judge4.getEmailAddress(), null, RoleType.JUDGE);
            roleDbInServer.insertRole(judge5.getEmailAddress(), null, RoleType.JUDGE);
            roleDbInServer.insertRole(judge6.getEmailAddress(), null, RoleType.JUDGE);


            gameDbInServer.insertGame(game1);
            gameDbInServer.insertGame(game2);
            gameDbInServer.insertGame(game3);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void deleteAllDbsRecords() throws SQLException
    {
        final List<Db> dbs = new ArrayList<>();

        dbs.add(AlertDbInServer.getInstance());
        dbs.add(CoachDbInServer.getInstance());
        dbs.add(CourtDbInServer.getInstance());
//        dbs.add(FanDbInServer.getInstance());
        //todo-no getInstance()
//        dbs.add(FanPageDbInServer.getInstance());
        dbs.add(FinancialActivityDbInServer.getInstance());
        dbs.add(GameDbInServer.getInstance());
        dbs.add(GameEventsDbInServer.getInstance());
        dbs.add(GameJudgesListDbInServer.getInstance());
        dbs.add(JudgeDbInServer.getInstance());
        dbs.add(JudgeSeasonLeagueDbInServer.getInstance());
        dbs.add(LeagueDbInServer.getInstance());
        dbs.add(PageDbInServer.getInstance());
        dbs.add(PermissionDbInServer.getInstance());
        dbs.add(PlayerDbInServer.getInstance());
        dbs.add(RepresentativeAssociationDbInServer.getInstance());
        dbs.add(RoleDbInServer.getInstance());
        dbs.add(SeasonDbInServer.getInstance());
        dbs.add(SeasonLeagueDbInServer.getInstance());
        dbs.add(SubscriberDbInServer.getInstance());
        dbs.add(SystemAdministratorDbInServer.getInstance());
        dbs.add(TeamDbInServer.getInstance());
        dbs.add(TeamManagerDbInServer.getInstance());
        dbs.add(TeamOwnerDbInServer.getInstance());

        for (Db db : dbs)
        {
            db.deleteAll();
        }
    }
}
