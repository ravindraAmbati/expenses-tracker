package learn.myapps.expensestracker.api.payment;

import learn.myapps.expensestracker.api.ApiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class PaymentModeDetailsService implements ApiService<PaymentModeDetails> {

    public static final String ID_NULL_CHECK_ERROR_MESSAGE = "Requested Payment Mode Details Id should NOT be empty";
    public static final String ENTITY_NULL_CHECK_ERROR_MESSAGE = "Requested Payment Mode Details should not be empty";

    private final PaymentModeDetailsRepo paymentModeDetailsRepo;

    public PaymentModeDetailsService(PaymentModeDetailsRepo paymentModeDetailsRepo) {
        this.paymentModeDetailsRepo = paymentModeDetailsRepo;
    }

    @Override
    public Long create(PaymentModeDetails paymentModeDetails) {
        Assert.notNull(paymentModeDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.isNull(paymentModeDetails.getId(), "Requested Payment Mode Details Id should be empty");
        PaymentModeDetails createdBasicDetails = paymentModeDetailsRepo.save(paymentModeDetails);
        Assert.notNull(createdBasicDetails, "Failed to create the Payment Mode Details Entity");
        return createdBasicDetails.getId();
    }

    @Override
    public PaymentModeDetails update(PaymentModeDetails paymentModeDetails) {
        Assert.notNull(paymentModeDetails, ENTITY_NULL_CHECK_ERROR_MESSAGE);
        Assert.notNull(paymentModeDetails.getId(), ID_NULL_CHECK_ERROR_MESSAGE);
        findById(paymentModeDetails.getId());
        PaymentModeDetails createdExpensesCategoryDetails = paymentModeDetailsRepo.save(paymentModeDetails);
        Assert.notNull(createdExpensesCategoryDetails, "Failed to update the Payment Mode Details Entity");
        return createdExpensesCategoryDetails;
    }

    @Override
    public boolean isDeleted(Long id) {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        paymentModeDetailsRepo.deleteById(id);
        Assert.isTrue(paymentModeDetailsRepo.findById(id).isEmpty(), MessageFormat.format("Failed to delete the Payment Mode Details Entity of id: {0}", id));
        return paymentModeDetailsRepo.findById(id).isEmpty();
    }

    @Override
    public PaymentModeDetails findById(Long id) {
        Assert.notNull(id, ID_NULL_CHECK_ERROR_MESSAGE);
        Optional<PaymentModeDetails> retrievedExpensesCategory = paymentModeDetailsRepo.findById(id);
        Assert.isTrue(retrievedExpensesCategory.isPresent(), MessageFormat.format("Searched Payment Mode Details Entity is not found of the id: {0}", id));
        return retrievedExpensesCategory.get();
    }

    @Override
    public Page<PaymentModeDetails> findAll(int pageNumber, int pageSize, Sort sort) {
        Page<PaymentModeDetails> all = paymentModeDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize, sort));
        Assert.isTrue(pageNumber == all.getNumber(), MessageFormat.format("Failed to get requested Payment Mode Details Entities from page number: {0}", pageNumber));
        Assert.isTrue(pageSize == all.getSize(), MessageFormat.format("Failed to get requested Payment Mode Details Entities of page size: {0}", pageSize));
        Assert.isTrue(sort.equals(all.getSort()), MessageFormat.format("Failed to get requested Payment Mode Details Entities by sort: {0}", sort));
        return all;
    }
}
