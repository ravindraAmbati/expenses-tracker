package learn.myapps.expensestracker.api.user;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
import learn.myapps.expensestracker.api.ApiService;
import learn.myapps.expensestracker.api.search.PredicateBuilder;
import learn.myapps.expensestracker.api.search.SearchCriteria;
import learn.myapps.expensestracker.api.search.SearchCriteriaBuilder;
import learn.myapps.expensestracker.util.CustomAssert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService implements ApiService<UserDetails> {

    public static final String ID_NULL_CHECK_ERROR_MESSAGE = "Requested User Details Id should NOT be empty";
    public static final String ENTITY_NULL_CHECK_ERROR_MESSAGE = "Requested User Details should not be empty";

    private final UserDetailsRepo userDetailsRepo;
    private final PathBuilder<UserDetails> userDetailsPathBuilder = new PathBuilder<>(UserDetails.class, "userDetails");
    public UserDetailsService(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    @Override
    public Long create(UserDetails userDetails) {
        Assert.notNull(userDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.isNull(userDetails.getId(), "Requested User Details Id should be empty");
        UserDetails createdBasicDetails = userDetailsRepo.save(userDetails);
        Assert.notNull(createdBasicDetails, "Failed to create the User Details Entity");
        return createdBasicDetails.getId();
    }

    @Override
    public UserDetails update(UserDetails userDetails) throws ResourceNotFoundException {
        Assert.notNull(userDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.notNull(userDetails.getId(), ID_NULL_CHECK_ERROR_MESSAGE);
        findById(userDetails.getId());
        UserDetails updatedUserDetails = userDetailsRepo.save(userDetails);
        Assert.notNull(updatedUserDetails, "Failed to update the User Details Entity");
        return updatedUserDetails;
    }

    @Override
    public boolean isDeleted(Long id) {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        userDetailsRepo.deleteById(id);
        Assert.isTrue(userDetailsRepo.findById(id).isEmpty(), MessageFormat.format("Failed to delete the User Details Entity of id: {0}", id));
        return userDetailsRepo.findById(id).isEmpty();
    }

    @Override
    public UserDetails findById(Long id) throws ResourceNotFoundException {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        Optional<UserDetails> retrievedUserDetails = userDetailsRepo.findById(id);
        CustomAssert.isResourcePresent(retrievedUserDetails.isPresent(), MessageFormat.format("Searched User Details Entity is not found of the id: {0}", id));
        return retrievedUserDetails.get();
    }

    @Override
    public Page<UserDetails> findAll(int pageNumber, int pageSize, Sort sort) {
        Page<UserDetails> all = userDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize, sort));
        validatePageResults(pageNumber, pageSize, sort, all);
        return all;
    }

    public Page<UserDetails> search(String search, int pageNumber, int pageSize, Sort sort) {
        List<SearchCriteria> searchCriteriaList = SearchCriteriaBuilder.buildSearchCriteria(search);
        PredicateBuilder predicateBuilder = new PredicateBuilder(searchCriteriaList);
        BooleanExpression exp = predicateBuilder.build(userDetailsPathBuilder);
        Page<UserDetails> all = userDetailsRepo.findAll(exp, PageRequest.of(pageNumber, pageSize, sort));
        validatePageResults(pageNumber, pageSize, sort, all);
        return all;
    }

    private static void validatePageResults(int pageNumber, int pageSize, Sort sort, Page<UserDetails> all) {
        Assert.isTrue(pageNumber == all.getNumber(), MessageFormat.format("Failed to get requested User Details Entity from page number: {0}", pageNumber));
        Assert.isTrue(pageSize == all.getSize(), MessageFormat.format("Failed to get requested User Details Entity of page size: {0}", pageSize));
        Assert.isTrue(sort.equals(all.getSort()), MessageFormat.format("Failed to get requested User Details Entity by sort: {0}", sort));
    }
}
