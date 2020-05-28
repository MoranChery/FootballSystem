package Data;

import Model.UsersTypes.SystemAdministrator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SystemAdministratorDbInMemory implements SystemAdministratorDb {
    /*structure like the DB of SystemAdministrator*/
    //key: username
    //value: subscriber class
    private Map<String, SystemAdministrator> systemAdministrators;

    public SystemAdministratorDbInMemory() {
        this.systemAdministrators = new HashMap<>();
    }

    private static Data.SystemAdministratorDbInMemory ourInstance = new Data.SystemAdministratorDbInMemory();

    public static Data.SystemAdministratorDbInMemory getInstance() {
        return ourInstance;
    }

    /**
     * for the tests - create systemAdministrator in DB
     *
     * @param systemAdministrator
     * @throws Exception
     */
    @Override
    public void insertSystemAdministrator(SystemAdministrator systemAdministrator) throws Exception {
        if (systemAdministrators.containsKey(systemAdministrator.getEmailAddress())) {
            throw new Exception("SystemAdministrator already exists");
        }
        systemAdministrators.put(systemAdministrator.getEmailAddress(), systemAdministrator);
    }


    @Override
    public SystemAdministrator getSystemAdministrator(String emailAddress) throws Exception {
        if (!systemAdministrators.containsKey(emailAddress)) {
            throw new NotFoundException("SystemAdministrator not found");
        }
        return systemAdministrators.get(emailAddress);
    }

    @Override
    public boolean removeSystemAdministratorFromDB(SystemAdministrator systemAdministrator) {
        if (!systemAdministrators.containsKey(systemAdministrator.getEmailAddress()))
            return false;
        systemAdministrators.remove(systemAdministrator.getEmailAddress());
        return true;
    }

    @Override
    public Set<String> getAllSystemAdministrators() {
        return systemAdministrators.keySet();
    }


    @Override
    public void deleteAll() {
        systemAdministrators.clear();
    }
}
