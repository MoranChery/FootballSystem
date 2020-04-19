package Model;

import Model.UsersTypes.SystemAdministrator;
import Model.UsersTypes.User;

import java.util.ArrayList;
import java.util.List;

public class System {

    List<League> allLeagues;
    List<User> allUsers;
    List<Complaint> allComplaints;
    List<SystemAdministrator> allSystemAdministrators;

    private static System ourInstance= new System();

    private System() {
        this.allLeagues = new ArrayList<>();
        this.allUsers = new ArrayList<>();
        this.allComplaints = new ArrayList<>();
        this.allSystemAdministrators = new ArrayList<>();
    }

    public System(List<League> allLeagues, List<User> allUsers, List<Complaint> allComplaints, List<SystemAdministrator> allSystemAdministrators) throws Exception {
        if(allLeagues!=null) {
            if(allLeagues.size()>0) {
                this.allLeagues = allLeagues;
            }
            else {
                throw new Exception("There should be at least one league");
            }
        }
        else{
            throw new Exception("Initialization all leagues in the system are failed");
        }
        if(allUsers!=null) {
            if(allUsers.size()>0) {
                this.allUsers = allUsers;
            }
            else {
                throw new Exception("There must be at least one user who is an administrator");
            }
        }
        else{
            throw new Exception("Initialization all users in the system are failed");
        }
        if(allComplaints!=null) {
            this.allComplaints = allComplaints;
        }
        else{
            throw new Exception("Initialization all complaints in the system are failed");
        }

        if(allSystemAdministrators!=null) {
            if(allSystemAdministrators.size()>0) {
                this.allSystemAdministrators = allSystemAdministrators;
            }
            else {
                throw new Exception("There must be one administrator");
            }
        }
        else{
            throw new Exception("Initialization all system administrators in the system are failed");
        }
    }

    public static System getInstance() {
        return ourInstance;
    }

    public List<League> getAllLeagues() {
        return allLeagues;
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

    public List<Complaint> getAllComplaints() {
        return allComplaints;
    }

    public List<SystemAdministrator> getAllSystemAdministrators() {
        return allSystemAdministrators;
    }

}
