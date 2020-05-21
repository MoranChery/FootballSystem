package Data;

import Model.FinancialActivity;

import java.util.HashMap;
import java.util.Map;

public class FinancialActivityDbInMemory implements FinancialActivityDb {

    Map<String, FinancialActivity> financialActivities;

    private static FinancialActivityDbInMemory ourInstance = new FinancialActivityDbInMemory();

    public static FinancialActivityDbInMemory getInstance() {
        return ourInstance;
    }

    public FinancialActivityDbInMemory() {
        this.financialActivities = new HashMap<>();
    }

    @Override
    public void insertFinancialActivity(FinancialActivity financialActivity) throws Exception {
        String financialActivityId = financialActivity.getFinancialActivityId();
        if(financialActivities.containsKey(financialActivityId)) {
            throw new Exception("Financial Activity already exists");
        }
        financialActivities.put(financialActivityId, financialActivity);
    }

    @Override
    public void deleteAll() {
        financialActivities.clear();
    }
}
