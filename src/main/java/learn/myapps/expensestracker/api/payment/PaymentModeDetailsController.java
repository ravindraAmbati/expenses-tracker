package learn.myapps.expensestracker.api.payment;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/payment")
@RestController
public class PaymentModeDetailsController {

    private final PaymentModeDetailsService paymentModeDetailsService;

    public PaymentModeDetailsController(PaymentModeDetailsService paymentModeDetailsService) {
        this.paymentModeDetailsService = paymentModeDetailsService;
    }

    @GetMapping("/all")
    public Page<PaymentModeDetails> findAll(@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return paymentModeDetailsService.findAll(pageNumber, pageSize);
    }
}
