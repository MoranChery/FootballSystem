package Controller;

import Data.JudgeDb;
import Data.JudgeDbInMemory;
import Data.NotFoundException;
import Model.Enums.QualificationJudge;
import Model.UsersTypes.Judge;

public class JudgeController {
    private JudgeDb judgeDb;

    public JudgeController() {
        this.judgeDb = JudgeDbInMemory.getInstance();
    }
    public void createJudge(Judge judge) throws Exception{
        if (judge == null){
            throw new NullPointerException("Can't create this judge");
        }
        judgeDb.createJudge(judge);
    }
    public void wantToEditPassword(String judgeMail, String newPassword) throws Exception {
        if(judgeMail == null || newPassword == null){
            throw new NullPointerException("bad input");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        if(judge == null){
            throw new NotFoundException("Judge not found to edit the password");
        }
        if(judge.getPassword().equals(newPassword)){
            throw new Exception("This password is the same as the old one");
        }
        judgeDb.wantToEditPassword(judgeMail, newPassword);
    }
    public void wantToEditFirstName(String judgeMail, String newFirstName) throws Exception {
        if(judgeMail == null || newFirstName == null){
            throw new NullPointerException("bad input");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        if(judge == null){
            throw new NotFoundException("Judge not found to edit the first name");
        }
        if(judge.getFirstName().equals(newFirstName)){
            throw new Exception("This name is the same as the old one");
        }
        judgeDb.wantToEditFirstName(judgeMail, newFirstName);
    }
    public void wantToEditLastName(String judgeMail, String newLastName) throws Exception {
        if(judgeMail == null || newLastName == null){
            throw new NullPointerException("bad input");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        if(judge == null){
            throw new NotFoundException("Judge not found to edit the last name");
        }
        if(judge.getLastName().equals(newLastName)){
            throw new Exception("This name is the same as the old one");
        }
        judgeDb.wantToEditLastName(judgeMail, newLastName);
    }
    public void wantToEditQualification(String judgeMail, String newQualification) throws Exception {
        if(judgeMail == null || newQualification == null){
            throw new NullPointerException("bad input");
        }
        Judge judge = judgeDb.getJudge(judgeMail);
        if(judge == null){
            throw new NotFoundException("Judge not found to edit the last name");
        }
        QualificationJudge theJudgeNewQualification = QualificationJudge.valueOf(newQualification);
        if(judge.getQualificationJudge().equals(theJudgeNewQualification)){
            throw new Exception("This name is the same as the old one");
        }
        judgeDb.wantToEditQualification(judgeMail, newQualification);
    }



}
