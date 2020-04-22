package Service;

import Controller.JudgeController;

public class JudgeService {

    private JudgeController judgeController;

    public JudgeService() {
        this.judgeController = new JudgeController();
    }
    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception {
        judgeController.wantToEditQualification(judgeMail, newQualification);
    }
}
