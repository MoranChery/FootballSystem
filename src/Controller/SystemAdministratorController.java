package Controller;

import Data.*;
import Model.Enums.TeamStatus;
import Model.Game;
import Model.PersonalPage;
import Model.Team;
import Model.UsersTypes.*;

public class SystemAdministratorController {
    private TeamDb teamDb;
    private SubscriberDb subscriberDb;
    private CoachDb coachDb;
    private CourtDb courtDb;
    private JudgeDb judgeDb;
    private PlayerDb playerDb;
    private TeamManagerDb teamManagerDb;
    private TeamOwnerDb teamOwnerDb;
    private PersonalPageDb personalPageDb;
    private FanDb fanDb;


    public SystemAdministratorController() {
        teamDb = TeamDbInMemory.getInstance();
        subscriberDb = SubscriberDbInMemory.getInstance();
        coachDb = CoachDbInMemory.getInstance();
        courtDb = CourtDbInMemory.getInstance();
        judgeDb = JudgeDbInMemory.getInstance();
        playerDb = PlayerDbInMemory.getInstance();
        teamManagerDb = TeamManagerDbInMemory.getInstance();
        teamOwnerDb = TeamOwnerDbInMemory.getInstance();
        personalPageDb = PersonalPageDbInMemory.getInstance();
        fanDb=FanDbInMemory.getInstance();
    }

    //use case 8.1

    /**
     *
     * @param teamName that the system administrator want to close for ever
     */
    public void closeTeamForEver(String teamName) {
        try {
            Team teamToClose = teamDb.getTeam(teamName);
            teamToClose.setTeamStatus(TeamStatus.CLOSE);
            //todo: send alert to the team owners and to the team managers
            System.out.println("send alert to the team owners and to the team managers");
            //todo: update the log file
            System.out.println("log file updated");
        } catch (Exception e) {
            System.out.println("the team " + teamName + " doesn't exist in the system");
        }
    }

    //use case 8.2
    /**
     *
     * @param email of the subscriber that the system administrator want to remove
     */
    public void removeSubscriber(String email) {
        try {
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
            if(subscriberToRemove instanceof Fan){
                removeFan(subscriberToRemove);
            }
            System.out.println("the chosen subscriber with the Email "+email+" deleted successfully :)");
        } catch (Exception e) {
            System.out.println("the subscriber with the Email "+email+" doesn't in the system!");
        }
    }

    /**
     *
     * @param subscriberToRemove that is also fan
     * @throws Exception if the fan is already removed from fanDB
     */
    private void removeFan(Subscriber subscriberToRemove) throws Exception {
        //casting
        Fan fan= (Fan) subscriberToRemove;
        //remove the fan from personalPages that he followed after
        for(PersonalPage personalPage:fan.getMyPages().values()){
            personalPage.getFansFollowingThisPage().remove(fan);
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
        teamOwnerDb.removeSubscriptionTeamOwner(teamOwner.getId());
        try {
            subscriberDb.removeSubscriberFromDB(teamOwner);
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param subscriberToRemove that is also coach
     * @throws Exception if the coach is already removed from coachDB
     */
    private void removeCoach(Subscriber subscriberToRemove) throws Exception {
        //casting
        Coach coach = (Coach) subscriberToRemove;
        //todo: wait until coach will connect to personalPage
        //remove the personalPage from its followers

        //remove the personal page of the coach from PersonalPageDb

        //remove the coach from the team
        coach.getTeam().getCoaches().remove(coach);
        //remove the coach from the coachDB
        coachDb.removeCoach(coach);
    }

    /**
     *
     * @param subscriberToRemove that is als judge
     * @throws Exception if the judge is already removed from judgeDB
     */
    private void removeJudge(Subscriber subscriberToRemove) throws Exception {
        //casting
        Judge judge = (Judge) subscriberToRemove;
        //remove the judge from his connected games
        //todo: until judge will be connected to games
        for (Game game : judge.getGames()) {

        }
        //todo: remove judge from association class of league and Season

        //remove the judge from judgeDb
        judgeDb.removeJudge(judge.getEmailAddress());
    }

    /**
     *
     * @param subscriberToRemove that is also player
     * @throws Exception if the player is already removed from playerDB
     */
    private void removePlayer(Subscriber subscriberToRemove) throws Exception {
        //casting
        Player player = (Player) subscriberToRemove;
        //todo: wait until coach will connected to personalPage
        //remove the personalPage from its followers

        //remove the personal page of the player from PersonalPageDb
        personalPageDb.removePersonalPageFromDb(player.getPersonalPage().getId());
        //remove the player from his team
        player.getTeam().getPlayers().remove(player.getId());
        //remove the player from the playerDB
        playerDb.removePlayerFromDb(player);
    }

    /**
     *
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
