package Controller;

import Data.*;
import Model.Enums.TeamStatus;
import Model.Game;
import Model.Page;
import Model.PersonalPage;
import Model.Team;
import Model.UsersTypes.*;

import java.util.Map;

public class SystemAdministratorController {
    private TeamDb teamDb;
    private SubscriberDb subscriberDb;
    private CoachDb coachDb;
    private CourtDb courtDb;
    private JudgeDb judgeDb;
    private PlayerDb playerDb;
    private TeamManagerDb teamManagerDb;
    private TeamOwnerDb teamOwnerDb;
    private PageDb pageDb;
    private FanDb fanDb;
    private SeasonLeagueDb seasonalLeagueDB;
    private JudgeSeasonLeagueDb judgeSeasonLeagueDb;


    public SystemAdministratorController() {
        teamDb = TeamDbInMemory.getInstance();
        subscriberDb = SubscriberDbInMemory.getInstance();
        coachDb = CoachDbInMemory.getInstance();
        courtDb = CourtDbInMemory.getInstance();
        judgeDb = JudgeDbInMemory.getInstance();
        playerDb = PlayerDbInMemory.getInstance();
        teamManagerDb = TeamManagerDbInMemory.getInstance();
        teamOwnerDb = TeamOwnerDbInMemory.getInstance();
        pageDb = PageDbInMemory.getInstance();
        fanDb = FanDbInMemory.getInstance();
        seasonalLeagueDB = SeasonLeagueDbInMemory.getInstance();
        judgeSeasonLeagueDb = JudgeSeasonLeagueDbInMemory.getInstance();
        representativeAssociationDb=RepresentativeAssociationDbInMemory.getInstance();
    }

    //use case 8.1

    /**
     * @param teamName that the system administrator want to close for ever
     */
    public void closeTeamForEver(String teamName) throws Exception {
        Team teamToClose = teamDb.getTeam(teamName);
        teamToClose.setTeamStatus(TeamStatus.CLOSE);
        //todo: send alert to the team owners and to the team managers
        System.out.println("send alert to the team owners and to the team managers");
        //todo: update the log file
        System.out.println("log file updated");
    }

    //use case 8.2

    /**
     * @param email of the subscriber that the system administrator want to remove
     */
    public void removeSubscriber(String email) throws Exception {
            Subscriber subscriberToRemove = subscriberDb.getSubscriber(email);
            //remove the subscriber from subscriberDB
            subscriberDb.removeSubscriberFromDB(subscriberToRemove);

            if (subscriberToRemove instanceof Coach) {
                removeCoach(subscriberToRemove);
            }
            if (subscriberToRemove instanceof Judge) {
                removeJudge(subscriberToRemove);
            }
            if (subscriberToRemove instanceof Player) {
                removePlayer(subscriberToRemove);
            }
            if (subscriberToRemove instanceof TeamManager) {
                removeTeamManager(subscriberToRemove);
            }
            if (subscriberToRemove instanceof TeamOwner) {
                removeTeamOwner(subscriberToRemove);
            }
            if (subscriberToRemove instanceof Fan) {
                removeFan(subscriberToRemove);
            }
            if (subscriberToRemove instanceof RepresentativeAssociation) {
                removeRepresentativeAssociation(subscriberToRemove);
            }
            System.out.println("the chosen subscriber with the Email " + email + " deleted successfully :)");
    }

    /**
     * todo!!! have no RepresentativeAssociation in this version!!!
     *
     * @param subscriberToRemove subscriberToRemove that is also RepresentativeAssociation
     * @throws Exception if the RepresentativeAssociation is already removed from fanDB
     */
    private void removeRepresentativeAssociation(Subscriber subscriberToRemove) {

    }

    /**
     * @param subscriberToRemove that is also fan
     * @throws Exception if the fan is already removed from fanDB
     */
    private void removeFan(Subscriber subscriberToRemove) throws Exception {
        //casting
        Fan fan = (Fan) subscriberToRemove;
        //remove the fan from personalPages that he followed after
        for (String pageID : fan.getMyPages()) {
            Page page = pageDb.getPage(pageID);
            page.getFansFollowingThisPage().remove(fan);
        }
        //remove fan from fanDB
        fanDb.removeFan(fan);
    }

