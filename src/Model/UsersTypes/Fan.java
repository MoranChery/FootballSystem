package Model.UsersTypes;

public class Fan extends Subscriber {

    public Fan(String username, String password,Integer id,String firstName,String lastName) {
        setRegisteringDetails(username, password, id, firstName, lastName);
    }

}
