package Model.UsersTypes;

public class SystemAdministrator extends Subscriber {
    public SystemAdministrator(String username, String password,Integer id, String firstName, String lastName) {
        setRegisteringDetails(username,password,id,firstName,lastName);
    }
}
