package Model.UsersTypes;

public class RepresentativeAssociation extends Subscriber
{
    public RepresentativeAssociation(String username, String password,Integer id, String firstName, String lastName) {
        setRegisteringDetails(username,password,id,firstName,lastName);
    }
}
