package Data;

import Model.FinancialActivity;

public interface FinancialActivityDb {
    void createFinancialActivity(FinancialActivity financialActivity) throws Exception;
}
