package learn.myapps.expensestracker.api.expenses;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesDetailsRepo extends PagingAndSortingRepository<ExpensesDetails, Long> {
}
