package Controller;

import Data.*;
import Model.Enums.*;
import Model.*;
import Model.UsersTypes.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RepresentativeAssociationControllerTest extends BaseEmbeddedSQL
{
    private RepresentativeAssociationController representativeAssociationController = new RepresentativeAssociationController();
    private GameDb gameDb = GameDbInServer.getInstance();


    @Before
    public void init() throws SQLException
    {
        final List<Db> dbs = new ArrayList<>();

//        dbs.add(RepresentativeAssociationDbInMemory.getInstance());
//        dbs.add(RoleDbInMemory.getInstance());
//        dbs.add(SubscriberDbInMemory.getInstance());
//        dbs.add(LeagueDbInMemory.getInstance());
//        dbs.add(SeasonDbInMemory.getInstance());
//        dbs.add(SeasonLeagueDbInMemory.getInstance());
//        dbs.add(JudgeDbInMemory.getInstance());
//        dbs.add(JudgeSeasonLeagueDbInMemory.getInstance());

        dbs.add(RepresentativeAssociationDbInServer.getInstance());
        dbs.add(RoleDbInServer.getInstance());
        dbs.add(SubscriberDbInServer.getInstance());
        dbs.add(LeagueDbInServer.getInstance());
        dbs.add(SeasonDbInServer.getInstance());
        dbs.add(SeasonLeagueDbInServer.getInstance());
        dbs.add(JudgeDbInServer.getInstance());
        dbs.add(JudgeSeasonLeagueDbInServer.getInstance());
        dbs.add(GameDbInServer.getInstance());
        dbs.add((TeamDbInServer.getInstance()));
        dbs.add((CourtDbInServer.getInstance()));
        dbs.add(gameDb);
        dbs.add((GameJudgesListDbInServer.getInstance()));

        for (Db db : dbs)
        {
            db.deleteAll();
        }
    }

    //region createRepresentativeAssociation_Tests

    @Test
    public void createRepresentativeAssociation_null() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("No RepresentativeAssociation been created", e.getMessage());
        }
    }


    @Test
    public void createRepresentativeAssociation_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        RepresentativeAssociation representativeAssociation = RepresentativeAssociationDbInMemory.getInstance().getRepresentativeAssociation("username/emailAddress");
        RepresentativeAssociation representativeAssociation = RepresentativeAssociationDbInServer.getInstance().getRepresentativeAssociation("username/emailAddress");
        Assert.assertEquals("username/emailAddress", representativeAssociation.getEmailAddress());
    }


    @Test
    public void createRepresentativeAssociation_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
        }
        catch (Exception e)
        {
            Assert.assertEquals("RepresentativeAssociation already exists in the system", e.getMessage());
        }
    }

    //endregion


    //region createLeague_Tests

    @Test
    public void createLeague_null_all() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague(null, null);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }


    @Test
    public void createLeague_null_email() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague(null, "leagueName");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }


    @Test
    public void createLeague_null_leagueName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", null);
        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the League details incorrect", e.getMessage());
        }
    }


    @Test
    public void createLeague_noPermissions() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress2", "leagueName");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }


    @Test
    public void createLeague_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress","leagueName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        League league = LeagueDbInMemory.getInstance().getLeague("leagueName");
        League league = LeagueDbInServer.getInstance().getLeague("leagueName");
        Assert.assertEquals("leagueName", league.getLeagueName());
    }


    @Test
    public void createLeague_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createLeague("username/emailAddress","leagueName");
        }
        catch (Exception e)
        {
            Assert.assertEquals("League already exists in the system", e.getMessage());
        }
    }

    //endregion








    //region createSeason_Tests

    @Test
    public void createSeason_null_all() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createSeason(null, null);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }


    @Test
    public void createSeason_null_email() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createSeason(null, "seasonName");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }


    @Test
    public void createSeason_null_seasonName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createSeason("username/emailAddress", null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Season details incorrect", e.getMessage());
        }
    }


    @Test
    public void createSeason_noPermissions() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createSeason("username/emailAddress2", "seasonName");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }


    @Test
    public void createSeason_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        Season season = SeasonDbInMemory.getInstance().getSeason("seasonName");
        Season season = SeasonDbInServer.getInstance().getSeason("seasonName");
        Assert.assertEquals("seasonName", season.getSeasonName());
    }


    @Test
    public void createSeason_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season already exists in the system", e.getMessage());
        }
    }

    //endregion



    //region createSeasonLeague_Tests
    @Test
    public void createSeasonLeague_null_all() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague(null, null, null, null, null);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_null_email() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague(null, "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_null_leagueName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", null, "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the SeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_null_seasonName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName",null, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the SeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_null_CalculateLeaguePoints() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", null, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the SeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_null_InlayGames() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the SeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_noPermissions() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress2", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        SeasonLeague seasonLeague = SeasonLeagueDbInMemory.getInstance().getSeasonLeague("seasonName_leagueName");
        SeasonLeague seasonLeague = SeasonLeagueDbInServer.getInstance().getSeasonLeague("seasonName_leagueName");
        Assert.assertEquals("seasonName_leagueName", seasonLeague.getSeasonLeagueName());
    }

    @Test
    public void createSeasonLeague_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague already exists in the system", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_seasonNotExists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "season2Name", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Season not found", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_leagueNotExists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "league2Name", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            Assert.assertEquals("League not found", e.getMessage());
        }
    }
    //endregion



    //region createJudge_Tests

    @Test
    public void createJudge_null_all() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge(null,null, null, null, null, null, null);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void createJudge_null_email() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge(null,"username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void createJudge_null_username() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", null, "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudge_null_password() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", null, 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudge_null_id() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", null, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudge_null_firstName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, null, "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudge_null_lastName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", null, QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudge_null_QualificationJudge() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudge_null_JudgeType() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudge_noPermissions() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress2", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void createJudge_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber("username");
        Subscriber subscriber = SubscriberDbInServer.getInstance().getSubscriber("username");
        Assert.assertEquals("username", subscriber.getEmailAddress());

//        Judge judge = JudgeDbInMemory.getInstance().getJudge("username");
        Judge judge = JudgeDbInServer.getInstance().getJudge("username");
        Assert.assertEquals("username", judge.getEmailAddress());

        Role role = RoleDbInServer.getInstance().getRole("username");
        RoleType roleType = role.getRoleType();
        Assert.assertEquals(RoleType.JUDGE, roleType);
    }

    @Test
    public void createJudge_exists_inSubscriberDb() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge already exists in the system", e.getMessage());
        }
    }


    @Test
    public void createJudge_exists_inJudgeDb() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
