package Data;

import Model.PersonalPage;
import Model.UsersTypes.Fan;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.util.HashMap;
import java.util.Map;

public class FanDbInMemory implements FanDb{

    private Map<Integer, Fan> allFans;

    private static FanDbInMemory ourInstance = new FanDbInMemory();
    public FanDbInMemory() {
        this.allFans = new HashMap<>();
    }
    public static FanDbInMemory getInstance() {
        return ourInstance;
    }

    @Override
    public void addPageToFanList(Integer fanId, PersonalPage pageToAdd) throws Exception {
        if(pageToAdd == null){
            throw new Exception("Page not found");
        }
        Fan theFan = allFans.get(fanId);
        if(theFan == null){
            throw new Exception("Fan not found");
        }
        Map<Integer, PersonalPage> theFanPages = theFan.getMyPages();
        Integer pageToAddId = pageToAdd.getPageID();
        if(theFanPages.containsKey(pageToAddId)){
            throw new Exception("You are already follow this page");
        }
        theFanPages.put(pageToAddId,pageToAdd);
    }

    @Override
    public Fan getFan(Integer fanId) {
        Fan fan = allFans.get(fanId);
        if(fan == null){
            //throw new NotFoundException("Fan not found");

        }
        return fan;
    }
}
