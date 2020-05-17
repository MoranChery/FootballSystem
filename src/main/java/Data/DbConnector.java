package Data;

import Model.GameEventsLog;
import Model.UsersTypes.Judge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Set;

public class DbConnector
{
    public static final String API = "jdbc";
    public static final String DATABASE = "mysql";
    public static final String SERVER_NAME_DB_RUNNING_ON = "localhost";
    public static final String PORT = "3306";
    public static final String DB_NAME = "football_system_db";
    public static final String URL = API + ":" + DATABASE + "://" + SERVER_NAME_DB_RUNNING_ON + ":" + PORT + "/" + DB_NAME;

    public static final String URL1 = "jdbc:mysql://localhost:3306/";
    public static final String USER = "root";
    public static final String PASSWORD = "root";

    private static final DbConnector instance = new DbConnector();

    //private constructor to avoid client applications to use constructor
    public static DbConnector getInstance()
    {
        return instance;
    }

    private DbConnector()
    {
    }

    /**
     * Get a connection to database
     * @return Connection object
     */
    public static Connection getConnection()
    {
        try
        {
//            Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver");
            String urlNew = URL + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

            Connection connectionDb = DriverManager.getConnection(urlNew, USER, PASSWORD);

//            System.out.println("Connected to football_system_db");

            return connectionDb;
        }
        catch (SQLException | ClassNotFoundException ex)
        {
            throw new RuntimeException("Error connecting to the database", ex);
        }
    }

    /**
     * Test Connection
     */

    private void createAllTablesInDb() throws SQLException
    {
        System.out.println("Start Creating all tables in Database");

//        createAllSubscriberTableInDb();
        //todo- createComplaintTableInDb();
//        createCourtTableInDb();
//        createFinancialActivityTableInDb();
//        createGameTableInDb();
//        createGameEventTableInDb();
//        createJudgeSeasonLeagueTableInDb();
//        createLeagueTableInDb();
//        createPageTableInDb();
//        createPermissionsTableInDb();
//        createRoleTableInDb();
        createSeasonTableInDb();


        System.out.println("End Creating all tables in Database");
    }

    private void createSeasonTableInDb() throws SQLException
    {
        String createTableSql = "CREATE TABLE ";
        String allDetails = "("
                + "seasonName VARCHAR(45) NOT NULL,"
                /*+ "leagueName_SeasonLeagueName VARCHAR(45) NOT NULL,"*/;

        String primaryKey = "PRIMARY KEY (seasonName))";

        sqlCommand(createTableSql + "Season" + allDetails + primaryKey);
    }

    private void createRoleTableInDb() throws SQLException
    {
        String createTableSql = "CREATE TABLE ";
        String allDetails = "("
                + "emailAddress VARCHAR(45) NOT NULL,"
                + "teamName VARCHAR(45) DEFAULT NULL,"
                + "roleType VARCHAR(45) NOT NULL";

        String primaryKey = /*"PRIMARY KEY (emailAddress))"*/ ")";

        sqlCommand(createTableSql + "Role" + allDetails + primaryKey);
    }

    private void createPermissionsTableInDb() throws SQLException
    {
        String createTableSql = "CREATE TABLE ";
        String allDetails = "("
                + "emailAddress VARCHAR(45) NOT NULL,"
                + "permissionType VARCHAR(45) NOT NULL";

        String primaryKey = /*"PRIMARY KEY (emailAddress))"*/ ")";

        sqlCommand(createTableSql + "Permissions" + allDetails + primaryKey);
    }

    private void createPageTableInDb() throws SQLException
    {
        String createTableSql = "CREATE TABLE ";
        String allDetails = "("
                + "pageID VARCHAR(45) NOT NULL,"
                /*+ "fansFollowingThisPage VARCHAR(45) NOT NULL,"*/;

        String primaryKey = "PRIMARY KEY (pageID))";

        sqlCommand(createTableSql + "Page" + allDetails + primaryKey);
    }

