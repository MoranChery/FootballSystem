package Model.UsersTypes;

public class RepresentativeAssociation extends Subscriber
{
    private Integer id;

    public RepresentativeAssociation(Integer id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
