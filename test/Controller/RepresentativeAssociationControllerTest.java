package Controller;

import Controller.RepresentativeAssociationController;
import Data.*;
import Model.*;
import Model.Enums.*;
import Model.UsersTypes.Judge;
import Model.UsersTypes.RepresentativeAssociation;
import Model.UsersTypes.Subscriber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RepresentativeAssociationControllerTest
{
    private RepresentativeAssociationController representativeAssociationController = new RepresentativeAssociationController();

    @Before
    public void init()
    {
        final List<Db> dbs = new ArrayList<>();

        dbs.add(RepresentativeAssociationDbInMemory.getInstance());
        dbs.add(RoleDbInMemory.getInstance());
        dbs.add(SubscriberDbInMemory.getInstance());
        dbs.add(LeagueDbInMemory.getInstance());
        dbs.add(SeasonDbInMemory.getInstance());
        dbs.add(SeasonLeagueDbInMemory.getInstance());
        dbs.add(JudgeDbInMemory.getInstance());
        dbs.add(JudgeSeasonLeagueDbInMemory.getInstance());

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
        RepresentativeAssociation representativeAssociation = RepresentativeAssociationDbInMemory.getInstance().getRepresentativeAssociation("username/emailAddress");
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
    public void createLeague_null() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the League details incorrect", e.getMessage());
        }
    }

    @Test
    public void createLeague_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        League league = LeagueDbInMemory.getInstance().getLeague("leagueName");
        Assert.assertEquals("leagueName", league.getLeagueName());
    }

    @Test
    public void createLeague_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createLeague("leagueName");
        }
        catch (Exception e)
        {
            Assert.assertEquals("League already exists in the system", e.getMessage());
        }
    }
    //endregion

    //region createSeason_Tests
    @Test
    public void createSeason_null() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createSeason(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Season details incorrect", e.getMessage());
        }
    }

    @Test
    public void createSeason_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createSeason("seasonName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Season season = SeasonDbInMemory.getInstance().getSeason("seasonName");
        Assert.assertEquals("seasonName", season.getSeasonName());
    }

    @Test
    public void createSeason_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createSeason("seasonName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createSeason("seasonName");
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague(null, null, null, null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the SeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_null_leagueName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague(null, "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName",null, CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", null, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the SeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createSeasonLeague_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        SeasonLeague seasonLeague = SeasonLeagueDbInMemory.getInstance().getSeasonLeague("seasonName_leagueName");
        Assert.assertEquals("seasonName_leagueName", seasonLeague.getSeasonLeagueName());
    }

    @Test
    public void createSeasonLeague_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "season2Name", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("league2Name", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
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
            representativeAssociationController.createJudge(null, null, null, null, null, null, null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudge_null_username() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge(null, "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
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
            representativeAssociationController.createJudge("username", null, 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
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
            representativeAssociationController.createJudge("username", "password", null, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
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
            representativeAssociationController.createJudge("username", "password", 12345, null, "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
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
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", null, QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
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
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", null, JudgeType.MAJOR_JUDGE);
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
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudge_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Subscriber subscriber = SubscriberDbInMemory.getInstance().getSubscriber("username");
        Assert.assertEquals("username", subscriber.getEmailAddress());

        Judge judge = JudgeDbInMemory.getInstance().getJudge("username");
        Assert.assertEquals("username", judge.getEmailAddress());

        Role role = RoleDbInMemory.getInstance().getRole("username");
        RoleType roleType = role.getRoleType();
        Assert.assertEquals(RoleType.JUDGE, roleType);
    }

    @Test
    public void createJudge_exists_inSubscriberDb() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
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
            JudgeDbInMemory.getInstance().getAllJudgesMap().put("username", new Judge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE));
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
        }
        catch (Exception e)
        {
            Assert.assertEquals("Judge already exists in the system", e.getMessage());
        }
    }
    //endregion

    //region removeJudge_Tests
    @Test
    public void removeJudge_null() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            representativeAssociationController.removeJudge(null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the Judge details incorrect", e.getMessage());
        }
    }

    @Test
    public void removeJudge_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            representativeAssociationController.removeJudge("username");
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
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.removeJudge("username2");
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
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            JudgeDbInMemory.getInstance().getAllJudgesMap().put("username2", new Judge("username2", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.removeJudge("username2");
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            representativeAssociationController.createJudgeSeasonLeague(null, null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the JudgeSeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudgeSeasonLeague_null_seasonLeagueName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            representativeAssociationController.createJudgeSeasonLeague(null, "username");
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            representativeAssociationController.createJudgeSeasonLeague("seasonName_leagueName", null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("One or more of the JudgeSeasonLeague details incorrect", e.getMessage());
        }
    }

    @Test
    public void createJudgeSeasonLeague_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            representativeAssociationController.createJudgeSeasonLeague("seasonName_leagueName", "username");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        JudgeSeasonLeague judgeSeasonLeague = JudgeSeasonLeagueDbInMemory.getInstance().getJudgeSeasonLeague("seasonName_leagueName_username");
        Assert.assertEquals("seasonName_leagueName_username", judgeSeasonLeague.getJudgeSeasonLeagueName());
    }

    @Test
    public void createJudgeSeasonLeague_exists() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            representativeAssociationController.createJudgeSeasonLeague("seasonName_leagueName", "username");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            representativeAssociationController.createJudgeSeasonLeague("seasonName_leagueName", "username");
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            representativeAssociationController.createJudgeSeasonLeague("seasonName_leagueName2", "username");
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.createJudge("username", "password", 12345, "firstName", "lastName", QualificationJudge.FOOTBALL, JudgeType.MAJOR_JUDGE);
            representativeAssociationController.createJudgeSeasonLeague("seasonName_leagueName", "username2");
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy(null, null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("SeasonLeague or CalculateLeaguePointsPolicy details incorrect", e.getMessage());
        }
    }

    @Test
    public void changeCalculateLeaguePointsPolicy_null_seasonLeagueName() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy(null, CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1);
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
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy("seasonName_LeagueName", null);
        }
        catch (Exception e)
        {
            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("SeasonLeague or CalculateLeaguePointsPolicy details incorrect", e.getMessage());
        }
    }

    @Test
    public void changeCalculateLeaguePointsPolicy_legal() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy("seasonName_leagueName", CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        SeasonLeague seasonLeague = SeasonLeagueDbInMemory.getInstance().getSeasonLeague("seasonName_leagueName");
        Assert.assertEquals(CalculateLeaguePoints.WIN_IS_1_TIE_IS_0_LOSE_IS_MINUS1, seasonLeague.getCalculateLeaguePoints());
    }

    @Test
    public void changeCalculateLeaguePointsPolicy_exsist() throws Exception
    {
        try
        {
            representativeAssociationController.createRepresentativeAssociation(new RepresentativeAssociation("username/emailAddress", "password", 12345, "firstName", "lastName"));
            representativeAssociationController.createLeague("leagueName");
            representativeAssociationController.createSeason("seasonName");
            representativeAssociationController.createSeasonLeague("leagueName", "seasonName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, InlayGames.EACH_TWO_TEAMS_PLAY_ONE_TIME);
            representativeAssociationController.changeCalculateLeaguePointsPolicy("seasonName_leagueName", CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        SeasonLeague seasonLeague = SeasonLeagueDbInMemory.getInstance().getSeasonLeague("seasonName_leagueName");
        Assert.assertEquals(CalculateLeaguePoints.WIN_IS_2_TIE_IS_1_LOSE_IS_0, seasonLeague.getCalculateLeaguePoints());
    }
    //endregion

}