    private void createLeagueTableInDb() throws SQLException
    {
        String createTableSql = "CREATE TABLE ";
        String allDetails = "("
                + "leagueName VARCHAR(45) NOT NULL,"
                /*+ "seasonName_SeasonLeagueName VARCHAR(45) NOT NULL,"*/;

        String primaryKey = "PRIMARY KEY (leagueName))";

        sqlCommand(createTableSql + "League" + allDetails + primaryKey);
    }

    private void createJudgeSeasonLeagueTableInDb() throws SQLException
    {
        String createTableSql = "CREATE TABLE ";
        String allDetails = "("
                + "judgeSeasonLeagueName VARCHAR(45) NOT NULL,"
                + "seasonLeagueName VARCHAR(45) NOT NULL,"
                + "judgeEmailAddress VARCHAR(45) NOT NULL,";

        String primaryKey = "PRIMARY KEY (judgeSeasonLeagueName))";

        sqlCommand(createTableSql + "judgeSeasonLeague" + allDetails + primaryKey);
    }

    private void createGameEventTableInDb() throws SQLException
    {
        {
            String createTableSql = "CREATE TABLE ";
            String allDetails = "("
                    + "gameId VARCHAR(45) NOT NULL,"
                    + "eventId VARCHAR(45) NOT NULL,"
                    + "gameDate DATE NOT NULL,"
                    + "eventTime DATE NOT NULL,"
                    + "eventMinute INT NOT NULL,"
                    /*+ "gameEventType VARCHAR(45) NOT NULL,"*/
                    + "description VARCHAR(45) NOT NULL,";

            String primaryKey = "PRIMARY KEY (gameId))";

            sqlCommand(createTableSql + "gameEvent" + allDetails + primaryKey);
        }

    }

    private void createGameTableInDb() throws SQLException
    {
        String createTableSql = "CREATE TABLE ";
        String allDetails = "("
                + "gameID VARCHAR(45) NOT NULL,"
                + "gameDate DATE NOT NULL,"
                + "seasonLeague VARCHAR(45) NOT NULL,"
                + "hostTeam VARCHAR(45) NOT NULL,"
                + "guestTeam VARCHAR(45) NOT NULL,"
                + "court VARCHAR(45) NOT NULL,"
                + "hostTeamScore INT NOT NULL,"
                + "guestTeamScore INT NOT NULL,"
                /*+ "judgesOfTheGameList VARCHAR(45) NOT NULL,"*/
                /*+ "eventLog VARCHAR(45) NOT NULL,"*/
                + "majorJudge VARCHAR(45) NOT NULL,"
                + "endGameTime DATE NOT NULL,";

        String primaryKey = "PRIMARY KEY (gameID))";

        sqlCommand(createTableSql + "game" + allDetails + primaryKey);
    }

    private void createFinancialActivityTableInDb() throws SQLException
    {
        String createTable = "CREATE TABLE ";
        String allDetails = "("
                + "financialActivityId VARCHAR(45) NOT NULL,"
                + "financialActivityAmount DOUBLE NOT NULL,"
                + "description VARCHAR(45) NOT NULL,"
                + "financialActivityType VARCHAR(45) NOT NULL,"
                + "team VARCHAR(45) NOT NULL,";
        String primaryKey = "PRIMARY KEY (financialActivityId))";

        sqlCommand(createTable + "financialActivity" + allDetails + primaryKey);
    }

    private void createCourtTableInDb() throws SQLException
    {
        String createTable = "CREATE TABLE ";
        String allDetails = "("
                + "courtName VARCHAR(45) NOT NULL,"
                + "courtCity VARCHAR(45) NOT NULL,";
        String primaryKey = "PRIMARY KEY (courtName))";

        //HashMap<String, Team> teams Constraint in team teble

        sqlCommand(createTable + "court" + allDetails + primaryKey);
    }

    private void createComplaintTableInDb() throws SQLException
    {
        String createTable = "CREATE TABLE ";
        String allDetails = "("
                + "emailAddress VARCHAR(45) NOT NULL,"
                + "password VARCHAR(45) NOT NULL,"
                + "id int NOT NULL,"
                + "firstName VARCHAR(45) NOT NULL,"
                + "LastName varchar(45) NOT NULL,"
                + "status varchar(45) NOT NULL,";
        String primaryKey = "PRIMARY KEY (emailAddress))";

        sqlCommand(createTable + "complaint" + allDetails + coachExtraDetails() + primaryKey);
    }

