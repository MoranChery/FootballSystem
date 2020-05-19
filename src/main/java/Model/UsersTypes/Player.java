package Model.UsersTypes;

import Model.Enums.PlayerRole;
import Model.PersonalPage;
import Model.Team;

import java.util.Date;
import java.util.Objects;

public class Player extends Subscriber {
    private String team;
    private Date birthDate;
    private PlayerRole playerRole;
    private PersonalPage playerPage;


    public Player(String emailAddress, String password,Integer id, String firstName, String lastName, Date birthDate, PlayerRole playerRole) {
        setRegisteringDetails(emailAddress,password,id,firstName,lastName);
        this.birthDate = birthDate;
        this.playerRole = playerRole;
    }

    public Player(String emailAddress, Integer playerId, String firstName, String lastName, Date birthDate, PlayerRole playerRole) {
        setRegisteringDetails(emailAddress,playerId,firstName,lastName);
        this.birthDate = birthDate;
        this.playerRole = playerRole;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public PersonalPage getPlayerPage() {
        return playerPage;
    }
//
    public void setPlayerPage(PersonalPage playerPage) {
        this.playerPage = playerPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        if (!super.equals(o)) return false;
        Player player = (Player) o;
        return Objects.equals(team, player.team) &&
                Objects.equals(birthDate, player.birthDate) &&
                playerRole == player.playerRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), team, birthDate, playerRole);
    }

    @Override
    public String toString() {
        return "Player{" +
                "team='" + team + '\'' +
                ", birthDate=" + birthDate +
                ", playerRole=" + playerRole +
//                ", playerPage=" + playerPage +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", status=" + status +
                '}';
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Player)) return false;
//        if (!super.equals(o)) return false;
//        Player player = (Player) o;
//        return Objects.equals(birthDate, player.birthDate) &&
//                playerRole == player.playerRole;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(super.hashCode(), team, birthDate, playerRole);
//    }
}
