package Model.UsersTypes;

import java.Model.Enums.Status;

public abstract class Subscriber extends Model.UsersTypes.User {
    protected String username;
    protected String password;
    protected Integer id;
    protected String firstName;
    protected String LastName;
    protected Status status=Status.OFFLINE;

    public void setRegisteringDetails(String username, String password, Integer id, String firstName, String lastName){
        setFirstName(firstName);
        setId(id);
        setLastName(lastName);
        setPassword(password);
        setUsername(username);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
