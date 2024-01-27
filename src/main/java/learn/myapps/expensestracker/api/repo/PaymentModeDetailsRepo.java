package learn.myapps.expensestracker.api.repo;

import learn.myapps.expensestracker.api.ApiModelRepo;
import learn.myapps.expensestracker.model.PaymentModeDetails;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentModeDetailsRepo implements ApiModelRepo<PaymentModeDetails> {
    @Override
    public PaymentModeDetails getModel() {
        return null;
    }
}
