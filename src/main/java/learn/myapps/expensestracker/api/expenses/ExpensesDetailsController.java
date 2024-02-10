package learn.myapps.expensestracker.api.expenses;

import learn.myapps.expensestracker.api.ApiController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/expenses")
@RestController
public class ExpensesDetailsController implements ApiController<ExpensesDetails> {

    private final ExpensesDetailsService expensesDetailsService;

    public ExpensesDetailsController(ExpensesDetailsService expensesDetailsService) {
        this.expensesDetailsService = expensesDetailsService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Long> create(@RequestBody ExpensesDetails expensesDetails) {
        Long id = expensesDetailsService.create(expensesDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping
    @Override
    public ExpensesDetails update(@RequestBody ExpensesDetails expensesDetails) {
        return expensesDetailsService.update(expensesDetails);
    }

    @GetMapping(value = "/{id}")
    @Override
    public ExpensesDetails findById(@PathVariable("id") Long id) {
        return expensesDetailsService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public boolean isDeleted(@PathVariable("id") Long id) {
        return expensesDetailsService.isDeleted(id);
    }

    @GetMapping("/all")
    @Override
    public Page<ExpensesDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(name = "sort", defaultValue = "id") String sortBy) {
        return expensesDetailsService.findAll(pageNumber, pageSize, Sort.by(sortBy));
    }
}
