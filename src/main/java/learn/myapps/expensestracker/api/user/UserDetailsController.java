package learn.myapps.expensestracker.api.user;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/user")
@RestController
public class UserDetailsController {

    private final UserDetailsService userDetailsService;

    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/all")
    public Page<UserDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                     @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return userDetailsService.findAll(pageNumber, pageSize);
    }
}
