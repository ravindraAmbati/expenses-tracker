package learn.myapps.expensestracker.api.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    private final UserDetailsRepo userDetailsRepo;

    public UserDetailsService(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    public Page<UserDetails> findAll(int pageNumber, int pageSize) {
        return userDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