    private void createAllSubscriberTableInDb() throws SQLException
    {
        String createTable = "CREATE TABLE ";
        String allDetails = "("
                + "emailAddress VARCHAR(45) NOT NULL,"
                + "password VARCHAR(45) NOT NULL,"
                + "id int NOT NULL,"
                + "firstName VARCHAR(45) NOT NULL,"
                + "LastName VARCHAR(45) NOT NULL,"
                + "status VARCHAR(45) NOT NULL,";
        String primaryKey = "PRIMARY KEY (emailAddress)";
        String closeBody = ")";

        sqlCommand(createTable + "subscriber" + allDetails + primaryKey + closeBody);


        String emailDetails = "("
                + "emailAddress VARCHAR(45) NOT NULL,";
        String constraintForeignKey = ", CONSTRAINT \n" +
                "    FOREIGN KEY (emailAddress)\n" +
                "    REFERENCES subscriber (emailAddress)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";



//        sqlCommand("CREATE TABLE page(pageID VARCHAR(45) NOT NULL, PRIMARY KEY (pageID))");

        sqlCommand(createTable + "coach" + emailDetails + coachExtraDetails() + primaryKey + constraintForeignKey + closeBody);
        sqlCommand(createTable + "fan" + emailDetails + fanExtraDetails() + primaryKey + constraintForeignKey + closeBody);
        /*sqlCommand(createTable + "guest" + emailDetails + coachExtraDetails() + primaryKey + constraintForeignKey + closeBody);*/
        sqlCommand(createTable + "judge" + emailDetails + judgeExtraDetails() + primaryKey + constraintForeignKey + closeBody);
        sqlCommand(createTable + "player" + emailDetails + playerExtraDetails() + primaryKey + constraintForeignKey + closeBody);
        sqlCommand(createTable + "representativeAssociation" + emailDetails + representativeAssociationExtraDetails() + primaryKey + constraintForeignKey + closeBody);
//        sqlCommand(createTable + "systemAdministrator" + emailDetails + coachExtraDetails() + primaryKey + constraintForeignKey + closeBody);
//        sqlCommand(createTable + "teamManager" + emailDetails + coachExtraDetails() + primaryKey + constraintForeignKey + closeBody);
//        sqlCommand(createTable + "teamOwner" + emailDetails + coachExtraDetails() + primaryKey + constraintForeignKey + closeBody);
    }

    //region UsersTypes ExtraDetails
    private String coachExtraDetails()
    {
        String extraDetails = "team VARCHAR(45) NOT NULL,"
                + "coachRole VARCHAR(45) NOT NULL,"
                + "qualificationCoach VARCHAR(45) NOT NULL,"
                + "coachPage VARCHAR(45) NOT NULL,";
        //coachPage = Page.pageID

        return extraDetails;
    }

    private String fanExtraDetails()
    {
        String extraDetails = /*"myPages VARCHAR(45) NOT NULL,"
                + "mySearchHistory VARCHAR(45) NOT NULL,"
                +*/ "gamesAlert VARCHAR(45) NOT NULL,"
                + "alertWay BOOLEAN NOT NULL,";

//                + "alertWay varchar(45) NOT NULL,";

        return extraDetails;
    }

    private String guestExtraDetails()
    {
        String extraDetails = "qualificationJudge VARCHAR(45) NOT NULL,"
                /*+ "seasonLeagueName_JudgeSeasonLeagueName VARCHAR(45) NOT NULL,"*/
                /*+ "theJudgeGameList VARCHAR(45) NOT NULL,"*/;

        return extraDetails;
    }

