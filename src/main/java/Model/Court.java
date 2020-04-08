package Model;

import java.util.HashMap;

public class Court {
    private String courtName;
    private String courtCity;
    private HashMap<String,Team> teams;

    public Court(String courtName, String courtCity) {
        this.courtName = courtName;
        this.courtCity = courtCity;
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

    public HashMap<String, Team> getTeams() {
        return teams;
    }

    public void setTeams(HashMap<String, Team> teams) {
        this.teams = teams;
    }
}
