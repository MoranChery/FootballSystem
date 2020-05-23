package Data;

import Model.UsersTypes.RepresentativeAssociation;

import java.util.ArrayList;

public interface RepresentativeAssociationDb extends Db
{

    /**
     * Will receive from the Controller the RepresentativeAssociation, add RepresentativeAssociation to Data.
     * @param representativeAssociation-the new RepresentativeAssociation.
     * @throws Exception-if details are incorrect.
     */
    void insertRepresentativeAssociation(RepresentativeAssociation representativeAssociation) throws Exception;

    /**
     * Will receive from the Controller the representativeAssociation's emailAddress, return the RepresentativeAssociation.
     * @param representativeAssociationEmailAddress-emailAddress of the RepresentativeAssociation
     * @return the RepresentativeAssociation
     * @throws Exception-if details are incorrect.
     */
    RepresentativeAssociation getRepresentativeAssociation(String representativeAssociationEmailAddress) throws Exception;

    /**
     * Remove removeRepresentativeAssociation from db
     * @param email String the id
     */
    void removeRepresentativeAssociation(String email) throws Exception;

    ArrayList<String> getAllRepresentativeAssociationEmailAddress() throws Exception;
//    void changeGameLocation(String repMail, String newLocation, String gameID) throws Exception;
}
