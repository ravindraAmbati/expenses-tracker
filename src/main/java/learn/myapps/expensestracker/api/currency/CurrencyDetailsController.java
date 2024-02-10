package learn.myapps.expensestracker.api.currency;

import learn.myapps.expensestracker.api.ApiController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return currencyDetailsService.update(expensesCategoryDetails);
    }

    @GetMapping(value = "/{id}")
    @Override
    public CurrencyDetails findById(@PathVariable("id") Long id) {
        return currencyDetailsService.findById(id);
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
}
