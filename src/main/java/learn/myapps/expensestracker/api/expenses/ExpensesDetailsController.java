package learn.myapps.expensestracker.api.expenses;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/expenses")
@RestController
public class ExpensesDetailsController {

    private final ExpensesDetailsService expensesDetailsService;

    public ExpensesDetailsController(ExpensesDetailsService expensesDetailsService) {
        this.expensesDetailsService = expensesDetailsService;
    }

    @GetMapping("/all")
    public Page<ExpensesDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return expensesDetailsService.findAll(pageNumber, pageSize);
    }
}
