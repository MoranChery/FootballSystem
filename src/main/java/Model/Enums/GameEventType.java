package Model.Enums;

public enum GameEventType {
    GOAL,
    OFFSIDE,
    FOUL,
    RED_CARD,
    YELLOW_CARD,
    INJURY,
    EXCHANGE_PLAYER;

    public static GameEventType getGameEventType(String word) {
        if (word.equalsIgnoreCase("GOAL")) {
            return GameEventType.GOAL;
        }
        if (word.equalsIgnoreCase("OFFSIDE")) {
            return GameEventType.OFFSIDE;
        }
        if (word.equalsIgnoreCase("FOUL")) {
            return GameEventType.FOUL;
        }
        if (word.equalsIgnoreCase("RED_CARD")) {
            return GameEventType.RED_CARD;
        }
        if (word.equalsIgnoreCase("YELLOW_CARD")) {
            return GameEventType.YELLOW_CARD;
        }
        if (word.equalsIgnoreCase("INJURY")) {
            return GameEventType.INJURY;
        }
        if (word.equalsIgnoreCase("EXCHANGE_PLAYER")) {
            return GameEventType.EXCHANGE_PLAYER;
        }
        return null;
    }
}
