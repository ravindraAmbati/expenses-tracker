package learn.myapps.expensestracker.api.payment;

import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
import learn.myapps.expensestracker.api.ApiController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/v1/payment")
@RestController
public class PaymentModeDetailsController implements ApiController<PaymentModeDetails> {

    private final PaymentModeDetailsService paymentModeDetailsService;

    public PaymentModeDetailsController(PaymentModeDetailsService paymentModeDetailsService) {
        this.paymentModeDetailsService = paymentModeDetailsService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Long> create(@RequestBody PaymentModeDetails paymentModeDetails) {
        Long id = paymentModeDetailsService.create(paymentModeDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping
    @Override
    public PaymentModeDetails update(@RequestBody PaymentModeDetails paymentModeDetails) {
        try {
            return paymentModeDetailsService.update(paymentModeDetails);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    @Override
    public PaymentModeDetails findById(@PathVariable("id") Long id) {
        try {
            return paymentModeDetailsService.findById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public boolean isDeleted(@PathVariable("id") Long id) {
        return paymentModeDetailsService.isDeleted(id);
    }

    @GetMapping("/all")
    @Override
    public Page<PaymentModeDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                            @RequestParam(name = "sort", defaultValue = "id") String sortBy) {
        return paymentModeDetailsService.findAll(pageNumber, pageSize, Sort.by(sortBy));
    }

    @GetMapping
    @Override
    public Page<PaymentModeDetails> search(@RequestParam(value = "search") String search,
                                           @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(name = "sort", defaultValue = "id") String sortBy) {
        return paymentModeDetailsService.search(search, pageNumber, pageSize, Sort.by(sortBy));
    }
}
