package Data;

import Model.UsersTypes.SystemAdministrator;

public interface SystemAdministratorDb extends Db{
    void createSystemAdministrator(SystemAdministrator systemAdministrator) throws Exception;
    SystemAdministrator getSystemAdministrator(String emailAddress) throws Exception;
    boolean removeSystemAdministratorFromDB(SystemAdministrator systemAdministrator);

}