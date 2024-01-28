package learn.myapps.expensestracker.api.category;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/model")
@RestController
public class ExpensesCategoryDetailsController {

    private final ExpensesCategoryDetailsService expensesCategoryDetailsService;

    public ExpensesCategoryDetailsController(ExpensesCategoryDetailsService expensesCategoryDetailsService) {
        this.expensesCategoryDetailsService = expensesCategoryDetailsService;
    }

    @GetMapping("/all")
    public Page<ExpensesCategoryDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                 @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return expensesCategoryDetailsService.findAll(pageNumber, pageSize);
    }
}
