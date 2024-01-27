package learn.myapps.expensestracker.api;

import org.springframework.stereotype.Repository;

@Repository
public interface ApiModelRepo<T> {

    T getModel();
}
