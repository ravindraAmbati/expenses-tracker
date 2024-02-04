package learn.myapps.expensestracker.api.basic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicDetailsRepo extends CrudRepository<BasicDetails, Long>, PagingAndSortingRepository<BasicDetails, Long> {
}
