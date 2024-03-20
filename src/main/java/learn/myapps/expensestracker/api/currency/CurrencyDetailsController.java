package learn.myapps.expensestracker.api.currency;

import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
import learn.myapps.expensestracker.api.ApiController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/v1/currency")
@RestController
public class CurrencyDetailsController implements ApiController<CurrencyDetails> {

    private final CurrencyDetailsService currencyDetailsService;

    public CurrencyDetailsController(CurrencyDetailsService currencyDetailsService) {
        this.currencyDetailsService = currencyDetailsService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Long> create(@RequestBody CurrencyDetails expensesCategoryDetails) {
        Long id = currencyDetailsService.create(expensesCategoryDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping
    @Override
    public CurrencyDetails update(@RequestBody CurrencyDetails expensesCategoryDetails) {
        try {
            return currencyDetailsService.update(expensesCategoryDetails);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    @Override
    public CurrencyDetails findById(@PathVariable("id") Long id) {
        try {
            return currencyDetailsService.findById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public boolean isDeleted(@PathVariable("id") Long id) {
        return currencyDetailsService.isDeleted(id);
    }

    @GetMapping("/all")
    @Override
    public Page<CurrencyDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(name = "sort", defaultValue = "id") String sortBy) {
        return currencyDetailsService.findAll(pageNumber, pageSize, Sort.by(sortBy));
    }

    @GetMapping
    @Override
    public Page<CurrencyDetails> search(@RequestParam(value = "search") String search,
                                        @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                        @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                        @RequestParam(name = "sort", defaultValue = "id") String sortBy) {
        return currencyDetailsService.search(search, pageNumber, pageSize, Sort.by(sortBy));
    }
}
