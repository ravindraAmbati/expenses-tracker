package learn.myapps.expensestracker.api.expenses;

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
public class ExpensesDetailsService implements ApiService<ExpensesDetails> {

    public static final String ID_NULL_CHECK_ERROR_MESSAGE = "Requested Expenses Details Id should NOT be empty";
    public static final String ENTITY_NULL_CHECK_ERROR_MESSAGE = "Requested Expenses Details should not be empty";

    private final ExpensesDetailsRepo expensesDetailsRepo;
    private final PathBuilder<ExpensesDetails> expensesDetailsPathBuilder = new PathBuilder<>(ExpensesDetails.class, "expensesDetails");
    public ExpensesDetailsService(ExpensesDetailsRepo expensesDetailsRepo) {
        this.expensesDetailsRepo = expensesDetailsRepo;
    }

    @Override
    public Long create(ExpensesDetails expensesDetails) {
        Assert.notNull(expensesDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.isNull(expensesDetails.getId(), "Requested Expenses Details Id should be empty");
        ExpensesDetails createdBasicDetails = expensesDetailsRepo.save(expensesDetails);
        Assert.notNull(createdBasicDetails, "Failed to create the Expenses Details Entity");
        return createdBasicDetails.getId();
    }

    @Override
    public ExpensesDetails update(ExpensesDetails expensesDetails) throws ResourceNotFoundException {
        Assert.notNull(expensesDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.notNull(expensesDetails.getId(), ID_NULL_CHECK_ERROR_MESSAGE);
        findById(expensesDetails.getId());
        ExpensesDetails createdExpensesDetails = expensesDetailsRepo.save(expensesDetails);
        Assert.notNull(createdExpensesDetails, "Failed to update the Expenses Details Entity");
        return createdExpensesDetails;
    }

    @Override
    public boolean isDeleted(Long id) {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        expensesDetailsRepo.deleteById(id);
        Assert.isTrue(expensesDetailsRepo.findById(id).isEmpty(), MessageFormat.format("Failed to delete the Expenses Details Entity of id: {0}", id));
        return expensesDetailsRepo.findById(id).isEmpty();
    }

    @Override
    public ExpensesDetails findById(Long id) throws ResourceNotFoundException {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        Optional<ExpensesDetails> retrievedExpensesCategory = expensesDetailsRepo.findById(id);
        CustomAssert.isResourcePresent(retrievedExpensesCategory.isPresent(), MessageFormat.format("Searched Expenses Details Entity is not found of the id: {0}", id));
        return retrievedExpensesCategory.get();
    }

    @Override
    public Page<ExpensesDetails> findAll(int pageNumber, int pageSize, Sort sort) {
        Page<ExpensesDetails> all = expensesDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize, sort));
        validatePageResults(pageNumber, pageSize, sort, all);
        return all;
    }

    public Page<ExpensesDetails> search(String search, int pageNumber, int pageSize, Sort sort) {
        List<SearchCriteria> searchCriteriaList = SearchCriteriaBuilder.buildSearchCriteria(search);
        validateSearchCriteria(searchCriteriaList, ExpensesDetails.class.getDeclaredFields());
        PredicateBuilder predicateBuilder = new PredicateBuilder(searchCriteriaList);
        BooleanExpression exp = predicateBuilder.build(expensesDetailsPathBuilder);
        Page<ExpensesDetails> all = expensesDetailsRepo.findAll(exp, PageRequest.of(pageNumber, pageSize, sort));
        validatePageResults(pageNumber, pageSize, sort, all);
        return all;
    }
}
