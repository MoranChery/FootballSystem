package Service;

import Controller.FanController;
import Model.PersonalPage;

public class FanService {
    private FanController fanController;

    public FanService() {
        this.fanController = new FanController();
    }
    public void addPageToFanList(Integer pageId, String pageName, Integer fanId) throws Exception {
        fanController.addPageToFanList(pageId, pageName, fanId);
    }

}
