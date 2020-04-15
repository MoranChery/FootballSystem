package Service;

import Controller.JudgeController;

public class JudgeService {

    private JudgeController judgeController;

    public JudgeService() {
        this.judgeController = new JudgeController();
    }
    public void wantToEditPassword(String judgeMail, String newPassword) throws Exception {
        judgeController.wantToEditPassword(judgeMail, newPassword);
    }
    public void wantToEditFirstName(String judgeMail, String newFirstName) throws Exception {
        judgeController.wantToEditFirstName(judgeMail, newFirstName);
    }
    public void wantToEditLastName(String judgeMail, String newLastName) throws Exception {
        judgeController.wantToEditLastName(judgeMail, newLastName);
    }
    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception {
        judgeController.wantToEditQualification(judgeMail, newQualification);
    }
}
