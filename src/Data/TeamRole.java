package Data;

import Model.Enums.TeamRoleType;

import java.util.Date;

public class TeamRole {
    private String teamName;
    private TeamRoleType teamRoleType;
    private Date assignedDate;

    public TeamRole(String teamName, TeamRoleType teamRoleType) {
        this.teamName = teamName;
        this.teamRoleType = teamRoleType;
        this.assignedDate = new Date();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public TeamRoleType getTeamRoleType() {
        return teamRoleType;
    }

    public void setTeamRoleType(TeamRoleType teamRoleType) {
        this.teamRoleType = teamRoleType;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }
}
