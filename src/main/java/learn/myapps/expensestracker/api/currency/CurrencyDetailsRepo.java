package learn.myapps.expensestracker.api.currency;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyDetailsRepo extends CrudRepository<CurrencyDetails, Long>, PagingAndSortingRepository<CurrencyDetails, Long> {
}
