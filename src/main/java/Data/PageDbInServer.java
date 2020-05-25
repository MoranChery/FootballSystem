package Data;

import Model.Page;
import Model.PageType;
import Model.UsersTypes.Fan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PageDbInServer implements PageDb {
    private static PageDbInServer ourInstance = new PageDbInServer();

    public static PageDbInServer getInstance() {
        return ourInstance;
    }


    @Override
    public void addFanToPageFollowers(String pageID, Fan fanToAdd) throws Exception {

    }

    @Override
    public Page getPage(String pageId) throws NotFoundException {
        return null;
    }

    @Override
    public void insertPage(String pageID, PageType pageType) throws Exception {
        if(pageID == null){
            throw new NullPointerException("bad input");
        }
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into page (page_id,page_type)"
                    + " values (?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, pageID);
            preparedStmt.setString (2, pageType.name());
            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }
//
//    @Override
//    public void createPersonalPage(String pageID, Subscriber subscriber) throws Exception {
//
//    }

    @Override
    public void removePersonalPageFromDb(String pageId) throws Exception {

    }

    @Override
    public void deleteAll() throws SQLException {
        Connection conn = DbConnector.getConnection();
        try{
        Statement statement = conn.createStatement();
        statement.executeUpdate("delete from page");
    } finally {
        conn.close();
    }    }
}
