package learn.myapps.expensestracker.api.basic;

import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
import learn.myapps.expensestracker.api.ApiController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping("/api/v1/basic")
@RestController
public class BasicDetailsController implements ApiController<BasicDetails> {

    private final BasicDetailsService basicDetailsService;

    public BasicDetailsController(BasicDetailsService basicDetailsService) {
        this.basicDetailsService = basicDetailsService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Long> create(@RequestBody BasicDetails basicDetails) {
        Long id = basicDetailsService.create(basicDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping
    @Override
    public BasicDetails update(@RequestBody BasicDetails basicDetails) {
        try {
            return basicDetailsService.update(basicDetails);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping(value = "/{id}")
    @Override
    public BasicDetails findById(@PathVariable("id") Long id) {
        try {
            return basicDetailsService.findById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public boolean isDeleted(@PathVariable("id") Long id) {
        return basicDetailsService.isDeleted(id);
    }

    @GetMapping("/all")
    @Override
    public Page<BasicDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                      @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                      @RequestParam(name = "sort", defaultValue = "basicId") String sortBy) {
        return basicDetailsService.findAll(pageNumber, pageSize, Sort.by(sortBy));
    }

    @GetMapping
    public Page<BasicDetails> search(@RequestParam(value = "search") String search,
                                     @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                     @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(name = "sort", defaultValue = "basicId") String sortBy) {
        return basicDetailsService.search(search, pageNumber, pageSize, Sort.by(sortBy));
    }
}
