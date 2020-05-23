package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Court {
    private String courtName;
    private String courtCity;
    private List<String> teams;

    public Court(String courtName, String courtCity) {
        this.courtName = courtName;
        this.courtCity = courtCity;
        this.teams = new ArrayList<>();
    }

    public Court() {
        // must empty constructor in Spring
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getCourtCity() {
        return courtCity;
    }

    public void setCourtCity(String courtCity) {
        this.courtCity = courtCity;
    }

        public List<String> getTeams() {
        return teams;
    }

    public void setTeams(List<String> teams) {
        this.teams = teams;
    }

    public String getTeam(String teamName) {
        for (String team : teams) {
            if(team.equals(teamName)){
                return team;
            }
        }
    return null;
}
}
