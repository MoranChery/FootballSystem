package Model.Enums;

public enum QualificationCoach {
    UEFA_A,
    UEFA_B,
    UEFA_PRO,
    YOUTH;

    public static QualificationCoach getQualificationCoach(String word) {
        if(word.equalsIgnoreCase("UEFA_A"))
            return QualificationCoach.UEFA_A;
        if(word.equalsIgnoreCase("UEFA_B"))
            return QualificationCoach.UEFA_B;
        if(word.equalsIgnoreCase("UEFA_PRO"))
            return QualificationCoach.UEFA_PRO;
        if(word.equalsIgnoreCase("YOUTH"))
            return QualificationCoach.YOUTH;
        else return null;
    }
}
