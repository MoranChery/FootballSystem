package Model.UsersTypes;

public abstract class Subscriber extends User {

    private Integer id;
    private String firstName;
    private String lastName;

    public Subscriber(Integer id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Subscriber(Subscriber subscriber) {
        this.id = subscriber.getId();
        this.firstName = subscriber.getFirstName();
        this.lastName = subscriber.getLastName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
