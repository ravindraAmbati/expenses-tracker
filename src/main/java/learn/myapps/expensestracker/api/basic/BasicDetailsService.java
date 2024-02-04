package learn.myapps.expensestracker.api.basic;

import learn.myapps.expensestracker.api.ApiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class BasicDetailsService implements ApiService<BasicDetails> {

    private final BasicDetailsRepo basicDetailsRepo;

    public BasicDetailsService(BasicDetailsRepo basicDetailsRepo) {
        this.basicDetailsRepo = basicDetailsRepo;
    }

    @Override
    public Long create(BasicDetails basicDetails) {
        BasicDetails createdBasicDetails = basicDetailsRepo.save(basicDetails);
        Assert.notNull(createdBasicDetails, "Failed to create the basic details entity");
        return createdBasicDetails.getBasicId();
    }

    @Override
    public BasicDetails update(BasicDetails basicDetails) {
        BasicDetails createdBasicDetails = basicDetailsRepo.save(basicDetails);
        Assert.notNull(createdBasicDetails, "Failed to update the basic details entity");
        return createdBasicDetails;
    }

    @Override
    public boolean isDeleted(Long id) {
        basicDetailsRepo.deleteById(id);
        Assert.isTrue(basicDetailsRepo.findById(id).isEmpty(), MessageFormat.format("Failed to delete the basic details entity of id: {0}", id));
        return basicDetailsRepo.findById(id).isEmpty();
    }

    @Override
    public BasicDetails findById(Long id) {
        Optional<BasicDetails> retrievedBasicDetails = basicDetailsRepo.findById(id);
        Assert.isTrue(retrievedBasicDetails.isPresent(), MessageFormat.format("Searched basic details entity is not found of the id: {0}", id));
        return retrievedBasicDetails.get();
    }

    @Override
    public Page<BasicDetails> findAll(int pageNumber, int pageSize, Sort sort) {
        Page<BasicDetails> all = basicDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize, sort));
        Assert.isTrue(pageNumber == all.getNumber(), MessageFormat.format("Failed to get requested basic details entities from page number: {0}", pageNumber));
        Assert.isTrue(pageSize == all.getSize(), MessageFormat.format("Failed to get requested basic details entities of page size: {0}", pageSize));
        Assert.isTrue(sort.equals(all.getSort()), MessageFormat.format("Failed to get requested basic details entities by sort: {0}", sort));
        return all;
    }
}
