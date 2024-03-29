package learn.myapps.expensestracker.api.currency;

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
public class CurrencyDetailsService implements ApiService<CurrencyDetails> {

    public static final String ID_NULL_CHECK_ERROR_MESSAGE = "Requested Currency Details Id should NOT be empty";
    public static final String ENTITY_NULL_CHECK_ERROR_MESSAGE = "Requested Currency Details should not be empty";

    private final CurrencyDetailsRepo currencyDetailsRepo;

    private final PathBuilder<CurrencyDetails> currencyDetailsPathBuilder = new PathBuilder<>(CurrencyDetails.class, "currencyDetails");

    public CurrencyDetailsService(CurrencyDetailsRepo currencyDetailsRepo) {
        this.currencyDetailsRepo = currencyDetailsRepo;
    }

    @Override
    public Long create(CurrencyDetails currencyDetails) {
        Assert.notNull(currencyDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.isNull(currencyDetails.getId(), "Requested Currency Details Id should be empty");
        CurrencyDetails createdBasicDetails = currencyDetailsRepo.save(currencyDetails);
        Assert.notNull(createdBasicDetails, "Failed to create the Currency Details Entity");
        return createdBasicDetails.getId();
    }

    @Override
    public CurrencyDetails update(CurrencyDetails currencyDetails) throws ResourceNotFoundException {
        Assert.notNull(currencyDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.notNull(currencyDetails.getId(), ID_NULL_CHECK_ERROR_MESSAGE);
        findById(currencyDetails.getId());
        CurrencyDetails createdExpensesCategoryDetails = currencyDetailsRepo.save(currencyDetails);
        Assert.notNull(createdExpensesCategoryDetails, "Failed to update the Currency Details Entity");
        return createdExpensesCategoryDetails;
    }

    @Override
    public boolean isDeleted(Long id) {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        currencyDetailsRepo.deleteById(id);
        Assert.isTrue(currencyDetailsRepo.findById(id).isEmpty(), MessageFormat.format("Failed to delete the Currency Details Entity of id: {0}", id));
        return currencyDetailsRepo.findById(id).isEmpty();
    }

    @Override
    public CurrencyDetails findById(Long id) throws ResourceNotFoundException {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        Optional<CurrencyDetails> retrievedExpensesCategory = currencyDetailsRepo.findById(id);
        CustomAssert.isResourcePresent(retrievedExpensesCategory.isPresent(), MessageFormat.format("Searched Currency Details Entity is not found of the id: {0}", id));
        return retrievedExpensesCategory.get();
    }

    @Override
    public Page<CurrencyDetails> findAll(int pageNumber, int pageSize, Sort sort) {
        Page<CurrencyDetails> all = currencyDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize, sort));
        validatePageResults(pageNumber, pageSize, sort, all);
        return all;
    }

    public Page<CurrencyDetails> search(String search, int pageNumber, int pageSize, Sort sort) {
        List<SearchCriteria> searchCriteriaList = SearchCriteriaBuilder.buildSearchCriteria(search);
        validateSearchCriteria(searchCriteriaList, CurrencyDetails.class.getDeclaredFields());
        PredicateBuilder predicateBuilder = new PredicateBuilder(searchCriteriaList);
        BooleanExpression exp = predicateBuilder.build(currencyDetailsPathBuilder);
        Page<CurrencyDetails> all = currencyDetailsRepo.findAll(exp, PageRequest.of(pageNumber, pageSize, sort));
        validatePageResults(pageNumber, pageSize, sort, all);
        return all;
    }
}
