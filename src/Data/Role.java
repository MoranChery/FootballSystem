package Data;

import Model.Enums.RoleType;

import java.util.Date;

public class Role {
    private String teamName;
    private RoleType roleType;
    private Date assignedDate;

    public Role(String teamName, RoleType roleType) {
        this.teamName = teamName;
        this.roleType = roleType;
        this.assignedDate = new Date();
    }

    public Role(RoleType roleType) {
        this.roleType = roleType;
        this.assignedDate = new Date();
    }


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }
}
