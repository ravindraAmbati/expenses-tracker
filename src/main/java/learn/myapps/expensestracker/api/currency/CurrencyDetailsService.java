package learn.myapps.expensestracker.api.currency;

import learn.myapps.expensestracker.api.category.ExpensesCategoryDetails;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetailsRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class CurrencyDetailsService {

    private final CurrencyDetailsRepo currencyDetailsRepo;

    public CurrencyDetailsService(CurrencyDetailsRepo currencyDetailsRepo) {
        this.currencyDetailsRepo = currencyDetailsRepo;
    }

    public Page<CurrencyDetails> findAll(int pageNumber, int pageSize) {
        return currencyDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
