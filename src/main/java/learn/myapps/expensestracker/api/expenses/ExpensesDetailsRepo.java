package learn.myapps.expensestracker.api.expenses;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesDetailsRepo extends CrudRepository<ExpensesDetails, Long>, PagingAndSortingRepository<ExpensesDetails, Long> {
}
