package Controller;

import Data.*;
import Model.*;
import Model.Enums.CalculateLeaguePoints;
import Model.Enums.InlayGames;
import Model.Enums.Status;
import Model.Enums.TeamStatus;
import Model.UsersTypes.TeamOwner;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

public class NotificationControllerTest extends BaseEmbeddedSQL {

    private NotificationController notificationController = NotificationController.getInstance();
    private RepresentativeAssociationController repControll = new RepresentativeAssociationController();
    private SystemAdministratorController saController = new SystemAdministratorController();
    private TeamOwnerController teamOwnerController = new TeamOwnerController();
    private AlertDb alertDb = AlertDbInServer.getInstance();
    private RepresentativeAssociationDb repDb = RepresentativeAssociationDbInServer.getInstance();
    private SubscriberDb subscriberDb = SubscriberDbInServer.getInstance();
    private SeasonDb seasonDb = SeasonDbInServer.getInstance();
    private LeagueDb leagueDb = LeagueDbInServer.getInstance();
    private SeasonLeagueDb seasonLeagueDb = SeasonLeagueDbInServer.getInstance();
    private CourtDb courtDb = CourtDbInServer.getInstance();
    private TeamDb teamDb = TeamDbInServer.getInstance();
    private GameDb gameDb = GameDbInServer.getInstance();


    @Before
    public void init() throws SQLException {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(subscriberDb);
        dbs.add(alertDb);
        dbs.add(repDb);
        dbs.add(seasonDb);
        dbs.add(leagueDb);
        dbs.add(seasonLeagueDb);
        dbs.add(courtDb);
        dbs.add(gameDb);
        for (Db db : dbs) {
            db.deleteAll();
        }
    }

    @Test
    public void createAlertLocation() throws Exception {
        Season season = new Season("season");
        seasonDb.insertSeason(season);
        League league = new League("League");
        leagueDb.insertLeague(league);
        SeasonLeague seasonLeague = new SeasonLeague("season", "League", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);
        Court court = new Court("court", "courtCity");
        courtDb.insertCourt(court);
        teamDb.insertTeam("hostTeam");
        teamDb.insertTeam("guestTeam");
        Game gameToChange = new Game("gameId", new Date(), "season_League", "hostTeam", "guestTeam", "court");
        gameDb.insertGame(gameToChange);
        Alert locAlert = notificationController.createAlert("location", gameToChange, "court1");
        System.out.println(locAlert.toString());

    }
    @Test
    public void createAlertDate() throws Exception {
        Season season = new Season("season");
        seasonDb.insertSeason(season);
        League league = new League("League");
        leagueDb.insertLeague(league);
        SeasonLeague seasonLeague = new SeasonLeague("season", "League", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        seasonLeagueDb.insertSeasonLeague(seasonLeague);
        Court court = new Court("court", "courtCity");
        courtDb.insertCourt(court);
        teamDb.insertTeam("hostTeam");
        teamDb.insertTeam("guestTeam");
        Game gameToChange = new Game("gameId", new Date(), "season_League", "hostTeam", "guestTeam", "court");
        gameDb.insertGame(gameToChange);
        Date theDate = new GregorianCalendar(2020, Calendar.AUGUST, 11).getTime();
        theDate.setHours(18);
        theDate.setMinutes(30);
        theDate.setSeconds(00);
        Alert dateAlert = notificationController.createAlert("date", gameToChange, theDate);
        System.out.println(dateAlert.toString());
    }
    @Test
    public void createAlertStatus() throws Exception {
        Team statusTeam = new Team();
        statusTeam.setTeamName("teamName");
        statusTeam.setTeamStatus(TeamStatus.INACTIVE);
        Alert statusAlert = notificationController.createAlert("status", statusTeam, TeamStatus.ACTIVE);
        System.out.println(statusAlert.toString());
    }
    @Test
    public void createAlertRemoved() throws Exception {
        Team statusTeam = new Team();
        statusTeam.setTeamName("teamName");
        TeamOwner teamOwner = new TeamOwner("teamOwner@gmail.com", "password", 555555555, "firstName", "lastName", "teamName");
        Alert removedAlert = notificationController.createAlert("removed", statusTeam, teamOwner);
        System.out.println(removedAlert.toString());
    }
    @Test
    public void createAlertClosed() throws Exception {
        Alert closeAlert = notificationController.createAlert("close", "closedTeam", null);
        System.out.println(closeAlert.toString());
    }



}
