package Model.Enums;

public enum PermissionType {
    ADD_PLAYER,
    ADD_TEAM_MANAGER,
    ADD_COACH,
    ADD_COURT,
    ADD_FINANCIAL,
    CHANGE_STATUS,
    REMOVE_PLAYER,
    REMOVE_TEAM_MANAGER,
    REMOVE_COACH,
    REMOVE_COURT,
    OWNER,
    CREATE_NEW_TEAM,
    UPDATE_COACH,
    UPDATE_PLAYER,
    UPDATE_COURT,
    UPDATE_TEAM_MANAGER;

    public static PermissionType getPermissionType(String word){
        if(word.equalsIgnoreCase("ADD_PLAYER"))
            return PermissionType.ADD_PLAYER;
        if(word.equalsIgnoreCase("ADD_TEAM_MANAGER"))
            return PermissionType.ADD_TEAM_MANAGER;
        if(word.equalsIgnoreCase("ADD_COACH"))
            return PermissionType.ADD_COACH;
        if(word.equalsIgnoreCase("ADD_COURT"))
            return PermissionType.ADD_COURT;
        if(word.equalsIgnoreCase("ADD_FINANCIAL"))
            return PermissionType.ADD_FINANCIAL;
        if(word.equalsIgnoreCase("CHANGE_STATUS"))
            return PermissionType.CHANGE_STATUS;
        if(word.equalsIgnoreCase("REMOVE_PLAYER"))
            return PermissionType.REMOVE_PLAYER;
        if(word.equalsIgnoreCase("REMOVE_TEAM_MANAGER"))
            return PermissionType.REMOVE_TEAM_MANAGER;
        if(word.equalsIgnoreCase("REMOVE_COACH"))
            return PermissionType.REMOVE_COACH;
        if(word.equalsIgnoreCase("REMOVE_COURT"))
            return PermissionType.REMOVE_COURT;
        if(word.equalsIgnoreCase("OWNER"))
            return PermissionType.OWNER;
        if(word.equalsIgnoreCase("CREATE_NEW_TEAM"))
            return PermissionType.CREATE_NEW_TEAM;
        if(word.equalsIgnoreCase("UPDATE_COACH"))
            return PermissionType.UPDATE_COACH;
        if(word.equalsIgnoreCase("UPDATE_PLAYER"))
            return PermissionType.UPDATE_PLAYER;
        if(word.equalsIgnoreCase("UPDATE_COURT"))
            return PermissionType.UPDATE_COURT;
        if(word.equalsIgnoreCase("UPDATE_TEAM_MANAGER"))
            return PermissionType.UPDATE_TEAM_MANAGER;
        else return null;
    }
}
