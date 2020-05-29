package Data;

import Controller.SubscriberController;
import Model.Alert;
import Model.Enums.RoleType;
import Model.UsersTypes.RepresentativeAssociation;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RepresentativeAssociationDbInMemory implements RepresentativeAssociationDb
{
    /*structure like the DB of RepresentativeAssociations*/
    private Map<String, RepresentativeAssociation> representativeAssociationMap;

    private static RepresentativeAssociationDbInMemory ourInstance;

    static {
        try {
            ourInstance = new RepresentativeAssociationDbInMemory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static RepresentativeAssociationDbInMemory getInstance() { return ourInstance; }

    public RepresentativeAssociationDbInMemory() throws Exception {
        representativeAssociationMap = new HashMap<>();
        RepresentativeAssociation representativeAssociation = new RepresentativeAssociation( "representativeAssociation@gmail.com", "representativeAssociation", 111222333,"representativeAssociationName", "representativeAssociationLastName");
        Alert alert  = new Alert("new ", "new");
//        AlertDbInMemory alertDbInMemory = AlertDbInMemory.getInstance();
//        alertDbInMemory.insertAlertInDb("representativeAssociation@gmail.com" , alert);
        AlertDbInServer alertDbInServer = AlertDbInServer.getInstance();
        alertDbInServer.insertAlertInDb("representativeAssociation@gmail.com" , alert);
        SubscriberController subscriberController = new SubscriberController();
        subscriberController.createSubscriber(representativeAssociation);
        RoleDbInMemory.getInstance().createRoleInSystem("representativeAssociation@gmail.com", RoleType.REPRESENTATIVE_ASSOCIATION);

    }

    /**
     * Will receive from the Controller the RepresentativeAssociation, add RepresentativeAssociation to Data.
     *
     * for the tests - create RepresentativeAssociation in DB
     *
     * @param representativeAssociation-the new RepresentativeAssociation.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public void insertRepresentativeAssociation(RepresentativeAssociation representativeAssociation) throws Exception
    {
        if(representativeAssociationMap.containsKey(representativeAssociation.getEmailAddress()))
        {
            throw new Exception("RepresentativeAssociation already exists in the system");
        }
        representativeAssociationMap.put(representativeAssociation.getEmailAddress(), representativeAssociation);
    }

    /**
     * Will receive from the Controller the representativeAssociation's emailAddress, return the RepresentativeAssociation.
     * @param representativeAssociationEmailAddress-emailAddress of the RepresentativeAssociation
     * @return the RepresentativeAssociation
     * @throws Exception-if details are incorrect.
     */
    @Override
    public RepresentativeAssociation getRepresentativeAssociation(String representativeAssociationEmailAddress) throws Exception
    {
        if (!representativeAssociationMap.containsKey(representativeAssociationEmailAddress))
        {
            throw new Exception("RepresentativeAssociation not found");
        }
        return representativeAssociationMap.get(representativeAssociationEmailAddress);
    }

    /**
     * Remove removeRepresentativeAssociation from db
     * @param email String the id
     */
    @Override
    public void removeRepresentativeAssociation(String email) throws Exception{
        representativeAssociationMap.remove(email);
    }

    /**
     * For the tests-Clear the RepresentativeAssociation Map from the DB.
     */
    @Override
    public void deleteAll()
    {
        representativeAssociationMap.clear();
    }

    @Override
    public ArrayList<String> getAllRepresentativeAssociationEmailAddress() throws Exception
    {
        //todo
        throw new NotImplementedException();
//        return null;
    }
}
