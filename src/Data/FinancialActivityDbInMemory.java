package Data;

import Model.FinancialActivity;
import Model.UsersTypes.Player;

import java.util.HashMap;
import java.util.Map;

public class FinancialActivityDbInMemory implements FinancialActivityDb {

    Map<String, FinancialActivity> financialActivities;

    public FinancialActivityDbInMemory() {
        this.financialActivities = new HashMap<>();
    }

    @Override
    public void createFinancialActivity(FinancialActivity financialActivity) throws Exception {
        String financialActivityId = financialActivity.getFinancialActivityId();
        if(financialActivities.containsKey(financialActivityId)) {
            throw new Exception("Financial Activity already exists");
        }
        financialActivities.put(financialActivityId, financialActivity);
    }
}
