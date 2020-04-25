package Controller;

import Data.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SystemAdministratorControllerTest {
    SystemAdministratorController systemAdministratorController = new SystemAdministratorController();

    @Before
    public void init() {
        final List<Db> dbs = new ArrayList<>();
        dbs.add(SubscriberDbInMemory.getInstance());
        dbs.add(FanDbInMemory.getInstance());
        dbs.add(PageDbInMemory.getInstance());
        dbs.add(TeamDbInMemory.getInstance());
        dbs.add(CoachDbInMemory.getInstance());
        dbs.add(CourtDbInMemory.getInstance());
        dbs.add(JudgeDbInMemory.getInstance());
        dbs.add(PlayerDbInMemory.getInstance());
        dbs.add(TeamManagerDbInMemory.getInstance());
        dbs.add(TeamOwnerDbInMemory.getInstance());
        dbs.add(SeasonLeagueDbInMemory.getInstance());
        dbs.add(JudgeSeasonLeagueDbInMemory.getInstance());
        dbs.add(RoleDbInMemory.getInstance());
        dbs.add(SystemAdministratorDbInMemory.getInstance());
        dbs.add(RepresentativeAssociationDbInMemory.getInstance());
        dbs.add(TeamDbInMemory.getInstance());
        dbs.add(RoleDbInMemory.getInstance());
        for (Db db : dbs) {
            db.deleteAll();
        }
    }

    @Test
    public void closeExistTeam() {
        try {
            TeamDbInMemory.getInstance().createTeam("barca");
            systemAdministratorController.closeTeamForEver("barca");
        } catch (Exception e) {
            //not should enter here
            Assert.assertEquals(e.getMessage(), null);
        }
    }

    @Test
    public void closeNullTeam() {
        try {
            systemAdministratorController.closeTeamForEver(null);
        } catch (Exception e) {
            Assert.assertEquals("Team not found", e.getMessage());
        }
    }

    @Test
    public void closeNotExistTeam(){
        try {
            systemAdministratorController.closeTeamForEver("blaTeam");
        } catch (Exception e) {
            Assert.assertEquals("Team not found",e.getMessage());
        }
    }

}
