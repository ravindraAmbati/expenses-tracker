package learn.myapps.expensestracker.api.basic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BasicDetailsService {

    private final BasicDetailsRepo basicDetailsRepo;

    public BasicDetailsService(BasicDetailsRepo basicDetailsRepo) {
        this.basicDetailsRepo = basicDetailsRepo;
    }

    public Page<BasicDetails> findAll(int pageNumber, int pageSize) {
        return basicDetailsRepo.findAll(PageRequest.of(pageNumber, pageSize));
    }
}
