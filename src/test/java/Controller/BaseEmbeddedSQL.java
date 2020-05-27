
package Controller;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.*;

public class BaseEmbeddedSQL {
    private static EmbeddedMysql mysqld;

    @BeforeClass
    public static void initDb() {
        MysqldConfig config = aMysqldConfig(v5_7_13)
                .withPort(3306)
                .withUser("noy", "roiL0210")
//                .withUser("root", "root")
                .build();
        mysqld = anEmbeddedMysql(config)
                .addSchema("football_system_db", classPathScript("db/init.sql"))
                .start();

//        Connection conn = DbConnector.getConnection();
//        try {
//            Statement statement = conn.createStatement();
//            statement.executeUpdate(Tables.CREATE_TABLES);
//        } catch (SQLException e) {
//            Assert.fail(e.getMessage());
//        }
    }

    @AfterClass
    public static void closeDb() {
        mysqld.stop();
    }
}
