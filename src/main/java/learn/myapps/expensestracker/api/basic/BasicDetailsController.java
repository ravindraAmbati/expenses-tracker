package learn.myapps.expensestracker.api.basic;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/basic")
@RestController
public class BasicDetailsController {

    private final BasicDetailsService basicDetailsService;

    public BasicDetailsController(BasicDetailsService basicDetailsService) {
        this.basicDetailsService = basicDetailsService;
    }

    @GetMapping("/all")
    public Page<BasicDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                      @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return basicDetailsService.findAll(pageNumber, pageSize);
    }
}