    private String judgeExtraDetails()
    {
        String extraDetails = "qualificationJudge VARCHAR(45) NOT NULL,"
                + "coachRole VARCHAR(45) NOT NULL,"
                + "qualificationCoach VARCHAR(45) NOT NULL,"
                + "coachPage VARCHAR(45) NOT NULL,";

        return extraDetails;
    }

    private String playerExtraDetails()
    {
        String extraDetails = "team VARCHAR(45) NOT NULL,"
                + "birthDate DATE NOT NULL,"
                + "playerRole VARCHAR(45) NOT NULL,"
                + "playerPage VARCHAR(45) NOT NULL,";

        return extraDetails;
    }

    private String representativeAssociationExtraDetails()
    {
        String extraDetails = "";
//        String extraDetails = "moreMoreName varchar(45) NOT NULL,";

        return extraDetails;
    }

    private String systemAdministratorExtraDetails()
    {
        String extraDetails = "";
//        String extraDetails = "moreMoreName varchar(45) NOT NULL,";

        return extraDetails;
    }

    private String teamManagerExtraDetails()
    {
        String extraDetails = "";
//        String extraDetails = "moreMoreName varchar(45) NOT NULL,";

        return extraDetails;
    }

    private String teamOwnerExtraDetails()
    {
        String extraDetails = "";
//        String extraDetails = "moreMoreName varchar(45) NOT NULL,";

        return extraDetails;
    }
    //endregion

    private void sqlCommand(String createTableSqlQuery) throws SQLException
    {
        Connection connectionDb = getConnection();
        Statement statement = connectionDb.createStatement();
        statement.executeUpdate(createTableSqlQuery);
    }

    private void addConstraintToTables() throws SQLException
    {
        coachConstraint();
        financialActivityConstraint();
        gameConstraint();
        judgeSeasonLeagueConstraint();
        permissionsConstraint();
        playerConstraint();

    }

    //region coachConstraint
    private void coachConstraint() throws SQLException
    {
        sqlCommand(coachConstraint_pageId());
    }