//            JudgeDbInMemory.getInstance().getAllJudgesMap().put("username", new Judge("username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL));

            //todo-check this function
//            JudgeDbInServer.getInstance().getAllJudgesMap().put("username", new Judge("username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL));



            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge already exists in the system", e.getMessage());
        }
    }

    //endregion



    //region removeJudge_Tests
    @Test
    public void removeJudge_null_all() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.removeJudge(null, null);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void removeJudge_null_email() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.removeJudge(null, "username");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void removeJudge_null_judgeUser() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.removeJudge("username/emailAddress", null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void removeJudge_noPermissions() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.removeJudge("username/emailAddress2", "username");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void removeJudge_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.removeJudge("username/emailAddress", "username");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }

    @Test
    public void removeJudge_notExists_inJudgeDb() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.removeJudge("username/emailAddress", "username2");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }


    @Test
    public void removeJudge_notExists_inSubscriberDb() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
//            JudgeDbInMemory.getInstance().getAllJudgesMap().put("username2", new Judge("username2", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL));


            //todo-check this function
//            JudgeDbInServer.getInstance().getAllJudgesMap().put("username2", new Judge("username2", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL));
            


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.removeJudge("username/emailAddress", "username2");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }

    //endregion


    //region createJudgeSeasonLeague_Tests

    @Test
    public void createJudgeSeasonLeague_null_all() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.createJudgeSeasonLeague(null,null, null);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void createJudgeSeasonLeague_null_email() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.createJudgeSeasonLeague(null,"seasonName_leagueName", "username");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void createJudgeSeasonLeague_null_seasonLeagueName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.createJudgeSeasonLeague("username/emailAddress", null, "username");
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the JudgeSeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudgeSeasonLeague_null_judgeEmailAddress() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.createJudgeSeasonLeague("username/emailAddress", "seasonName_leagueName", null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the JudgeSeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudgeSeasonLeague_noPermissions() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.createJudgeSeasonLeague("username/emailAddress2", "seasonName_leagueName", "username");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void createJudgeSeasonLeague_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.createJudgeSeasonLeague("username/emailAddress", "seasonName_leagueName", "username");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        JudgeSeasonLeague judgeSeasonLeague = JudgeSeasonLeagueDbInMemory.getInstance().getJudgeSeasonLeague("seasonName_leagueName_username");
        JudgeSeasonLeague judgeSeasonLeague = JudgeSeasonLeagueDbInServer.getInstance().getJudgeSeasonLeague("seasonName_leagueName_username");
        Assert.assertEquals("seasonName_leagueName_username", judgeSeasonLeague.getJudgeSeasonLeagueName());
    }

    @Test
    public void createJudgeSeasonLeague_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.createJudgeSeasonLeague("username/emailAddress", "seasonName_leagueName", "username");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createJudgeSeasonLeague("username/emailAddress", "seasonName_leagueName", "username");
        }
        catch (Exception e)
        {
            Assert.assertEquals("JudgeSeasonLeague already exists in the system", e.getMessage());
        }
    }

    @Test
    public void createJudgeSeasonLeague_seasonLeagueNotExists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.createJudgeSeasonLeague("username/emailAddress", "seasonName_leagueName2", "username");
        }
        catch (Exception e)
        {
            Assert.assertEquals("SeasonLeague not found", e.getMessage());
        }
    }

    @Test
    public void createJudgeSeasonLeague_judgeNotExists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username/emailAddress", "username", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
            representativeAssociationController.createJudgeSeasonLeague("username/emailAddress", "seasonName_leagueName", "username2");
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge not found", e.getMessage());
        }
    }
    //endregion

    //region changeCalculateLeaguePointsPolicy_Tests
    @Test
    public void changeCalculateLeaguePointsPolicy_null_all() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy(null, null, null);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void changeCalculateLeaguePointsPolicy_null_email() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy(null, "seasonName_LeagueName", null);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void changeCalculateLeaguePointsPolicy_null_seasonLeagueName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy("username/emailAddress", null, CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("SeasonLeague or CalculateLeaguePointsPolicy details incorrect", e.getMessage());
        }
    }

    @Test
    public void changeCalculateLeaguePointsPolicy_null_CalculateLeaguePoints() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy("username/emailAddress", "seasonName_LeagueName", null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("SeasonLeague or CalculateLeaguePointsPolicy details incorrect", e.getMessage());
        }
    }

    @Test
    public void changeCalculateLeaguePointsPolicy_noPermissions() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy("username/emailAddress2", "seasonName_leagueName", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Only RepresentativeAssociation has permissions to this action!", e.getMessage());
        }
    }

    @Test
    public void changeCalculateLeaguePointsPolicy_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy("username/emailAddress", "seasonName_leagueName", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        SeasonLeague seasonLeague = SeasonLeagueDbInMemory.getInstance().getSeasonLeague("seasonName_leagueName");
        SeasonLeague seasonLeague = SeasonLeagueDbInServer.getInstance().getSeasonLeague("seasonName_leagueName");
        Assert.assertEquals(CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, seasonLeague.getCalculateLeaguePoints());
    }

    @Test
    public void changeCalculateLeaguePointsPolicy_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("username/emailAddress", "leagueName");
            representativeAssociationController.createSeason("username/emailAddress", "seasonName");
            representativeAssociationController.createSeasonLeague("username/emailAddress", "leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy("username/emailAddress", "seasonName_leagueName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
//        SeasonLeague seasonLeague = SeasonLeagueDbInMemory.getInstance().getSeasonLeague("seasonName_leagueName");
        SeasonLeague seasonLeague = SeasonLeagueDbInServer.getInstance().getSeasonLeague("seasonName_leagueName");
        Assert.assertEquals(CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, seasonLeague.getCalculateLeaguePoints());
    }

    //endregion



    @Test
    public void checkPermissionOfRepresentativeAssociation_withPreviousRole() throws Exception
    {
        Boolean permission;
        Subscriber subscriber = new Fan("username/emailAddress", "password", 12345, "firstName", "lastName");
        SubscriberDbInServer.getInstance().insertSubscriber(subscriber);

        RoleDbInServer.getInstance().insertRole("username/emailAddress", null, RoleType.FAN);
//        RoleDbInServer.getInstance().createRoleInSystem("username/emailAddress", RoleType.FAN);
        RepresentativeAssociation representativeAssociation = new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName");

        representativeAssociationController.createRepresentativeAssociation(representativeAssociation);

        permission = representativeAssociationController.checkPermissionOfRepresentativeAssociation("username/emailAddress");
        Assert.assertEquals(Boolean.TRUE, permission);
    }

