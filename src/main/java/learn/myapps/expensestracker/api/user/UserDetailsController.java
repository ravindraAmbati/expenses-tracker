package learn.myapps.expensestracker.api.user;

import learn.myapps.expensestracker.api.ApiController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/user")
@RestController
public class UserDetailsController implements ApiController<UserDetails> {

    private final UserDetailsService userDetailsService;

    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Long> create(@RequestBody UserDetails userDetails) {
        Long id = userDetailsService.create(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping
    @Override
    public UserDetails update(@RequestBody UserDetails userDetails) {
        return userDetailsService.update(userDetails);
    }

    @GetMapping(value = "/{id}")
    @Override
    public UserDetails findById(@PathVariable("id") Long id) {
        return userDetailsService.findById(id);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public boolean isDeleted(@PathVariable("id") Long id) {
        return userDetailsService.isDeleted(id);
    }

    @GetMapping("/all")
    @Override
    public Page<UserDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                     @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                     @RequestParam(name = "sort", defaultValue = "id") String sortBy) {
        return userDetailsService.findAll(pageNumber, pageSize, Sort.by(sortBy));
    }
}
