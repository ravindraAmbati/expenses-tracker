package learn.myapps.expensestracker.api.expenses;

import learn.myapps.expensestracker.api.currency.CurrencyDetails;
import learn.myapps.expensestracker.api.currency.CurrencyDetailsRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ExpensesDetailsService {

    private final ExpensesDetailsRepo expensesDetailsRepo;

    public ExpensesDetailsService(ExpensesDetailsRepo expensesDetailsRepo) {
        this.expensesDetailsRepo = expensesDetailsRepo;
    }

    public Page<ExpensesDetails> findAll(int pageNumber, int pageSize) {
        return expensesDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
