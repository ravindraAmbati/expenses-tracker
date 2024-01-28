package learn.myapps.expensestracker.api.category;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesCategoryDetailsRepo extends PagingAndSortingRepository<ExpensesCategoryDetails, Long> {
}
