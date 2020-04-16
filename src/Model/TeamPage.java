package Model;

public class TeamPage extends Page{

    Team team;
    public TeamPage(String ownerID) throws Exception {
        super(ownerID);
    }

    public TeamPage(String ownerID,Team team) throws Exception {
        super(ownerID);
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
