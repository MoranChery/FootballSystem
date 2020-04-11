package Data;

import Model.UsersTypes.Judge;

public interface JudgeDb
{
    void createJudge(String judgeName, String judgeQualification) throws Exception;
    Judge getJudge(Integer judgeId) throws Exception;
}
