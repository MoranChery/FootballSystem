package Data;

import Model.Enums.PlayerRole;
import Model.Enums.Status;
import Model.UsersTypes.Player;
import Model.UsersTypes.Subscriber;

import java.sql.*;
import java.util.Date;

public class PlayerDbInServer implements PlayerDb{

    @Override
    public void insertPlayer(Player player) throws Exception {
        Connection conn = DbConnector.getConnection();
        try
        {
            // the mysql insert statement
            String query = " insert into player (email_address,birth_date,player_role)"
                    + " values (?,?,?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, player.getEmailAddress());
//            preparedStmt.setString (2, player.getTeam().getTeamName());
            preparedStmt.setDate (2, new java.sql.Date(player.getBirthDate().getTime()));
            preparedStmt.setString (3, player.getPlayerRole().name());

            // execute the preparedstatement
            preparedStmt.execute();
        } finally {
            conn.close();
        }
    }

    @Override
    public Player getPlayer(String playerEmailAddress) throws Exception {
        if (playerEmailAddress == null) {
            throw new NullPointerException("bad input");
        }

        Connection conn = DbConnector.getConnection();

        String query = "select * from subscriber, player where subscriber.email_address = player.email_address and subscriber.email_address = \'" + playerEmailAddress + "\'";

        Statement preparedStmt = conn.createStatement();
        ResultSet rs = preparedStmt.executeQuery(query);

        // checking if ResultSet is empty
        if (rs.next() == false) {
            throw new NotFoundException("Player not found");
        }

        String userName = rs.getString("email_address");
        String password = rs.getString("password");
        Integer id = rs.getInt("id");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");
        String status = rs.getString("status");
        String team = rs.getString("team");
        Date birth_date = rs.getDate("birth_date");
        String player_role = rs.getString("player_role");
        conn.close();

        Player player = new Player(userName, id, first_name, last_name, birth_date, PlayerRole.valueOf(player_role));
        player.setPassword(password);
        player.setTeam(team);
        player.setStatus(Status.valueOf(status));
        return player;
    }

    @Override
    public void updatePlayerDetails(String playerEmailAddress, String firstName, String lastName, Date birthDate, PlayerRole playerRole) throws NotFoundException, SQLException {
        Connection conn = DbConnector.getConnection();
        String query = "UPDATE player join subscriber SET first_name = " + firstName + ", last_Name = " + lastName + " , birth_date = "+ birthDate + ", player_role = " + playerRole.name()  + "  WHERE subscriber.email_address = "+ playerEmailAddress ;
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.executeUpdate();
        conn.close();
    }

    @Override
    public void removePlayerFromDb(Player player) throws Exception {

    }

    @Override
    public void deleteAll() {

    }

    public static void main(String[] args) throws Exception {
        Player player = new Player("player@gmail.com","12345",111111,"player","last",new Date(),PlayerRole.GOALKEEPER);
        PlayerDbInServer playerDbInServer = new PlayerDbInServer();
//        playerDbInServer.insertPlayer(player);
        Player player1 = playerDbInServer.getPlayer("player@gmail.com");
        System.out.println(player1.toString());
    }
}
