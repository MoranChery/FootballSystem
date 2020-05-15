package Data;

import Model.UsersTypes.SystemAdministrator;

import java.util.Set;

public interface SystemAdministratorDb extends Db {
    void createSystemAdministrator(SystemAdministrator systemAdministrator) throws Exception;
    SystemAdministrator getSystemAdministrator(String emailAddress) throws Exception;
    boolean removeSystemAdministratorFromDB(SystemAdministrator systemAdministrator);
    Set<String> getAllSystemAdministrators();

}