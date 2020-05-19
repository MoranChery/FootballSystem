package Model.UsersTypes;

import Model.Enums.PermissionType;
import Model.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TeamManager extends Subscriber {
    private String team;
    private String ownedByEmail;
    private List<PermissionType> permissionTypes;

    public TeamManager(String emailAddress, String password,Integer id, String firstName, String lastName,String ownedByEmail) {
        setRegisteringDetails(emailAddress,password,id,firstName,lastName);
        this.ownedByEmail = ownedByEmail;
        permissionTypes = new ArrayList<>();
    }

    public TeamManager(String team,Subscriber subscriber, String ownedById,List<PermissionType> permissionTypes) {
        setRegisteringDetails(subscriber.getEmailAddress(),subscriber.getId(),subscriber.getFirstName(),subscriber.getLastName());
        this.team = team;
        this.ownedByEmail = ownedById;
        this.permissionTypes = permissionTypes;
    }

    public TeamManager(String emailAddress,Integer teamManagerId, String firstName, String lastName, String ownedById) {
        setRegisteringDetails(emailAddress,teamManagerId,firstName,lastName);
        this.ownedByEmail = ownedById;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getOwnedByEmail() {
        return ownedByEmail;
    }

    public void setOwnedByEmail(String ownedByEmail) {
        this.ownedByEmail = ownedByEmail;
    }

    public List<PermissionType> getPermissionTypes() {
        return permissionTypes;
    }

    public void setPermissionTypes(List<PermissionType> permissionTypes) {
        this.permissionTypes = permissionTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamManager)) return false;
        if (!super.equals(o)) return false;
        TeamManager that = (TeamManager) o;
        return Objects.equals(team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), team);
    }

    @Override
    public String toString() {
        return "TeamManager{" +
                "team='" + team + '\'' +
                ", ownedByEmail='" + ownedByEmail + '\'' +
                ", permissionTypes=" + permissionTypes +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", status=" + status +
                '}';
    }
}
