package Data;

import Model.League;
import Model.UsersTypes.Player;
import Model.UsersTypes.RepresentativeAssociation;

import java.util.HashMap;
import java.util.Map;

public class RepresentativeAssociationDbInMemory implements RepresentativeAssociationDb
{
    /*structure like the DB of RepresentativeAssociations*/
    private Map<String, RepresentativeAssociation> representativeAssociationMap;

    private static RepresentativeAssociationDbInMemory ourInstance = new RepresentativeAssociationDbInMemory();

    public static RepresentativeAssociationDbInMemory getInstance() { return ourInstance; }

    public RepresentativeAssociationDbInMemory() { representativeAssociationMap = new HashMap<>(); }

    /**
     * Will receive from the Controller the RepresentativeAssociation, add RepresentativeAssociation to Data.
     *
     * for the tests - create RepresentativeAssociation in DB
     *
     * @param representativeAssociation-the new RepresentativeAssociation.
     * @throws Exception-if details are incorrect.
     */
    @Override
    public void createRepresentativeAssociation(RepresentativeAssociation representativeAssociation) throws Exception
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
     * For the tests-Clear the RepresentativeAssociation Map from the DB.
     */
    @Override
    public void deleteAll()
    {
        representativeAssociationMap.clear();
    }


}
