package Data;
import Model.Complaint;
import java.util.HashMap;

public class ComplaintDbInMemory implements ComplaintDb {
    private static ComplaintDbInMemory ourInstance = new ComplaintDbInMemory();

    public static ComplaintDbInMemory getInstance() {
        return ourInstance;
    }

    HashMap<Integer,Complaint> complaints;

    public ComplaintDbInMemory() {
        complaints = new HashMap<>();
    }

    @Override
    public Complaint getComplaint(Integer complaintId) throws Exception {
        if (!complaints.containsKey(complaintId)) {
            throw new NotFoundException("Complaint not found");
        }
        return complaints.get(complaintId);
    }

    @Override
    public void createComplaint(Complaint complaint) throws Exception {
        if (complaints.containsKey(complaint.getId())) {
            throw new Exception("Complaint already exists");
        }
        complaints.put(complaint.getId(), complaint);
    }

    @Override
    public void deleteAll() {
        complaints.clear();
    }

}