    private String coachConstraint_pageId()
    {
        String constraint = "ALTER TABLE coach\n"
                + "ADD CONSTRAINT FK_coachPage\n" +
                "    FOREIGN KEY (coachPage)\n" +
                "    REFERENCES page (pageID)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }
    //endregion

    //region financialActivityConstraint
    private void financialActivityConstraint() throws SQLException
    {
        sqlCommand(financialActivityConstraint_teamName());
    }

    private String financialActivityConstraint_teamName()
    {
        String constraint = "ALTER TABLE financialActivity\n"
                + "ADD CONSTRAINT FK_teamName \n" +
                "    FOREIGN KEY (team)\n" +
                "    REFERENCES team (teamName)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }
    //endregion

    //region gameConstraint
    private void gameConstraint() throws SQLException
    {
        sqlCommand(gameConstraint_seasonLeague());
        sqlCommand(gameConstraint_hostTeam());
        sqlCommand(gameConstraint_guestTeam());
        sqlCommand(gameConstraint_court());
        //todo- sqlCommand(gameConstraint_judgesOfTheGameList());
        //todo- sqlCommand(gameConstraint_eventLog());
        sqlCommand(gameConstraint_majorJudge());
    }

    private String gameConstraint_majorJudge()
    {
        String constraint = "ALTER TABLE game\n"
                + "ADD CONSTRAINT FK_majorJudge \n" +
                "    FOREIGN KEY (majorJudge)\n" +
                "    REFERENCES judge (emailAddress)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }

    private String gameConstraint_court()
    {
        String constraint = "ALTER TABLE game\n"
                + "ADD CONSTRAINT FK_court \n" +
                "    FOREIGN KEY (court)\n" +
                "    REFERENCES team (courtName)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }

    private String gameConstraint_guestTeam()
    {
        String constraint = "ALTER TABLE game\n"
                + "ADD CONSTRAINT FK_guestTeam \n" +
                "    FOREIGN KEY (guestTeam)\n" +
                "    REFERENCES team (teamName)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }

    private String gameConstraint_hostTeam()
    {
        String constraint = "ALTER TABLE game\n"
                + "ADD CONSTRAINT FK_hostTeam \n" +
                "    FOREIGN KEY (hostTeam)\n" +
                "    REFERENCES team (teamName)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }

    private String gameConstraint_seasonLeague()
    {
        String constraint = "ALTER TABLE game\n"
                + "ADD CONSTRAINT FK_seasonLeague \n" +
                "    FOREIGN KEY (seasonLeague)\n" +
                "    REFERENCES seasonLeague (seasonLeagueName)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }
    //endregion

    //region judgeSeasonLeagueConstraint
    private void judgeSeasonLeagueConstraint() throws SQLException
    {
        sqlCommand(judgeSeasonLeagueConstraint_seasonLeagueName());
        sqlCommand(judgeSeasonLeagueConstraint_judgeEmailAddress());
    }

    private String judgeSeasonLeagueConstraint_judgeEmailAddress()
    {
        String constraint = "ALTER TABLE judgeSeasonLeague\n"
                + "ADD CONSTRAINT FK_judgeEmailAddress \n" +
                "    FOREIGN KEY (judgeEmailAddress)\n" +
                "    REFERENCES judge (emailAddress)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }

    private String judgeSeasonLeagueConstraint_seasonLeagueName()
    {
        String constraint = "ALTER TABLE judgeSeasonLeague\n"
                + "ADD CONSTRAINT FK_seasonLeagueName \n" +
                "    FOREIGN KEY (seasonLeagueName)\n" +
                "    REFERENCES seasonLeague (seasonLeagueName)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }
    //endregion

    //region permissionsConstraint
    private void permissionsConstraint() throws SQLException
    {
        sqlCommand(permissionsConstraint_emailAddress());
    }

    private String permissionsConstraint_emailAddress()
    {
        String constraint = "ALTER TABLE permissions\n"
                + "ADD CONSTRAINT FK_permissions_emailAddress\n" +
                "    FOREIGN KEY (emailAddress)\n" +
                "    REFERENCES subscriber (emailAddress)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }
    //endregion

    //region playerConstraint
    private void playerConstraint() throws SQLException
    {
        sqlCommand(playerConstraint_teamName());
        sqlCommand(playerConstraint_pageId());
    }

    private String playerConstraint_teamName()
    {
        String constraint = "ALTER TABLE player\n"
                + "ADD CONSTRAINT FK_teamName \n" +
                "    FOREIGN KEY (team)\n" +
                "    REFERENCES team (teamName)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }

    private String playerConstraint_pageId()
    {
        String constraint = "ALTER TABLE player\n"
                + "ADD CONSTRAINT FK_playerPage\n" +
                "    FOREIGN KEY (playerPage)\n" +
                "    REFERENCES page (pageID)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }
    //endregion

    //region roleConstraint
    private void roleConstraint() throws SQLException
    {
        sqlCommand(roleConstraint_emailAddress());
        sqlCommand(roleConstraint_teamName());
    }

    private String roleConstraint_emailAddress()
    {
        String constraint = "ALTER TABLE role\n"
                + "ADD CONSTRAINT FK_role_emailAddress\n" +
                "    FOREIGN KEY (emailAddress)\n" +
                "    REFERENCES subscriber (emailAddress)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }

    private String roleConstraint_teamName()
    {
        String constraint = "ALTER TABLE role\n"
                + "ADD CONSTRAINT FK_teamName \n" +
                "    REFERENCES team (teamName)\n" +
                "    ON DELETE CASCADE\n" +
                "    ON UPDATE CASCADE";

        return constraint;
    }
    //endregion


    public static void main(String[] args) throws SQLException
    {
        String url = DbConnector.URL;
        String url1 = DbConnector.URL1;
        String user = DbConnector.USER;
        String password = DbConnector.PASSWORD;

        System.out.println(url);
//        System.out.println(url1);
//        System.out.println(user);
//        System.out.println(password);

        System.out.println("BEFORE");

        DbConnector dbc = DbConnector.getInstance();

        dbc.getConnection();

        dbc.createAllTablesInDb();
//        dbc.addConstraintToTables();

        System.out.println("AFTER");
    }




}
