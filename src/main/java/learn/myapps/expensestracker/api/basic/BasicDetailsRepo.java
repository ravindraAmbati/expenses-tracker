package learn.myapps.expensestracker.api.basic;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicDetailsRepo extends PagingAndSortingRepository<BasicDetails, Long> {
}
