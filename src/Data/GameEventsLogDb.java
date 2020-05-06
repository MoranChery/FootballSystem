package Data;

import Model.GameEvent;
import Model.GameEventsLog;

import java.util.Map;

public interface GameEventsLogDb {


    void addEvent(GameEvent gameEvent) throws NotFoundException;

    void setUpdatedDetails(GameEvent gameEvent) throws Exception;
}