    /**
     * recursive way to remove all the owners of the owner that we remove
     *
     * @param subscriberToRemove the root of the tree of teamOwners
     * @throws Exception is someone from the "subscriberToRemove" owners has been deleted
     */
    private void removeTeamOwner(Subscriber subscriberToRemove) throws Exception {
        if (subscriberToRemove == null)
            return;
        //casting
        TeamOwner teamOwner = (TeamOwner) subscriberToRemove;
        //remove all the teamOwner's subscribers
        for (TeamOwner owner : teamOwner.getTeamOwnersByThis().values()) {
            removeTeamOwner(owner);
        }
        //remove teamOwner from teamOwnerDb
        teamOwnerDb.removeSubscriptionTeamOwner(teamOwner.getEmailAddress());
        try {
            subscriberDb.removeSubscriberFromDB(teamOwner);
        } catch (Exception e) {
        }
    }

    /**
     * @param subscriberToRemove that is also coach
     * @throws Exception if the coach is already removed from coachDB
     */
    private void removeCoach(Subscriber subscriberToRemove) throws Exception {
        //casting
        Coach coach = (Coach) subscriberToRemove;
        //remove the personalPage from its followers
        Page coachPage = coach.getCoachPage();
        for (String fanEmail : coachPage.getFansFollowingThisPage().keySet()) {
            fanDb.getFan(fanEmail).getMyPages().remove(coachPage);
        }
        //remove the personal page of the coach from PersonalPageDb
        pageDb.removePersonalPageFromDb(coachPage.getPageID());
        //remove the coach from his team
        coach.getTeam().getCoaches().remove(coach);
        //remove the coach from the coachDB
        coachDb.removeCoach(coach);
    }

    /**
     * @param subscriberToRemove that is als judge
     * @throws Exception if the judge is already removed from judgeDB
     */
    private void removeJudge(Subscriber subscriberToRemove) throws Exception {
        //casting
        Judge judge = (Judge) subscriberToRemove;
        //remove the judge from his connected games
        for (Game game : judge.getTheJudgeGameList().values()) {
            game.getJudgesOfTheGameList().remove(judge);
        }
        // remove judge from association class of league and Season
        Map<Integer, Integer> seasonLeagueId_JudgeSeasonLeagueId = judge.getSeasonLeagueId_JudgeSeasonLeagueId();
        //remove the judge from his connected seasonLeague
        for (Integer seasonLeagueId : seasonLeagueId_JudgeSeasonLeagueId.keySet()) {
            seasonalLeagueDB.getSeasonLeague(seasonLeagueId).getJudgeEmailAddress_JudgeSeasonLeagueId().remove(judge.getEmailAddress());
        }
        //remove judge and his JudgeSeasonLeagueId from judgeSeasonLeagueDB
        for (Integer judgeSeasonLeagueId : seasonLeagueId_JudgeSeasonLeagueId.values()) {
            judgeSeasonLeagueDb.removeJudgeSeasonLeague(judgeSeasonLeagueId);
        }
        //remove the judge from judgeDb
        judgeDb.removeJudge(judge.getEmailAddress());
    }

    /**
     * @param subscriberToRemove that is also player
     * @throws Exception if the player is already removed from playerDB
     */
    private void removePlayer(Subscriber subscriberToRemove) throws Exception {
        //casting
        Player player = (Player) subscriberToRemove;
        //remove the personalPage from its followers
        Page playerPage = player.getPlayerPage();
        for (String fanEmail : playerPage.getFansFollowingThisPage().keySet()) {
            fanDb.getFan(fanEmail).getMyPages().remove(playerPage);
        }
        //remove the personal page of the player from PersonalPageDb
        pageDb.removePersonalPageFromDb(player.getPlayerPage().getPageID());
        //remove the player from his team
        player.getTeam().getPlayers().remove(player.getId());
        //remove the player from the playerDB
        playerDb.removePlayerFromDb(player);
    }

    /**
     * @param subscriberToRemove that is als teamManager
     * @throws Exception if the teamManager is already removed from teamManagerDB
     */
    private void removeTeamManager(Subscriber subscriberToRemove) throws Exception {
        //casting
        TeamManager teamManager = (TeamManager) subscriberToRemove;
        //remove teamManager from his team
        teamManager.getTeam().getTeamManagers().remove(teamManager.getOwnedByEmail());
        //remove the teamManager from teamManagerDB
        teamManagerDb.removeSubscriptionTeamManager(teamManager.getOwnedByEmail());
    }
}
