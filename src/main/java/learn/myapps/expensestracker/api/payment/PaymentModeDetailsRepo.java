package learn.myapps.expensestracker.api.payment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentModeDetailsRepo extends CrudRepository<PaymentModeDetails, Long>, PagingAndSortingRepository<PaymentModeDetails, Long> {
}
