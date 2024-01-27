package learn.myapps.expensestracker.api;

import learn.myapps.expensestracker.api.repo.*;
import learn.myapps.expensestracker.model.*;
import org.springframework.stereotype.Service;

@Service
public class ApiModelService<T> {

    private final CurrencyDetailsRepo currencyDetailsRepo;
    private final ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo;
    private final ExpensesDetailsRepo expensesDetailsRepo;
    private final PaymentModeDetailsRepo paymentModeDetailsRepo;
    private final UserDetailsRepo userDetailsRepo;

    public ApiModelService(CurrencyDetailsRepo currencyDetailsRepo, ExpensesCategoryDetailsRepo expensesCategoryDetailsRepo, ExpensesDetailsRepo expensesDetailsRepo, PaymentModeDetailsRepo paymentModeDetailsRepo, UserDetailsRepo userDetailsRepo) {
        this.currencyDetailsRepo = currencyDetailsRepo;
        this.expensesCategoryDetailsRepo = expensesCategoryDetailsRepo;
        this.expensesDetailsRepo = expensesDetailsRepo;
        this.paymentModeDetailsRepo = paymentModeDetailsRepo;
        this.userDetailsRepo = userDetailsRepo;
    }

    public T getModel(ApiRequestBody apiRequestBody) throws ClassNotFoundException {
        validateApiRequest(apiRequestBody);
        ApiModelRepo<T> modelRepo = getModelRepo();
        return modelRepo.getModel();
    }

    private ApiModelRepo<T> getModelRepo() throws ClassNotFoundException {
        Class<?> aClass = getTypeParameter();
        if (null != aClass) {
            if (aClass.equals(CurrencyDetails.class)) {
                return (ApiModelRepo<T>) currencyDetailsRepo;
            } else if (aClass.equals(ExpensesCategoryDetails.class)) {
                return (ApiModelRepo<T>) expensesCategoryDetailsRepo;
            } else if (aClass.equals(ExpensesDetails.class)) {
                return (ApiModelRepo<T>) expensesDetailsRepo;
            } else if (aClass.equals(PaymentModeDetails.class)) {
                return (ApiModelRepo<T>) paymentModeDetailsRepo;
            } else if (aClass.equals(UserDetails.class)) {
                return (ApiModelRepo<T>) userDetailsRepo;
            }
        }
        throw new ClassNotFoundException();
    }

    private void validateApiRequest(ApiRequestBody apiRequestBody) {
        //throw IllegalArgumentException
        //enable disable model query for specific models thru configuration
    }

    Class<?> getTypeParameter() {
        return this.getClass().getTypeParameters()[0].getClass();
    }
}
