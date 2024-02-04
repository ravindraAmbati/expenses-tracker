package learn.myapps.expensestracker.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface ApiService<T> {

    Long create(T t);

    T update(T t);

    boolean isDeleted(Long id);

    T findById(Long id);

    Page<T> findAll(int page, int size, Sort sort);

}
