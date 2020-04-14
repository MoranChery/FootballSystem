package Controller;

import Data.*;
import Model.Enums.TeamStatus;
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


    public SystemAdministratorController() {
        teamDb = TeamDbInMemory.getInstance();
        subscriberDb = SubscriberDbInMemory.getInstance();
        coachDb = CoachDbInMemory.getInstance();
        courtDb = CourtDbInMemory.getInstance();
        judgeDb = JudgeDbInMemory.getInstance();
        playerDb = PlayerDbInMemory.getInstance();
        teamManagerDb = TeamManagerDbInMemory.getInstance();
        teamOwnerDb = TeamOwnerDbInMemory.getInstance();
    }

    //use case 8.1
    public void closeTeamForEver(String teamName) {
        try {
            Team teamToClose = teamDb.getTeam(teamName);
            teamToClose.setTeamStatus(TeamStatus.CLOSE);
            //todo: send alert to the team owners and to the team managers
            //todo: update the log file
        } catch (Exception e) {
            System.out.println("the team " + teamName + " doesn't exist in the system");
        }
    }

    //todo: remove the subscriber from the association
    //use case 8.2
    public void removeSubscriber(String email) {
        try {
            Subscriber subscriberToRemove = subscriberDb.getSubscriber(email);
            subscriberToRemove.removeMe();
            subscriberDb.removeSubscriberFromDB(email);
            if (subscriberToRemove instanceof Coach) {
                coachDb.removeCoach((Coach) subscriberToRemove);
            }
            if (subscriberToRemove instanceof Judge) {
                judgeDb.removeJudge(((Judge) subscriberToRemove).getId());
            }
            if (subscriberToRemove instanceof Player) {
                playerDb.removePlayer((Player) subscriberToRemove);
            }
            if (subscriberToRemove instanceof TeamManager) {
                teamManagerDb.removeSubscriptionTeamManager(((TeamManager) subscriberToRemove).getId());
            }
            if (subscriberToRemove instanceof TeamOwner) {
                teamOwnerDb.removeSubscriptionTeamOwner(((TeamOwner) subscriberToRemove).getId());
            }
        } catch (Exception e) {
            System.out.println("the subscriber doesn't in the system!");
        }
    }
}
