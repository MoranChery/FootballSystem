package Model.UsersTypes;

import Model.Enums.PlayerRole;
import Model.Team;

import java.util.Date;
import java.util.Objects;

public class Player extends Subscriber {
    private Integer id;
    private Team team;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private PlayerRole playerRole;

    public Player(Integer id, String firstName, String lastName, Date birthDate, PlayerRole playerRole) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.playerRole = playerRole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) &&
                Objects.equals(firstName, player.firstName) &&
                Objects.equals(lastName, player.lastName) &&
                Objects.equals(birthDate, player.birthDate) &&
                playerRole == player.playerRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, playerRole);
    }
}
