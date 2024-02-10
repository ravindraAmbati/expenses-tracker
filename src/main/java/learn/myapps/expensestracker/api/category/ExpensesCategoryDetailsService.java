package learn.myapps.expensestracker.api.category;

import learn.myapps.expensestracker.api.ApiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class ExpensesCategoryDetailsService implements ApiService<ExpensesCategoryDetails> {

    public static final String ID_NULL_CHECK_ERROR_MESSAGE = "Requested Expenses Category Details Id should NOT be empty";
    public static final String ENTITY_NULL_CHECK_ERROR_MESSAGE = "Requested Expenses Category Details should not be empty";

    private final ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo;

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
    public ExpensesCategoryDetails update(ExpensesCategoryDetails expensesCategoryDetails) {
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
    public ExpensesCategoryDetails findById(Long id) {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        Optional<ExpensesCategoryDetails> retrievedExpensesCategory = expensesCategoryDetailsRepo.findById(id);
        Assert.isTrue(retrievedExpensesCategory.isPresent(), MessageFormat.format("Searched Expenses Category Details Entity is not found of the id: {0}", id));
        return retrievedExpensesCategory.get();
    }

    @Override
    public Page<ExpensesCategoryDetails> findAll(int pageNumber, int pageSize, Sort sort) {
        Page<ExpensesCategoryDetails> all = expensesCategoryDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize, sort));
        Assert.isTrue(pageNumber == all.getNumber(), MessageFormat.format("Failed to get requested Expenses Category Details Entities from page number: {0}", pageNumber));
        Assert.isTrue(pageSize == all.getSize(), MessageFormat.format("Failed to get requested Expenses Category Details Entities of page size: {0}", pageSize));
        Assert.isTrue(sort.equals(all.getSort()), MessageFormat.format("Failed to get requested Expenses Category Details Entities by sort: {0}", sort));
        return all;
    }
}
