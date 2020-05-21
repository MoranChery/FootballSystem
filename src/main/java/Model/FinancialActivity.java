package Model;

import Model.Enums.FinancialActivityType;

public class FinancialActivity {

   private String financialActivityId;
   private double  financialActivityAmount;
   private String description;
   private FinancialActivityType financialActivityType;
   private String team;

    public FinancialActivity(String financialActivityId, double financialActivityAmount, String description, FinancialActivityType financialActivityType, String team) {
        this.financialActivityId = financialActivityId;
        this.financialActivityAmount = financialActivityAmount;
        this.description = description;
        this.financialActivityType = financialActivityType;
        this.team = team;
    }

    public double getFinancialActivityAmount() {
        return financialActivityAmount;
    }

    public void setFinancialActivityAmount(double financialActivityAmount) {
        this.financialActivityAmount = financialActivityAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFinancialActivityId() {
        return financialActivityId;
    }

    public void setFinancialActivityId(String financialActivityId) {
        this.financialActivityId = financialActivityId;
    }

    public FinancialActivityType getFinancialActivityType() {
        return financialActivityType;
    }

    public void setFinancialActivityType(FinancialActivityType financialActivityType) {
        this.financialActivityType = financialActivityType;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
