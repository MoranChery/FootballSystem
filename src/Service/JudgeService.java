package Service;

import Controller.JudgeController;
import Model.Game;

public class JudgeService {

    private JudgeController judgeController;

    public JudgeService() {
        this.judgeController = new JudgeController();
    }
    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception {
        judgeController.wantToEditQualification(judgeMail, newQualification);
    }
    public void addGameToTheJudge(String judgeMail, Game gameToAdd) throws Exception{
        judgeController.addGameToTheJudge(judgeMail, gameToAdd);
    }
}
