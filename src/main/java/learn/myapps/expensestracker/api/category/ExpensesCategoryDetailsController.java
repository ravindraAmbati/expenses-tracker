package learn.myapps.expensestracker.api.category;

import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
import learn.myapps.expensestracker.api.ApiController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/v1/category")
@RestController
public class ExpensesCategoryDetailsController implements ApiController<ExpensesCategoryDetails> {

    private final ExpensesCategoryDetailsService expensesCategoryDetailsService;

    public ExpensesCategoryDetailsController(ExpensesCategoryDetailsService expensesCategoryDetailsService) {
        this.expensesCategoryDetailsService = expensesCategoryDetailsService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Long> create(@RequestBody ExpensesCategoryDetails expensesCategoryDetails) {
        Long id = expensesCategoryDetailsService.create(expensesCategoryDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping
    @Override
    public ExpensesCategoryDetails update(@RequestBody ExpensesCategoryDetails expensesCategoryDetails) {
        try {
            return expensesCategoryDetailsService.update(expensesCategoryDetails);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    @Override
    public ExpensesCategoryDetails findById(@PathVariable("id") Long id) {
        try {
            return expensesCategoryDetailsService.findById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public boolean isDeleted(@PathVariable("id") Long id) {
        return expensesCategoryDetailsService.isDeleted(id);
    }

    @GetMapping("/all")
    @Override
    public Page<ExpensesCategoryDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                 @RequestParam(name = "sort", defaultValue = "id") String sortBy) {
        return expensesCategoryDetailsService.findAll(pageNumber, pageSize, Sort.by(sortBy));
    }

    @GetMapping
    @Override
    public Page<ExpensesCategoryDetails> search(@RequestParam(value = "search") String search,
                                                @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                                @RequestParam(name = "sort", defaultValue = "id") String sortBy) {
        return expensesCategoryDetailsService.search(search, pageNumber, pageSize, Sort.by(sortBy));
    }
}
