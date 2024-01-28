package learn.myapps.expensestracker.api.payment;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentModeDetailsRepo extends PagingAndSortingRepository<PaymentModeDetails, Long> {
}
