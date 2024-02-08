package learn.myapps.expensestracker;

import learn.myapps.expensestracker.api.basic.BasicDetailsController;
import learn.myapps.expensestracker.api.basic.BasicDetailsRepo;
import learn.myapps.expensestracker.api.basic.BasicDetailsService;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetailsController;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetailsRepo;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetailsService;
import learn.myapps.expensestracker.api.currency.CurrencyDetailsController;
import learn.myapps.expensestracker.api.currency.CurrencyDetailsRepo;
import learn.myapps.expensestracker.api.currency.CurrencyDetailsService;
import learn.myapps.expensestracker.api.expenses.ExpensesDetailsController;
import learn.myapps.expensestracker.api.expenses.ExpensesDetailsRepo;
import learn.myapps.expensestracker.api.expenses.ExpensesDetailsService;
import learn.myapps.expensestracker.api.payment.PaymentModeDetailsController;
import learn.myapps.expensestracker.api.payment.PaymentModeDetailsRepo;
import learn.myapps.expensestracker.api.payment.PaymentModeDetailsService;
import learn.myapps.expensestracker.api.user.UserDetailsController;
import learn.myapps.expensestracker.api.user.UserDetailsRepo;
import learn.myapps.expensestracker.api.user.UserDetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ExpensesTrackerApplicationTests {

    @Autowired
    private BasicDetailsRepo basicDetailsRepo;
    @Autowired
    private BasicDetailsService basicDetailsService;
    @Autowired
    private BasicDetailsController basicDetailsController;

    @Autowired
    private ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo;
    @Autowired
    private ExpensesCategoryDetailsService expensesCategoryDetailsService;
    @Autowired
    private ExpensesCategoryDetailsController expensesCategoryDetailsController;

    @Autowired
    private CurrencyDetailsRepo currencyDetailsRepo;
    @Autowired
    private CurrencyDetailsService currencyDetailsService;
    @Autowired
    private CurrencyDetailsController currencyDetailsController;

    @Autowired
    private PaymentModeDetailsRepo paymentModeDetailsRepo;
    @Autowired
    private PaymentModeDetailsService paymentModeDetailsService;
    @Autowired
    private PaymentModeDetailsController paymentModeDetailsController;

    @Autowired
    private UserDetailsRepo userDetailsRepo;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDetailsController userDetailsController;

    @Autowired
    private ExpensesDetailsRepo expensesDetailsRepo;
    @Autowired
    private ExpensesDetailsService expensesDetailsService;
    @Autowired
    private ExpensesDetailsController expensesDetailsController;


    @Test
	void contextLoads() {
        Assertions.assertNotNull(basicDetailsRepo);
        Assertions.assertNotNull(basicDetailsService);
        Assertions.assertNotNull(basicDetailsController);

        Assertions.assertNotNull(expensesCategoryDetailsRepo);
        Assertions.assertNotNull(expensesCategoryDetailsService);
        Assertions.assertNotNull(expensesCategoryDetailsController);

        Assertions.assertNotNull(currencyDetailsRepo);
        Assertions.assertNotNull(currencyDetailsService);
        Assertions.assertNotNull(currencyDetailsService);

        Assertions.assertNotNull(paymentModeDetailsRepo);
        Assertions.assertNotNull(paymentModeDetailsService);
        Assertions.assertNotNull(paymentModeDetailsController);

        Assertions.assertNotNull(userDetailsRepo);
        Assertions.assertNotNull(userDetailsService);
        Assertions.assertNotNull(userDetailsController);

        Assertions.assertNotNull(expensesDetailsRepo);
        Assertions.assertNotNull(expensesDetailsService);
        Assertions.assertNotNull(expensesDetailsController);
	}

}