////////////////////////////////////////////////////
    private void initForChangeGame_location_and_date_Test()
    {
        Season season = new Season("seasonName");
        League league = new League("leagueName");
        SeasonLeague seasonLeague = new SeasonLeague(season.getSeasonName(), league.getLeagueName(), CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);

        Team teamHost = new Team();
        teamHost.setTeamName("teamHost");
        Team teamGuest = new Team();
        teamGuest.setTeamName("teamGuest");

        Court court1 = new Court();
        court1.setCourtName("court1");
        court1.setCourtCity("court1city1");

        Court court2 = new Court();
        court2.setCourtName("court2");
        court2.setCourtCity("court1city2");

        Date dateStart = new Date(20, 10, 8);

        Game game1 = new Game("game1", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName());
        Game game2 = new Game("game2", dateStart, seasonLeague.getSeasonLeagueName(), teamHost.getTeamName(), teamGuest.getTeamName(), court1.getCourtName());
        try
        {
            SeasonDbInServer.getInstance().insertSeason(season);
            LeagueDbInServer.getInstance().insertLeague(league);
            SeasonLeagueDbInServer.getInstance().insertSeasonLeague(seasonLeague);

            TeamDbInServer.getInstance().insertTeam(teamHost.getTeamName());
            TeamDbInServer.getInstance().insertTeam(teamGuest.getTeamName());

            CourtDbInServer.getInstance().insertCourt(court1);
            CourtDbInServer.getInstance().insertCourt(court2);

            GameDbInServer.getInstance().insertGame(game1);
            GameDbInServer.getInstance().insertGame(game2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void changeGameLocation_empty_all() {
        RepresentativeAssociation representativeAssociation = new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName");

        initForChangeGame_location_and_date_Test();
    }

    @Test
    public void changeGameLocationEmptyInput() throws Exception {
        try{
            representativeAssociationController.changeGameLocation("","loc", "gameid");
        }
        catch (Exception e){
            Assert.assertEquals("The value is empty", e.getMessage());
        }
    }


    @Test
    public void changeGameLocationNullInput() throws Exception{
        try{
            representativeAssociationController.changeGameLocation(null,"loc", "gameid");
        }
        catch (Exception e){
            Assert.assertEquals("bad input", e.getMessage());
        }
    }


    @Test
    public void changeGameLocationGameNotInDB() throws Exception{
        try{

            representativeAssociationController.changeGameLocation("rep@gmail.com","loc", "gameid");
        }
        catch (Exception e){
            Assert.assertEquals("game not in DB", e.getMessage());
        }
    }

    @Test
    public void changeGameLocationSameLoc() throws Exception{
        try{
            Game gameToChange = new Game("gameID1", new Date(), "seasonLeague", "hostTeam",  "guestTeam",  "court");
            gameDb.insertGame(gameToChange);
            representativeAssociationController.changeGameLocation("rep@gmail.com","court", "gameID1");
        }
        catch (Exception e){
            Assert.assertEquals("same location", e.getMessage());
        }
    }

    @Test
    public void changeGameLocationLegal() throws Exception {
        Game gameToChange = new Game("gameID1", new Date(), "seasonLeague", "hostTeam", "guestTeam", "court");
        gameDb.insertGame(gameToChange);
        representativeAssociationController.changeGameLocation("rep@gmail.com", "loc", "gameID1");

        Assert.assertEquals("loc", gameToChange.getCourt());
    }
    @Test
    public void changeGameDateEmptyInput() throws Exception{

    }


}
