package Model.Enums;

public enum CoachRole {
    MAJOR,
    GOALKEEPER,
    FITNESS;
    public static CoachRole getCoachRole(String word) {
        if(word.equalsIgnoreCase("MAJOR"))
            return CoachRole.MAJOR;
        if(word.equalsIgnoreCase("GOALKEEPER"))
            return CoachRole.GOALKEEPER;
        if(word.equalsIgnoreCase("FITNESS"))
            return CoachRole.FITNESS;
        else return null;
    }
}
