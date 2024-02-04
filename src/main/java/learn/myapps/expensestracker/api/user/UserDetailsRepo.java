package learn.myapps.expensestracker.api.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends CrudRepository<UserDetails, Long>, PagingAndSortingRepository<UserDetails, Long> {
}
