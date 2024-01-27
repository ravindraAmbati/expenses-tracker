package learn.myapps.expensestracker.api.repo;

import learn.myapps.expensestracker.api.ApiModelRepo;
import learn.myapps.expensestracker.model.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsRepo implements ApiModelRepo<UserDetails> {
    @Override
    public UserDetails getModel() {
        return null;
    }
}
