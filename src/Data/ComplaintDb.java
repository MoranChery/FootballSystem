package Data;
import Model.Complaint;

public interface ComplaintDb extends Db {
    Complaint getComplaint(Integer complaintId) throws Exception;

    void createComplaint(Complaint complaintId) throws Exception;
}
