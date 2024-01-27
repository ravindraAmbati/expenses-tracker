package learn.myapps.expensestracker.api.repo;

import learn.myapps.expensestracker.api.ApiModelRepo;
import learn.myapps.expensestracker.model.CurrencyDetails;
import org.springframework.stereotype.Repository;

@Repository
public class CurrencyDetailsRepo implements ApiModelRepo<CurrencyDetails> {
    @Override
    public CurrencyDetails getModel() {
        return null;
    }
}
