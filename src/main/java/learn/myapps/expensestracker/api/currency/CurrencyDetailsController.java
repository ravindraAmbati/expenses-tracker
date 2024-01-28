package learn.myapps.expensestracker.api.currency;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/model")
@RestController
public class CurrencyDetailsController {

    private final CurrencyDetailsService currencyDetailsService;

    public CurrencyDetailsController(CurrencyDetailsService currencyDetailsService) {
        this.currencyDetailsService = currencyDetailsService;
    }

    @GetMapping("/all")
    public Page<CurrencyDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                         @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return currencyDetailsService.findAll(pageNumber, pageSize);
    }
}
