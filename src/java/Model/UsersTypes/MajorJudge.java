package Model.UsersTypes;

import Model.Enums.QualificationJudge;

public class MajorJudge extends Judge {
    public MajorJudge(String username, String password, Integer id, String firstName, String lastName, QualificationJudge qualificationJudge) {
        super(username, password, id, firstName, lastName, qualificationJudge);
    }
}
