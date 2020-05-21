package Data;

import Model.FinancialActivity;

public interface FinancialActivityDb extends Db {
    void insertFinancialActivity(FinancialActivity financialActivity) throws Exception;
}
