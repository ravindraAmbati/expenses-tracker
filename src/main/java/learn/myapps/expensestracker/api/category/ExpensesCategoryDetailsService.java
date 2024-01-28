package learn.myapps.expensestracker.api.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ExpensesCategoryDetailsService {

    private final ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo;

    public ExpensesCategoryDetailsService(ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo) {
        this.expensesCategoryDetailsRepo = expensesCategoryDetailsRepo;
    }

    public Page<ExpensesCategoryDetails> findAll(int pageNumber, int pageSize) {
        return expensesCategoryDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
