package learn.myapps.expensestracker.api.payment;

import learn.myapps.expensestracker.api.expenses.ExpensesDetails;
import learn.myapps.expensestracker.api.expenses.ExpensesDetailsRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PaymentModeDetailsService {

    private final PaymentModeDetailsRepo paymentModeDetailsRepo;

    public PaymentModeDetailsService(PaymentModeDetailsRepo paymentModeDetailsRepo) {
        this.paymentModeDetailsRepo = paymentModeDetailsRepo;
    }

    public Page<PaymentModeDetails> findAll(int pageNumber, int pageSize) {
        return paymentModeDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
