package learn.myapps.expensestracker.api.category;

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
public class ExpensesCategoryDetailsService implements ApiService<ExpensesCategoryDetails> {

    public static final String ID_NULL_CHECK_ERROR_MESSAGE = "Requested Expenses Category Details Id should NOT be empty";
    public static final String ENTITY_NULL_CHECK_ERROR_MESSAGE = "Requested Expenses Category Details should not be empty";

    private final ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo;

    private final PathBuilder<ExpensesCategoryDetails> expensesCategoryDetailsPathBuilder = new PathBuilder<>(ExpensesCategoryDetails.class, "expensesCategoryDetails");

    public ExpensesCategoryDetailsService(ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo) {
        this.expensesCategoryDetailsRepo = expensesCategoryDetailsRepo;
    }

    @Override
    public Long create(ExpensesCategoryDetails expensesCategoryDetails) {
        Assert.notNull(expensesCategoryDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.isNull(expensesCategoryDetails.getId(), "Requested Expenses Category Details Id should be empty");
        ExpensesCategoryDetails createdBasicDetails = expensesCategoryDetailsRepo.save(expensesCategoryDetails);
        Assert.notNull(createdBasicDetails, "Failed to create the Expenses Category Details Entity");
        return createdBasicDetails.getId();
    }

    @Override
    public ExpensesCategoryDetails update(ExpensesCategoryDetails expensesCategoryDetails) throws ResourceNotFoundException {
        Assert.notNull(expensesCategoryDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.notNull(expensesCategoryDetails.getId(), ID_NULL_CHECK_ERROR_MESSAGE);
        findById(expensesCategoryDetails.getId());
        ExpensesCategoryDetails createdExpensesCategoryDetails = expensesCategoryDetailsRepo.save(expensesCategoryDetails);
        Assert.notNull(createdExpensesCategoryDetails, "Failed to update the Expenses Category Details Entity");
        return createdExpensesCategoryDetails;
    }

    @Override
    public boolean isDeleted(Long id) {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        expensesCategoryDetailsRepo.deleteById(id);
        Assert.isTrue(expensesCategoryDetailsRepo.findById(id).isEmpty(), MessageFormat.format("Failed to delete the Expenses Category Details Entity of id: {0}", id));
        return expensesCategoryDetailsRepo.findById(id).isEmpty();
    }

    @Override
    public ExpensesCategoryDetails findById(Long id) throws ResourceNotFoundException {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        Optional<ExpensesCategoryDetails> retrievedExpensesCategory = expensesCategoryDetailsRepo.findById(id);
        CustomAssert.isResourcePresent(retrievedExpensesCategory.isPresent(), MessageFormat.format("Searched Expenses Category Details Entity is not found of the id: {0}", id));
        return retrievedExpensesCategory.get();
    }

    @Override
    public Page<ExpensesCategoryDetails> findAll(int pageNumber, int pageSize, Sort sort) {
        Page<ExpensesCategoryDetails> all = expensesCategoryDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize, sort));
        validatePageResults(pageNumber, pageSize, sort, all);
        return all;
    }

    public Page<ExpensesCategoryDetails> search(String search, int pageNumber, int pageSize, Sort sort) {
        List<SearchCriteria> searchCriteriaList = SearchCriteriaBuilder.buildSearchCriteria(search);
        validateSearchCriteria(searchCriteriaList, ExpensesCategoryDetails.class.getDeclaredFields());
        PredicateBuilder predicateBuilder = new PredicateBuilder(searchCriteriaList);
        BooleanExpression exp = predicateBuilder.build(expensesCategoryDetailsPathBuilder);
        Page<ExpensesCategoryDetails> all = expensesCategoryDetailsRepo.findAll(exp, PageRequest.of(pageNumber, pageSize, sort));
        validatePageResults(pageNumber, pageSize, sort, all);
        return all;
    }
}
