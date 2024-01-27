package learn.myapps.expensestracker.api.repo;

import learn.myapps.expensestracker.api.ApiModelRepo;
import learn.myapps.expensestracker.model.ExpensesDetails;
import org.springframework.stereotype.Repository;

@Repository
public class ExpensesDetailsRepo implements ApiModelRepo<ExpensesDetails> {
    @Override
    public ExpensesDetails getModel() {
        return null;
    }
}
