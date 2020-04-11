package Model.UsersTypes;

public class Judge extends Subscriber
{
    static int judgeIdCounter = 0;

    private Integer id;
    private String judgeName;
    private String judgeQualification;

    public Judge(String judgeName, String judgeQualification)
    {
        this.judgeName = judgeName;
        this.id = judgeIdCounter;
        this.judgeQualification = judgeQualification;

        judgeIdCounter++;
    }

    //region Getters
    public Integer getId() {
        return id;
    }

    public String getJudgeName() { return judgeName; }

    public String getJudgeQualification() { return judgeQualification; }
    //endregion

    //region Setters
//    public void setId(Integer id) { this.id = id; }

//    public void setJudgeName(String judgeName) { this.judgeName = judgeName; }

//    public void setJudgeQualification(String judgeQualification) { this.judgeQualification = judgeQualification; }
    //endregion
}
