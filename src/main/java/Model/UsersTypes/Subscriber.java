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
}
