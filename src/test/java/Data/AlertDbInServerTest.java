package Data;

import Controller.BaseEmbeddedSQL;
import Model.Alert;
import Model.Enums.QualificationJudge;
import Model.UsersTypes.Judge;
import Model.UsersTypes.RepresentativeAssociation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlertDbInServerTest extends BaseEmbeddedSQL
{
    private AlertDbInServer alertDbInServer = AlertDbInServer.getInstance();

    private RepresentativeAssociationDbInServer representativeAssociationDbInServer = RepresentativeAssociationDbInServer.getInstance();
    private JudgeDbInServer judgeDbInServer = JudgeDbInServer.getInstance();
    private SubscriberDbInServer subscriberDbInServer = SubscriberDbInServer.getInstance();

    @Before
    public void init() throws SQLException
    {
        final List<Db> dbs = new ArrayList<>();

        dbs.add(AlertDbInServer.getInstance());
        dbs.add(RepresentativeAssociationDbInServer.getInstance());
        dbs.add(JudgeDbInServer.getInstance());
        dbs.add(SubscriberDbInServer.getInstance());

        for (Db db : dbs)
        {
            db.deleteAll();
        }
    }

    @Test
    public void insertAlert_null() throws Exception
    {
        String email = null;
        Alert alert = null;

        try
        {
            alertDbInServer.insertAlertInDb(email, alert);
        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("Alert already exists in the system", e.getMessage());
        }
    }

    @Test
    public void insertAlert_legal() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        String email1 = judge1.getEmailAddress();
        String email2 = representativeAssociation1.getEmailAddress();
        Alert alert11 = new Alert("header for e1a1", "body for e1a1");
        Alert alert12 = new Alert("header for e1a2", "body for e1a2");
        Alert alert21 = new Alert("header for e2a1", "body for e2a1");

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            alertDbInServer.insertAlertInDb(email1, alert11);
            alertDbInServer.insertAlertInDb(email1, alert12);
            alertDbInServer.insertAlertInDb(email2, alert21);

            List<Alert> getAlertsForUser = alertDbInServer.getAlertsForUser(email1);

            List<String> getAlertsHeader = getAlertsHeader(getAlertsForUser);

            List<String> getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(2, getAlertsForUser.size());

            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(true, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e2a1"));

            getAlertsForUser = alertDbInServer.getAlertsForUser(email2);

            getAlertsHeader = getAlertsHeader(getAlertsForUser);

            getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(1, getAlertsForUser.size());

            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(false, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e2a1"));
        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("kkkAlert already exists in the system", e.getMessage());
        }
    }

    @Test
    public void getAlertsForUser_legal() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        String email1 = judge1.getEmailAddress();
        String email2 = representativeAssociation1.getEmailAddress();
        Alert alert11 = new Alert("header for e1a1", "body for e1a1");
        Alert alert12 = new Alert("header for e1a2", "body for e1a2");
        Alert alert21 = new Alert("header for e2a1", "body for e2a1");

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            alertDbInServer.insertAlertInDb(email1, alert11);
            alertDbInServer.insertAlertInDb(email1, alert12);
            alertDbInServer.insertAlertInDb(email2, alert21);

            List<Alert> getAlertsForUser = alertDbInServer.getAlertsForUser(email1);

            List<String> getAlertsHeader = getAlertsHeader(getAlertsForUser);

            List<String> getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(2, getAlertsForUser.size());

            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(true, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e2a1"));

            getAlertsForUser = alertDbInServer.getAlertsForUser(email2);

            getAlertsHeader = getAlertsHeader(getAlertsForUser);

            getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(1, getAlertsForUser.size());

            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(false, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e2a1"));
        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("kkkAlert already exists in the system", e.getMessage());
        }
    }

    @Test
    public void haveAlertInDB_legal() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        String email1 = judge1.getEmailAddress();
        String email2 = representativeAssociation1.getEmailAddress();
        Alert alert11 = new Alert("header for e1a1", "body for e1a1");
        Alert alert12 = new Alert("header for e1a2", "body for e1a2");

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            alertDbInServer.insertAlertInDb(email1, alert11);
            alertDbInServer.insertAlertInDb(email1, alert12);

            Assert.assertEquals(true, alertDbInServer.haveAlertInDB(email1));
            Assert.assertEquals(false, alertDbInServer.haveAlertInDB(email2));
        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("kkkAlert already exists in the system", e.getMessage());
        }
    }

    @Test
    public void deleteAll_legal() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        String email1 = judge1.getEmailAddress();
        String email2 = representativeAssociation1.getEmailAddress();
        Alert alert11 = new Alert("header for e1a1", "body for e1a1");
        Alert alert12 = new Alert("header for e1a2", "body for e1a2");
        Alert alert21 = new Alert("header for e2a1", "body for e2a1");

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            alertDbInServer.insertAlertInDb(email1, alert11);
            alertDbInServer.insertAlertInDb(email1, alert12);
            alertDbInServer.insertAlertInDb(email2, alert21);

            List<Alert> getAlertsForUser = alertDbInServer.getAlertsForUser(email1);

            List<String> getAlertsHeader = getAlertsHeader(getAlertsForUser);

            List<String> getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(2, getAlertsForUser.size());

            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(true, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e2a1"));

            getAlertsForUser = alertDbInServer.getAlertsForUser(email2);

            getAlertsHeader = getAlertsHeader(getAlertsForUser);

            getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(1, getAlertsForUser.size());

            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(false, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e2a1"));

            alertDbInServer.deleteAll();

            Assert.assertEquals(false, alertDbInServer.haveAlertInDB(email1));
            Assert.assertEquals(false, alertDbInServer.haveAlertInDB(email2));

        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("kkkAlert already exists in the system", e.getMessage());
        }
    }

    @Test
    public void removeUserFromTheAlertDB_legal() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        String email1 = judge1.getEmailAddress();
        String email2 = representativeAssociation1.getEmailAddress();
        Alert alert11 = new Alert("header for e1a1", "body for e1a1");
        Alert alert12 = new Alert("header for e1a2", "body for e1a2");
        Alert alert21 = new Alert("header for e2a1", "body for e2a1");

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            alertDbInServer.insertAlertInDb(email1, alert11);
            alertDbInServer.insertAlertInDb(email1, alert12);
            alertDbInServer.insertAlertInDb(email2, alert21);

            List<Alert> getAlertsForUser = alertDbInServer.getAlertsForUser(email1);

            List<String> getAlertsHeader = getAlertsHeader(getAlertsForUser);

            List<String> getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(2, getAlertsForUser.size());

            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(true, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e2a1"));

            getAlertsForUser = alertDbInServer.getAlertsForUser(email2);

            getAlertsHeader = getAlertsHeader(getAlertsForUser);

            getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(1, getAlertsForUser.size());

            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(false, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e2a1"));

            alertDbInServer.removeUserFromTheAlertDB(email1);

            Assert.assertEquals(false, alertDbInServer.haveAlertInDB(email1));
            Assert.assertEquals(true, alertDbInServer.haveAlertInDB(email2));

        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("kkkAlert already exists in the system", e.getMessage());
        }
    }

    @Test
    public void removeAllTheAlertTheUserHave_legal() throws Exception
    {
        Judge judge1 = new Judge("judge1@gmail.com", "password", 12345, "firstName", "lastName", QualificationJudge.NATIONAL);
        RepresentativeAssociation representativeAssociation1 = new RepresentativeAssociation("representativeAssociation1@gmail.com","password", 12345, "firstName", "lastName");

        String email1 = judge1.getEmailAddress();
        String email2 = representativeAssociation1.getEmailAddress();
        Alert alert11 = new Alert("header for e1a1", "body for e1a1");
        Alert alert12 = new Alert("header for e1a2", "body for e1a2");
        Alert alert21 = new Alert("header for e2a1", "body for e2a1");

        try
        {
            subscriberDbInServer.insertSubscriber(judge1);
            judgeDbInServer.insertJudge(judge1);

            subscriberDbInServer.insertSubscriber(representativeAssociation1);
            representativeAssociationDbInServer.insertRepresentativeAssociation(representativeAssociation1);

            alertDbInServer.insertAlertInDb(email1, alert11);
            alertDbInServer.insertAlertInDb(email1, alert12);
            alertDbInServer.insertAlertInDb(email2, alert21);

            List<Alert> getAlertsForUser = alertDbInServer.getAlertsForUser(email1);

            List<String> getAlertsHeader = getAlertsHeader(getAlertsForUser);

            List<String> getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(2, getAlertsForUser.size());

            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(true, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e2a1"));

            getAlertsForUser = alertDbInServer.getAlertsForUser(email2);

            getAlertsHeader = getAlertsHeader(getAlertsForUser);

            getAlertsBody = getAlertsBody(getAlertsForUser);

            Assert.assertEquals(1, getAlertsForUser.size());

            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a1"));
            Assert.assertEquals(false, getAlertsHeader.contains("header for e1a2"));
            Assert.assertEquals(true, getAlertsHeader.contains("header for e2a1"));

            Assert.assertEquals(false, getAlertsBody.contains("body for e1a1"));
            Assert.assertEquals(false, getAlertsBody.contains("body for e1a2"));
            Assert.assertEquals(true, getAlertsBody.contains("body for e2a1"));

            alertDbInServer.removeUserFromTheAlertDB(email1);

            Assert.assertEquals(false, alertDbInServer.haveAlertInDB(email1));
            Assert.assertEquals(true, alertDbInServer.haveAlertInDB(email2));

        }
        catch (Exception e)
        {
//            Assert.assertTrue(e instanceof NullPointerException);
            Assert.assertEquals("kkkAlert already exists in the system", e.getMessage());
        }
    }



    private List<String> getAlertsHeader(List<Alert> getAlertsForUser)
    {
        List<String> getAlertsHeader = new ArrayList<>();
        for (Alert alert : getAlertsForUser)
        {
            getAlertsHeader.add(alert.getMsgHeader());
        }
        return getAlertsHeader;
    }

    private List<String> getAlertsBody(List<Alert> getAlertsForUser)
    {
        List<String> getAlertsBody = new ArrayList<>();
        for (Alert alert : getAlertsForUser)
        {
            getAlertsBody.add(alert.getMsgBody());
        }
        return getAlertsBody;
    }
}
