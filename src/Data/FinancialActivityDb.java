package Data;

import Model.FinancialActivity;

public interface FinancialActivityDb extends Db {
    void createFinancialActivity(FinancialActivity financialActivity) throws Exception;
}
