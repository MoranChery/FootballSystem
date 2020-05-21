package Model.UsersTypes;

import Model.Enums.Status;

import java.util.Objects;


public class Subscriber extends User {
    protected String emailAddress;
    protected String password;
    protected Integer id;
    protected String firstName;
    protected String LastName;
    protected Status status;
    protected boolean wantAlertInMail;

    public Subscriber(String userName, String password, Integer id, String first_name, String last_name, Status status) {
        setFirstName(first_name);
        setId(id);
        setLastName(last_name);
        setPassword(password);
        setEmailAddress(userName);
        setStatus(status);
        wantAlertInMail = false;
    }

    public Subscriber() {

    }


    public void setRegisteringDetails(String emailAddress, String password, Integer id, String firstName, String lastName){
        setFirstName(firstName);
        setId(id);
        setLastName(lastName);
        setPassword(password);
        setEmailAddress(emailAddress);
        status = Status.OFFLINE;
        wantAlertInMail = false;
    }

    public void setRegisteringDetails(String emailAddress,Integer id, String firstName, String lastName){
        setEmailAddress(emailAddress);
        setFirstName(firstName);
        setId(id);
        setLastName(lastName);
        //status = Status.ONLINE;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isWantAlertInMail() {
        return wantAlertInMail;
    }

    public void setWantAlertInMail(boolean wantAlertInMail) {
        this.wantAlertInMail = wantAlertInMail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscriber)) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(emailAddress, that.emailAddress) &&
                Objects.equals(password, that.password) &&
                Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(LastName, that.LastName) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, password, id, firstName, LastName, status);
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", status=" + status +
                '}';
    }
}
