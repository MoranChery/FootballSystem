package Model.Enums;

public enum PlayerRole {
    GOALKEEPER,
    DEFENDER,
    MIDFIELDER,
    FORWARD,
    ATTACKER;

    public static PlayerRole getPlayerRole(String word) {
        if(word.equalsIgnoreCase("GOALKEEPER"))
            return PlayerRole.GOALKEEPER;
        if(word.equalsIgnoreCase("DEFENDER"))
            return PlayerRole.DEFENDER;
        if(word.equalsIgnoreCase("MIDFIELDER"))
            return PlayerRole.MIDFIELDER;
        if(word.equalsIgnoreCase("FORWARD"))
            return PlayerRole.FORWARD;
        if(word.equalsIgnoreCase("ATTACKER"))
            return PlayerRole.ATTACKER;
        else return null;
    }
}
